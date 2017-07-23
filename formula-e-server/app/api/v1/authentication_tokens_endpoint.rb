module V1
  class AuthenticationTokensEndpoint < Grape::API
    resource :authentication_tokens do
      desc 'Create a token.'
      params do
        requires :email, type: String
        requires :password, type: String
      end
      post '/' do
        user = User.find_for_authentication(email: params[:email])
        
        if user && user.valid_password?(params[:password])
          user.authentication_tokens.create
        else
          unauthorized!
        end
      end

      desc 'Revoke all tokens.'
      delete '/' do
        authenticate!
        current_user.authentication_tokens.destroy_all
      end

      group '/:id' do
        before do
          authenticate!
          @authentication_token = AuthenticationToken.find(params[:id])
          authorize! :manage, @authentication_token
        end

        desc 'Extend the expiration time of a token'
        post '/refresh' do
          @authentication_token.refresh!
          @authentication_token
        end

        desc 'Revoke a token.'
        delete '/' do
          @authentication_token.destroy
        end
      end
    end
  end
end
