module V1
  class TeamsEndpoint < BaseEndpoint
    resource :teams do
      desc 'Get teams'
      get '/' do
        Team.all
      end

      desc 'Get team with id'
      get '/:id' do
        Team.find(params[:id])
      end
    end
  end
end
