package com.fiaformulae.wayfinder.sidebar.raceday;

import com.fiaformulae.wayfinder.network.WayfinderApi;
import com.fiaformulae.wayfinder.utils.RxUtils;
import rx.subscriptions.CompositeSubscription;

public class RaceDayPresenter implements RaceDayContract.Presenter {
  private RaceDayContract.View view;
  private CompositeSubscription compositeSubscription;
  private WayfinderApi wayfinderApi;

  public RaceDayPresenter(RaceDayContract.View view) {
    this.view = view;
    compositeSubscription = new CompositeSubscription();
    wayfinderApi = WayfinderApi.getInstance();
  }

  @Override public void onDestroy() {
    RxUtils.clearCompositeSubscription(compositeSubscription);
  }
}
