package com.algar.ifuckforwind.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.algar.ifuckforwind.fragment.SectionsPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by algar on 2016-08-27.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private List<SectionsPagerFragment> fragments = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SectionsPagerFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 5;
    }
}