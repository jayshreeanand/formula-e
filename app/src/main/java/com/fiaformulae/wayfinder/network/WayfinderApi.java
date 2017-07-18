package com.fiaformulae.wayfinder.network;

import com.fiaformulae.wayfinder.Wayfinder;
import okhttp3.OkHttpClient;

public class WayfinderApi {
  private static WayfinderService wayfinderService;

  public WayfinderApi(String baseUrl, OkHttpClient okHttpClient) {
    wayfinderService =
        ServiceGenerator.createService(WayfinderService.class, okHttpClient, baseUrl);
  }

  public static WayfinderApi getInstance() {
    return new WayfinderApi(Wayfinder.getBaseUrl(), OkHttpHelper.getOkHttpClientInstance());
  }
}
