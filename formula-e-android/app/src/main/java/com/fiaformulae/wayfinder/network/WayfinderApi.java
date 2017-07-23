package com.fiaformulae.wayfinder.network;

import com.fiaformulae.wayfinder.Wayfinder;
import com.fiaformulae.wayfinder.models.Driver;
import com.fiaformulae.wayfinder.models.Event;
import com.fiaformulae.wayfinder.models.Place;
import com.fiaformulae.wayfinder.models.Team;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import rx.observables.ConnectableObservable;

import static com.fiaformulae.wayfinder.utils.RxUtils.applyIOToMainThreadSchedulers;

public class WayfinderApi {
  private static WayfinderService wayfinderService;

  public WayfinderApi(String baseUrl, OkHttpClient okHttpClient) {
    wayfinderService =
        ServiceGenerator.createService(WayfinderService.class, okHttpClient, baseUrl);
  }

  public static WayfinderApi getInstance() {
    return new WayfinderApi(Wayfinder.getBaseUrl(), OkHttpHelper.getOkHttpClientInstance());
  }

  public ConnectableObservable<ArrayList<Event>> getEvents() {
    return wayfinderService.getEvents().compose(applyIOToMainThreadSchedulers()).replay();
  }

  public ConnectableObservable<ArrayList<Place>> getPlaces() {
    return wayfinderService.getPlaces().compose(applyIOToMainThreadSchedulers()).replay();
  }

  public ConnectableObservable<ArrayList<Team>> getTeams() {
    return wayfinderService.getTeams().compose(applyIOToMainThreadSchedulers()).replay();
  }

  public ConnectableObservable<ArrayList<Driver>> getDrivers() {
    return wayfinderService.getDrivers().compose(applyIOToMainThreadSchedulers()).replay();
  }
}
