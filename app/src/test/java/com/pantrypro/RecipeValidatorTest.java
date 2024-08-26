package com.pantrypro;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.pantrypro.logic.RecipeValidator;
import com.pantrypro.objects.Recipe;

import org.junit.Before;
import org.junit.Test;

public class RecipeValidatorTest {
    final private Recipe recipe = new Recipe("Kraft Dinner",
            "15",
            "Meal",
            "1 box Kraft dinner, 1 teaspoon butter, 1/4 cup milk",
            "1. Boil noodles, and strain water 2. Add butter, cheese and milk 3. Stir and serve when ready!",
            "image");

    @Before
    public void setup() {
        System.out.println("Starting test for Recipe");
    }

    @Test
    public void testCreateRecipe() {
        System.out.println("\nStarting testCreateRecipe");

        assertNotNull(recipe);

        System.out.println("Finished testCreateRecipe");
    }

    @Test
    public void testValidateTitle() {
        System.out.println("\nStarting testValidateTitle");

        assertTrue(RecipeValidator.validateTitle(recipe.getRecipeTitle()));

        System.out.println("Finished testValidateTitle");
    }

    @Test
    public void testValidateDuration() {
        System.out.println("\nStarting testValidateDuration");

        assertTrue(RecipeValidator.validateDuration(recipe.getDuration()) > 0);

        System.out.println("Finished testValidateDuration");
    }

    @Test
    public void testValidateIngredients() {
        System.out.println("\nStarting testValidateIngredients");

        assertTrue(RecipeValidator.validateIngredients(recipe.getIngredients()));

        System.out.println("Finished testValidateIngredients");
    }

    @Test
    public void testValidateSteps() {
        System.out.println("\nStarting testValidateSteps");

        assertTrue(RecipeValidator.validateSteps(recipe.getSteps()));

        System.out.println("Finished testValidateSteps");
    }
}
