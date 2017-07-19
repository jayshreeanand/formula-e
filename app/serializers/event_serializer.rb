class EventSerializer < BaseSerializer
  attributes :name, :description, :starts_at, :ends_at, :display_picture

  has_one :place


  def display_picture
    object.display_picture_url(:normal)
  end
end
