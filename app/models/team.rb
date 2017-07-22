class Team < ApplicationRecord
  validates :name, presence: true, uniqueness: true
  validates :description, presence: true

  serialize :statistics

  mount_uploader :display_picture, TeamDisplayPictureUploader
end
