package com.pantrypro.systemTests;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.pantrypro.R;
import com.pantrypro.presentation.LoginActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignupTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    // Declare an IdlingResource
    private static final String IDLING_RESOURCE_NAME = "WaitForView";
    private static final CountingIdlingResource countingIdlingResource = new CountingIdlingResource(IDLING_RESOURCE_NAME);

    // Register the IdlingResource before the test
    @Before
    public void setup() {
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }

    // Unregister the IdlingResource after the test
    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource);
    }

    // Use IdlingResource to wait
    public static void waitFor(long millis) {
        countingIdlingResource.increment();
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countingIdlingResource.decrement();
        }
    }

    @Test
    public void testSignup() {
        waitFor(3000); // Wait for 3 seconds
        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.signupRedirectText), withText("Don't have an account? Sign up"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                5),
                        isDisplayed()));
        materialTextView.perform(click());

        waitFor(3000); // Wait for 3 seconds

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.signupName),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.signupEmail),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("test5@test.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.signupUsername),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                7),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Testing5"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.signupPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                9),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Testing100!"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.signupConfirmPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                11),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Testing100!"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.signupButton), withText("SIGN UP"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                12),
                        isDisplayed()));
        materialButton.perform(click());

        waitFor(3000); // Wait for 3 seconds

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.loginUsername),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Testing5"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.loginPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("Testing100!"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.loginButton), withText("LOG IN"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                4),
                        isDisplayed()));
        materialButton2.perform(click());

        waitFor(3000); // Wait for 3 seconds

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.bottom_profile), withContentDescription("Your Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        waitFor(3000); // Wait for 3 seconds

        ViewInteraction textView = onView(
                allOf(withId(R.id.profileName), withText("Test"),
                        withParent(withParent(withId(R.id.linearLayout))),
                        isDisplayed()));
        textView.check(matches(withText("Test")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.profileEmail), withText("test5@test.com"),
                        withParent(withParent(withId(R.id.linearLayout))),
                        isDisplayed()));
        textView2.check(matches(withText("test5@test.com")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.profileUsername), withText("Testing5"),
                        withParent(withParent(withId(R.id.linearLayout))),
                        isDisplayed()));
        textView3.check(matches(withText("Testing5")));

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.deleteAccount), withText("Delete Account"),
                        childAtPosition(
                                allOf(withId(R.id.profilePicSection),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("YES"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.test.espresso.contrib.R.id.buttonPanel),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());
        waitFor(3000); // wait for the server side to delete account else test considers this as fail
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
