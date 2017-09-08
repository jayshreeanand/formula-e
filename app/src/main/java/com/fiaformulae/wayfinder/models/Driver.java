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
  @Column(name = "Team", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
  @SerializedName("team") Team team;
  @Column(name = "ImageDefault") String imageDefault;
  @Column(name = "ImageNormal") String imageNormal;
  @Column(name = "ImageThumbnail") String imageThumbnail;
  @Column(name = "Details") String driverDetails;

  @SerializedName("display_picture") Image image;
  @SerializedName("statistics") JsonObject details;

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

  public String getImageDefault() {
    return imageDefault;
  }

  public Team getTeam() {
    return team;
  }

  public String getDetails() {
    return driverDetails;
  }

  public void setFields(Team team) {
    this.team = team;
    imageDefault = image.getDefault();
    imageNormal = image.getNormal();
    imageThumbnail = image.getThumbnail();
    driverDetails = details.toString();
  }
}
