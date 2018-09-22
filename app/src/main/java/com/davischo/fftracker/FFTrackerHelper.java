package com.davischo.fftracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.davischo.fftracker.First_Run_Activity.sharedPreferences;

/**
 * Created by yx on 2018/6/16.
 */

public class FFTrackerHelper {

    static double[] activity_level_factors = {1.2, 1.375, 1.55, 1.725};
    static int[] goal_offsets = {0, -500, -1000, 500, 1000};

    static int YEAR_DEFAULT = 1980;
    static int MONTH_DEFAULT = 1;
    static int DAY_DEFAULT = 1;
    static String GENDER_DEFAULT = "male";
    static int HEIGHT_DEFAULT = 165;  //in cm
    static int WEIGHT_DEFAULT = 65;  //in kg
    static int ACTIVITY_LEVEL_DEFAULT = 0;
    static int GOAL_DEFAULT = 0;

    public static String getCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        Date date = new Date();
        return format.format(date);
    }

    //calculate calorie remaining using H-B equation and save it in sharedPref:
            /*
               For men, BMR = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) + 5
               For women, BMR = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) - 161
               (Source: wikipedia)
               activity level:
                 0 - sedentary: BMR * 1.2
                 1 - lightly active: BMR * 1.375
                 2 - moderately active: BMR * 1.55
                 3 - very active: BMR * 1.725
               goal:  (in kcal)
                 0 - maintain weight: +0
                 1 - lose 0.5kg (1 lb) per week : -500
                 2 - lose 1 kg (2 lb) per week -1000
                 3 - gain 0.5 kg (1 lb) per week +500
                 4 - gain 1 kg(2 lb) per week +1000
               (Source: http://www.calculator.net/calorie-calculator.html)
            */
    public static int calculateCalRemain(String gender, int age, int height, int weight, int activity_level, int goal_level) {
        double BMR;
        if(gender == "male"){
            BMR = 10 * weight + 6.25 * height - 5 * age + 5;
        }else {
            BMR = 10 * weight + 6.25 * height - 5 * age - 161;
        }
        int orig_cal_remain = (int) Math.round(BMR * activity_level_factors[activity_level] + goal_offsets[goal_level]);
        return orig_cal_remain;
    }
    public static int calculateCalRemain() {
        double BMR;
        if(getLocalGender() == "male"){
            BMR = 10 * getLocalWeight() + 6.25 * getLocalHeight() - 5 * getLocalAge() + 5;
        }else {
            BMR = 10 * getLocalWeight() + 6.25 * getLocalHeight() - 5 * getLocalAge() - 161;
        }
        int orig_cal_remain = (int) Math.round(BMR * activity_level_factors[getLocalActivityLevel()]
                + goal_offsets[getLocalGoal()]);
        return orig_cal_remain;
    }

    public static int getAge(int dobYear, int dobMonth, int dobDay) {
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
    public static int getLocalAge(){
        return getAge(getLocalDOBYear(), getLocalDOBMonth(), getLocalDOBDay());
    }
    public static String getLocalGender(){
        return sharedPreferences.getString("gender", GENDER_DEFAULT);
    }
    public static int getLocalDOBYear() {
        return sharedPreferences.getInt("dobYear", YEAR_DEFAULT);
    }
    public static int getLocalDOBMonth() {
        return sharedPreferences.getInt("dobMonth", MONTH_DEFAULT);
    }
    public static int getLocalDOBDay() {
        return sharedPreferences.getInt("dobDay", DAY_DEFAULT);
    }
    public static int getLocalHeight() {
        return sharedPreferences.getInt("height", HEIGHT_DEFAULT);
    }
    public static int getLocalWeight() {
        return sharedPreferences.getInt("weight", WEIGHT_DEFAULT);
    }
    public static int getLocalActivityLevel() {
        return sharedPreferences.getInt("activity_level", ACTIVITY_LEVEL_DEFAULT);
    }
    public static int getLocalGoal() {
        return sharedPreferences.getInt("goal", GOAL_DEFAULT);
    }

}
