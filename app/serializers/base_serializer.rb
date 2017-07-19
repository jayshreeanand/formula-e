class BaseSerializer < ActiveModel::Serializer
  attributes :id, :created_at, :updated_at

  delegate :current_user, to: :scope
end
