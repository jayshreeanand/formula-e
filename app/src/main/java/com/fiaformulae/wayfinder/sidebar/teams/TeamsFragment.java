package com.fiaformulae.wayfinder.sidebar.teams;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fiaformulae.wayfinder.R;
import com.fiaformulae.wayfinder.models.Team;
import java.util.ArrayList;

public class TeamsFragment extends Fragment implements TeamsContract.View {
  TeamsContract.Presenter presenter;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.team_list) RecyclerView teamsRecyclerView;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_teams, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    teamsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    setAdapter(new ArrayList<>());

    presenter = new TeamsPresenter(this);
    presenter.getTeams();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override public void showProgressBar() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideProgressBar() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void onGettingTeams(ArrayList<Team> teams) {
    setAdapter(teams);
  }

  private void setAdapter(ArrayList<Team> teams) {
    RecyclerView.Adapter adapter = new TeamsAdapter(getActivity(), teams);
    teamsRecyclerView.setAdapter(adapter);
  }
}
