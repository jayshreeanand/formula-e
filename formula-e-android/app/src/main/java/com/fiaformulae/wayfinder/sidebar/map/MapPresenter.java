package com.fiaformulae.wayfinder.sidebar.map;

import android.util.Log;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.fiaformulae.wayfinder.models.Place;
import com.fiaformulae.wayfinder.network.WayfinderApi;
import com.fiaformulae.wayfinder.utils.RxUtils;
import java.util.ArrayList;
import java.util.List;
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

  @Override public List<Place> getPlacesFromDb() {
    return new Select().from(Place.class).orderBy("Name ASC").execute();
  }

  @Override public ArrayList<Place> getPlacesContainingString(List<Place> places, String name) {
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
    new Delete().from(Place.class).execute();
    for (Place place : places) {
      place.save();
    }
    view.onGettingPlaces(places);
  }

  private void onGetPlacesFailure(Throwable throwable) {
    view.hideProgressBar();
    Log.d("Error", "Failed to get places - " + throwable.getMessage());
  }
}
