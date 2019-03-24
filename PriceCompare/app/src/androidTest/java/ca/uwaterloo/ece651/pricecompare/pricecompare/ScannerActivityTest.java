package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ScannerActivityTest {
    @Rule
    public ActivityTestRule<ScannerActivity> activityTestRule = new ActivityTestRule<>(ScannerActivity.class);


}