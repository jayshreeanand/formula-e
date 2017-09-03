package com.fiaformulae.wayfinder.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Teams") public class Team extends Model implements Serializable {
  @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  @SerializedName("id") int remoteId;
  @Column(name = "Name") @SerializedName("name") String name;
  @Column(name = "Description") @SerializedName("description") String description;
  @Column(name = "Image") @SerializedName("display_picture") Image image;
  @Column(name = "Drivers") @SerializedName("drivers") ArrayList<Driver> drivers;
  @Column(name = "Logo") @SerializedName("logo") Image logo;
  @Column(name = "Flag") @SerializedName("flag") Image flag;
  @Column(name = "Details") @SerializedName("statistics") JsonObject details;

  public Team() {
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

  public ArrayList<Driver> getDrivers() {
    return drivers;
  }

  public Image getLogo() {
    return logo;
  }

  public Image getFlag() {
    return flag;
  }

  public JsonObject getDetails() {
    return details;
  }

  public List<Driver> drivers() {
    return getMany(Driver.class, "Team");
  }
}
