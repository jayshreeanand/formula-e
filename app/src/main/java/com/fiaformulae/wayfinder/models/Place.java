package com.fiaformulae.wayfinder.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

@Table(name = "Places") public class Place extends Model implements Serializable {
  @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  @SerializedName("id") int remoteId;
  @Column(name = "Name") @SerializedName("name") String name;
  @Column(name = "Description") @SerializedName("description") String description;
  @Column(name = "Latitude") @SerializedName("latitude") double latitude;
  @Column(name = "Longitude") @SerializedName("longitude") double longitude;
  @Column(name = "Kind") @SerializedName("kind") String kind;

  public Place() {
    super();
  }

  public int getRemoteId() {
    return remoteId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getKind() {
    return kind;
  }
}
