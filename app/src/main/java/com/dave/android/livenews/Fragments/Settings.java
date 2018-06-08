package com.dave.android.livenews.Fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.dave.android.livenews.R;

/**
 * created by dave on 06/25/2018.
 */
public class Settings extends PreferenceFragment {
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        //load preference from xml resource
        addPreferencesFromResource(R.xml.settings);
    }
}
