package cz.muni.fi.pv256.movio2.uco_396537;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by david on 12.1.17.
 */

public class InstrumentedUITest {

    private MainActivity mainActivity;

    @Rule
    public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() {
        mainActivity = rule.getActivity();
    }

    @Test
    public void testBigScreen() {
        
    }
}
