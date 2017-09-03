package com.fiaformulae.wayfinder.network;

import android.support.annotation.NonNull;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Modifier;
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
        .addConverterFactory(GsonConverterFactory.create(
            new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT,
                Modifier.STATIC).serializeNulls().create()))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build();

    return retroFit.create(serviceClass);
  }

  public static Retrofit getRetroFit() {
    return retroFit;
  }
}
