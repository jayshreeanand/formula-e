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
  @Column(name = "Details") String teamDetails;
  @Column(name = "ImageDefault") String imageDefault;
  @Column(name = "ImageNormal") String imageNormal;
  @Column(name = "ImageThumbnail") String imageThumbnail;
  @Column(name = "LogoDefault") String logoDefault;
  @Column(name = "LogoNormal") String logoNormal;
  @Column(name = "LogoThumbnail") String logoThumbnail;
  @Column(name = "FlagDefault") String flagDefault;
  @Column(name = "FlagNormal") String flagNormal;
  @Column(name = "FlagThumbnail") String flagThumbnail;

  @SerializedName("statistics") JsonObject details;
  @SerializedName("drivers") ArrayList<Driver> drivers;
  @SerializedName("display_picture") Image image;
  @SerializedName("logo") Image logo;
  @SerializedName("flag") Image flag;

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

  public String getImageDefault() {
    return imageDefault;
  }

  public String getFlagThumbnail() {
    return flagThumbnail;
  }

  public String getLogoDefault() {
    return logoDefault;
  }

  public void setFields() {
    imageDefault = image.getDefault();
    imageNormal = image.getNormal();
    imageThumbnail = image.getThumbnail();
    logoDefault = logo.getDefault();
    logoNormal = logo.getNormal();
    logoThumbnail = logo.getThumbnail();
    flagDefault = flag.getDefault();
    flagNormal = flag.getNormal();
    flagThumbnail = flag.getThumbnail();
    teamDetails = details.toString();
  }

  public void setDrivers() {
    for (Driver driver : drivers) {
      driver.setFields(this);
      driver.save();
    }
  }

  public String getDetails() {
    return teamDetails;
  }

  public List<Driver> drivers() {
    return getMany(Driver.class, "Team");
  }
}
