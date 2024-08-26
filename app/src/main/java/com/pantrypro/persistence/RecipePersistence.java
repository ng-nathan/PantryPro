package com.pantrypro.persistence;

import com.pantrypro.objects.Recipe;
import com.pantrypro.logic.exceptions.RecipeException;

import java.util.List;

public interface RecipePersistence {
    List<Recipe> getRecipeList();
    Recipe getRecipeByID(int id) throws RecipeException;
    void addRecipe(Recipe toAdd) throws RecipeException;
    void removeRecipe(Recipe toRemove) throws RecipeException;
    void removeRecipeByID(int idToRemove);
    void updateRecipe(Recipe updatedRecipe) throws RecipeException;

}
