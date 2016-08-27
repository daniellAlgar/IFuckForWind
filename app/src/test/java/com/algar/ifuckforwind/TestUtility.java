package com.algar.ifuckforwind;

import com.algar.ifuckforwind.util.Utility;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by algar on 2016-08-27.
 */

public class TestUtility {

    @Test
    public void testRandInInterval() {
        assertTrue(Utility.randInInterval(10, 10) == 10);

        for (int i = 0; i < 10; i++) {
            assertTrue(Utility.randInInterval(i, 10) >= i);
            assertTrue(Utility.randInInterval(i) <= i);
        }
    }
}
