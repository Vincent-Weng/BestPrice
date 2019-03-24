package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    //click testing
    private void testClick(final int id) {
        onView(withId(id)).perform(click());
    }

    //edittest testing
    private void testEditText(final int id, String matchString) {
        onView(withId(id)).check(matches(withText(matchString)));
    }

    @Test
    public void test() {
        testClick(R.id.add_button);
    }


}