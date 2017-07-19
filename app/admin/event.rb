ActiveAdmin.register Event do
  permit_params :name, :description, :starts_at, :ends_at, :place_id, :display_picture

  index do
    selectable_column
    id_column
    column :name
    column :description
    column :display_picture do |product|
      image_tag product.display_picture.thumb.url
    end
    column :place
    column :starts_at
    column :ends_at
    actions
  end
end
