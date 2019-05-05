package com.example.growthmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.growthmaster.BroadcastDetailActivity;
import com.example.growthmaster.FMActivity;
import com.example.growthmaster.R;
import com.example.growthmaster.bean.FM;
import com.example.growthmaster.util.PictureLoader;
import com.google.gson.Gson;

import java.util.List;

public class FMAdapter extends RecyclerView.Adapter<FMAdapter.ViewHolder> {

    private Context mContext;
    private List<FM> mFMList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View fmView;
        ImageView fmCover;
        TextView fmTitle;
        TextView fmSpeak;
        TextView fmViewnum;

        public ViewHolder(View view){
            super(view);
            fmView = view;
            fmCover = (ImageView) view.findViewById(R.id.fm_cover);
            fmTitle = (TextView) view.findViewById(R.id.fm_title);
            fmSpeak = (TextView) view.findViewById(R.id.fm_speak);
            fmViewnum = (TextView) view.findViewById(R.id.fm_viewnum);
        }
    }

    public FMAdapter(Context context, List<FM> fmList) {
        mContext = context;
        mFMList = fmList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fm_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.fmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                FM fm = mFMList.get(position);
                Intent intent = new Intent(mContext, BroadcastDetailActivity.class);
                intent.putExtra("object_id",fm.getObject_id());
                intent.putExtra("media_url",fm.getUrl());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FM fm = mFMList.get(position);
        //loader.load(holder.fmCover, fm.getCover());
        RequestOptions options = new RequestOptions()
                .error(R.drawable.error)
                .placeholder(R.drawable.loading);
        Glide.with(mContext).load(fm.getCover()).apply(options).into(holder.fmCover);
        holder.fmTitle.setText(fm.getTitle());
        holder.fmSpeak.setText(fm.getSpeak());
        holder.fmViewnum.setText("收听 "+fm.getFavnum());
    }

    @Override
    public int getItemCount() {
        return mFMList.size();
    }


}
