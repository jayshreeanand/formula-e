require 'nokogiri'
require 'open-uri'

class TeamParser
  def self.run
    # doc = Nokogiri::HTML(open('https://s3.amazonaws.com/visualize-staging/uploads/static/teams-and-drivers.html'))
    base_url = "http://localhost:3005"

    doc = Nokogiri::HTML(open(URI.join(base_url, 'teams-and-drivers.html').to_s))

    results = []
    doc.css('.m-teams-hub__wrap .b-card-item').each do |team|
      result = {}
      result[:flag] = URI.join(base_url, team.css('.s-flag img').attribute('src').value).to_s
      result[:logo] = URI.join(base_url, team.css('.s-text__top img').attribute('src').value).to_s
      result[:display_picture] = URI.join(base_url, team.css('.s-media img').attribute('src').value).to_s
      result[:name] = team.css('.s-team-name').text
      result[:description] = team.css('.s-short-biografy p').text
      statistics = {}


      statistics[:wins] = team.css('.js-team-details .s-stats .s-wins').text.strip.split("\n")[0]
      statistics[:podiums] = team.css('.js-team-details .s-stats .s-wins').text.strip.split("\n")[0]
      team.css('.js-team-details .s-stats .s-item').each do |stat|
        statistics[stat.children[1].text.parameterize] = stat.children[3].text
      end
      result[:statistics] = statistics
      results << result
    end

    Team.all.destroy_all
    results.each do |team_attributes|
      team = Team.new
      team.remote_flag_url = team_attributes[:flag]
      team.remote_logo_url = team_attributes[:logo]
      team.remote_display_picture_url = team_attributes[:display_picture]
      team.name = team_attributes[:name]
      team.description = team_attributes[:description]
      team.statistics = team_attributes[:statistics]
      team.save!
    end
  end
end
