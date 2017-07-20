package com.fiaformulae.wayfinder.sidebar.teams;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.fiaformulae.wayfinder.R;

public class TeamsFragment extends Fragment implements TeamsContract.View {
  TeamsContract.Presenter presenter;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_teams, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    presenter = new TeamsPresenter(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }
}
