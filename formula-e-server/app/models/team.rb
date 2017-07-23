class Team < ApplicationRecord
  validates :name, presence: true, uniqueness: true
  validates :description, presence: true

  has_many :drivers, dependent: :destroy
  
  serialize :statistics

  mount_uploader :display_picture, TeamDisplayPictureUploader
  mount_uploader :flag, TeamFlagUploader
  mount_uploader :logo, TeamLogoUploader
end
