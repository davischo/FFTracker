package com.davischo.fftracker;

import android.content.Intent;
import android.content.SharedPreferences;
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

import static com.davischo.fftracker.FFFragment.refreshFFFragment;

public class MainActivity extends AppCompatActivity {

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;

    //static Boolean first_time = true;

    public static SQLiteDatabase storage;

    private void populateTables(){
        storage.execSQL("INSERT INTO food VALUES ('" + FFTrackerHelper.getCurrentDate() + "', 'bananas', 75)");
        storage.execSQL("INSERT INTO food VALUES ('" + FFTrackerHelper.getCurrentDate() + "', 'apples', 55)");
        storage.execSQL("INSERT INTO exercise VALUES ('" + FFTrackerHelper.getCurrentDate() + "', 'running', 100)");
        storage.execSQL("INSERT INTO exercise VALUES ('" + FFTrackerHelper.getCurrentDate() + "', 'fapping', 50)");
        storage.execSQL("INSERT INTO weight VALUES ('2018-08-22', 75)");
        storage.execSQL("INSERT INTO weight VALUES ('2018-08-22', 65)");
        storage.execSQL("INSERT INTO weight VALUES ('2018-08-22', 70)");
        storage.execSQL("INSERT INTO weight VALUES ('2018-07-22', 65)");
        storage.execSQL("INSERT INTO weight VALUES ('2018-06-22', 60)");
        storage.execSQL("INSERT INTO weight VALUES ('2018-05-22', 75)");
        storage.execSQL("INSERT INTO weight VALUES ('2018-04-22', 65)");
        storage.execSQL("INSERT INTO weight VALUES ('2018-03-22', 70)");
        storage.execSQL("INSERT INTO weight VALUES ('2018-02-22', 65)");
        storage.execSQL("INSERT INTO weight VALUES ('2018-01-22', 60)");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = this.getSharedPreferences("fftracker", MODE_PRIVATE);
        editor = sharedPreferences.edit();

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
        refreshAll();
    }

    public static void refreshAll(){
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
