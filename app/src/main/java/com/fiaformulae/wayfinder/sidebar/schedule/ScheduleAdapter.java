package com.fiaformulae.wayfinder.sidebar.schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fiaformulae.wayfinder.R;
import com.fiaformulae.wayfinder.models.Event;
import com.fiaformulae.wayfinder.models.Place;
import com.fiaformulae.wayfinder.utils.DateUtils;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
  private List<Event> events;
  private Context context;
  private EventClickListener eventClickListener;

  public ScheduleAdapter(Context context, List<Event> events, EventClickListener listener) {
    this.context = context;
    this.events = events;
    this.eventClickListener = listener;
  }

  @Override public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override public void onBindViewHolder(ScheduleAdapter.ViewHolder holder, int position) {
    Event event = events.get(position);

    holder.startTimeView.setText(DateUtils.getTimeString(event.getStartTime()));
    holder.endTimeView.setText(DateUtils.getTimeString(event.getEndTime()));
    holder.eventNameView.setText(event.getName());
    if (event.getPlace() != null) {
      holder.placeNameView.setText(event.getPlace().getName());
    }

    holder.getView().setOnClickListener(view -> {
      if (event.getPlace() == null) return;
      eventClickListener.onEventClick(event.getPlace());
    });
  }

  @Override public int getItemCount() {
    return events.size();
  }

  public interface EventClickListener {
    public void onEventClick(Place place);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.start_time) TextView startTimeView;
    @BindView(R.id.end_time) TextView endTimeView;
    @BindView(R.id.event_name) TextView eventNameView;
    @BindView(R.id.place_name) TextView placeNameView;
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
