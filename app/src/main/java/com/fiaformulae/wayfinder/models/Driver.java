package com.fiaformulae.wayfinder.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Driver implements Serializable {
  @SerializedName("id") int id;
  @SerializedName("name") String name;
  @SerializedName("description") String description;
  @SerializedName("display_picture") String imageUrl;
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

  public String getImageUrl() {
    return imageUrl;
  }

  public Team getTeam() {
    return team;
  }
}
