package com.pantrypro.logic;

import android.util.Log;

import com.pantrypro.application.Services;
import com.pantrypro.logic.exceptions.RecipeException;
import com.pantrypro.objects.Recipe;
import com.pantrypro.persistence.RecipePersistence;

import java.util.List;

/**
 * AccessRecipes class provides access to recipes data.
 */
public class AccessRecipes {

    // Reference to the interface for accessing recipe data
    private final RecipePersistence recipesData;

    /**
     * Constructor initializes the AccessRecipes object.
     */
    public AccessRecipes(boolean forProduction) {
        recipesData = Services.getRecipePersistence(forProduction);
    }

    /**
     * Retrieves a list of all recipes.
     */
    public List<Recipe> getAllRecipes() {
        return recipesData.getRecipeList();
    }

    public Recipe getRecipeByID(int id) {
        try {
            return recipesData.getRecipeByID(id);
        } catch (RecipeException e) {
            Log.e("Recipe Exception", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void addRecipe(Recipe toAdd) {
        try {
            recipesData.addRecipe(toAdd);
        } catch (RecipeException e) {
            Log.e("Recipe Exception", e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeRecipe(Recipe toRemove) {
        try {
            recipesData.removeRecipe(toRemove);
        } catch (RecipeException e) {
            Log.e("Recipe Exception", e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeRecipeByID(int id) {
        recipesData.removeRecipeByID(id);
    }

    public void updateRecipe(Recipe updatedRecipe) {
        try {
            recipesData.updateRecipe(updatedRecipe);
        } catch (RecipeException e) {
            Log.e("Recipe Exception", e.getMessage());
            e.printStackTrace();
        }
    }
}
