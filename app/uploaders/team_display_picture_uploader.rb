# encoding: utf-8
class TeamDisplayPictureUploader < BaseImageUploader
  process resize_to_limit: [1440, 720]

  version :normal do
    process resize_to_fit: [360, 180]
  end

  version :thumb, from_version: :normal do
    process resize_to_fit: [200, 100]
  end
end
