class Driver < ApplicationRecord
  validates :name, presence: true, uniqueness: true
  validates :description, presence: true

  belongs_to :team
  
  serialize :statistics

  mount_uploader :display_picture, TeamDisplayPictureUploader
end
