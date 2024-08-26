package com.pantrypro.application;

/**
 * Main class for PantryPro app.
 */
public class Main {

    // Default name for the database
    private static String dbName = "PantryProDB";

    /**
     * Sets the path name for the database.
     * @param name The new path name for the database.
     */
    public static void setDBPathName(final String name) {
        try {
            // Load the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Set the database name to new name
        dbName = name;
    }

    /**
     * Retrieves the current path name of the database.
     */
    public static String getDBPathName() {
        return dbName;
    }
}
