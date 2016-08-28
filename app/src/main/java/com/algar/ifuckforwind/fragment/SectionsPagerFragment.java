package com.algar.ifuckforwind.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.algar.ifuckforwind.R;
import com.algar.ifuckforwind.activity.DetailActivity;
import com.algar.ifuckforwind.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by algar on 2016-08-27.
 */
public class SectionsPagerFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final String INTENT_EXTRA_IS_HAPPY_DAY = "isHappyDay";
    public static final String INTENT_EXTRA_CURRENT_DAY = "currentDay";

    private int mSectionNumber;
    private String mCurrentDay;
    @BindView(R.id.fragment_main_day_textview) TextView mDay;
    @BindView(R.id.fragment_main_day_message_textview) TextView mDayMessage;
    @BindView(R.id.fragment_main_week_layout_container) LinearLayout mLinearLayout;
    private Context mContext;

    private boolean mIsHappyDay;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        mIsHappyDay = Utility.randIsHappyDay();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View  mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, mRootView);

        mCurrentDay = Utility.getPrettyDate(mSectionNumber);
        mDay.setText(mIsHappyDay ? mCurrentDay + "!" : mCurrentDay + " :(");

        mDayMessage.setText(mIsHappyDay
                ? Utility.getHappyString(getContext())
                : Utility.getSadString(getContext()));

        if (mIsHappyDay) mLinearLayout.setBackgroundColor(Utility.getHappyColor(getContext()));
        else mLinearLayout.setBackgroundColor(Utility.getSadColor(getContext()));

        return mRootView;
    }


    @OnClick(R.id.fragment_main_week_layout_container)
    public void onTabClick(View view) {
        if (mIsHappyDay) {
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra(INTENT_EXTRA_IS_HAPPY_DAY, mIsHappyDay);
            detailIntent.putExtra(INTENT_EXTRA_CURRENT_DAY, mCurrentDay);
            startActivity(detailIntent);
        } else Snackbar.make(getView(), R.string.no_wind_snackbar, Snackbar.LENGTH_SHORT).show();
    }
}
