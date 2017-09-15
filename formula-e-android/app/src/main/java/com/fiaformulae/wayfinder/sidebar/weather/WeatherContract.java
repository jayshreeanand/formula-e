package com.fiaformulae.wayfinder.sidebar.weather;

import com.fiaformulae.wayfinder.models.Weather;

public interface WeatherContract {
  public interface View {
    void showProgressbar();

    void hideProgresbar();

    void onGettingWeather(Weather weather);
  }

  public interface Presenter {
    public void onDestroy();

    public void getWeather();
  }
}
