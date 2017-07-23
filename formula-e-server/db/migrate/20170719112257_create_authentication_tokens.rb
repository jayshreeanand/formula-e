class CreateAuthenticationTokens < ActiveRecord::Migration[5.0]
  def change
     create_table :authentication_tokens do |t|
      t.references :user, foreign_key: true
      t.string :digest
      t.datetime :expires_at

      t.timestamps
    end
    add_index :authentication_tokens, :digest, unique: true
  end
end
