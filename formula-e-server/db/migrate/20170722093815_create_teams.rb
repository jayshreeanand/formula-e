class CreateTeams < ActiveRecord::Migration[5.0]
  def change
    create_table :teams do |t|
      t.string :name
      t.text :description
      t.text :statistics
      t.string :display_picture
      t.timestamps
    end
  end
end
