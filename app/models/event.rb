class Event < ApplicationRecord
  validates :name, presence: true
  validates :starts_at, presence: true
  validates :ends_at, presence: true
  
  belongs_to :place, presence: true

  mount_uploader :display_picture, EventDisplayPictureUploader
end
