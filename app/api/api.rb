class API < Grape::API
  include ApiErrorHandler

  prefix 'api'
  format :json
  formatter :json, Grape::Formatter::ActiveModelSerializers
  default_format :json

  helpers do
    # Auth
    def authenticate!
      unauthorized! if !current_user
    end

    def current_user
      @current_user
    end

    def authorize!(*args)
      ::Ability.new(current_user).authorize!(*args)
    end

    # Errors
    def unauthorized!
      error! 'Unauthorized', 401
    end

    def access_token_expired!
      error! 'Access Token has expired.', 401
    end

    def clean_params
      ActionController::Parameters.new(params)
    end

    def respond_with(response)
      if response.try(:errors).present?
        error!({ errors: response.errors }, 422)
      else
        response
      end
    end
  end

  before do
    access_token = request.headers['X-Access-Token']

    if !access_token.blank?
      authentication_token = AuthenticationToken.find_by_access_token(access_token)

      unauthorized! if !authentication_token
      access_token_expired! if authentication_token.expired?

      @current_user = authentication_token.user
    end
  end

  mount V1::Root
end
