package com.pantrypro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.pantrypro.application.Services;
import com.pantrypro.logic.AccessRecipes;
import com.pantrypro.objects.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AccessRecipesIT {
    private AccessRecipes recipeHandler;
    private File tempDB;
    private List<Recipe> listRecipes;

    @Before
    public void setup() throws IOException {
        System.out.println("Starting integration test for AccessRecipes");
        this.tempDB = TestUtils.copyDB();
        recipeHandler = new AccessRecipes(true);
        listRecipes = recipeHandler.getAllRecipes();
    }

    @Test
    public void testRecipeList() {
        System.out.println("Starting testRecipeList");
        assert(!listRecipes.isEmpty());
        assertEquals(listRecipes.get(0).getRecipeTitle(), "Baguette Pizza");
        System.out.println("Finished testRecipeList");
    }

    @Test
    public void testRecipeAddAndGet() {
        System.out.println("Starting testRecipeAddAndGet");
        //Add recipe
        Recipe toAdd = new Recipe("TestRecipe", "Some Ingredients", "Some Steps", "An image");
        recipeHandler.addRecipe(toAdd);

        //Database has 3 initial recipes. Get the fourth one that we added
        Recipe temp = recipeHandler.getRecipeByID(4);

        assertEquals(toAdd.getRecipeTitle(), temp.getRecipeTitle());
        assertEquals(toAdd.getIngredients(), temp.getIngredients());
        assertEquals(toAdd.getSteps(), temp.getSteps());
        assertEquals(toAdd.getImage(), temp.getImage());

        System.out.println("Finished testRecipeAddAndGet");
    }

    @Test
    public void testRecipeRemove() {
        System.out.println("Starting testRecipeRemove");

        Recipe toRemove = listRecipes.get(0);
        //Ensure the object we have is still at the same position in the list
        assert(toRemove.equals(listRecipes.get(0)));
        recipeHandler.removeRecipe(toRemove);
        listRecipes = recipeHandler.getAllRecipes();
        assertFalse(toRemove.equals(listRecipes.get(0)));

        System.out.println("Finished testRecipeRemove");
    }

    @Test
    public void testGetRecipeByID() {
        System.out.println("Starting testGetRecipeByID");
        Recipe toGet = listRecipes.get(1);
        Recipe compareTo = recipeHandler.getRecipeByID(toGet.getID());
        assert(toGet.equals(compareTo));
        System.out.println("Finished testGetRecipeByID");
    }

    @Test
    public void testRemoveRecipeByID() {
        System.out.println("Starting testRemoveRecipeByID");

        Recipe toRemove = listRecipes.get(1);
        //Ensure the object we have is still at the same position in the list
        assert(toRemove.equals(listRecipes.get(1)));
        //Double check ID to be safe
        assert(toRemove.equals(recipeHandler.getRecipeByID(toRemove.getID())));

        recipeHandler.removeRecipeByID(toRemove.getID());
        listRecipes = recipeHandler.getAllRecipes();
        assertFalse(toRemove.equals(listRecipes.get(1)));

        //Check every recipe in the list. None should be the same as toRemove
        List<Recipe> newList = recipeHandler.getAllRecipes();
        for(Recipe curr : newList) {
            assertFalse(toRemove.equals(curr));
        }

        System.out.println("Finished testRemoveRecipeByID");
    }

    @Test
    public void testUpdateRecipe() {
        System.out.println("Starting testUpdateRecipe");

        Recipe toUpdate = listRecipes.get(0);
        recipeHandler.updateRecipe(new Recipe(toUpdate.getID(), toUpdate.getRecipeTitle(), toUpdate.getDuration(), toUpdate.getFoodType(), "New Ingredients", toUpdate.getSteps(), toUpdate.getImage()));

        Recipe updated = recipeHandler.getRecipeByID(toUpdate.getID());

        assertEquals(updated.getRecipeTitle(), toUpdate.getRecipeTitle());
        assertEquals(updated.getIngredients(), "New Ingredients");
        assertNotEquals(updated.getIngredients(), toUpdate.getIngredients());
        assertEquals(updated.getSteps(), toUpdate.getSteps());
        assertEquals(updated.getImage(), toUpdate.getImage());

        System.out.println("Finished testUpdateRecipe");
    }

    @After
    public void tearDown() {
        System.out.println("Reset database");
        this.tempDB.delete();
        Services.clean();
    }
}
