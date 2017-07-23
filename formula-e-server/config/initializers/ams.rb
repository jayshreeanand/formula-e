ActiveModel::Serializer.configure do |config|
  config.embed = :ids
  config.embed_in_root = true
  config.embed_in_root_key = :linked
end
