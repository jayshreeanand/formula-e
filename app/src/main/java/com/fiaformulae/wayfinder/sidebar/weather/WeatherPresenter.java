package com.fiaformulae.wayfinder.sidebar.weather;

import android.util.Log;
import com.fiaformulae.wayfinder.models.Weather;
import com.fiaformulae.wayfinder.network.WayfinderApi;
import com.fiaformulae.wayfinder.utils.RxUtils;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;

import static com.fiaformulae.wayfinder.AppConstants.USER_LATITUDE;
import static com.fiaformulae.wayfinder.AppConstants.USER_LONGITUDE;

public class WeatherPresenter implements WeatherContract.Presenter {
  private static final String TAG = "WeatherPresenter";
  private WeatherContract.View view;
  private CompositeSubscription compositeSubscription;
  private WayfinderApi wayfinderApi;

  public WeatherPresenter(WeatherContract.View view) {
    this.view = view;
    compositeSubscription = new CompositeSubscription();
    wayfinderApi = WayfinderApi.getInstance();
  }

  @Override public void onDestroy() {
    RxUtils.clearCompositeSubscription(compositeSubscription);
  }

  @Override public void getWeather() {
    view.showProgressbar();
    ConnectableObservable<Weather> observable =
        wayfinderApi.getWeather(USER_LATITUDE, USER_LONGITUDE);
    compositeSubscription.add(observable.connect());
    compositeSubscription.add(
        observable.subscribe(this::onGetWeatherSuccess, this::onGetWeatherFailure));
  }

  private void onGetWeatherSuccess(Weather weather) {
    view.hideProgresbar();
    view.onGettingWeather(weather);
  }

  private void onGetWeatherFailure(Throwable throwable) {
    view.hideProgresbar();
    Log.d(TAG, throwable.getMessage());
  }
}
