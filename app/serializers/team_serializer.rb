class TeamSerializer < BaseSerializer
  attributes :name, :description, :display_picture, :logo, :flag

  def display_picture
    {
      default: object.display_picture_url,
      normal: object.display_picture_url(:normal),
      thumb: object.display_picture_url(:thumb)
    }
  end

  def logo
    {
      default: object.logo_url,
      normal: object.logo_url(:normal),
      thumb: object.logo_url(:thumb)
    }
  end

  def flag
    {
      default: object.flag_url,
      normal: object.flag_url(:normal),
      thumb: object.flag_url(:thumb)
    }
  end
end
