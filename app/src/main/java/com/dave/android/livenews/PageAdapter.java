package com.dave.android.livenews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dave.android.livenews.Fragments.Business;
import com.dave.android.livenews.Fragments.Entertainment;
import com.dave.android.livenews.Fragments.General;
import com.dave.android.livenews.Fragments.Sports;
import com.dave.android.livenews.Fragments.Technology;

/**
 * created by dave on 05/25/2018.
 */
public class PageAdapter extends FragmentStatePagerAdapter {

        int mNumOfTabs;

        public PageAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    General general = new General();
                    return general;
                case 1:
                    Sports sports = new Sports();
                    return sports;
                case 2:
                    Technology technology = new Technology();
                    return technology;
                case 3:
                    Entertainment entertainment = new Entertainment();
                    return entertainment;
                case 4:
                    Business business = new Business();
                    return business;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
}
