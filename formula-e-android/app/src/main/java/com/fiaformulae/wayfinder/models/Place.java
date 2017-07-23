package com.fiaformulae.wayfinder.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Place implements Serializable {
  @SerializedName("id") int id;
  @SerializedName("name") String name;
  @SerializedName("description") String description;
  @SerializedName("latitude") double latitude;
  @SerializedName("longitude") double longitude;
  @SerializedName("kind") String kind;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getKind() {
    return kind;
  }
}
