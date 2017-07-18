package com.fiaformulae.wayfinder.network;

import android.support.annotation.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
  private static Retrofit retroFit;

  private ServiceGenerator() {
  }

  public static <T> T createService(@NonNull Class<T> serviceClass, final OkHttpClient okHttpClient,
      @NonNull final String baseUrl) {
    retroFit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build();

    return retroFit.create(serviceClass);
  }

  public static Retrofit getRetroFit() {
    return retroFit;
  }
}
