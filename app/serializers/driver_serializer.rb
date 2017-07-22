class DriverSerializer < BaseSerializer
  attributes :name, :description, :display_picture, :statistics

  def display_picture
    {
      default: object.display_picture_url,
      normal: object.display_picture_url(:normal),
      thumb: object.display_picture_url(:thumb)
    }
  end
end
