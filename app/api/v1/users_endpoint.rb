module V1
  class UsersEndpoint < BaseEndpoint
    resource :users do

      params do
        requires :id, regexp: /[0-9]+/
      end
      get '/:id' do
        User.find(params[:id])
      end

      desc 'Update user.'
      params do
        requires :user, type: Hash
      end
      put '/:id' do
        user = User.find(params[:id])
        authorize! :manage, user

        user.update_attributes(UserParameters.new(clean_params).permit)
        respond_with user
      end

      desc 'Resend email verification instructions'
      params do
        requires :email
      end
      post '/resend_verification_email' do
        status 200

        user = User.find_by_email!(params[:email])
        user.resend_confirmation_instructions
      end

      desc 'Send forgot password instructions'
      params do
        requires :email
      end
      post '/forgot_password' do
        status 200

        user = User.find_by_email!(params[:email])
        user.send_reset_password_instructions
      end
    end
  end
end
