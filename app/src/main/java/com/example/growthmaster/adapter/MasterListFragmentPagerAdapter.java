package com.example.growthmaster.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.growthmaster.PsychologicalServices;
import com.example.growthmaster.fragment.MasterListFragment;

public class MasterListFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 6;
    private MasterListFragment masterListFragment_1 = null;
    private MasterListFragment masterListFragment_2 = null;
    private MasterListFragment masterListFragment_3 = null;
    private MasterListFragment masterListFragment_4 = null;
    private MasterListFragment masterListFragment_5 = null;
    private MasterListFragment masterListFragment_6 = null;

    public MasterListFragmentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
        masterListFragment_1 = new MasterListFragment();
        masterListFragment_2 = new MasterListFragment();
        masterListFragment_3 = new MasterListFragment();
        masterListFragment_4 = new MasterListFragment();
        masterListFragment_5 = new MasterListFragment();
        masterListFragment_6 = new MasterListFragment();
    }
    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case PsychologicalServices.PAGE_ONE:
                fragment = masterListFragment_1;
                break;
            case PsychologicalServices.PAGE_TWO:
                fragment = masterListFragment_2;
                break;
            case PsychologicalServices.PAGE_THREE:
                fragment = masterListFragment_3;
                break;
            case PsychologicalServices.PAGE_FOUR:
                fragment = masterListFragment_4;
                break;
            case PsychologicalServices.PAGE_FIVE:
                fragment = masterListFragment_5;
                break;
            case PsychologicalServices.PAGE_SIX:
                fragment = masterListFragment_6;
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
