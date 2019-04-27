package com.example.growthmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.growthmaster.MasterDetailActivity;
import com.example.growthmaster.R;
import com.example.growthmaster.ResourceContent;
import com.example.growthmaster.db.Resource;
import com.google.gson.Gson;

import java.util.List;

public class ResourceRecyclerViewAdapter extends RecyclerView.Adapter<ResourceRecyclerViewAdapter.ViewHolder> {

    private FragmentManager fManager;
    private List<Resource> mResourceList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View resourceView;
        TextView resourceTitle;
        TextView resourceAuthor;
        TextView resourcePublishTime;
        ImageView resourceImage;

        public ViewHolder(View view) {
            super(view);
            resourceView = view;
            resourceTitle = (TextView) view.findViewById(R.id.study_resource_title);
            resourceAuthor = (TextView) view.findViewById(R.id.study_resource_author);
            resourcePublishTime = (TextView) view.findViewById(R.id.study_resource_publishTime);
            resourceImage = (ImageView) view.findViewById(R.id.study_resource_image);
        }
    }

    public ResourceRecyclerViewAdapter(List<Resource> resourceList) {
        mResourceList = resourceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_resource_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.resourceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Resource resource = mResourceList.get(position);
                Intent intent = new Intent(view.getContext(), ResourceContent.class);
                intent.putExtra("resource",new Gson().toJson(resource));
                view.getContext().startActivity(intent);

            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Resource resource = mResourceList.get(position);
        holder.resourceTitle.setText(resource.getTitle());
        holder.resourceAuthor.setText(resource.getAuthor());
        holder.resourcePublishTime.setText(resource.getPublishTime());
        holder.resourceImage.setImageResource(resource.getImageId());

    }

    @Override
    public int getItemCount() {
        return mResourceList.size();
    }


}
