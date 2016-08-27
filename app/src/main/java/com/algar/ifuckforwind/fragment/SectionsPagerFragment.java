package com.algar.ifuckforwind.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.algar.ifuckforwind.R;
import com.algar.ifuckforwind.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by algar on 2016-08-27.
 */
public class SectionsPagerFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mSectionNumber;
    @BindView(R.id.fragment_main_week_layout_textview) TextView mDayMessage;
    @BindView(R.id.fragment_main_week_layout_container) CoordinatorLayout mCoordinatorLayout;

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
        ButterKnife.bind(this, rootView);

        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        mDayMessage.setText(Utility.randIsHappyDay()
                ? Utility.getHappyString(getContext()) : Utility.getSadString(getContext()));

        return rootView;
    }
}
