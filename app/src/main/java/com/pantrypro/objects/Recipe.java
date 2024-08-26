package com.pantrypro.objects;

import com.pantrypro.logic.exceptions.InvalidRecipeValues;

/**
 * Recipe object class.
 */
public class Recipe {
    private int id;
    private String recipeTitle;
    private String ingredients;
    private String steps;
    private String image;
    private String foodType;
    private String duration;

    public static final int minStrLen = 3; //The title, ingredients, and steps all must have a minimum length
    public static final int minDuration = 0;
    public static final int maxDuration = 999;


    /**
     * Constructor for creating a Recipe object <br>
     * Primarily used for testing
     * @param recipeTitle Title of the recipe
     * @param ingredients Ingredients needed for the recipe
     * @param steps Steps to prepare the recipe
     * @param image Image representing the recipe
     */
    public Recipe(String recipeTitle, String ingredients, String steps, String image) {
        this.recipeTitle = recipeTitle;
        this.ingredients = ingredients;
        this.steps = steps;
        this.image = image;
    }

    /**
     * Constructor for creating a Recipe object <br>
     * Primarily used for testing and creating a recipe when the ID is not available
     * @param recipeTitle Title of the recipe
     * @param duration Time needed to make the recipe
     * @param foodType Category of food the recipe falls under
     * @param ingredients Ingredients needed for the recipe
     * @param steps Steps to prepare the recipe
     * @param image Image representing the recipe
     */
    public Recipe(String recipeTitle, String duration, String foodType, String ingredients, String steps, String image) {
        this(recipeTitle, ingredients, steps, image);
        this.duration = duration;
        this.foodType = foodType;
    }



    /**
     * Constructor for creating a Recipe object with an ID.
     * @param id Unique identifier for the recipe
     * @param recipeTitle Title of the recipe
     * @param duration Time needed to make the recipe
     * @param foodType Category of food the recipe falls under
     * @param ingredients Ingredients needed for the recipe
     * @param steps Steps to prepare the recipe
     * @param image Image representing the recipe
     */
    public Recipe(int id, String recipeTitle, String duration, String foodType, String ingredients, String steps, String image) {
        this(recipeTitle, duration, foodType, ingredients, steps, image);
        this.id = id;
    }


    // constructor for storing image on Firebase only
    public Recipe(String recipeTitle, String image){
        this.recipeTitle = recipeTitle;
        this.image = image;
    }

    /**
     * Getter method for retrieving the recipe's ID.
     */
    public int getID() {
        return id;
    }

    /**
     * Getter method for retrieving the recipe's title.
     */
    public String getRecipeTitle() {
        return recipeTitle;
    }

    /**
     * Getter method for retrieving the recipe's ingredients.
     */
    public String getIngredients() {
        return ingredients;
    }

    /**
     * Getter method for retrieving the recipe's steps.
     */
    public String getSteps() {
        return steps;
    }

    /**
     * Getter method for retrieving the recipe's image.
     */
    public String getImage() {
        return image;
    }

    public String getFoodType() {
        return foodType;
    }

    public String getDuration() {
        return duration;
    }

    /**
     * Check to make sure no values are null/unaccepted values <br>
     * Used before interacting with a Recipe to avoid NullPointerExceptions
     */
    public void checkForNulls() throws InvalidRecipeValues {
        if(id < 0) {
            throw new InvalidRecipeValues("Invalid ID value");
        } else if(recipeTitle == null) {
            throw new InvalidRecipeValues("Invalid Title");
        } else if(ingredients == null) {
            throw new InvalidRecipeValues("Invalid Ingredients");
        } else if(steps == null) {
            throw new InvalidRecipeValues("Invalid Steps");
        } else if(image == null) {
            throw new InvalidRecipeValues("Invalid Image");
        }
    }

    //Compare all values of this and compareTo
    public boolean equals(Recipe compareTo) {

        try {
            //Make sure neither Recipe object has illegal values
            checkForNulls();
            //Ensure compareTo is not null before calling a method on it
            if(compareTo != null) {
                compareTo.checkForNulls();
            }

        } catch (InvalidRecipeValues e) {
            return false; //Don't want to compare values if there are illegal values
        }
        //Check equality if there are no problems from checkForNulls
        return compareTo != null
                && id == compareTo.getID()
                && recipeTitle.equals(compareTo.getRecipeTitle())
                && ingredients.equals(compareTo.getIngredients())
                && steps.equals(compareTo.getSteps())
                && image.equals(compareTo.getImage());
    }
}
