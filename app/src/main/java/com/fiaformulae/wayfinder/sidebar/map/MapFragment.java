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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fiaformulae.wayfinder.MainActivity;
import com.fiaformulae.wayfinder.R;
import com.fiaformulae.wayfinder.models.Place;
import com.fiaformulae.wayfinder.utils.GeoJsonUtils;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import java.util.ArrayList;
import java.util.List;

import static com.fiaformulae.wayfinder.AppConstants.CURRENT_LOCATION;
import static com.fiaformulae.wayfinder.AppConstants.PLACES;
import static com.fiaformulae.wayfinder.AppConstants.PLACE_FOOD;
import static com.fiaformulae.wayfinder.AppConstants.PLACE_GAMING;
import static com.fiaformulae.wayfinder.AppConstants.PLACE_WASHROOM;

public class MapFragment extends Fragment
    implements MapContract.View, OnMapReadyCallback, Map.MapListener {
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
  private List<Place> places;
  private boolean isFabOpen = false;
  private Map map;

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
    map = new Map(getActivity(), mapboxMap, this);
    map.setCurrentLocation();
    new DrawTrackGeoJson().execute();
    new DrawEVillageGeoJson().execute();
    if (getArguments() != null && getArguments().getSerializable(PLACES) != null) {
      ArrayList<Place> markerPlaces = (ArrayList<Place>) getArguments().getSerializable(PLACES);
      map.showMarkers(markerPlaces);
    }
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
    map.showMarkers(washrooms);
  }

  @OnClick(R.id.fab_game) void onFabGameClick() {
    ArrayList<Place> gameLocations = presenter.getPlacesContainingString(places, PLACE_GAMING);
    map.showMarkers(gameLocations);
  }

  @OnClick(R.id.fab_food) void onFabFoodClick() {
    ArrayList<Place> foodBooths = presenter.getPlacesContainingString(places, PLACE_FOOD);
    map.showMarkers(foodBooths);
  }

  @OnClick(R.id.direction_button) void onDirectionButtonClick() {
    map.showRoute();
  }

  @OnClick(R.id.fab_location) void onFabLocationClick() {
    map.showMarkerOnUserLocation();
    map.animateCameraToPosition(map.userLocation.getLatLng());
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
    map.clearMarkers();
    fab.setImageResource(R.drawable.ic_fab_expand);
    fab.setBackgroundTintList(
        ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.colorAccent)));
    fabWashroom.animate().translationX(0);
    fabGame.animate().translationX(0);
    fabFood.animate().translationX(0);
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

  @Override public void onDrawRoute() {
    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
  }

  @Override public void onMarkerClicked(String title) {
    locationText.setText(title);
    directionButton.setVisibility(View.VISIBLE);
    if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
      slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }
    if (CURRENT_LOCATION.equalsIgnoreCase(title)) {
      directionButton.setVisibility(View.GONE);
    }
  }

  private class DrawTrackGeoJson extends AsyncTask<Void, Void, List<LatLng>> {

    @Override protected List<LatLng> doInBackground(Void... voids) {
      ArrayList<LatLng> points = GeoJsonUtils.parseCoordinates(getActivity(), "track.geojson");
      return points;
    }

    @Override protected void onPostExecute(List<LatLng> points) {
      super.onPostExecute(points);
      if (points.size() > 0) {
        map.addPolyline(R.color.white, 4, points);
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
        map.addPolyline(R.color.colorAccent, 4, points);
      }
    }
  }
}
