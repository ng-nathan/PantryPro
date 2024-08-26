package com.pantrypro.logic.exceptions;

public class InvalidRecipeValues extends RecipeException {
    static final String DEFAULT = "Invalid values found in Recipe";
    public InvalidRecipeValues() {
        super(DEFAULT);
    }

    public InvalidRecipeValues(String err) {
        super(err);
    }
}
