package com.davischo.fftracker;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

        public void onCompleteButtonClicked(View view) {
            //gather user data
            //switch to main activity
            Intent intent = new Intent(First_Run_Activity.this, MainActivity.class);
            intent.putExtra("first_time", false);
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

        public static class askingGenderFragment extends Fragment {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                ViewGroup rootView = (ViewGroup) inflater.inflate(
                        R.layout.asking_gender_layout, container, false);
                //do stuff...eg: store user input.
                return rootView;
            }
        }
        public static class askingAgeFragment extends Fragment {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                ViewGroup rootView = (ViewGroup) inflater.inflate(
                        R.layout.asking_age_layout, container, false);
                return rootView;
            }
        }
        public static class askingHeightFragment extends Fragment {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                ViewGroup rootView = (ViewGroup) inflater.inflate(
                        R.layout.asking_height_layout, container, false);
                return rootView;
            }
        }
        public static class askingActivityLevelFragment extends Fragment {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                ViewGroup rootView = (ViewGroup) inflater.inflate(
                        R.layout.asking_activity_level_layout, container, false);

                return rootView;
            }
        }
        public static class askingGoalFragment extends Fragment {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                ViewGroup rootView = (ViewGroup) inflater.inflate(
                        R.layout.asking_goal_layout, container, false);

                return rootView;
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
                        return new askingActivityLevelFragment();
                    case 4:
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
