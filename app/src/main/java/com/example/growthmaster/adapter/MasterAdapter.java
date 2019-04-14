package com.example.growthmaster.util;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.growthmaster.MasterDetailActivity;
import com.example.growthmaster.PsychologicalServices;
import com.example.growthmaster.R;
import com.example.growthmaster.db.Master;
import com.google.gson.Gson;


import java.util.List;

public class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.ViewHolder> {

    private List<Master> mMasterList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View masterView;
        ImageView masterImage;
        TextView masterName;
        TextView masterTitle;
        TextView masterField;
        TextView masterPrice;

        public ViewHolder(View view){
            super(view);
            masterView = view;
            masterImage = (ImageView) view.findViewById(R.id.master_image);
            masterName = (TextView) view.findViewById(R.id.master_name);
            masterTitle = (TextView) view.findViewById(R.id.master_title);
            masterField = (TextView) view.findViewById(R.id.master_field);
            masterPrice = (TextView) view.findViewById(R.id.master_price);
        }
    }
    public MasterAdapter(List<Master> masterList) {
        mMasterList = masterList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.master_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.masterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Master master = mMasterList.get(position);
                Intent intent = new Intent(view.getContext(), MasterDetailActivity.class);
                intent.putExtra("master",new Gson().toJson(master));
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Master master = mMasterList.get(position);
        holder.masterImage.setImageResource(master.getImageId());
        holder.masterName.setText(master.getName());
        holder.masterTitle.setText(master.getTitle());
        holder.masterField.setText(master.getField());
        holder.masterField.setText(master.getField());
    }

    @Override
    public int getItemCount() {
        return mMasterList.size();
    }
}
