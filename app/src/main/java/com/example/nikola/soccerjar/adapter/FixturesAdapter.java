package com.example.nikola.soccerjar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikola.soccerjar.R;
import com.example.nikola.soccerjar.database.ListItems;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.ViewHolder> {

    private List<ListItems> fixtureList = new ArrayList<>();

    public void updateList(List<ListItems> list) {
        this.fixtureList.clear();
        this.fixtureList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public FixturesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contactView = layoutInflater.inflate(R.layout.my_fixtures_view, parent, false);

        return new FixturesAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ListItems teamList = fixtureList.get(position);

        TextView viewHome = holder.txtHome;
        TextView viewAway = holder.txtAway;
        TextView viewHomeScore = holder.txtHomeScore;
        TextView viewAwayScore = holder.txtAwayScore;

        viewHome.setText(teamList.getHomeTeamName());
        viewAway.setText(teamList.getAwayTeamName());
//        viewHomeScore.setText(teamList.getResult().getGoalsHomeTeam() + "");
//        viewAwayScore.setText(teamList.getResult().getGoalsAwayTeam() + "");
        viewHomeScore.setText(teamList.getStatus());
        viewAwayScore.setText(teamList.getStatus());
    }

    @Override
    public int getItemCount() {
        return fixtureList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_home)
        TextView txtHome;
        @BindView(R.id.txt_away)
        TextView txtAway;
        @BindView(R.id.text_homeScore)
        TextView txtHomeScore;
        @BindView(R.id.text_awayScore)
        TextView txtAwayScore;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}


