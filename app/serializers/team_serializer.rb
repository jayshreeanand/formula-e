class TeamSerializer < BaseSerializer
  attributes :name, :description, :starts_at, :ends_at, :display_picture

  def display_picture
    object.display_picture_url(:normal)
  end
end
