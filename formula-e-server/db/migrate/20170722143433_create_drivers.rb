class CreateDrivers < ActiveRecord::Migration[5.0]
  def change
    create_table :drivers do |t|
      t.belongs_to :team, index: true
      t.string :name
      t.text :description
      t.text :statistics
      t.string :display_picture
      t.timestamps
    end
  end
end
