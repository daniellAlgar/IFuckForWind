package com.algar.ifuckforwind;

import com.algar.ifuckforwind.util.Utility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by algar on 2016-08-27
 */

@RunWith(JUnit4.class)
public class UtilityTest {
    // TODO: Test all functions accepting Context as input parameter

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
        // This function defaults to getoffsetDate for i > 2. Therefore it should always be tested
        // after the getOffsetDate tests
        String[] expected = {"Today", "Tomorrow", "Tuesday"};

        for (int i = 0; i < 2; i++) {
            String actual = Utility.getPrettyDate(i);
            String errMsg = "getPrettyDate(" + i + ") should return " + expected[i] +
                    ", but actually " + "returned " + actual + ".";
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
