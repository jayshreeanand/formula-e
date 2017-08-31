module V1
  class WeatherEndpoint < BaseEndpoint
    resource :weather do
      desc 'Get weather of location'
      params do
        requires :latitude, type: BigDecimal
        requires :longitude, type: BigDecimal
      end
      get '/' do
        coordinates = "#{params[:latitude]},#{params[:longitude]}"
        byebug
        barometer = Barometer.new(coordinates)
        weather = barometer.measure

        { current_weather: weather.current.temperature, forecast_weather: weather.forecast.temperature }
      end
    end
  end
end
