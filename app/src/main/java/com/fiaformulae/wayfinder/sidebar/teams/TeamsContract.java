package com.fiaformulae.wayfinder.sidebar.teams;

import com.fiaformulae.wayfinder.models.Team;
import java.util.ArrayList;

public interface TeamsContract {
  public interface View {
    void showProgressBar();

    void hideProgressBar();

    void onGettingTeams(ArrayList<Team> teams);
  }

  public interface Presenter {
    void onDestroy();

    void getTeams();
  }
}
