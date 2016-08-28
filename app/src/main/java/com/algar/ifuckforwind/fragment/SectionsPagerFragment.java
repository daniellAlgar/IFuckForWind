package com.algar.ifuckforwind.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

    private int mSectionNumber;
    @BindView(R.id.fragment_main_week_layout_textview) TextView mDayMessage;
    @BindView(R.id.fragment_main_week_layout_container) RelativeLayout mRelativeLayout;
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

        mDayMessage.setText(mIsHappyDay
                ? Utility.getHappyString(getContext())
                : Utility.getSadString(getContext()));

        if (mIsHappyDay) mRelativeLayout.setBackgroundColor(Utility.getHappyColor(getContext()));
        else mRelativeLayout.setBackgroundColor(Utility.getSadColor(getContext()));

        return mRootView;
    }


    @OnClick(R.id.fragment_main_week_layout_container)
    public void onTabClick(View view) {
        if (mIsHappyDay) {
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra(INTENT_EXTRA_IS_HAPPY_DAY, mIsHappyDay);
            startActivity(detailIntent);
        }
    }
}
