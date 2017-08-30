package com.fiaformulae.wayfinder.sidebar.map;

import com.fiaformulae.wayfinder.models.Place;
import java.util.ArrayList;
import java.util.List;

public interface MapContract {
  public interface View {
    void showProgressBar();

    void hideProgressBar();

    void onGettingPlaces(List<Place> places);
  }

  public interface Presenter {
    void onDestroy();

    void getPlaces();

    List<Place> getPlacesFromDb();

    ArrayList<Place> getPlacesContainingString(List<Place> places, String name);
  }
}
