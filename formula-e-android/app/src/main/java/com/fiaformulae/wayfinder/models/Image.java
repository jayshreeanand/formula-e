package com.fiaformulae.wayfinder.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Image implements Serializable {
  @SerializedName("default") String defaultImage;
  @SerializedName("normal") String normal;
  @SerializedName("thumb") String thumbnail;

  public String getDefault() {
    return defaultImage;
  }

  public String getNormal() {
    return normal;
  }

  public String getThumbnail() {
    return thumbnail;
  }
}
