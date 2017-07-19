module V1
  class Root < Grape::API
    version 'v1', using: :path

    # Unauthenticated or Semi-authenticated Endpoints
    

    # Authenticated Endpoints
    group do
      before do
        authenticate!
      end
    end
  end
end
