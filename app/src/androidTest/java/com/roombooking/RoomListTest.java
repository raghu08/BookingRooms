package com.roombooking;

/**
 * Created by Raghavendra Malgi on 20-11-2017.
 */
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.roombooking.ui.home.RoomListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RoomListTest {

    @Rule
    public IntentsTestRule<RoomListActivity> activityRule =
            new IntentsTestRule<>(RoomListActivity.class, true, false);

    @Test
    public void shouldShowRecyclerView() {
        startActivity();

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    private RoomListActivity startActivity() {
        return activityRule.launchActivity(null);
    }
}
