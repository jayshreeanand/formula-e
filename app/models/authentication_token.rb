class AuthenticationToken < ApplicationRecord
  EXPIRY_DURATION = 30.days

  belongs_to :user

  validates :digest, presence: true
  validates :expires_at, presence: true
  validates :user, presence: true

  before_validation :set_digest
  before_validation :set_expires_at

  def self.generate_digest
    loop do
      digest = SecureRandom.base64
      break digest if !exists?(digest: digest)
    end
  end

  def self.find_by_access_token(access_token)
    id, digest = AccessTokenGenerator.decode(access_token)
    authentication_token = find_by(id: id)

    if authentication_token && Devise.secure_compare(authentication_token.digest, digest)
      authentication_token
    end
  end

  def access_token
    AccessTokenGenerator.encode(id, digest)
  end

  def refresh!
    self.digest = self.class.generate_digest
    self.expires_at = new_expiry_date
    save!
  end

  def expired?
    DateTime.now >= expires_at
  end

  private

  def set_digest
    self.digest ||= self.class.generate_digest
  end

  def set_expires_at
    self.expires_at ||= new_expiry_date
  end

  def new_expiry_date
    DateTime.now + EXPIRY_DURATION
  end
end
