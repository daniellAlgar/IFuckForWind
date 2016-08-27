package com.algar.ifuckforwind.util;

import android.content.Context;

import com.algar.ifuckforwind.R;

import java.util.Random;

/**
 * Created by algar on 2016-08-27.
 */
public class Utility {

    public static int getSadColor(Context context) {
        int[] sadColors = context.getResources().getIntArray(R.array.sadColors);
        return sadColors[randInInterval(sadColors.length - 1)];
    }

    public static int getHappyColor(Context context) {
        int[] happyColors = context.getResources().getIntArray(R.array.happyColors);

        return happyColors[randInInterval(happyColors.length - 1)];
    }

    public static String getSadString(Context context) {
        String[] sadStrings = context.getResources().getStringArray(R.array.sadStrings);
        return sadStrings[randInInterval(sadStrings.length - 1)];
    }

    public static String getHappyString(Context context) {
        String[] happyStrings = context.getResources().getStringArray(R.array.happyStrings);
        return happyStrings[randInInterval(happyStrings.length - 1)];
    }

    public static int randInInterval(int stop) {
        return randInInterval(0, stop);
    }

    public static int randInInterval(int start, int stop) {
        if (stop < start) throw new IllegalArgumentException("Start value must be smaller than stop");

        Random random = new Random();
        return random.nextInt(stop - start + 1) + start;
    }

    public static boolean randIsHappyDay() {
        return new Random().nextBoolean();
    }
}
