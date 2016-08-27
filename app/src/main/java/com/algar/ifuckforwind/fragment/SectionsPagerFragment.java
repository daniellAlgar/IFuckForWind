package com.algar.ifuckforwind.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.algar.ifuckforwind.R;

/**
 * Created by algar on 2016-08-27.
 */
public class SectionsPagerFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mSectionNumber;

    public SectionsPagerFragment() {
    }

    public static SectionsPagerFragment newInstance(int sectionNumber) {
        SectionsPagerFragment fragment = new SectionsPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        return rootView;
    }
}
