module V1
  class Root < Grape::API
    version 'v1', using: :path

    # Unauthenticated or Semi-authenticated Endpoints
    mount V1::AuthenticationTokensEndpoint
    mount V1::UsersEndpoint
    mount V1::PlacesEndpoint

    # Authenticated Endpoints
    group do
      before do
        authenticate!
      end
      mount V1::CurrentUserEndpoint
    end
  end
end
