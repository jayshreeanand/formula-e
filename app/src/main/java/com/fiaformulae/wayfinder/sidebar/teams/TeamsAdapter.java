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
import java.util.ArrayList;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {
  private ArrayList<Team> teams;
  private Context context;

  public TeamsAdapter(Context context, ArrayList<Team> teams) {
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
    Glide.with(context).load(team.getImage().getDefault()).into(holder.teamImageView);

    holder.getView().setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        context.startActivity(new Intent(context, DriversActivity.class));
      }
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
