package com.fiaformulae.wayfinder.sidebar.faq;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import butterknife.ButterKnife;

public class FaqFragment extends Fragment implements FaqContract.View {
  FaqContract.Presenter presenter;

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    presenter = new FaqPresenter(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }
}
