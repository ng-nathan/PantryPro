package com.pantrypro.application;

import com.pantrypro.persistence.RecipePersistence;
import com.pantrypro.persistence.hsqldb.RecipePersistenceHSQLDB;
import com.pantrypro.persistence.stubs.RecipePersistenceStub;

/**
 * Services class provides access to various support methods
 */
public class Services {

    // Reference to recipe database interface
    private static RecipePersistence recipeDB = null;

    /**
     * Retrieves the recipe persistence
     * and creates one if NULL
     */
    public static synchronized RecipePersistence getRecipePersistence(boolean forProduction){
        if(recipeDB == null){
            // If null, create a new instance of RecipePersistence
            if (forProduction) {
                recipeDB = new RecipePersistenceHSQLDB(Main.getDBPathName());
            } else {
                recipeDB = new RecipePersistenceStub();
            }

        }
        return recipeDB;
    }

    public static synchronized void clean() {
        recipeDB = null;
    }
}
