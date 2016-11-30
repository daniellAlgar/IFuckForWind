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

import static com.algar.ifuckforwind.R.array.sadColors;
import static java.util.Arrays.asList;

/**
 * Created by algar on 2016-08-27
 */
public class Utility {

    private static LRUCache mCache = LRUCache.getInstance();

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
        return getNextHappyOrSadColor(sadColors, R.string.sadColorCacheKey, context);
    }

    public static int getHappyColor(Context context) {
        return getNextHappyOrSadColor(R.array.happyColors, R.string.happyColorCacheKey, context);
    }

    @SuppressWarnings("unchecked")
    private static int getNextHappyOrSadColor(int intArrayId, int cacheKeyId, Context context) {
        String cacheKey = context.getString(cacheKeyId);

        int[] colors = context.getResources().getIntArray(intArrayId);
        List<Integer> colorInts = new ArrayList<>();

        for (int color : colors) {
            colorInts.add(color);
        }

        ArrayList<Integer> usedColors = (ArrayList<Integer>) mCache.getLru().get(cacheKey);

        if ((usedColors == null) || (usedColors.size() >= colorInts.size())) {
            int randColor = colorInts.get(randInInterval(colorInts.size() - 1));
            ArrayList<Integer> toCache = new ArrayList<>();
            toCache.add(randColor);
            mCache.getLru().put(cacheKey, toCache);
            return randColor;
        } else {
            for (Integer usedString : usedColors) {
                if (colorInts.contains(usedString)) {
                    colorInts.remove(usedString);
                }
            }

            int randColor = colorInts.get(randInInterval(colorInts.size() - 1));
            usedColors.add(randColor);
            mCache.getLru().put(cacheKey, usedColors);
            return randColor;
        }
    }

    public static String getSadString(Context context) {
        return getNextSentimentString(R.array.sadStrings, R.string.sadStringCacheKey, context);
    }

    public static String getHappyString(Context context) {
        return getNextSentimentString(R.array.happyStrings, R.string.happyStringCacheKey, context);
    }

    @SuppressWarnings("unchecked")
    private static String getNextSentimentString(int stringArrayId, int cacheKeyId, Context context) {
        String cacheKey = context.getString(cacheKeyId);

        ArrayList<String> sentimentStrings = new ArrayList<>(asList(context.getResources().getStringArray(stringArrayId)));
        ArrayList<String> usedSentimentStrings = (ArrayList<String>) mCache.getLru().get(cacheKey);

        if ((usedSentimentStrings == null) || (usedSentimentStrings.size() >= sentimentStrings.size())) {
            String happyString = sentimentStrings.get(randInInterval(sentimentStrings.size() - 1));
            ArrayList<String> toCache = new ArrayList<>();
            toCache.add(happyString);
            mCache.getLru().put(cacheKey, toCache);
            return happyString;
        } else {
            for (String usedString : usedSentimentStrings) {
                if (sentimentStrings.contains(usedString)) {
                    sentimentStrings.remove(usedString);
                }
            }

            String happyString = sentimentStrings.get(randInInterval(sentimentStrings.size() - 1));
            usedSentimentStrings.add(happyString);
            mCache.getLru().put(cacheKey, usedSentimentStrings);
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
