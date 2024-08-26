package com.pantrypro.logic;

import com.pantrypro.objects.ErrorMessage;
import com.pantrypro.objects.Recipe;
import com.pantrypro.presentation.AddRecipeFragment;

public class RecipeValidator {

    public static boolean validateRecipe(Recipe recipe){
        return recipe !=null;
    }

    //Generic validate method used to check title, ingredients, and steps strings
    private static boolean validateString(String value) {
        return value != null && !(value.isEmpty() || value.length() < Recipe.minStrLen);
    }

    public static boolean validateTitle(String title){
        return validateString(title);
    }


    public static int validateDuration(String duration){
        if(duration.isEmpty()){
            return -1; //Invalid duration
        } else {
            int dur = Integer.parseInt(duration);
            //Duration needs to be between min and max duration
            if (dur > Recipe.minDuration && dur < Recipe.maxDuration) {
                return 1; //Duration is good
            } else if (dur > Recipe.maxDuration) {
                return 0; //Duration is too long
            } else {
                return -1; //Duration is below 0
            }
        }
    }

    public static boolean validateIngredients(String ingredients){
        return validateString(ingredients);
    }

    public static boolean validateSteps(String steps){
        return validateString(steps);
    }

    public static boolean validateUserInput(AddRecipeFragment recipeFragment) {
        boolean valid = true;
        if(!validateTitle(recipeFragment.getTitle())) {
            recipeFragment.titleBox.setError(ErrorMessage.INVALID_TITLE);
            recipeFragment.titleBox.requestFocus();
            valid = false;
        }
        if(RecipeValidator.validateDuration(recipeFragment.getDuration()) <= 0) {
            recipeFragment.durationBox.setError(ErrorMessage.INVALID_DURATION);
            recipeFragment.durationBox.requestFocus();
            valid = false;
        }
        if(!RecipeValidator.validateIngredients(recipeFragment.getIngredients())) {
            recipeFragment.ingredientsBox.setError(ErrorMessage.INVALID_INGREDIENTS);
            recipeFragment.ingredientsBox.requestFocus();
            valid = false;
        }
        if(!RecipeValidator.validateSteps(recipeFragment.getSteps())) {
            recipeFragment.stepsBox.setError(ErrorMessage.INVALID_STEPS);
            recipeFragment.stepsBox.requestFocus();
            valid = false;
        }
        if(valid) {
            recipeFragment.clearAllErrors();
        }
        return valid;
    }
}
