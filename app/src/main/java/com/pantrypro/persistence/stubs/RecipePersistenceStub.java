package com.pantrypro.persistence.stubs;

import com.pantrypro.R;
import com.pantrypro.logic.exceptions.InvalidRecipeValues;
import com.pantrypro.logic.exceptions.RecipeDNE;
import com.pantrypro.logic.exceptions.RecipeException;
import com.pantrypro.objects.Recipe;
import com.pantrypro.persistence.RecipePersistence;

import java.util.ArrayList;
import java.util.List;

/**
 * RecipePersistenceStub class implementing the RecipePersistence interface.
 * Providies a stub database for managing recipes in memory.
 */
public class RecipePersistenceStub implements RecipePersistence {
    private List<Recipe> recipes;

    /**
     * Constructor for RecipePersistenceStub.
     * Initializes the list of recipes with sample recipe data.
     */
    public RecipePersistenceStub() {
        recipes = new ArrayList<>();
        recipes.add(new Recipe(
                "Baguette Pizza",
                "40",
                "Meal",
                "1 French Baguette\n" +
                        "1/2 cup pizza sauce\n" +
                        "2/3 cup shredded mozzarella cheese\n" +
                        "2 ounces cooked and crumbled sausage\n" +
                        "2 ounces mini pepperoni slices\n",
                "Preheat the oven to 375 degrees F (190 degrees C).\n" +
                        "Split baguette in half lengthwise. Warm pizza sauce in a microwave-safe bowl until hot, about  45 seconds.\n" +
                        "Spread pizza sauce on baguette, sprinkle on cheese, and scatter sausage and pepperoni on top as desired. Place pizza onto a baking tray.\n" +
                        "Bake in the preheated oven until cheese is golden, about 18 minutes. Optional step: turn on the oven’s broiler, set a rack 6 inches below the heating element, and broil the pizza for a deeper color, 1 to 2 minutes.\n" +
                        "Cut pizza crosswise into slices. Serve and enjoy.",
                Integer.toString(R.drawable.baguette_pizza)
        ));

        recipes.add(new Recipe(
                "Peanut Butter Mousse",
                "20",
                "Dessert",
                "2 ounces Neufchâtel cheese\n" +
                        "1/4 cup peanut butter\n" +
                        "1/4 cup confectioners' sugar\n" +
                        "1/2 cup heavy whipping cream\n",
                "Beat Neufchâtel cheese, confectioners' sugar, and peanut butter together in a bowl until smooth and well combined. Set aside.\n" +
                        "Place heavy whipping cream in another bowl and whip until medium peaks form. Add peanut butter mixture and whip just until combined.\n" +
                        "Divide evenly into 2 serving glasses. Serve at once, or cover and refrigerate.\n",
                Integer.toString(R.drawable.pb_mousse)
        ));

        recipes.add(new Recipe(
                "Scalloped Potatoes",
                "40",
                "Meal",
                "2 tablespoons unsalted butter\n" +
                        "4 medium russet potatoes\n" +
                        "1 1/2 cups heavy cream\n" +
                        "1/2 cup whole milk\n" +
                        "2 cloves garlic, minced\n" +
                        "1/2 teaspoon fresh thyme leaves\n" +
                        "Kosher salt and freshly ground black pepper\n" +
                        "Pinch of freshly grated nutmeg\n" +
                        "1 cup shredded Gruyere\n" +
                        "1/2 cup finely grated parmesan\n",
                "Preheat the oven to 350 degrees F. Grease an 8-inch square baking dish with unsalted butter.\n" +
                        "Peel the potatoes and cut them into 1/8-inch-thick slices using a mandoline or sharp knife.\n" +
                        "Add the sliced potatoes, heavy cream, whole milk, 2 tablespoons butter, garlic, thyme, 1 1/2 teaspoons salt, 1/2 teaspoon pepper and the nutmeg to a medium saucepan. Bring to a simmer over medium heat and cook, stirring occasionally, until the potatoes are tender and almost cooked through but still hold their shape, about 5 minutes. (They should not be soft and falling apart.) Remove the saucepan from the heat.\n" +
                        "Add the Gruyere and Parmesan, stirring gently to combine. Pour the potato mixture into the prepared baking dish. Bake until the top is light golden brown and the potatoes are cooked through and tender, about 45 minutes. Let rest for 10 minutes before serving.\n",
                Integer.toString(R.drawable.scalloped_potatoes)
        ));

    }
    @Override
    public List<Recipe> getRecipeList() {
        return recipes;
    }
    @Override
    public Recipe getRecipeByID(int id) {
        for (Recipe curr : recipes) {
            if (curr.getID() == id) {
                return curr;
            }
        }
        return null;
    }
    @Override
    public void addRecipe(Recipe toAdd) throws RecipeException {
        if(toAdd == null) {
            throw new InvalidRecipeValues();
        }
        toAdd.checkForNulls();

        recipes.add(toAdd);
    }
    @Override
    public void removeRecipe(Recipe toRemove) throws RecipeException {
        if(toRemove == null) {
            throw new InvalidRecipeValues();
        }
        toRemove.checkForNulls();

        recipes.remove(toRemove);
    }
    @Override
    public void removeRecipeByID(int idToRemove) {
        recipes.removeIf(r -> (r.getID() == idToRemove));
    }
    @Override
    public void updateRecipe(Recipe updatedRecipe) throws RecipeException {
        //Removes original recipe and replaces it with new updated one
        if(recipes.removeIf(r -> (r.getID() == updatedRecipe.getID()))) {
            recipes.add(updatedRecipe);
        } else {
            //Throws exception if the recipe cannot be found
            throw new RecipeDNE();
        }
    }

}
