class AuthenticationTokenSerializer < BaseSerializer
  attributes :access_token, :expires_at, :expires_at

  has_one :user
end
