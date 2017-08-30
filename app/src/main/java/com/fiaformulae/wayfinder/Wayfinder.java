package com.fiaformulae.wayfinder;

import android.content.Context;
import timber.log.Timber;

public class Wayfinder extends com.activeandroid.app.Application {
  private static Context context;

  public static String getBaseUrl() {
    return BuildConfig.BASE_URL;
  }

  public static Context getAppContext() {
    return context;
  }

  @Override public void onCreate() {
    super.onCreate();

    Timber.plant(new Timber.DebugTree());
    context = getApplicationContext();
  }
}
