package com.fiaformulae.wayfinder.sidebar.map;

import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fiaformulae.wayfinder.MainActivity;
import com.fiaformulae.wayfinder.R;
import com.fiaformulae.wayfinder.models.Place;
import com.fiaformulae.wayfinder.utils.GeoJsonUtils;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import java.util.ArrayList;
import java.util.List;

import static com.fiaformulae.wayfinder.AppConstants.PLACE_FOOD;
import static com.fiaformulae.wayfinder.AppConstants.PLACE_GAMING;
import static com.fiaformulae.wayfinder.AppConstants.PLACE_WASHROOM;

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback {
  private static final String TAG = "MapFragment";
  @BindView(R.id.mapview) MapView mapView;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.fab_washroom) FloatingActionButton fabWashroom;
  @BindView(R.id.fab_game) FloatingActionButton fabGame;
  @BindView(R.id.fab_food) FloatingActionButton fabFood;
  private MapContract.Presenter presenter;
  private MapboxMap mapboxMap;
  private ArrayList<Place> places;
  private boolean isFabOpen = false;
  private ArrayList<Marker> markers = new ArrayList<>();

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
  }

  @Override public void showProgressBar() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideProgressBar() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void onGettingPlaces(ArrayList<Place> places) {
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
    showMapMarkers(washrooms);
  }

  @OnClick(R.id.fab_game) void onFabGameClick() {
    ArrayList<Place> gameLocations = presenter.getPlacesContainingString(places, PLACE_GAMING);
    showMapMarkers(gameLocations);
  }

  @OnClick(R.id.fab_food) void onFabFoodClick() {
    ArrayList<Place> foodBooths = presenter.getPlacesContainingString(places, PLACE_FOOD);
    showMapMarkers(foodBooths);
  }

  private void showMapMarkers(ArrayList<Place> placeList) {
    clearMapMarkers();
    for (Place place : placeList) {
      Marker marker = mapboxMap.addMarker(
          new MarkerOptions().position(new LatLng(place.getLatitude(), place.getLongitude()))
              .title(place.getName()));
      markers.add(marker);
    }
  }

  private void clearMapMarkers() {
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
    clearMapMarkers();
    fab.setImageResource(R.drawable.ic_fab_expand);
    fab.setBackgroundTintList(
        ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.colorAccent)));
    fabWashroom.animate().translationX(0);
    fabGame.animate().translationX(0);
    fabFood.animate().translationX(0);
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
