package com.algar.ifuckforwind;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

import com.algar.ifuckforwind.util.Utility;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by algar on 2016-08-27
 */

@RunWith(MockitoJUnitRunner.class)
public class UtilityTest {
    // TODO: Test all functions accepting Context as input parameter

    private enum Sentiment {
        HAPPY, SAD
    }

    @Mock Context mContext;
    @Mock Resources mResources;

    private String[] mSentimentStrings = {"String_1", "String_2", "String_3", "String_4", "String_5", "String_6", "String_7", "String_8"};
    private int[] mSentimentColors = {Color.RED, Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.MAGENTA};
    private ArrayList<String> mSentimentStringArrayList;
    private ArrayList<Integer> mSentimentIntegerArrayList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mContext.getResources()).thenReturn(mResources);

        // Mock HAPPY string
        when(mContext.getResources().getStringArray(R.array.happyStrings)).thenReturn(mSentimentStrings);
        when(mContext.getString(R.string.happyStringCacheKey)).thenReturn("happyStringCacheKey");

        // Mock SAD string
        when(mContext.getResources().getStringArray(R.array.sadStrings)).thenReturn(mSentimentStrings);
        when(mContext.getString(R.string.sadStringCacheKey)).thenReturn("sadStringsCacheKey");

        // Mock HAPPY color
        when(mContext.getResources().getIntArray(R.array.happyColors)).thenReturn(mSentimentColors);
        when(mContext.getString(R.string.happyColorCacheKey)).thenReturn("happyColorCacheKey");

        // Mock SAD color
        when(mContext.getResources().getIntArray(R.array.sadColors)).thenReturn(mSentimentColors);
        when(mContext.getString(R.string.sadColorCacheKey)).thenReturn("sadColorCacheKey");

        mSentimentStringArrayList = new ArrayList<>();
        mSentimentIntegerArrayList = new ArrayList<>();
    }

    @Test
    public void getSadColor_should_not_return_same_color_twice() {
        assertCheckForSentimentColor(Sentiment.SAD);
    }

    @Test
    public void getHappyColor_should_not_return_same_color_twice() {
        assertCheckForSentimentColor(Sentiment.HAPPY);
    }

    private void assertCheckForSentimentColor(Sentiment sentiment) {
        // Due to the random selection of int from the array - run the assertion n times to minimize "bad luck"
        for (int i = 0; i < 10; i++) {
            for (int ignored : mSentimentColors) {
                int sentimentColor = sentiment == Sentiment.HAPPY ? Utility.getHappyColor(mContext) : Utility.getSadColor(mContext);
                String errMsg = "int \"" + sentimentColor + "\" already returned from " +
                        (sentiment == Sentiment.HAPPY ? "getHappyColor." : "getSadColor.");

                assertFalse(errMsg, mSentimentIntegerArrayList.contains(sentimentColor));
                mSentimentIntegerArrayList.add(sentimentColor);
            }
            mSentimentIntegerArrayList.clear();
        }
    }

    private void assertCheckForSentimentString(Sentiment sentiment) {
        // Due to the random selection of string from the array - run the assertion n times to minimize "bad luck"
        for (int i = 0; i < 10; i++) {
            for (String ignored : mSentimentStrings) {
                String sentimentString = sentiment == Sentiment.HAPPY ? Utility.getHappyString(mContext) : Utility.getSadString(mContext);
                String errMsg = "String \"" + sentimentString + "\" already returned from " +
                        (sentiment == Sentiment.HAPPY ? "getHappyString." : "getSadString.");

                assertFalse(errMsg, mSentimentStringArrayList.contains(sentimentString));
                mSentimentStringArrayList.add(sentimentString);
            }
            mSentimentStringArrayList.clear();
        }
    }

    @Test
    public void getSadString_should_not_return_same_string_twice() {
        assertCheckForSentimentString(Sentiment.SAD);
    }

    @Test
    public void getHappyString_should_not_return_same_string_twice() {
        assertCheckForSentimentString(Sentiment.HAPPY);
    }


    @Test
    public void getOffsetDate() {
        String[] weekDays = getWeekdaysFromToday();
        for (int i = 0; i < weekDays.length; i++) {
            assertTrue(Utility.getOffsetDate(i).equals(weekDays[i]));
        }
    }

    @Test
    public void getPrettyDate_the_first_two_days_should_be_pretty() {
        // This function defaults to getOffsetDate for i > 2. Therefore it should always be tested
        // after the getOffsetDate tests
        String[] weekDays = getWeekdaysFromToday();
        String[] expected = {"Today", "Tomorrow", weekDays[2]};

        for (int i = 0; i <= 2; i++) {
            String actual = Utility.getPrettyDate(i);
            String errMsg = "getPrettyDate(" + i + ") should return " + expected[i] +
                    ", but actually returned " + actual + ".";
            assertEquals(errMsg, expected[i], actual);
        }
    }

    private String[] getWeekdaysFromToday() {
        String[] weekDays = new String[7];

        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY    : weekDays = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}; break;
            case Calendar.TUESDAY   : weekDays = new String[] {"Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Monday"}; break;
            case Calendar.WEDNESDAY : weekDays = new String[] {"Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday"}; break;
            case Calendar.THURSDAY  : weekDays = new String[] {"Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday"}; break;
            case Calendar.FRIDAY    : weekDays = new String[] {"Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}; break;
            case Calendar.SATURDAY  : weekDays = new String[] {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}; break;
            case Calendar.SUNDAY    : weekDays = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        }
        return weekDays;
    }
    
    @Test
    public void randInInterval_check_that_a_number_within_given_interval_is_return() {
        int max = 20;

        for (int i = 0; i <= max; i++) {
            int actual = Utility.randInInterval(i, max);
            String errMsg = "Expected: " + i + " <= actual <= " + max + ". Actual = " + actual + ".";
            assertTrue(errMsg, actual >= i && actual <= max);

            actual = Utility.randInInterval(i);
            errMsg = "Expected: 0 <= actual <= " + i + ". Actual = " + actual + ".";
            assertTrue(errMsg, actual >= 0 && actual <= max);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void randInInterval_exception_if_start_is_greater_than_stop() {
        Utility.randInInterval(10, 0);
    }
}
