package com.fiaformulae.wayfinder.network;

import com.fiaformulae.wayfinder.models.Event;
import com.fiaformulae.wayfinder.models.Place;
import java.util.ArrayList;
import retrofit2.http.GET;
import rx.Observable;

public interface WayfinderService {

  @GET("events") Observable<ArrayList<Event>> getEvents();

  @GET("places") Observable<ArrayList<Place>> getPlaces();
}
