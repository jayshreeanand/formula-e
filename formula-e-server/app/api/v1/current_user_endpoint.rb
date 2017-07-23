module V1
  class CurrentUserEndpoint < BaseEndpoint
    resource :current_user do
      desc 'Find current user.'
      get '/' do
        current_user
      end

      desc 'Update current user.'
      params do
        requires :user, type: Hash
      end
      put '/' do
        current_user.update_attributes(UserParameters.new(clean_params).permit)
        respond_with current_user
      end
    end
  end
end
