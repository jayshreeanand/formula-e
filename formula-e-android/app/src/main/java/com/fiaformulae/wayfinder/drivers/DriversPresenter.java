package com.fiaformulae.wayfinder.drivers;

import com.activeandroid.query.Select;
import com.fiaformulae.wayfinder.models.Team;

public class DriversPresenter implements DriversContract.Presenter {
  private DriversContract.View view;

  public DriversPresenter(DriversContract.View view) {
    this.view = view;
  }

  @Override public Team getTeam(int teamId) {
    return new Select().from(Team.class).where("remote_id = ?", teamId).executeSingle();
  }
}
