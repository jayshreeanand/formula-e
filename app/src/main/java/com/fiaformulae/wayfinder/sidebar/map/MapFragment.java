package com.fiaformulae.wayfinder.sidebar.map;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fiaformulae.wayfinder.MainActivity;
import com.fiaformulae.wayfinder.R;
import com.fiaformulae.wayfinder.models.Place;
import com.fiaformulae.wayfinder.utils.GeoJsonUtils;
import com.mapbox.mapboxsdk.Mapbox;
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
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.api.directions.v5.DirectionsCriteria;
import com.mapbox.services.api.directions.v5.MapboxDirections;
import com.mapbox.services.api.directions.v5.models.DirectionsResponse;
import com.mapbox.services.api.directions.v5.models.DirectionsRoute;
import com.mapbox.services.commons.geojson.LineString;
import com.mapbox.services.commons.models.Position;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fiaformulae.wayfinder.AppConstants.CURRENT_LOCATION;
import static com.fiaformulae.wayfinder.AppConstants.PLACES;
import static com.fiaformulae.wayfinder.AppConstants.PLACE_FOOD;
import static com.fiaformulae.wayfinder.AppConstants.PLACE_GAMING;
import static com.fiaformulae.wayfinder.AppConstants.PLACE_WASHROOM;
import static com.fiaformulae.wayfinder.AppConstants.USER_LATITUDE;
import static com.fiaformulae.wayfinder.AppConstants.USER_LONGITUDE;
import static com.mapbox.services.Constants.PRECISION_6;

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback {
  private static final String TAG = "MapFragment";
  @BindView(R.id.sliding_layout) SlidingUpPanelLayout slidingLayout;
  @BindView(R.id.mapview) MapView mapView;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.fab_washroom) FloatingActionButton fabWashroom;
  @BindView(R.id.fab_game) FloatingActionButton fabGame;
  @BindView(R.id.fab_food) FloatingActionButton fabFood;
  @BindView(R.id.fab_location) FloatingActionButton fabLocation;
  @BindView(R.id.location_name) TextView locationText;
  @BindView(R.id.direction_button) Button directionButton;
  private MapContract.Presenter presenter;
  private MapboxMap mapboxMap;
  private List<Place> places;
  private boolean isFabOpen = false;
  private ArrayList<Marker> markers = new ArrayList<>();
  private Place userLocation = new Place();
  private Marker currentMarker;
  private DirectionsRoute currentRoute = null;
  private Polyline currentRoutePolyline;

  public static MapFragment newInstance(ArrayList<Place> markerPlaces) {
    MapFragment fragment = new MapFragment();
    if (markerPlaces != null) {
      Bundle args = new Bundle();
      args.putSerializable(PLACES, markerPlaces);
      fragment.setArguments(args);
    }
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    Mapbox.getInstance(getActivity(), getString(R.string.mapbox_access_token));
    return inflater.inflate(R.layout.fragment_map, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    ((MainActivity) getActivity()).showToolbar();
    presenter = new MapPresenter(this);
    places = presenter.getPlacesFromDb();
    presenter.getPlaces();
    initializeMap(savedInstanceState);
  }

  @Override public void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override public void onResume() {
    super.onResume();
    mapView.onResume();
    setCurrentLocation();
    slidingLayout.setFadeOnClickListener(
        view -> slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN));
    slidingLayout.addPanelSlideListener(onSlideListener());
  }

  @Override public void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override public void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
    mapView.onDestroy();
  }

  private void initializeMap(Bundle savedInstanceState) {
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this);
  }

  @Override public void onMapReady(MapboxMap mapboxMap) {
    this.mapboxMap = mapboxMap;
    new DrawTrackGeoJson().execute();
    new DrawEVillageGeoJson().execute();
    if (getArguments() != null && getArguments().getSerializable(PLACES) != null) {
      ArrayList<Place> markerPlaces = (ArrayList<Place>) getArguments().getSerializable(PLACES);
      showMarkers(markerPlaces);
    }
    mapboxMap.setOnMarkerClickListener(marker -> {
      animateCameraToPosition(marker.getPosition());
      currentMarker = marker;
      locationText.setText(marker.getTitle());
      directionButton.setVisibility(View.VISIBLE);
      if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
      }
      if (CURRENT_LOCATION.equalsIgnoreCase(marker.getTitle())) {
        directionButton.setVisibility(View.GONE);
      }
      return true;
    });
  }

  @Override public void showProgressBar() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideProgressBar() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void onGettingPlaces(List<Place> places) {
    this.places = places;
  }

  @OnClick(R.id.fab) void onFabClick() {
    if (!isFabOpen) {
      expandFabMenu();
    } else {
      collapseFabMenu();
    }
  }

  @OnClick(R.id.fab_washroom) void onFabWashroomClick() {
    ArrayList<Place> washrooms = presenter.getPlacesContainingString(places, PLACE_WASHROOM);
    showMarkers(washrooms);
  }

  @OnClick(R.id.fab_game) void onFabGameClick() {
    ArrayList<Place> gameLocations = presenter.getPlacesContainingString(places, PLACE_GAMING);
    showMarkers(gameLocations);
  }

  @OnClick(R.id.fab_food) void onFabFoodClick() {
    ArrayList<Place> foodBooths = presenter.getPlacesContainingString(places, PLACE_FOOD);
    showMarkers(foodBooths);
  }

  @OnClick(R.id.direction_button) void onDirectionButtonClick() {
    Position origin =
        Position.fromCoordinates(userLocation.getLongitude(), userLocation.getLatitude());
    Position destination = Position.fromCoordinates(currentMarker.getPosition().getLongitude(),
        currentMarker.getPosition().getLatitude());
    getRoute(origin, destination);
  }

  @OnClick(R.id.fab_location) void showMarkerOnUserLocation() {
    IconFactory iconFactory = IconFactory.getInstance(getContext());
    Icon icon = iconFactory.fromResource(R.drawable.ic_marker_blue);
    Marker marker = mapboxMap.addMarker(new MarkerOptions().position(userLocation.getLatLng())
        .title(userLocation.getName())
        .icon(icon));
    markers.add(marker);

    animateCameraToPosition(userLocation.getLatLng());
  }

  private void animateCameraToPosition(LatLng latLng) {
    CameraPosition position =
        new CameraPosition.Builder().target(latLng) // Sets the new camera position
            .tilt(30).build();
    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
  }

  private void getRoute(Position origin, Position destination) {
    MapboxDirections client =
        new MapboxDirections.Builder().setAccessToken(getString(R.string.mapbox_access_token))
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
        Log.d(TAG, "Response code: " + response.code());

        if (response.body() == null || response.body().getRoutes().size() < 1) {
          Log.e(TAG, "No routes found, make sure you set the right user and access token.");
          return;
        }
        currentRoute = response.body().getRoutes().get(0);
        Log.d(TAG, "Distance: " + currentRoute.getDistance());
        Toast.makeText(getContext(), "Distance: " + currentRoute.getDistance(), Toast.LENGTH_SHORT)
            .show();
        drawRoute(currentRoute);
      }

      @Override public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
        Log.e(TAG, "Error: " + throwable.getMessage());
        Toast.makeText(getContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void drawRoute(DirectionsRoute route) {
    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    // Convert LineString coordinates into LatLng[]
    LineString lineString = LineString.fromPolyline(route.getGeometry(), PRECISION_6);
    List<Position> coordinates = lineString.getCoordinates();
    LatLng[] points = new LatLng[coordinates.size()];
    for (int i = 0; i < coordinates.size(); i++) {
      points[i] = new LatLng(coordinates.get(i).getLatitude(), coordinates.get(i).getLongitude());
    }

    if (currentRoutePolyline != null) mapboxMap.removePolyline(currentRoutePolyline);
    currentRoutePolyline = mapboxMap.addPolyline(
        new PolylineOptions().add(points).color(Color.parseColor("#007cbf")).width(3));
    showMarkerOnUserLocation();
  }

  private void showMarkers(ArrayList<Place> placeList) {
    clearMarkers();
    ArrayList<LatLng> locations = new ArrayList<>();
    for (Place place : placeList) {
      locations.add(place.getLatLng());
      Marker marker = mapboxMap.addMarker(
          new MarkerOptions().position(place.getLatLng()).title(place.getName()));
      markers.add(marker);
    }
    LatLngBounds.Builder latLngBoundsBuilder = new LatLngBounds.Builder();
    for (LatLng location : locations) {
      latLngBoundsBuilder.include(location);
    }
    LatLngBounds latLngBounds = latLngBoundsBuilder.build();
    mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100), 2000);
  }

  private void clearMarkers() {
    for (Marker marker : markers) {
      mapboxMap.removeMarker(marker);
    }
  }

  private void expandFabMenu() {
    isFabOpen = true;
    fab.setImageResource(R.drawable.ic_fab_collapse);
    fab.setBackgroundTintList(
        ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.white)));
    fabWashroom.animate().translationX(-(fab.getWidth() + 16));
    fabGame.animate().translationX(-(fab.getWidth() + 16) * 2);
    fabFood.animate().translationX(-(fab.getWidth() + 16) * 3);
  }

  private void collapseFabMenu() {
    isFabOpen = false;
    clearMarkers();
    fab.setImageResource(R.drawable.ic_fab_expand);
    fab.setBackgroundTintList(
        ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.colorAccent)));
    fabWashroom.animate().translationX(0);
    fabGame.animate().translationX(0);
    fabFood.animate().translationX(0);
  }

  private void setCurrentLocation() {
    userLocation.setLatitude(USER_LATITUDE);
    userLocation.setLongitude(USER_LONGITUDE);
    userLocation.setName(CURRENT_LOCATION);
  }

  private SlidingUpPanelLayout.PanelSlideListener onSlideListener() {
    return new SlidingUpPanelLayout.PanelSlideListener() {
      @Override public void onPanelSlide(View view, float v) {
      }

      @Override
      public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState,
          SlidingUpPanelLayout.PanelState newState) {
        if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
          slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        }
      }
    };
  }

  private class DrawTrackGeoJson extends AsyncTask<Void, Void, List<LatLng>> {

    @Override protected List<LatLng> doInBackground(Void... voids) {
      ArrayList<LatLng> points = GeoJsonUtils.parseCoordinates(getActivity(), "track.geojson");
      return points;
    }

    @Override protected void onPostExecute(List<LatLng> points) {
      super.onPostExecute(points);
      if (points.size() > 0) {
        mapboxMap.addPolyline(new PolylineOptions().addAll(points)
            .color(ContextCompat.getColor(getActivity(), R.color.white))
            .width(4));
      }
    }
  }

  private class DrawEVillageGeoJson extends AsyncTask<Void, Void, List<LatLng>> {

    @Override protected List<LatLng> doInBackground(Void... voids) {
      ArrayList<LatLng> points = GeoJsonUtils.parseCoordinates(getActivity(), "evillage.geojson");
      return points;
    }

    @Override protected void onPostExecute(List<LatLng> points) {
      super.onPostExecute(points);
      if (points.size() > 0) {
        mapboxMap.addPolyline(new PolylineOptions().addAll(points)
            .color(ContextCompat.getColor(getActivity(), R.color.colorAccent))
            .width(4));
      }
    }
  }
}
