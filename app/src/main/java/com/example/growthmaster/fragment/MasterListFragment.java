package com.example.growthmaster.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.growthmaster.R;
import com.example.growthmaster.db.Master;
import com.example.growthmaster.adapter.MasterAdapter;

import java.util.ArrayList;
import java.util.List;

public class MasterListFragment extends Fragment {

    private List<Master> masterList = new ArrayList<>();

    public MasterListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.master_list,container,false);
        RecyclerView masterListRecyclerView = (RecyclerView) view.findViewById(R.id.master_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        masterListRecyclerView.setLayoutManager(layoutManager);
        initMaster();
        MasterAdapter masterAdapter = new MasterAdapter(masterList);
        masterListRecyclerView.setAdapter(masterAdapter);
        return view;
    }

    private void initMaster(){
        for(int i = 0; i < 8; i++){
            Master master = new Master("欧洲帅哥","心理学硕士",
                    "儿童成长","100-200",R.drawable.master);
            masterList.add(master);
        }
    }
}
