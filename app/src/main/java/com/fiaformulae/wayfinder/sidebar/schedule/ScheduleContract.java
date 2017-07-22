package com.fiaformulae.wayfinder.sidebar.schedule;

import com.fiaformulae.wayfinder.models.Event;
import java.util.ArrayList;

public interface ScheduleContract {
  public interface View {
    void showProgressBar();

    void hideProgressBar();

    void onGettingEvents(ArrayList<Event> events);
  }

  public interface Presenter {
    void onDestroy();

    void getEvents();
  }
}
