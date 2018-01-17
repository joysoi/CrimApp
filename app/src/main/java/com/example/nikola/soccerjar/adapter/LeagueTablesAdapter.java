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


public class LeagueTablesAdapter extends RecyclerView.Adapter<LeagueTablesAdapter.ViewHolder> {

    private List<ListItems> teamList = new ArrayList<>();

    public void updateLeagueTable(List<ListItems> teamList) {
        this.teamList.clear();
        this.teamList.addAll(teamList);
        notifyDataSetChanged();
    }

    @Override
    public LeagueTablesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.my_detail_view, parent, false);

        return new LeagueTablesAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(final LeagueTablesAdapter.ViewHolder holder, int position) {

        final ListItems resultsParcelable = teamList.get(position);
        TextView teamName = holder.txtTeamName;
        TextView pos = holder.txtPosition;
        TextView pnts = holder.txtPoints;
        TextView pld = holder.txtPlayed;
        TextView gd = holder.txtGDiff;
        teamName.setText(resultsParcelable.getTeamName());
        pos.setText(resultsParcelable.getPosition());
        pnts.setText(resultsParcelable.getPoints());
        pld.setText(resultsParcelable.getPlayedGames());
        gd.setText(resultsParcelable.getGoalDifference());

    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_teamName)
        TextView txtTeamName;
        @BindView(R.id.txt_position)
        TextView txtPosition;
        @BindView(R.id.txt_points)
        TextView txtPoints;
        @BindView(R.id.txt_played)
        TextView txtPlayed;
        @BindView(R.id.txt_gdiff)
        TextView txtGDiff;
        @BindView(R.id.separatorView)
        TextView itmSeparator;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}































