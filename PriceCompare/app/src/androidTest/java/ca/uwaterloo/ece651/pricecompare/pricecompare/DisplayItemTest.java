package ca.uwaterloo.ece651.pricecompare.pricecompare;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DisplayItemTest {
    @Rule
    public ActivityTestRule<ScannerActivity> intentsTestRule =
            new ActivityTestRule<>(ScannerActivity.class);
    //click testing
    private void testClick(final int id)
    {
        onView(withId(id)).perform(click());
    }
    //edittest testing
    private void testEditText(final int id,String matchString){
        onView(withId(id)).check(matches(withText(matchString)));
    }
    //hint testing
    private void testHint(final int id, String hint){
        onView(withId(id)).check(matches(withHint(hint)));
    }
    //start Display
    private void startDisplayItem(String UPC) throws InterruptedException {
        intentsTestRule.getActivity().turnToDisOrAdd(UPC);
    }


    @Test
    public void test() throws InterruptedException {
        String UPC = "60410017715";
        startDisplayItem(UPC);
        testEditText(R.id.edt_dis_upc,UPC);
        testClick(R.id.display_store);

    }

}