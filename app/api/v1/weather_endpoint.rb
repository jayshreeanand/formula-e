module V1
  class WeatherEndpoint < BaseEndpoint
    resource :places do
      desc 'Get weather of location'
      params do
        requires :latitude, type: Decimal
        requires :longitude, type: Decimal
      end
      get '/' do
        
      end
    end
  end
end
