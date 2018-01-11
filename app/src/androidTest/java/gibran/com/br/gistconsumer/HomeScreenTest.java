package gibran.com.br.gistconsumer;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.net.nowkids.ui.screen.home.HomeActivity;
import gibran.com.br.gistconsumer.ui.home.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static org.hamcrest.Matchers.allOf;

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
    public void showSettingsScreen() {
//        onView(withId(R.id.fragment_screen_settings_view))
//                .perform(scrollTo(), click());
//        onView(withId(R.id.password_field)).check(matches(isDisplayed()));
    }

    @Test
    public void showSearchScreen() {
        onView(withId(R.id.action_search)).perform(click());
        onView(withId(R.id.fragment_search_container)).check(matches(isDisplayed()));
    }

    @Test
    public void showRentedScreenLogged() {
        onView(withId(R.id.action_rented)).perform(click());
        onView(withId(R.id.fragment_video_progress_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void showRentedScreenNotLogged() {
        AuthorizationManager.getInstance().logout();
        onView(withId(R.id.action_rented)).perform(click());
        onView(withText(AppContext.getInstance().getResources()
                .getString(R.string.login_message_dialog_login_is_needed)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void showCharacter() {
        ViewInteraction cardView = onView(
                allOf(withId(R.id.character_recycler_character_card),
                        withContentDescription("character_Ladybug")));
        cardView.perform(click());
        onView(withId(R.id.actionbar_character_character_name))
                .check(matches(isDisplayed()));
    }

    @Test
    public void showChannel() {
        ViewInteraction itemView = onView(
                allOf(withId(R.id.channel_recycler_item_channel_image),
                        withContentDescription("Gloob")));
        itemView.perform(scrollTo(), click());
        onView(withId(R.id.action_bar_home_image))
                .check(matches(isDisplayed()));
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                homeIntentsTestRule.getActivity().getCountingIdlingResource());
    }
}
