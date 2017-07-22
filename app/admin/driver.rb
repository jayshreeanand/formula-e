ActiveAdmin.register Driver do
  permit_params :name, :description, :statistics, :display_picture

  index do
    selectable_column
    id_column
    column :name
    column :description
    column :display_picture do |driver|
      image_tag driver.display_picture.thumb.url
    end
    column :statistics
    actions
  end
end
