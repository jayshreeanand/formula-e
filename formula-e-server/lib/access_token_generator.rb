class AccessTokenGenerator
  def self.encode(id, digest)
    Base64.strict_encode64("#{id}:#{digest}")
  end

  def self.decode(access_token)
    decoded_access_token = Base64.decode64(access_token || '')
    (decoded_access_token || '').split(':')
  end
end
