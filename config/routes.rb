Rails.application.routes.draw do
  root 'pages#home'
  get 'pages/home'

  devise_for :users
  devise_for :admin_users, ActiveAdmin::Devise.config
  ActiveAdmin.routes(self)
end
