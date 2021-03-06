package com.example.markus.todoregister.gui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Markus on 12.4.2017.
 * Adapter for showing the viewPager
 */

  class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> tabTitles = new ArrayList<>();


    /**
     * Add the fragment to the pager list
     * @param fragment fragment to add
     * @param titles title of the fragment
     */
    void addFragments(Fragment fragment, String titles) {
        this.fragments.add(fragment);
        this.tabTitles.add(titles);
    }


     ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //Get page fragment with position
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    //Get amount of page fragments we have
    @Override
    public int getCount() {
        return fragments.size();
    }

    //Get the title of the page
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}