package com.dave.android.livenews.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dave.android.livenews.R;

/**
 * created by dave on 06/25/2018.
 */
public class Settings extends AppCompatActivity {
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("Settings");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //Display fragment as main content
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_container, new com.dave.android.livenews.Fragments.Settings())
                .commit();
    }
}
