package com.fiaformulae.wayfinder.sidebar.teams;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.fiaformulae.wayfinder.R;
import com.fiaformulae.wayfinder.drivers.DriversActivity;
import com.fiaformulae.wayfinder.models.Team;
import java.util.List;

import static com.fiaformulae.wayfinder.AppConstants.TEAM_ID;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {
  private List<Team> teams;
  private Context context;

  public TeamsAdapter(Context context, List<Team> teams) {
    this.context = context;
    this.teams = teams;
  }

  @Override public TeamsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override public void onBindViewHolder(TeamsAdapter.ViewHolder holder, int position) {
    Team team = teams.get(position);
    holder.teamNameView.setText(team.getName());
    holder.teamDescriptionView.setText(team.getDescription());
    if (team.getImage() != null) {
      Glide.with(context).load(team.getImage().getDefault()).into(holder.teamImageView);
    }

    holder.getView().setOnClickListener(view -> {
      Intent intent = new Intent(context, DriversActivity.class);
      intent.putExtra(TEAM_ID, team.getRemoteId());
      context.startActivity(intent);
    });
  }

  @Override public int getItemCount() {
    return teams.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.team_image) ImageView teamImageView;
    @BindView(R.id.team_name) TextView teamNameView;
    @BindView(R.id.team_description) TextView teamDescriptionView;
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
