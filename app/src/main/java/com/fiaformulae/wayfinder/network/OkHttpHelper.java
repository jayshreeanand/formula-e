package com.fiaformulae.wayfinder.network;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpHelper {
  private OkHttpHelper() {
  }

  public static OkHttpClient getOkHttpClientInstance() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    return new OkHttpClient.Builder().connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
        .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS,
            ConnectionSpec.CLEARTEXT))
        .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
        .addInterceptor(new WayfinderInterceptor())
        .addInterceptor(logging)
        .build();
  }
}
