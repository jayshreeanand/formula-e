package com.fiaformulae.wayfinder.sidebar.schedule;

import android.util.Log;
import com.fiaformulae.wayfinder.models.Event;
import com.fiaformulae.wayfinder.network.WayfinderApi;
import com.fiaformulae.wayfinder.utils.RxUtils;
import java.util.ArrayList;
import java.util.Collections;
import rx.observables.ConnectableObservable;
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

  @Override public void getEvents() {
    view.showProgressBar();
    ConnectableObservable<ArrayList<Event>> observable = wayfinderApi.getEvents();
    compositeSubscription.add(observable.connect());
    compositeSubscription.add(
        observable.subscribe(this::onGetEventsSuccess, this::onGetEventsFailure));
  }

  private void onGetEventsSuccess(ArrayList<Event> events) {
    view.hideProgressBar();
    Collections.sort(events);
    view.onGettingEvents(events);
  }

  private void onGetEventsFailure(Throwable throwable) {
    view.hideProgressBar();
    Log.d("Error", "Failed to get events - " + throwable.getMessage());
  }
}
