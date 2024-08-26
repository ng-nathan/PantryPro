package com.pantrypro.logic.exceptions;

import com.pantrypro.objects.Recipe;

public class RecipeException extends Exception {
    static final String DEFAULT = "Error in Recipe";
    public RecipeException() {
        super(DEFAULT);
    }

    public RecipeException(String err) {
        super(err);
    }
}
