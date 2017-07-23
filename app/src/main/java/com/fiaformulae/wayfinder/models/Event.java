package com.fiaformulae.wayfinder.models;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable, Comparable<Event> {
  @SerializedName("id") int id;
  @SerializedName("name") String name;
  @SerializedName("description") String description;
  @SerializedName("place") Place place;
  @SerializedName("starts_at") Date startTime;
  @SerializedName("ends_at") Date endTime;
  @SerializedName("display_picture") Image image;

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

  public Image getImage() {
    return image;
  }

  @Override public int compareTo(@NonNull Event event) {
    return getStartTime().compareTo(event.getStartTime());
  }
}
