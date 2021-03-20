package dk.easj.anbo.firebaseauth3;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testLogin() {
        onView(withText("Hello World!")).check(matches(isDisplayed()));
        onView(withId(R.id.mainEmailEditText)).perform(typeText("andersb@gmail.com"));
        onView(withId(R.id.mainPasswordEditText)).perform(typeText("andersb"))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.mainLoginButton)).perform(click());
        pause(1000);
        onView(withId(R.id.mainMessageTextView)).check(matches(withText(("Welcome andersb@gmail.com"))));
    }

    private void pause(int millis) {
        try {
            Thread.sleep(millis);
            // https://www.repeato.app/android-espresso-why-to-avoid-thread-sleep/
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}