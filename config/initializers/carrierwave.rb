require 'carrierwave/orm/activerecord'
require 'carrierwave/storage/abstract'
require 'carrierwave/storage/file'
require 'carrierwave/storage/fog'

CarrierWave.configure do |config|
  if Rails.env.production? || Rails.env.staging?
    config.storage = :fog
    config.fog_provider = 'fog/aws'
    config.fog_directory = Rails.application.secrets.s3_bucket
    config.fog_credentials = {
      provider: 'AWS',
      aws_access_key_id: Rails.application.secrets.aws_access_key_id,
      aws_secret_access_key: Rails.application.secrets.aws_secret_access_key,
      persistent: false,
      region: Rails.application.secrets.s3_region
    }
    
    config.remove_previously_stored_files_after_update = true
  else
    config.storage = :file
  end

  config.asset_host = ActionController::Base.asset_host
  config.enable_processing = false if Rails.env.test?
end

module CarrierWave
  module MiniMagick
    def fix_exif_rotation
      manipulate! do |img|
        img.tap(&:auto_orient)
        img = yield(img) if block_given?
        img
      end
    end
  end
end
