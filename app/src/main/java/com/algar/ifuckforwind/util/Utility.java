package com.algar.ifuckforwind.util;

import android.content.Context;

import com.algar.ifuckforwind.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by algar on 2016-08-27
 */
public class Utility {

    public static String getPrettyDate(int offset) {
        switch (offset) {
            case 0:
                return "Today";
            case 1:
                return "Tomorrow";
            default:
                return getOffsetDate(offset);
        }
    }

    public static String getOffsetDate(int offset) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.add(Calendar.DAY_OF_WEEK, offset);
        return new SimpleDateFormat("EEEE", Locale.US).format(calendar.getTime().getTime());
    }

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

    @SuppressWarnings("unchecked")
    public static String getHappyString(Context context) {
        LRUCache cache = LRUCache.getInstance();
        String happyStringsKey = context.getString(R.string.happyStringCacheKey);

        ArrayList<String> happyStrings = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.happyStrings)));
        ArrayList<String> usedHappyStrings = (ArrayList<String>) cache.getLru().get(happyStringsKey);

        if ((usedHappyStrings == null) || (usedHappyStrings.size() >= happyStrings.size())) {
            String happyString = happyStrings.get(randInInterval(happyStrings.size() - 1));
            ArrayList<String> toCache = new ArrayList<>();
            toCache.add(happyString);
            cache.getLru().put(happyStringsKey, toCache);
            return happyString;
        } else {
            for (String usedString : usedHappyStrings) {
                if (happyStrings.contains(usedString)) {
                    happyStrings.remove(usedString);
                }
            }

            String happyString = happyStrings.get(randInInterval(happyStrings.size() - 1));
            usedHappyStrings.add(happyString);
            cache.getLru().put(happyStringsKey, usedHappyStrings);
            return happyString;
        }
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
