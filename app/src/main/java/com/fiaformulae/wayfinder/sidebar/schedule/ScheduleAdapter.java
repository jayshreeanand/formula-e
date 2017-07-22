package com.fiaformulae.wayfinder.sidebar.schedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fiaformulae.wayfinder.R;
import com.fiaformulae.wayfinder.models.Event;
import com.fiaformulae.wayfinder.utils.DateUtils;
import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
  private ArrayList<Event> events;

  public ScheduleAdapter(ArrayList<Event> events) {
    this.events = events;
  }

  @Override public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override public void onBindViewHolder(ScheduleAdapter.ViewHolder holder, int position) {
    Event event = events.get(position);
    holder.topLine.setVisibility(View.VISIBLE);
    holder.bottomLine.setVisibility(View.VISIBLE);
    if (position == 0) holder.topLine.setVisibility(View.GONE);
    if (position == getItemCount() - 1) holder.bottomLine.setVisibility(View.GONE);

    holder.startTimeView.setText(DateUtils.getTimeString(event.getStartTime()));
    holder.endTimeView.setText(DateUtils.getTimeString(event.getEndTime()));
    holder.eventNameView.setText(event.getName());
    holder.placeNameView.setText(event.getPlace().getName());

    holder.getView().setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

      }
    });
  }

  @Override public int getItemCount() {
    return events.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.start_time) TextView startTimeView;
    @BindView(R.id.end_time) TextView endTimeView;
    @BindView(R.id.event_name) TextView eventNameView;
    @BindView(R.id.place_name) TextView placeNameView;
    @BindView(R.id.top_line) View topLine;
    @BindView(R.id.bottom_line) View bottomLine;
    private View view;

    public ViewHolder(View view) {
      super(view);
      this.view = view;
      ButterKnife.bind(this, view);
    }

    public View getView() {
      return view;
    }
  }
}
