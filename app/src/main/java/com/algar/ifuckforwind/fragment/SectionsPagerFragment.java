package com.algar.ifuckforwind.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
    @BindView(R.id.fragment_main_week_layout_container)
    RelativeLayout mRelativeLayout;

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

        View  mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, mRootView);

        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        boolean isHappyDay = Utility.randIsHappyDay();

        mDayMessage.setText(isHappyDay
                ? Utility.getHappyString(getContext())
                : Utility.getSadString(getContext()));

        if (isHappyDay) mRelativeLayout.setBackgroundColor(Utility.getHappyColor(getContext()));
        else mRelativeLayout.setBackgroundColor(Utility.getSadColor(getContext()));

        return mRootView;
    }
}
