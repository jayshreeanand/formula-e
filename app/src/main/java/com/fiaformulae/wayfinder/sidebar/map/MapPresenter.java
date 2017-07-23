package com.fiaformulae.wayfinder.sidebar.map;

import android.util.Log;
import com.fiaformulae.wayfinder.models.Place;
import com.fiaformulae.wayfinder.network.WayfinderApi;
import com.fiaformulae.wayfinder.utils.RxUtils;
import java.util.ArrayList;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;

public class MapPresenter implements MapContract.Presenter {
  private MapContract.View view;
  private CompositeSubscription compositeSubscription;
  private WayfinderApi wayfinderApi;

  public MapPresenter(MapContract.View view) {
    this.view = view;
    compositeSubscription = new CompositeSubscription();
    wayfinderApi = WayfinderApi.getInstance();
  }

  @Override public void onDestroy() {
    RxUtils.clearCompositeSubscription(compositeSubscription);
  }

  @Override public void getPlaces() {
    view.showProgressBar();
    ConnectableObservable<ArrayList<Place>> observable = wayfinderApi.getPlaces();
    compositeSubscription.add(observable.connect());
    compositeSubscription.add(
        observable.subscribe(this::onGetPlacesSuccess, this::onGetPlacesFailure));
  }

  @Override
  public ArrayList<Place> getPlacesContainingString(ArrayList<Place> places, String name) {
    ArrayList<Place> filteredPlaces = new ArrayList<>();
    for (Place place : places) {
      if (place.getName().toLowerCase().contains(name)) {
        filteredPlaces.add(place);
      }
    }
    return filteredPlaces;
  }

  private void onGetPlacesSuccess(ArrayList<Place> places) {
    view.hideProgressBar();
    view.onGettingPlaces(places);
  }

  private void onGetPlacesFailure(Throwable throwable) {
    view.hideProgressBar();
    Log.d("Error", "Failed to get places - " + throwable.getMessage());
  }
}
