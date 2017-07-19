class CreateEvents < ActiveRecord::Migration[5.0]
  def change
    create_table :events do |t|
      t.string :name
      t.text :description
      t.belongs_to :place, index: true
      t.datetime :starts_at
      t.datetime :ends_at
      t.timestamps
    end
  end
end
