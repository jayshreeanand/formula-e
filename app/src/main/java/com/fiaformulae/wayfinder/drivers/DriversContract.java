package com.fiaformulae.wayfinder.drivers;

import com.fiaformulae.wayfinder.models.Team;

public interface DriversContract {
  public interface View {

  }

  public interface Presenter {
    Team getTeam(int teamId);
  }
}
