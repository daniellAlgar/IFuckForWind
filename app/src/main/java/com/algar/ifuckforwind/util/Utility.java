package com.algar.ifuckforwind.util;

import android.content.Context;

import com.algar.ifuckforwind.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import static java.util.Arrays.asList;

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
        return getNextHappyOrSadColor(R.array.happyColors, R.string.happyColorCacheKey, context);
//        int[] happyColors = context.getResources().getIntArray(R.array.happyColors);
//        return happyColors[randInInterval(happyColors.length - 1)];
    }

    @SuppressWarnings("unchecked")
    private static int getNextHappyOrSadColor(int intArrayId, int cacheKeyId, Context context) {
        LRUCache cache = LRUCache.getInstance();

        String cacheKey = context.getString(cacheKeyId);

        int[] colors = context.getResources().getIntArray(intArrayId);
        List<Integer> colorInts = new ArrayList<>();

        for (int color : colors) {
            colorInts.add(color);
        }

        ArrayList<Integer> usedColors = (ArrayList<Integer>) cache.getLru().get(cacheKey);

        if ((usedColors == null) || (usedColors.size() >= colorInts.size())) {
            int randColor = colorInts.get(randInInterval(colorInts.size() - 1));
            ArrayList<Integer> toCache = new ArrayList<>();
            toCache.add(randColor);
            cache.getLru().put(cacheKey, toCache);
            return randColor;
        } else {
            for (Integer usedString : usedColors) {
                if (colorInts.contains(usedString)) {
                    colorInts.remove(usedString);
                }
            }

            int randColor = colorInts.get(randInInterval(colorInts.size() - 1));
            usedColors.add(randColor);
            cache.getLru().put(cacheKey, usedColors);
            return randColor;
        }
    }

    public static String getSadString(Context context) {
        return getNextHappyOrSadString(R.array.sadStrings, R.string.sadStringCacheKey, context);
    }

    public static String getHappyString(Context context) {
        return getNextHappyOrSadString(R.array.happyStrings, R.string.happyStringCacheKey, context);
    }

    @SuppressWarnings("unchecked")
    private static String getNextHappyOrSadString(int stringArrayId, int cacheKeyId, Context context) {
        LRUCache cache = LRUCache.getInstance();

        String cacheKey = context.getString(cacheKeyId);

        ArrayList<String> happyStrings = new ArrayList<>(asList(context.getResources().getStringArray(stringArrayId)));
        ArrayList<String> usedHappyStrings = (ArrayList<String>) cache.getLru().get(cacheKey);

        if ((usedHappyStrings == null) || (usedHappyStrings.size() >= happyStrings.size())) {
            String happyString = happyStrings.get(randInInterval(happyStrings.size() - 1));
            ArrayList<String> toCache = new ArrayList<>();
            toCache.add(happyString);
            cache.getLru().put(cacheKey, toCache);
            return happyString;
        } else {
            for (String usedString : usedHappyStrings) {
                if (happyStrings.contains(usedString)) {
                    happyStrings.remove(usedString);
                }
            }

            String happyString = happyStrings.get(randInInterval(happyStrings.size() - 1));
            usedHappyStrings.add(happyString);
            cache.getLru().put(cacheKey, usedHappyStrings);
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
