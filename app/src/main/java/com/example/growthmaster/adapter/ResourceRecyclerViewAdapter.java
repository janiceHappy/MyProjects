package com.example.growthmaster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.growthmaster.R;

public class StudyResourceAdapter extends RecyclerView.Adapter<StudyResourceAdapter.ViewHolder> {

    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }

    public StudyResourceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_resource_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }


}
