package com.fiaformulae.wayfinder.network;

import com.fiaformulae.wayfinder.models.Driver;
import com.fiaformulae.wayfinder.models.Event;
import com.fiaformulae.wayfinder.models.Place;
import com.fiaformulae.wayfinder.models.Team;
import com.fiaformulae.wayfinder.models.Weather;
import java.util.ArrayList;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WayfinderService {

  @GET("events") Observable<ArrayList<Event>> getEvents();

  @GET("places") Observable<ArrayList<Place>> getPlaces();

  @GET("teams") Observable<ArrayList<Team>> getTeams();

  @GET("drivers") Observable<ArrayList<Driver>> getDrivers();

  @GET("weather") Observable<Weather> getWeather(@Query("latitude") double latitude,
      @Query("longitude") double longitude);
}
