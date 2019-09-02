package com.droidoxy.easymoneyrewards.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.droidoxy.easymoneyrewards.fragments.HomeFragment;
import com.droidoxy.easymoneyrewards.fragments.TransactionsFragment;


/**
 * Created by DroidOXY
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence Titles[];
    private int NumbOfTabs;

    // Constructor
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0) {

            return new HomeFragment();

        }else{

            return new TransactionsFragment();

        }
    }

    @Override
    public CharSequence getPageTitle(int position){ return Titles[position]; }

    @Override
    public int getCount(){ return NumbOfTabs; }

}