require 'nokogiri'
require 'open-uri'
require 'watir'

class TeamParser
  def self.run
    # doc = Nokogiri::HTML(open('https://s3.amazonaws.com/visualize-staging/uploads/static/teams-and-drivers.html'))
    base_url = "http://localhost:3005"
    browser = Watir::Browser.new
    doc = Nokogiri::HTML(open(URI.join(base_url, 'teams-and-drivers.html').to_s))

    Team.all.destroy_all
    Driver.all.destroy_all

    doc.css('.m-teams-hub__wrap .b-card-item').each do |team|
      result = {}
      result[:flag] = URI.join(base_url, team.css('.s-flag img').attribute('src').value).to_s
      result[:logo] = URI.join(base_url, team.css('.s-text__top img').attribute('src').value).to_s
      result[:display_picture] = URI.join(base_url, team.css('.s-media img').attribute('src').value).to_s
      result[:name] = team.css('.s-team-name').text
      result[:description] = team.css('.s-short-biografy p').text
      statistics = {}


      statistics[:wins] = team.css('.js-team-details .s-stats .s-wins').text.strip.split("\n")[0]
      statistics[:podiums] = team.css('.js-team-details .s-stats .s-podiums').text.strip.split("\n")[0]
      team.css('.js-team-details .s-stats .s-item').each do |stat|
        statistics[stat.children[1].text.parameterize] = stat.children[3].text
      end
      result[:statistics] = statistics
      drivers = []
      team.css('.s-driver-cont .s-driver-cont__info').each do |d|
        driver = {}
        driver_url = d.attributes['href'].value
        browser.goto driver_url
        sleep 5
        driver_doc = Nokogiri::HTML.parse(browser.html)

        driver[:display_picture] = driver_doc.css('.b-driver-avatar img').attribute('src').value
        driver[:name] = driver_doc.css('.s-name').text
        driver[:description] = driver_doc.css('.s-content__biography p')[0].text || '-'
        driver_statistics = {}
        driver_statistics[:wins] = driver_doc.css('forix-stats .s-wins').text.strip.split("\n")[0]
        driver_statistics[:podiums] = driver_doc.css('forix-stats .s-podiums').text.strip.split("\n")[0]
        driver_doc.css('forix-stats .s-item').each do |stat|
          driver_statistics[stat.children[1].text.parameterize] = stat.children[3].text
        end
        driver[:statistics] = driver_statistics
        drivers << driver
      end
      result[:drivers] = drivers

      team = Team.new
      team.remote_flag_url = result[:flag]
      team.remote_logo_url = result[:logo]
      team.remote_display_picture_url = result[:display_picture]
      team.name = result[:name]
      team.description = result[:description]
      team.statistics = result[:statistics]
      team.save!

      result[:drivers].each do |driver_attributes|
        driver  = Driver.new
        driver.team = team
        driver.name = driver_attributes[:name]
        driver.description = driver_attributes[:description]
        driver.remote_display_picture_url = driver_attributes[:display_picture]
        driver.statistics = driver_attributes[:statistics]
        driver.save!
      end
    end

    

    
  end
end
