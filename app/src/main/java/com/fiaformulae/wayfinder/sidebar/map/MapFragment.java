package com.fiaformulae.wayfinder.sidebar.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fiaformulae.wayfinder.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback {
  @BindView(R.id.mapview) MapView mapView;
  private MapContract.Presenter presenter;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    Mapbox.getInstance(getActivity(), getString(R.string.mapbox_access_token));
    return inflater.inflate(R.layout.fragment_map, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    presenter = new MapPresenter(this);

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
    mapboxMap.setMyLocationEnabled(true);
    //LatLng latLng = new LatLng(mapboxMap.getMyLocation().getLatitude(), mapboxMap.getMyLocation().getLongitude());
    //Log.d("location", latLng.toString());
  }
}
