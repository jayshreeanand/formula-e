package com.fiaformulae.wayfinder.sidebar.map;

import com.fiaformulae.wayfinder.models.Place;
import java.util.ArrayList;

public interface MapContract {
  public interface View {
    void showProgressBar();

    void hideProgressBar();

    void onGettingPlaces(ArrayList<Place> places);
  }

  public interface Presenter {
    void onDestroy();

    void getPlaces();
  }
}
