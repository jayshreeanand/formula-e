package com.fiaformulae.wayfinder.sidebar.teams;

import android.util.Log;
import com.fiaformulae.wayfinder.models.Team;
import com.fiaformulae.wayfinder.network.WayfinderApi;
import com.fiaformulae.wayfinder.utils.RxUtils;
import java.util.ArrayList;
import rx.observables.ConnectableObservable;
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

  @Override public void getTeams() {
    view.showProgressBar();
    ConnectableObservable<ArrayList<Team>> observable = wayfinderApi.getTeams();
    compositeSubscription.add(observable.connect());
    compositeSubscription.add(
        observable.subscribe(this::onGetTeamsSuccess, this::onGetTeamsFailure));
  }

  private void onGetTeamsSuccess(ArrayList<Team> teams) {
    view.hideProgressBar();
    view.onGettingTeams(teams);
  }

  private void onGetTeamsFailure(Throwable throwable) {
    view.hideProgressBar();
    Log.d("Error", "Failed to get teams - " + throwable.getMessage());
  }
}
