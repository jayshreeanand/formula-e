package com.fiaformulae.wayfinder.sidebar.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fiaformulae.wayfinder.MainActivity;
import com.fiaformulae.wayfinder.R;

public class HomeFragment extends Fragment implements HomeContract.View {
  @BindView(R.id.menu_icon) ImageView menuIcon;
  private HomeContract.Presenter presenter;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    presenter = new HomePresenter(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @OnClick(R.id.menu_icon) public void onMenuClick() {
    ((MainActivity) getActivity()).openDrawer();
  }
}
