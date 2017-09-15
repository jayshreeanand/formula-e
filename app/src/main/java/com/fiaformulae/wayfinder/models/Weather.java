package com.fiaformulae.wayfinder.models;

import com.google.gson.annotations.SerializedName;

public class Weather {
  @SerializedName("current_weather") double currentTemperature;
  @SerializedName("forecast_weather") double forecastTemperature;

  public double getCurrentTemperature() {
    return currentTemperature;
  }

  public double getForecastTemperature() {
    return forecastTemperature;
  }
}
