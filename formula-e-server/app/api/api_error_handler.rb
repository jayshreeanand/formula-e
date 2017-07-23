require 'custom_error'

module ApiErrorHandler
  extend ActiveSupport::Concern

  included do
    rescue_from ActiveRecord::RecordNotFound do |e|
      Rack::Response.new({
        error: 'Record Not Found',
        message: e.message
      }.to_json, 404)
    end

    rescue_from ActiveRecord::RecordInvalid do |e|
      error!({
        message: e.message
      }, 422)
    end

    rescue_from Grape::Exceptions::ValidationErrors do |e|
      Rack::Response.new({
        status: e.status,
        message: e.message,
        errors: e.errors
      }.to_json, e.status)
    end

    rescue_from CanCan::AccessDenied do |e|
      Rack::Response.new({
        status: :forbidden,
        message: e.message,
      }.to_json, 403)
    end

    rescue_from Errors::CustomError do |e|
      Rack::Response.new({
        message: e.message,
      }.to_json, 400)
    end

    rescue_from Errors::AuthorizationError do |e|
      Rack::Response.new({
        message: e.message,
      }.to_json, 401)
    end
  end
end
