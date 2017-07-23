# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: 'Star Wars' }, { name: 'Lord of the Rings' }])
#   Character.create(name: 'Luke', movie: movies.first)
AdminUser.create!(email: 'admin@example.com', password: 'password', password_confirmation: 'password') if Rails.env.development?

date = '2017-08-01 00:00:00'.to_datetime
events = [
  { starts_at: '7:30', ends_at: '8:00', name: 'Doors opening', place: 'Allianz eVillage' },
  { starts_at: '8:00', ends_at: '8:45', name: 'Free Practice 1', place: 'Track' },
  { starts_at: '10:00', ends_at: '10:20', name: 'Drawing of qualifications', place: 'Allianz eVillage - Podium' },
  { starts_at: '10:30', ends_at: '11:00', name: 'Free Practice 2', place: 'Track' },
  { starts_at: '12:00', ends_at: '12:36', name: 'Qualifying tests', place: 'Track' },
  { starts_at: '12:45', ends_at: '13:00', name: 'Super Pole', place: 'Track' },
  { starts_at: '13:45', ends_at: '14:00', name: 'Erace', place: 'Allianz eVillage' },
  { starts_at: '14:00', ends_at: '14:30', name: 'Autograph session', place: 'Allianz eVillage' },
  { starts_at: '15:00', ends_at: '15:10', name: "Pilot's Parade", place: 'Track' },
  { starts_at: '15:30', ends_at: '16:00', name: 'Griding Procedure', place: 'Track' },
  { starts_at: '16:00', ends_at: '17:00', name: 'Race', place: 'Track' },
  { starts_at: '17:00', ends_at: '18:00', name: 'Podium', place: 'Allianz eVillage - Podium' },
  { starts_at: '18:00', ends_at: '18:30', name: 'Closing of the Allianz eVillage', place: 'Allianz eVillage' }
]

events.each do |event|
  e = Event.new
  start_hours = event[:starts_at].split(':')[0].to_i
  start_minutes = event[:starts_at].split(':')[1].to_i
  end_hours = event[:ends_at].split(':')[0].to_i
  end_minutes = event[:ends_at].split(':')[1].to_i
  e.starts_at = date + start_hours.hours + start_minutes.minutes
  e.ends_at = date + end_hours.hours + end_minutes.minutes
  e.name = event[:name]
  e.place = Place.find_by_name(event[:place])
  e.save!
end