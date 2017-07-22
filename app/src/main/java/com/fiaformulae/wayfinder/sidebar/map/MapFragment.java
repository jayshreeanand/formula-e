package com.fiaformulae.wayfinder.sidebar.map;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fiaformulae.wayfinder.MainActivity;
import com.fiaformulae.wayfinder.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.commons.utils.TextUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback {
  private static final String TAG = "MapFragment";
  @BindView(R.id.mapview) MapView mapView;
  private MapContract.Presenter presenter;
  private MapboxMap mapboxMap;

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
  }

  private class DrawTrackGeoJson extends AsyncTask<Void, Void, List<LatLng>> {
    @Override protected List<LatLng> doInBackground(Void... voids) {

      ArrayList<LatLng> points = new ArrayList<>();

      try {
        // Load GeoJSON file
        InputStream inputStream = getActivity().getAssets().open("track.geojson");
        BufferedReader rd =
            new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }

        inputStream.close();

        // Parse JSON
        JSONObject json = new JSONObject(sb.toString());
        JSONArray features = json.getJSONArray("features");
        JSONObject feature = features.getJSONObject(0);
        JSONObject geometry = feature.getJSONObject("geometry");
        if (geometry != null) {
          String type = geometry.getString("type");

          // Our GeoJSON only has one feature: a line string
          if (!TextUtils.isEmpty(type) && type.equalsIgnoreCase("LineString")) {

            // Get the Coordinates
            JSONArray coords = geometry.getJSONArray("coordinates");
            for (int lc = 0; lc < coords.length(); lc++) {
              JSONArray coord = coords.getJSONArray(lc);
              LatLng latLng = new LatLng(coord.getDouble(1), coord.getDouble(0));
              points.add(latLng);
            }
          }
        }
      } catch (Exception exception) {
        Log.e(TAG, "Exception Loading GeoJSON: " + exception.toString());
      }

      return points;
    }

    @Override protected void onPostExecute(List<LatLng> points) {
      super.onPostExecute(points);

      if (points.size() > 0) {

        // Draw polyline on map
        mapboxMap.addPolyline(
            new PolylineOptions().addAll(points).color(Color.parseColor("#FFFFFF")).width(4));
      }
    }
  }
}
