package com.fiaformulae.wayfinder.sidebar.map;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import com.fiaformulae.wayfinder.R;
import com.fiaformulae.wayfinder.models.Place;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.api.directions.v5.DirectionsCriteria;
import com.mapbox.services.api.directions.v5.MapboxDirections;
import com.mapbox.services.api.directions.v5.models.DirectionsResponse;
import com.mapbox.services.api.directions.v5.models.DirectionsRoute;
import com.mapbox.services.commons.geojson.LineString;
import com.mapbox.services.commons.models.Position;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fiaformulae.wayfinder.AppConstants.CURRENT_LOCATION;
import static com.fiaformulae.wayfinder.AppConstants.USER_LATITUDE;
import static com.fiaformulae.wayfinder.AppConstants.USER_LONGITUDE;
import static com.mapbox.services.Constants.PRECISION_6;

public class Map {
  private static final String TAG = "MAP";
  public MapboxMap mapboxMap;
  public ArrayList<Marker> markers = new ArrayList<>();
  public Place userLocation = new Place();
  public Marker currentMarker;
  public DirectionsRoute currentRoute = null;
  public Polyline currentRoutePolyline;
  public Context context;
  public MapListener listener;

  public Map(Context context, MapboxMap mapboxMap, MapListener listener) {
    this.context = context;
    this.mapboxMap = mapboxMap;
    this.listener = listener;

    mapboxMap.setOnMarkerClickListener(marker -> {
      animateCameraToPosition(marker.getPosition());
      currentMarker = marker;
      listener.onMarkerClicked(marker.getTitle());
      return true;
    });
  }

  public void setCurrentLocation() {
    userLocation.setLatitude(USER_LATITUDE);
    userLocation.setLongitude(USER_LONGITUDE);
    userLocation.setName(CURRENT_LOCATION);
  }

  public void showMarkerOnUserLocation() {
    IconFactory iconFactory = IconFactory.getInstance(context);
    Icon icon = iconFactory.fromResource(R.drawable.ic_marker_blue);
    Marker marker = mapboxMap.addMarker(new MarkerOptions().position(userLocation.getLatLng())
        .title(userLocation.getName())
        .icon(icon));
    markers.add(marker);
  }

  public void animateCameraToPosition(LatLng latLng) {
    CameraPosition position = new CameraPosition.Builder().target(latLng).tilt(30).build();
    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
  }

  public void showRoute() {
    Position origin =
        Position.fromCoordinates(userLocation.getLongitude(), userLocation.getLatitude());
    Position destination = Position.fromCoordinates(currentMarker.getPosition().getLongitude(),
        currentMarker.getPosition().getLatitude());
    MapboxDirections client = new MapboxDirections.Builder().setAccessToken(
        context.getString(R.string.mapbox_access_token))
        .setOrigin(origin)
        .setDestination(destination)
        .setOverview(DirectionsCriteria.OVERVIEW_FULL)
        .setProfile(DirectionsCriteria.PROFILE_WALKING)
        .setAnnotation(DirectionsCriteria.ANNOTATION_DISTANCE,
            DirectionsCriteria.ANNOTATION_DURATION)
        .build();

    client.enqueueCall(new Callback<DirectionsResponse>() {
      @Override
      public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
        Log.d(TAG, call.request().url().toString());
        if (response.body() == null || response.body().getRoutes().size() < 1) {
          Log.e(TAG, "No routes found, make sure you set the right user and access token.");
          return;
        }
        currentRoute = response.body().getRoutes().get(0);
        Toast.makeText(context, "Distance: " + currentRoute.getDistance() + " metres",
            Toast.LENGTH_LONG).show();
        drawRoute(currentRoute);
      }

      @Override public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
        Log.e(TAG, "Error: " + throwable.getMessage());
        Toast.makeText(context, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  public void drawRoute(DirectionsRoute route) {
    // Convert LineString coordinates into LatLng[]
    LineString lineString = LineString.fromPolyline(route.getGeometry(), PRECISION_6);
    List<Position> coordinates = lineString.getCoordinates();
    List<LatLng> points = new ArrayList<>();
    for (Position position : coordinates) {
      points.add(new LatLng(position.getLatitude(), position.getLongitude()));
    }

    if (currentRoutePolyline != null) mapboxMap.removePolyline(currentRoutePolyline);
    currentRoutePolyline = addPolyline(R.color.lochmara, 3, points);
    showMarkerOnUserLocation();
    ArrayList<LatLng> locations = new ArrayList<>();
    locations.add(userLocation.getLatLng());
    locations.add(currentMarker.getPosition());
    fitCameraWithinBounds(locations);
    listener.onDrawRoute();
  }

  public Polyline addPolyline(int color, int width, List<LatLng> points) {
    return mapboxMap.addPolyline(new PolylineOptions().addAll(points)
        .color(ContextCompat.getColor(context, color))
        .width(width));
  }

  public void showMarkers(ArrayList<Place> placeList) {
    clearMarkers();
    ArrayList<LatLng> locations = new ArrayList<>();
    for (Place place : placeList) {
      locations.add(place.getLatLng());
      Marker marker = mapboxMap.addMarker(
          new MarkerOptions().position(place.getLatLng()).title(place.getName()));
      markers.add(marker);
    }
    if (locations.size() > 1) fitCameraWithinBounds(locations);
  }

  public void fitCameraWithinBounds(ArrayList<LatLng> locations) {
    LatLngBounds.Builder latLngBoundsBuilder = new LatLngBounds.Builder();
    for (LatLng location : locations) {
      latLngBoundsBuilder.include(location);
    }
    LatLngBounds latLngBounds = latLngBoundsBuilder.build();
    mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 250), 2000);
  }

  public void clearMarkers() {
    for (Marker marker : markers) {
      mapboxMap.removeMarker(marker);
    }
  }

  public interface MapListener {
    public void onDrawRoute();

    public void onMarkerClicked(String title);
  }
}
