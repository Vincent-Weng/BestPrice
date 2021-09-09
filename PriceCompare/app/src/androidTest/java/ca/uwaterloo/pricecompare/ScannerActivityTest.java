package ca.uwaterloo.pricecompare;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ScannerActivityTest {
    @Rule
    public ActivityTestRule<ScannerActivity> activityTestRule = new ActivityTestRule<>(ScannerActivity.class);


}