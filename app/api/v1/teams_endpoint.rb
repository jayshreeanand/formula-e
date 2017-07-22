module V1
  class TeamsEndpoint < BaseEndpoint
    resource :teams do
      desc 'Get teams'
      get '/' do
        Team.all
      end
    end
  end
end
