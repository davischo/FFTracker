package com.davischo.fftracker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    //static Boolean first_time = true;

    public static SQLiteDatabase storage;

    private void populateTables(){
        storage.execSQL("INSERT INTO weight VALUES (1537567923818, 98)");
        storage.execSQL("INSERT INTO weight VALUES (1537567983818, 102)");
        storage.execSQL("INSERT INTO weight VALUES (1537569923818, 95)");
        storage.execSQL("INSERT INTO exercise VALUES (1537567923818, 'running', 100)");
        storage.execSQL("INSERT INTO exercise VALUES (1537567983818, 'studying', 200)");
        storage.execSQL("INSERT INTO exercise VALUES (1537569923818, 'jerking', 300)");
        storage.execSQL("INSERT INTO food VALUES (1537567923818, 'eggs', 600)");
        storage.execSQL("INSERT INTO food VALUES (1537567983818, 'hashbrowns', 500)");
        storage.execSQL("INSERT INTO food VALUES (1537569923818, 'sausages', 400)");

        storage.execSQL("INSERT INTO food VALUES ('" + FFTrackerHelper.getCurrentDate() + "', 'bananas', 75)");
        storage.execSQL("INSERT INTO food VALUES ('" + FFTrackerHelper.getCurrentDate() + "', 'apples', 55)");
        storage.execSQL("INSERT INTO exercise VALUES ('" + FFTrackerHelper.getCurrentDate() + "', 'running', 100)");
        storage.execSQL("INSERT INTO exercise VALUES ('" + FFTrackerHelper.getCurrentDate() + "', 'fapping', 50)");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ADDED SQL HERE
        try {
            this.deleteDatabase("dataPoints");
            storage = this.openOrCreateDatabase("dataPoints", MODE_PRIVATE, null);
            storage.execSQL("CREATE TABLE IF NOT EXISTS weight (time TEXT, weight INT)");
            storage.execSQL("CREATE TABLE IF NOT EXISTS exercise (time TEXT, name TEXT, calories INT)");
            storage.execSQL("CREATE TABLE IF NOT EXISTS food (time TEXT, name TEXT, calories INT)");
            populateTables();
        }
        catch(Exception e){
            System.out.println("Something went wrong");
        }

        if(getIntent().getBooleanExtra("first_time", true)) {
            startActivity(new Intent(MainActivity.this, First_Run_Activity.class));
            Toast.makeText(MainActivity.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void submitClicked(View v){
        FFFragment.submitClicked(v);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            if(position==0)
                return new FFFragment();
            else if(position ==1)
                return new TrendFragment();
            else
                return new AccountFragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Food & Fitness";
                case 1:
                    return "Trend";
                case 2:
                    return "My Account";
            }
            return null;
        }
    }
}
