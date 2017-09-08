package com.fiaformulae.wayfinder.drivers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.fiaformulae.wayfinder.R;
import com.fiaformulae.wayfinder.models.Driver;

public class DriverFragment extends Fragment {
  public static final String DRIVER_IMAGE = "driver_image";
  public static final String DRIVER_DESCRIPTION = "driver_description";
  @BindView(R.id.driver_description) TextView driverDescriptionView;
  @BindView(R.id.driver_image) ImageView driverImageView;
  private String driverImage;
  private String driverDescription;

  public static DriverFragment newInstance(Driver driver) {
    DriverFragment fragment = new DriverFragment();
    Bundle args = new Bundle();
    args.putString(DRIVER_IMAGE, driver.getImageDefault());
    args.putString(DRIVER_DESCRIPTION, driver.getDescription());
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_driver, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    driverImage = getArguments().getString(DRIVER_IMAGE);
    driverDescription = getArguments().getString(DRIVER_DESCRIPTION);
    Glide.with(getActivity()).load(driverImage).into(driverImageView);
    driverDescriptionView.setText(driverDescription);
  }
}
