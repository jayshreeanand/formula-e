package com.fiaformulae.wayfinder.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {
  @SerializedName("id") int id;
  @SerializedName("name") String name;
  @SerializedName("description") String description;
  @SerializedName("display_picture") String imageUrl;
  @SerializedName("drivers") ArrayList<Driver> drivers;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public ArrayList<Driver> getDrivers() {
    return drivers;
  }
}
