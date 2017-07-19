module V1
  class PlacesEndpoint < BaseEndpoint
    resource :places do
      desc 'Get places'
      get '/' do
        Place.all
      end
    end
  end
end
