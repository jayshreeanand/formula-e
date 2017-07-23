class ModifyTeam < ActiveRecord::Migration[5.0]
  def change
    add_column :teams, :logo, :string
    add_column :teams, :flag, :string
  end
end
