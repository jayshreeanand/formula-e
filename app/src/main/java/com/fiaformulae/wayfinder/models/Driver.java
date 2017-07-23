package com.fiaformulae.wayfinder.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Driver implements Serializable {
  @SerializedName("id") int id;
  @SerializedName("name") String name;
  @SerializedName("description") String description;
  @SerializedName("display_picture") Image image;
  @SerializedName("statistics") String details;
  @SerializedName("team") Team team;

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

  public Team getTeam() {
    return team;
  }

  public String getDetails() {
    return details;
  }
}
