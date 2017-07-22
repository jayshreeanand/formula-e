class EventSerializer < BaseSerializer
  attributes :name, :description, :starts_at, :ends_at, :display_picture

  has_one :place


  def display_picture
    {
      default: object.display_picture_url,
      normal: object.display_picture_url(:normal),
      thumb: object.display_picture_url(:thumb)
    }
  end
end
