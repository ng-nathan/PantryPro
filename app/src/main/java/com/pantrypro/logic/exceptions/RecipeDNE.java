package com.pantrypro.logic.exceptions;

public class RecipeDNE extends RecipeException {
    static final String DEFAULT = "Recipe Does Not Exist";
    public RecipeDNE() {
        super(DEFAULT);
    }

    public RecipeDNE(String err) {
        super(err);
    }
}
