package com.fiaformulae.wayfinder.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {
  @SerializedName("id") int id;
  @SerializedName("name") String name;
  @SerializedName("description") String description;
  @SerializedName("display_picture") Image image;
  @SerializedName("drivers") ArrayList<Driver> drivers;
  @SerializedName("logo") Image logo;
  @SerializedName("flag") Image flag;
  @SerializedName("statistics") String details;

  public int getId() {
    return id;
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

  public String getDetails() {
    return details;
  }
}
