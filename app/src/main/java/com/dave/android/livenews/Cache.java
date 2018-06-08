package com.dave.android.livenews;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * created by dave on 06/25/2018.
 */
public class Cache extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

       FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
