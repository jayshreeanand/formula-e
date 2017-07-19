class Place < ApplicationRecord
  validates :name, presence: true, uniqueness: true
  validates :latitude, presence: true
  validates :longitude, presence: true, uniqueness: { scope: :longitude }

  enum kind: { track: 0, evillage: 1, utility: 2 }
end
