package com.pantrypro;


import static org.junit.Assert.assertEquals;

import com.pantrypro.objects.Recipe;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Recipe class.
 */
public class RecipeTest {
    @Before
    public void setup() {
        System.out.println("Starting test for Recipe");
    }

    @Test
    public void testCreateRecipe() {
        System.out.println("\nStarting testCreateRecipe");
        Recipe recipe = new Recipe("Cookies",
                "Chocolate Chips",
                "Eat the chocolate chips",
                "0");

        assertEquals("Cookies", recipe.getRecipeTitle());
        assertEquals("Chocolate Chips", recipe.getIngredients());
        assertEquals("Eat the chocolate chips", recipe.getSteps());
        assertEquals("0", recipe.getImage());

        System.out.println("Finished testCreateRecipe");
    }
}
