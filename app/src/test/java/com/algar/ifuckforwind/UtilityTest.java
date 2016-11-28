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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by algar on 2016-08-27
 */

@RunWith(MockitoJUnitRunner.class)
public class UtilityTest {
    // TODO: Test all functions accepting Context as input parameter

    @Mock Context mContext;
    @Mock Resources mResources;

    String[] mTestStrings = {"String_1", "String_2", "String_3", "String_4", "String_5", "String_6", "String_7", "String_8"};
    int[] colors = {Color.RED, Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.MAGENTA};
    ArrayList<String> mStringArrayList;
    ArrayList<Integer> mIntArraylist;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mContext.getResources()).thenReturn(mResources);

        // Mock happy string
        when(mContext.getResources().getStringArray(R.array.happyStrings)).thenReturn(mTestStrings);
        when(mContext.getString(R.string.happyStringCacheKey)).thenReturn("happyStringCacheKey");

        // Mock sad string
        when(mContext.getResources().getStringArray(R.array.sadStrings)).thenReturn(mTestStrings);
        when(mContext.getString(R.string.sadStringCacheKey)).thenReturn("sadStringsCacheKey");

        // Mock happy color
        when(mContext.getResources().getIntArray(R.array.happyColors)).thenReturn(colors);
        when(mContext.getString(R.string.happyColorCacheKey)).thenReturn("happyColorCacheKey");

        // Mock sad color
        when(mContext.getResources().getIntArray(R.array.sadColors)).thenReturn(colors);
        when(mContext.getString(R.string.sadColorCacheKey)).thenReturn("sadColorCacheKey");

        mStringArrayList = new ArrayList<>();
        mIntArraylist = new ArrayList<>();
    }

    @Test
    public void getSadColor_should_not_return_same_color_twice() {
        assertCheckForHappyAndSadColor(false);
    }

    @Test
    public void getHappyColor_should_not_return_same_color_twice() {
        assertCheckForHappyAndSadColor(true);
    }

    // Helpef function for getSad-/HappyColor assertion
    private void assertCheckForHappyAndSadColor(boolean checkHappy) {
        // Due to the random selection of string from the array - run the assertion n times to minimize "bad luck"
        for (int i = 0; i < 10; i++) {
            for (int colorInt : colors) {
                int happySadColor = checkHappy ? Utility.getHappyColor(mContext) : Utility.getSadColor(mContext);
                String errMsg = "int \"" + happySadColor + "\" already returned from " +
                        (checkHappy ? "getHappyColor." : "getSadColor.");

                assertFalse(errMsg, mIntArraylist.contains(happySadColor));
                mIntArraylist.add(happySadColor);
            }
            mIntArraylist.clear();
        }
    }

    // Helper function for getSad-/HappyString assertion
    private void assertCheckForHappyAndSadString(boolean checkHappy) {
        // Due to the random selection of string from the array - run the assertion n times to minimize "bad luck"
        for (int i = 0; i < 10; i++) {
            for (String happyString : mTestStrings) {
                String happySadString = checkHappy ? Utility.getHappyString(mContext) : Utility.getSadString(mContext);
                String errMsg = "String \"" + happySadString + "\" already returned from " +
                        (checkHappy ? "getHappyString." : "getSadString.");

                assertFalse(errMsg, mStringArrayList.contains(happySadString));
                mStringArrayList.add(happySadString);
            }
            mStringArrayList.clear();
        }
    }

    @Test
    public void getSadString_should_not_return_same_string_twice() {
        assertCheckForHappyAndSadString(false);
    }

    @Test
    public void getHappyString_should_not_return_same_string_twice() {
        assertCheckForHappyAndSadString(true);
    }

    @Test
    public void getOffsetDate() {
        String[] weekDays = new String[7];

        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY : weekDays = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}; break;
            case Calendar.TUESDAY : weekDays = new String[] {"Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Monday"}; break;
            case Calendar.WEDNESDAY : weekDays = new String[] {"Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday"}; break;
            case Calendar.THURSDAY : weekDays = new String[] {"Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday"}; break;
            case Calendar.FRIDAY : weekDays = new String[] {"Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}; break;
            case Calendar.SATURDAY : weekDays = new String[] {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}; break;
            case Calendar.SUNDAY : weekDays = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        }

        for (int i = 0; i < weekDays.length; i++) {
            assertTrue(Utility.getOffsetDate(i).equals(weekDays[i]));
        }
    }

    @Test
    public void getPrettyDate() {
        // This function defaults to getOffsetDate for i > 2. Therefore it should always be tested
        // after the getOffsetDate tests
        String[] expected = {"Today", "Tomorrow", "Tuesday"};

        for (int i = 0; i < 2; i++) {
            String actual = Utility.getPrettyDate(i);
            String errMsg = "getPrettyDate(" + i + ") should return " + expected[i] +
                    ", but actually returned " + actual + ".";
            assertEquals(errMsg, expected[i], actual);
        }
    }

    @Test
    public void randInInterval() {
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
    public void randInInterval_exception() {
        Utility.randInInterval(10, 0);
    }
}
