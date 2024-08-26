package com.pantrypro.systemTests;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
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
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.pantrypro.R;
import com.pantrypro.presentation.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddandDeleteRecipeTest {

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
    public void testAddandDeleteRecipe() {
        waitFor(5000); // Wait for 5 seconds
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.bottom_addRecipe), withContentDescription("Add Recipe"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        waitFor(5000); // Wait for 5 seconds
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.createTitle),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                1)));
        appCompatEditText.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.createDuration),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("30"), closeSoftKeyboard());

        onView(withId(R.id.scrollview)).perform(ViewActions.swipeUp());
        waitFor(2000); // Wait for 2 seconds

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.createIngredients),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                6)));
        appCompatEditText3.perform(scrollTo(), replaceText("1 test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.createSteps),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                8)));
        appCompatEditText4.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.createRecipe), withText("Post your recipe"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        9),
                                0)));
        materialButton.perform(scrollTo(), click());

        waitFor(5000); // Wait for 5 seconds
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipesRecyclerView),
                        childAtPosition(
                                withId(R.id.frameLayout),
                                2)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.recipeTitle), withText("test"),
                        withParent(withParent(withId(R.id.recipeCard))),
                        isDisplayed()));
        textView.check(matches(withText("test")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.recipeIngredients), withText("1 test"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView2.check(matches(withText("1 test")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.recipeSteps), withText("test"),
                        withParent(withParent(withId(R.id.recipeCard))),
                        isDisplayed()));
        textView3.check(matches(withText("test")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.recipeDuration), withText("Prep and cook time: ~30 min"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView4.check(matches(withText("Prep and cook time: ~30 min")));

        ViewInteraction actionMenuItemView = onView(
                allOf(withContentDescription(" "),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(androidx.transition.R.id.title), withText("Delete Recipe"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.content),
                                        childAtPosition(
                                                withClassName(is("androidx.appcompat.view.menu.ListMenuItemView")),
                                                1)),
                                1),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("YES, DELETE"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.buttonPanel),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());
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
