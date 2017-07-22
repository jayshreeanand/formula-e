module V1
  class DriversEndpoint < BaseEndpoint
    resource :drivers do
      desc 'Get drivers'
      get '/' do
        Driver.all
      end
    end
  end
end
