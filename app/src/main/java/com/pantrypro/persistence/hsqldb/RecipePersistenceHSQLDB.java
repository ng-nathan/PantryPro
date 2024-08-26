package com.pantrypro.persistence.hsqldb;

import android.util.Log;

import com.pantrypro.logic.exceptions.InvalidRecipeValues;
import com.pantrypro.logic.exceptions.RecipeDNE;
import com.pantrypro.logic.exceptions.RecipeException;
import com.pantrypro.objects.Recipe;
import com.pantrypro.persistence.RecipePersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * RecipePersistenceHSQLDB class implementing the RecipePersistence interface,
 * providing methods to interact with the HSQLDB database to manage recipes.
 */
public class RecipePersistenceHSQLDB implements RecipePersistence {
    private final String dbPath; // Path to the database
    private ArrayList<Recipe> recipes; // List of recipes

    /**
     * Constructor for RecipePersistenceHSQLDB.
     * @param dbPath The path to the HSQLDB database.
     */
    public RecipePersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
        this.recipes = new ArrayList<>();
        loadRecipes();
    }

    /**
     * Connects to the HSQLDB database.
     * @return A Connection object representing the database connection.
     * @throws SQLException If a database access error occurs.
     */
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    /**
     * Converts a ResultSet object to a Recipe object.
     * @param rs The ResultSet containing recipe information.
     * @return A Recipe object from the ResultSet.
     * @throws SQLException If a database access error occurs.
     */
    private Recipe fromResultSet(final ResultSet rs) throws SQLException {
        int recipeID = rs.getInt("id");
        String recipeTitle = rs.getString("title");
        String steps = rs.getString("steps");
        String ingredients = rs.getString("ingredients");
        String previewImg = rs.getString("image");
        String duration = rs.getString("duration");
        String foodType = rs.getString("type");

        return new Recipe(recipeID, recipeTitle, duration, foodType, ingredients, steps, previewImg);
    }

    /**
     * Loads recipes from the database and populates the recipes list.
     */
    private void loadRecipes() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM RECIPES;");

            while (resultSet.next()) {
                final Recipe recipe = fromResultSet(resultSet);
                this.recipes.add(recipe);
            }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Recipe> getRecipeList() {
        final ArrayList<Recipe> recipeArray = new ArrayList<>();
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM RECIPES;");

            while (resultSet.next()) {
                final Recipe recipe = fromResultSet(resultSet);
                recipeArray.add(recipe);
            }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return recipeArray;
    }

    @Override
    public Recipe getRecipeByID(int id) throws RecipeException {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM RECIPES WHERE RECIPES.id = ?;");
            statement.setString(1, Integer.toString(id));
            final ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return fromResultSet(resultSet);
            }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        throw new RecipeDNE();
    }
    @Override
    public void addRecipe(Recipe toAdd) throws RecipeException {
        if(toAdd == null) {
            throw new InvalidRecipeValues();
        }
        toAdd.checkForNulls();

        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO RECIPES (TITLE, DURATION, TYPE, INGREDIENTS, STEPS, IMAGE) VALUES (?, ?, ?, ?, ?, ?);");
            statement.setString(1, toAdd.getRecipeTitle());
            statement.setString(2, toAdd.getDuration());
            statement.setString(3, toAdd.getFoodType());
            statement.setString(4, toAdd.getIngredients());
            statement.setString(5, toAdd.getSteps());
            statement.setString(6, toAdd.getImage());
            statement.execute();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }
    @Override
    public void removeRecipe(Recipe toRemove) throws RecipeException {
        if(toRemove == null) {
            throw new InvalidRecipeValues();
        }
        toRemove.checkForNulls();
        checkIDExists(toRemove.getID());

        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM RECIPES WHERE RECIPES.ID = ?;");
            statement.setInt(1, toRemove.getID());
            statement.executeUpdate();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }
    @Override
    public void removeRecipeByID(int idToRemove) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM RECIPES WHERE RECIPES.ID = ?;");
            statement.setInt(1, idToRemove);
            statement.executeUpdate();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }
    @Override
    public void updateRecipe(Recipe updatedRecipe) throws RecipeException {
        if(updatedRecipe == null) {
            throw new InvalidRecipeValues();
        }
        updatedRecipe.checkForNulls();
        checkIDExists(updatedRecipe.getID());

        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("UPDATE RECIPES SET TITLE = ?, DURATION = ?, TYPE = ?, INGREDIENTS = ?, STEPS = ?, IMAGE = ? WHERE ID = ?;");
            statement.setString(1, updatedRecipe.getRecipeTitle());
            statement.setString(2, updatedRecipe.getDuration());
            statement.setString(3, updatedRecipe.getFoodType());
            statement.setString(4, updatedRecipe.getIngredients());
            statement.setString(5, updatedRecipe.getSteps());
            statement.setString(6, updatedRecipe.getImage());
            statement.setInt(7, updatedRecipe.getID());
            statement.executeUpdate();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    //Returns the true if found, otherwise false
    private void checkIDExists(int id) throws RecipeDNE {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT COUNT(ID) AS ID FROM RECIPES WHERE RECIPES.ID = ?;");
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return;
            }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        throw new RecipeDNE();
    }

}
