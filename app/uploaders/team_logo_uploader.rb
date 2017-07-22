# encoding: utf-8
class TeamLogoUploader < BaseImageUploader
  process resize_to_limit: [1000, 1000]

  version :normal do
    process resize_to_fit: [360, 360]
  end

  version :thumb, from_version: :normal do
    process resize_to_fit: [100, 100]
  end
end
