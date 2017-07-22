package com.fiaformulae.wayfinder.sidebar.schedule;

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
import com.fiaformulae.wayfinder.models.Event;
import java.util.ArrayList;

public class ScheduleFragment extends Fragment implements ScheduleContract.View {
  ScheduleContract.Presenter presenter;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.event_list) RecyclerView eventsRecyclerView;
  private RecyclerView.Adapter adapter;
  private RecyclerView.LayoutManager layoutManager;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_schedule, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    setAdapter(new ArrayList<>());

    presenter = new SchedulePresenter(this);
    presenter.getEvents();
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

  @Override public void onGettingEvents(ArrayList<Event> events) {
    setAdapter(events);
  }

  private void setAdapter(ArrayList<Event> events) {
    adapter = new ScheduleAdapter(events);
    eventsRecyclerView.setAdapter(adapter);
  }
}
