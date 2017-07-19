module V1
  class EventsEndpoint < BaseEndpoint
    resource :events do
      desc 'Get events'
      get '/' do
        Event.all
      end
    end
  end
end
