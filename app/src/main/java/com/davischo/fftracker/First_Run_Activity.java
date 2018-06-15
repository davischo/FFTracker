package com.davischo.fftracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

import static java.lang.Integer.parseInt;


//public class First_Run_Activity extends AppCompatActivity {



public class First_Run_Activity extends FragmentActivity {
        /**
         * The number of pages (wizard steps) to show in this demo.
         */
        private static final int NUM_PAGES = 5;

        /**
         * The pager widget, which handles animation and allows swiping horizontally to access previous
         * and next wizard steps.
         */
        private ViewPager mPager;

        /**
         * The pager adapter, which provides the pages to the view pager widget.
         */
        private PagerAdapter mPagerAdapter;

        static RadioGroup radioGroup;
        Button completeButton;

        static SharedPreferences sharedPreferences;
        static SharedPreferences.Editor editor;

        static int YEAR_DEFAULT = 1980;
        static int MONTH_DEFAULT = 1;
        static int DAY_DEFAULT = 1;
        static String GENDER_DEFAULT = "male";
        static int HEIGHT_DEFAULT = 165;  //in cm
        static int WEIGHT_DEFAULT = 65;  //in kg
        static int ACTIVITY_LEVEL_DEFAULT = 0;
        static int GOAL_DEFAULT = 0;


        public int getAge(int dobYear, int dobMonth, int dobDay) {
            Calendar c = Calendar.getInstance();
            int currYear = c.get(Calendar.YEAR);
            int currMonth = c.get(Calendar.MONTH);
            int currDay = c.get(Calendar.DAY_OF_MONTH);

            int age = currYear - dobYear;
            if(currMonth < dobMonth || (currMonth == dobMonth) && (currDay < dobDay)) {
                age--;
            }
            if(age < 0) {
                age = 0;
            }
            return age;
        }

        public void onCompleteButtonClicked(View view) {
            //calculate user age based on birth date:
            int dobYear = sharedPreferences.getInt("dobYear", YEAR_DEFAULT);
            int dobMonth = sharedPreferences.getInt("dobMonth", MONTH_DEFAULT);
            int dobDay = sharedPreferences.getInt("dobDay", MONTH_DEFAULT);
            int age = getAge(dobYear, dobMonth, dobDay);
            editor.putInt("age", age).commit();
            //calculate calorie remaining using H-B equation and save it in sharedPref:
            //int calorie_remaining =

            //push all data stored in sharedpreference onto database:

            //switch to main activity
            Intent intent = new Intent(First_Run_Activity.this, MainActivity.class);
            intent.putExtra("first_time", false);
            Log.i("user data stored: ", sharedPreferences.getString("gender", "male") + ", "
                    + sharedPreferences.getInt("dobYear", YEAR_DEFAULT) + ", "
                    + sharedPreferences.getInt("dobMonth", MONTH_DEFAULT) + ", "
                    + sharedPreferences.getInt("dobDay", DAY_DEFAULT) + ", "
                    + sharedPreferences.getInt("age", 0) + ", "
                    + sharedPreferences.getInt("height", HEIGHT_DEFAULT) + ", "
                    + sharedPreferences.getInt("weight", WEIGHT_DEFAULT) + ", "
                    + sharedPreferences.getInt("activity_level", ACTIVITY_LEVEL_DEFAULT) + ", "
                    + sharedPreferences.getInt("goal", GOAL_DEFAULT));
            startActivity(intent);
        }

        public void onRadioButtonClicked(View view) {
            int rb_pos = Integer.parseInt(view.getTag().toString());
            mPager.setCurrentItem(rb_pos);
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_first_run);

            sharedPreferences = this.getSharedPreferences("fftracker", MODE_PRIVATE);
            editor = sharedPreferences.edit();

            //save default user data:
            editor.putString("gender", GENDER_DEFAULT);
            editor.putInt("dobYear", YEAR_DEFAULT);
            editor.putInt("dobMonth", MONTH_DEFAULT);
            editor.putInt("dobMonth", DAY_DEFAULT);
            editor.putInt("height", HEIGHT_DEFAULT);
            editor.putInt("weight", WEIGHT_DEFAULT);
            editor.putInt("activity_level", ACTIVITY_LEVEL_DEFAULT);
            editor.putInt("goal", GOAL_DEFAULT);
            editor.commit();

            radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
            completeButton = (Button) findViewById(R.id.completeButton);


            // Instantiate a ViewPager and a PagerAdapter.
            mPager = (ViewPager) findViewById(R.id.user_stats_view_pager);
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
            mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch(position){
                        case 0:
                            radioGroup.check(R.id.radioButton);
                            break;
                        case 1:
                            radioGroup.check(R.id.radioButton2);
                            break;
                        case 2:
                            radioGroup.check(R.id.radioButton3);
                            break;
                        case 3:
                            radioGroup.check(R.id.radioButton4);
                            break;
                        case 4:
                            radioGroup.check(R.id.radioButton5);
                            break;
                        case 5:
                            radioGroup.check(R.id.radioButton6);
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        @Override
        public void onBackPressed() {
            if (mPager.getCurrentItem() == 0) {
                // If the user is currently looking at the first step, allow the system to handle the
                // Back button. This calls finish() on this activity and pops the back stack.
                super.onBackPressed();
            } else {
                // Otherwise, select the previous step.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        }

        /**
         * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
         * sequence.
         */
        private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
            public ScreenSlidePagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {

                switch(position){
                    case 0:
                        return new askingGenderFragment();
                    case 1:
                        return new askingAgeFragment();
                    case 2:
                        return new askingHeightFragment();
                    case 3:
                        return new askingWeightFragment();
                    case 4:
                        return new askingActivityLevelFragment();
                    case 5:
                        return new askingGoalFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return NUM_PAGES;
            }
        }
    }

//}
