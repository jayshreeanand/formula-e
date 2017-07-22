require 'nokogiri'
require 'open-uri'

class TeamParser
  def self.run
    doc = Nokogiri::HTML(open('http://www.fiaformulae.com/en/championship/teams-and-drivers'))
    result = []
    puts "### Search for nodes by css"
    doc.css('.m-teams-hub__wrap .b-card-item').each do |team|
      result[:flag] = team.css('.s-flag img').attribute('src').value
      result[:logo] = team.css('.s-text__top img').attribute('src').value
      result[:display_picture] = team.css('.s-media img').attribute('src').value
      result[:name] = team.css('.s-team-name').text
      result[:description] = team.css('.s-short-biografy p').text
      statistics = {}
      # statistics[:wins] = team.css('.js-team-details .s-stats .s-wins').text
      # statistics[:podiums] = 

      # team.css('.js-team-details .s-stats .s-item')
    end
  end
end
