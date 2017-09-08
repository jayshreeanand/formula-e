package com.fiaformulae.wayfinder.sidebar.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fiaformulae.wayfinder.MainActivity;
import com.fiaformulae.wayfinder.R;
import com.fiaformulae.wayfinder.models.Weather;
import java.text.DecimalFormat;

public class WeatherFragment extends Fragment implements WeatherContract.View {
  @BindView(R.id.temperature) TextView temperatureView;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
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
    temperatureView.setText("");
    presenter.getWeather();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override public void showProgressbar() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideProgresbar() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void onGettingWeather(Weather weather) {
    DecimalFormat df = new DecimalFormat("#.#");
    temperatureView.setText(df.format(weather.getCurrentTemperature()) + " C");
  }
}
