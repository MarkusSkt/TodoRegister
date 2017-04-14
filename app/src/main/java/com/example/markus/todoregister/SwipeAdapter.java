package com.example.markus.todoregister;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Markus on 11.4.2017.
 */

public class SwipeAdapter extends FragmentPagerAdapter {
    private final int pagesCount = 3;

    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //Fragment f = new TaskFragment();
       // Bundle bundle = new Bundle();
       // bundle.putInt("count", position+1);
       // f.setArguments(bundle);
        return null;
    }

    @Override
    public int getCount() {
        return pagesCount;
    }
}
