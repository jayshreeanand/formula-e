# encoding: utf-8
class BaseImageUploader < BaseUploader
  include CarrierWave::MiniMagick

  process :fix_exif_rotation

  def extension_white_list
    %w(jpg jpeg gif png webp)
  end
end
