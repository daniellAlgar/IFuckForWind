package com.algar.ifuckforwind;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.algar.ifuckforwind.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by algar on 2016-11-30
 */

@RunWith(AndroidJUnit4.class)
public class SectionsPagerFragmentUITest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void fragmentIsShown() {
        onView(allOf(withId(R.id.fragment_main_week_layout_container), isDisplayed()));
    }

}
