package ca.uwaterloo.pricecompare;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    // back to the main activity
    private void testBackToMain(){
        onView(isRoot()).perform(ViewActions.pressBack());
    }
    @Test
    public void test() {
        testClick(R.id.cat_button_0);
        testBackToMain();
        testClick(R.id.cat_button_1);
        testBackToMain();
        testClick(R.id.cat_button_2);
        testBackToMain();
        testClick(R.id.cat_button_3);
        testBackToMain();
        testClick(R.id.cat_button_4);
        testBackToMain();
        testClick(R.id.cat_button_5);
        testBackToMain();
        testClick(R.id.recycler_view);

    }


}