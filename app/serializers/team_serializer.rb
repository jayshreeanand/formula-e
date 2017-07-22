class TeamSerializer < BaseSerializer
  attributes :name, :description, :starts_at, :ends_at, :display_picture, :logo, :flag

  def display_picture
    object.display_picture_url(:normal)
  end

  def display_picture
    object.logo_url(:normal)
  end

  def display_picture
    object.flag_url(:normal)
  end
end
