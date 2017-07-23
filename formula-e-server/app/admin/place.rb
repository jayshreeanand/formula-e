ActiveAdmin.register Place do
  permit_params :name, :description, :latitude, :longitude, :kind
end
