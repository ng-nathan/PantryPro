package com.pantrypro.systemTests;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.pantrypro.R;
import com.pantrypro.presentation.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);
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
    public void testSearch() {
        waitFor(5000); // Wait for 5 seconds
        ViewInteraction searchAutoComplete = onView(
                allOf(withId(androidx.appcompat.R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("Scalloped Potatoes"), closeSoftKeyboard());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withId(androidx.appcompat.R.id.search_src_text), withText("Scalloped Potatoes"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView), withText("Scalloped Potatoes"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Scalloped Potatoes")));
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
