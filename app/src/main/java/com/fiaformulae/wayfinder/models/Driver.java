package com.fiaformulae.wayfinder.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

@Table(name = "Drivers") public class Driver extends Model implements Serializable {
  @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  @SerializedName("id") int remoteId;
  @Column(name = "Name") @SerializedName("name") String name;
  @Column(name = "Description") @SerializedName("description") String description;
  @Column(name = "Image") @SerializedName("display_picture") Image image;
  @Column(name = "Details") @SerializedName("statistics") JsonObject details;
  @Column(name = "Team", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
  @SerializedName("team") Team team;

  public Driver() {
    super();
  }

  public int getRemoteId() {
    return remoteId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Image getImage() {
    return image;
  }

  public Team getTeam() {
    return team;
  }

  public JsonObject getDetails() {
    return details;
  }
}
