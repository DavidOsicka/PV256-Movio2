package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.res.Configuration;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by david on 12.1.17.
 */

public class InstrumentedUITest {


    private MainActivity mainActivity;

    private boolean isTablet = false;

    @Rule
    public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mainActivity = rule.getActivity();
        isTablet = (mainActivity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                    (mainActivity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    @Test
    public void testBigScreen() throws InterruptedException {
        if(isTablet) {
            onView(withId(R.id.list_view_fragment)).check(matches(isDisplayed()));
            onView(withId(R.id.detail_view_fragment)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.list_view_fragment)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void testDetailFragmentAppear() throws InterruptedException {
        onView(withId(R.id.list_view_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.empty_view)).check(matches(isDisplayed()));

        Thread.sleep(5000);

        ViewInteraction recyclerView = onView(withId(R.id.recycler_view));
        recyclerView.check(matches(isDisplayed()));
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.detail_view_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testShowSavedMovies() throws InterruptedException {
        boolean downloadedMoviesShown = true;
        Thread.sleep(5000);

        try {
            onView(withId(R.id.category_view)).check(matches(isDisplayed()));
        } catch (Exception e) {
            downloadedMoviesShown = false;
        }

        onView(withId(R.id.switcher)).perform(click());

        if(downloadedMoviesShown) {
            onView(withId(R.id.category_view)).check(doesNotExist());
        } else {
            onView(withId(R.id.category_view)).check(matches(isDisplayed()));
        }
    }

}

