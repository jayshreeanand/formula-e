source 'https://rubygems.org'

git_source(:github) do |repo_name|
  repo_name = "#{repo_name}/#{repo_name}" unless repo_name.include?("/")
  "https://github.com/#{repo_name}.git"
end

# Stack
gem 'rails', '~> 5.0.3'
gem 'pg'
gem 'puma', '~> 3.0'
gem 'foreman'

# API
gem 'grape'


# Serializers
gem 'active_model_serializers'
gem 'grape-active_model_serializers'

# Auth
gem 'devise'
gem 'cancancan'

# Admin
gem 'activeadmin', github: 'activeadmin'


# Uploads
gem 'fog-aws'
gem 'carrierwave', github: 'carrierwaveuploader/carrierwave'
gem 'mini_magick'

# Misc
gem 'action_parameter'

# View
gem 'sass-rails', '~> 5.0'
gem 'uglifier', '>= 1.3.0'
gem 'coffee-rails', '~> 4.2'
gem 'jquery-rails'
gem 'turbolinks', '~> 5'
gem 'jbuilder', '~> 2.5'


group :development, :test do
  gem 'byebug', platform: :mri
end

group :development do
  gem 'web-console', '>= 3.3.0'
  gem 'listen', '~> 3.0.5'
  gem 'spring'
  gem 'spring-watcher-listen', '~> 2.0.0'
end

# Logging
group :production, :staging do
  gem 'rails_12factor'
end
