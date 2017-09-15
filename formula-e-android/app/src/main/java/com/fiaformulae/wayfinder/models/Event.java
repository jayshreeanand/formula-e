package com.fiaformulae.wayfinder.models;

import android.support.annotation.NonNull;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

@Table(name = "Events") public class Event extends Model
    implements Serializable, Comparable<Event> {
  @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  @SerializedName("id") int remoteId;
  @Column(name = "Name") @SerializedName("name") String name;
  @Column(name = "Description") @SerializedName("description") String description;
  @Column(name = "Place") @SerializedName("place") Place place;
  @Column(name = "StartTime") @SerializedName("starts_at") Date startTime;
  @Column(name = "EndTime") @SerializedName("ends_at") Date endTime;
  @Column(name = "Image") @SerializedName("display_picture") Image image;

  public Event() {
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
