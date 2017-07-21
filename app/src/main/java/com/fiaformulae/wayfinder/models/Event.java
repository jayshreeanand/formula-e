package com.fiaformulae.wayfinder.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
  @SerializedName("id") int id;
  @SerializedName("name") String name;
  @SerializedName("description") String description;
  @SerializedName("place") Place place;
  @SerializedName("starts_at") Date startTime;
  @SerializedName("ends_at") Date endTime;
  @SerializedName("display_picture") String imageUrl;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Place getPlace() {
    return place;
  }

  public Date getStartTime() {
    return startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public String getImageUrl() {
    return imageUrl;
  }
}
