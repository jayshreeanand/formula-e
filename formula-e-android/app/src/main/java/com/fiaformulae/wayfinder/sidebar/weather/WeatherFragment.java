package com.fiaformulae.wayfinder.sidebar.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.fiaformulae.wayfinder.MainActivity;
import com.fiaformulae.wayfinder.R;

public class WeatherFragment extends Fragment implements WeatherContract.View {
  private WeatherContract.Presenter presenter;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_weather, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    ((MainActivity) getActivity()).showToolbar();
    presenter = new WeatherPresenter(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }
}
