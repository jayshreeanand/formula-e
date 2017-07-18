package com.fiaformulae.wayfinder.sidebar.weather;

import com.fiaformulae.wayfinder.network.WayfinderApi;
import com.fiaformulae.wayfinder.utils.RxUtils;
import rx.subscriptions.CompositeSubscription;

public class WeatherPresenter implements WeatherContract.Presenter {
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
}
