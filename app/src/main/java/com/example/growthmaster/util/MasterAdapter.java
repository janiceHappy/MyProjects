package com.example.growthmaster.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.growthmaster.R;
import com.example.growthmaster.db.Master;

import org.w3c.dom.Text;

import java.util.List;

public class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.ViewHolder> {

    private List<Master> mMasterList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView masterImage;
        TextView masterName;
        TextView masterTitle;
        TextView masterField;
        TextView masterPrice;

        public ViewHolder(View view){
            super(view);
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.master_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
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
