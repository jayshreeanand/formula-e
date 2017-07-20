package com.fiaformulae.wayfinder.sidebar.schedule;

import com.fiaformulae.wayfinder.network.WayfinderApi;
import com.fiaformulae.wayfinder.utils.RxUtils;
import rx.subscriptions.CompositeSubscription;

public class SchedulePresenter implements ScheduleContract.Presenter {
  private ScheduleContract.View view;
  private CompositeSubscription compositeSubscription;
  private WayfinderApi wayfinderApi;

  public SchedulePresenter(ScheduleContract.View view) {
    this.view = view;
    compositeSubscription = new CompositeSubscription();
    wayfinderApi = WayfinderApi.getInstance();
  }

  @Override public void onDestroy() {
    RxUtils.clearCompositeSubscription(compositeSubscription);
  }
}
