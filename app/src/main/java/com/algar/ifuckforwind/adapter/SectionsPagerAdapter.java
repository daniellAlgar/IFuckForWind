package com.algar.ifuckforwind.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.algar.ifuckforwind.fragment.SectionsPagerFragment;
import com.algar.ifuckforwind.fragment.PreferenceFragment;

/**
 * Created by algar on 2016-08-27.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return position == 0
                ? PreferenceFragment.newInstance(position)
                : SectionsPagerFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 6;
    }
}