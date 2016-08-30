package com.algar.ifuckforwind.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.algar.ifuckforwind.R;

/**
 * Created by algar on 2016-08-30.
 */
public class SettingsFragment extends Fragment {

    private static final String SECTION_NUMBER = "sectionNumber";
    private int mSectionNumber;

    public SettingsFragment() {}

    public static SettingsFragment newInstance(int sectionNumber) {
        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SECTION_NUMBER, sectionNumber);
        settingsFragment.setArguments(bundle);
        return settingsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSectionNumber = getArguments().getInt(SECTION_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        return rootView;
    }
}
