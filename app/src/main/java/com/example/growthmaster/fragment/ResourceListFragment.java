package com.example.growthmaster.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.growthmaster.R;
import com.example.growthmaster.adapter.ResourceRecyclerViewAdapter;
import com.example.growthmaster.db.Resource;

import java.util.ArrayList;
import java.util.List;


public class ResourceListFragment extends Fragment {

    private List<Resource> resourceList = new ArrayList<>();
    RecyclerView resourceRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resource_list_fragment, container, false);
        resourceRecyclerView = (RecyclerView) view.findViewById(R.id.resource_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        resourceRecyclerView.setLayoutManager(layoutManager);
        initResource();
        ResourceRecyclerViewAdapter mRecyclerViewAdapter = new ResourceRecyclerViewAdapter(resourceList);
        resourceRecyclerView.setAdapter(mRecyclerViewAdapter);
        return view;
    }

    private void initResource(){
        for(int i = 0; i < 8; i++){
            Resource resource= new Resource("论调节青少年心态的十大有效方法","中国心理健康教育网",
                    "2019-4-14",R.drawable.master);
            resourceList.add(resource);
        }
    }

}
