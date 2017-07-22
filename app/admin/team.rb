ActiveAdmin.register Team do
  permit_params :name, :description, :statistics, :display_picture, :logo, :flag

  index do
    selectable_column
    id_column
    column :name
    column :description
    column :display_picture do |team|
      image_tag team.display_picture.thumb.url
    end
    column :logo do |team|
      image_tag team.logo.thumb.url
    end
    column :flag do |team|
      image_tag team.flag.thumb.url
    end
    column :statistics
    actions
  end
end
