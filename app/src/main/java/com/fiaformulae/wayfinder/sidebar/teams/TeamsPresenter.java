package com.fiaformulae.wayfinder.sidebar.teams;

import com.fiaformulae.wayfinder.network.WayfinderApi;
import com.fiaformulae.wayfinder.utils.RxUtils;
import rx.subscriptions.CompositeSubscription;

public class TeamsPresenter implements TeamsContract.Presenter {
  private TeamsContract.View view;
  private CompositeSubscription compositeSubscription;
  private WayfinderApi wayfinderApi;

  public TeamsPresenter(TeamsContract.View view) {
    this.view = view;
    compositeSubscription = new CompositeSubscription();
    wayfinderApi = WayfinderApi.getInstance();
  }

  @Override public void onDestroy() {
    RxUtils.clearCompositeSubscription(compositeSubscription);
  }
}
