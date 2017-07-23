package com.fiaformulae.wayfinder.utils;

import android.content.Context;
import android.util.Log;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.commons.utils.TextUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeoJsonUtils {
  private static final String TAG = "GeoJsonUtils";

  public static ArrayList<LatLng> parseCoordinates(Context context, String filename) {
    ArrayList<LatLng> points = new ArrayList<>();
    try {
      // Load GeoJSON file
      InputStream inputStream = context.getAssets().open(filename);
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
}
