package com.pantrypro.systemTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.pantrypro.R;
import com.pantrypro.presentation.LoginActivity;
import com.pantrypro.presentation.MainActivity;
import com.pantrypro.presentation.SignupActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Before
    public void setUp() {
        // Start the LoginActivity before each test
        ActivityScenario.launch(LoginActivity.class);
    }

    /*
    * Testing visability. Any further tests with login are tested in SignupTest
    * where we go from signup to login then delete account
    * */

    @Test
    public void testCheckUsernameAndPasswordInputsDisplayed() {
        // Check if the username input is displayed
        onView(ViewMatchers.withId(R.id.loginUsername))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check if the password input is displayed
        onView(ViewMatchers.withId(R.id.loginPassword))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testRedirectToSignup() {
        // Register IntentMonitor
        Intents.init();

        // Click on the redirect to sign up page
        onView(ViewMatchers.withId(R.id.signupRedirectText))
                .perform(ViewActions.click());
        // See if they are matched
        intended(IntentMatchers.hasComponent(SignupActivity.class.getName()));

        // Unregister IntentMonitor
        Intents.release();
    }

    @Test
    public void testLoginAsGuest(){
        // Register IntentMonitor
        Intents.init();

        onView(ViewMatchers.withId(R.id.loginAsGuest))
                .perform(ViewActions.click());
        intended(IntentMatchers.hasComponent(MainActivity.class.getName()));
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.bottom_profile), withContentDescription("Your Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.deleteAccount), withText("Create an Account"),
                        childAtPosition(
                                allOf(withId(R.id.profilePicSection),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        intended(IntentMatchers.hasComponent(SignupActivity.class.getName()));

        // Unregister IntentMonitor
        Intents.release();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
