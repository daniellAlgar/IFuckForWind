package com.algar.ifuckforwind.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.algar.ifuckforwind.R;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by algar on 2016-08-30
 */
public class SettingsFragment extends PreferenceFragmentCompat {

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
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);
        mSectionNumber = getArguments().getInt(SECTION_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

//        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
//
//        return rootView;
    }
}
