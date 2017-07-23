class User < ApplicationRecord
  devise :database_authenticatable, :recoverable, :rememberable, :trackable, :validatable

  has_many :authentication_tokens, dependent: :destroy

  def display_name
    email
  end
end
