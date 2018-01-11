package gibran.com.br.gistconsumer;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gibran.com.br.gistconsumer.ui.home.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by gibranlyra on 14/09/17.
 */

public class HomeScreenTest {
    /**
     * {@link IntentsTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public IntentsTestRule<HomeActivity> homeIntentsTestRule =
            new IntentsTestRule<>(HomeActivity.class);

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests significantly
     * more reliable.
     */
    @Before
    public void registerIdlingResource() {
        Espresso.registerIdlingResources(
                homeIntentsTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void showFavoriteTab() {
        onView(withId(R.id.action_favorites)).perform(click());
        onView(withId(R.id.favoritesRecycler)).check(matches(isDisplayed()));
    }

    @Test
    public void showGistDetails() {
        onView(withId(R.id.favoritesRecycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.favoriteButton)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                homeIntentsTestRule.getActivity().getCountingIdlingResource());
    }
}
