package com.pantrypro.systemTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.os.SystemClock;
import android.view.MotionEvent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.pantrypro.R;
import com.pantrypro.logic.AccessRecipes;
import com.pantrypro.objects.Recipe;
import com.pantrypro.presentation.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

public class RecipeTest {
    private final int sleepTime = 500;
    private AccessRecipes handleRecipesDB;
    List<Recipe> recipes;
    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
        handleRecipesDB = new AccessRecipes(true);
        recipes = handleRecipesDB.getAllRecipes();
    }

    @Test
    public void testIsRecycleViewDisplayed() {
        onView(ViewMatchers.withId(R.id.recipesRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testToolbarDisplayed () {
        onView(withId(R.id.recipesRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Collections.reverse(recipes);
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText(recipes.get(0).getRecipeTitle()))));
    }

    @Test
    public void testSelectRecipeDisplayed() {
        onView(withId(R.id.recipesRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Collections.reverse(recipes);
        onView(withId(R.id.recipeTitle)).check(matches(withText(recipes.get(0).getRecipeTitle())));
        onView(withId(R.id.recipeIngredients)).check(matches(withText(recipes.get(0).getIngredients())));
        onView(withId(R.id.recipeSteps)).check(matches(withText(recipes.get(0).getSteps())));
    }

}
