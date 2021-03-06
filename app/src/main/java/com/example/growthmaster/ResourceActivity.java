package com.example.growthmaster;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.growthmaster.adapter.ResourceFragmentStatePagerAdapter;
import com.example.growthmaster.fragment.ResourceListFragment;

import java.util.ArrayList;
import java.util.List;

public class StudyResource extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_resource_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.study_resource_toolbar);
        setSupportActionBar(toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.study_resource_tabs);
        mViewPager = (ViewPager) findViewById(R.id.study_resource_viewpager);
        initViewPager();
    }

    private void initViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("精选");
        titles.add("体育");
        titles.add("巴萨");
        titles.add("购物");
        titles.add("明星");
        titles.add("视频");
        titles.add("健康");
        titles.add("励志");
        titles.add("图文");
        titles.add("本地");
        titles.add("动漫");
        titles.add("搞笑");
        titles.add("精选");

        for(int i=0;i<titles.size();i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        List<Fragment> fragments = new ArrayList<>();

        for(int i=0;i<titles.size();i++){
            fragments.add(new ResourceListFragment());
        }

        ResourceFragmentStatePagerAdapter mFragmentStatePagerAdapter =
                new ResourceFragmentStatePagerAdapter(getSupportFragmentManager(), fragments, titles);

        //给ViewPager设置适配器
        mViewPager.setAdapter(mFragmentStatePagerAdapter);
        //将TabLayout和ViewPager关联起来。
        mTabLayout.setupWithViewPager(mViewPager);
        //给TabLayout设置适配器
        //mTabLayout.setTabsFromPagerAdapter(mFragmentStatePagerAdapter);
    }

}
