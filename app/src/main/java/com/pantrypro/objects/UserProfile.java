package com.pantrypro.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * UserProfile class for user profile information.
 */
public class UserProfile {

    private String name;            // User's name
    private String email;           // User's email
    private String username;        // User's username
    private String password;        // User's password

    /**
     * Empty constructor for UserProfile.
     */
    public UserProfile() {
    }

    /**
     * Constructor for creating a UserProfile.
     * @param name User's name
     * @param email User's email
     * @param username User's username
     * @param password User's password
     */
    public UserProfile(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * Getter method for retrieving the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for setting the user's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for retrieving the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for setting the user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for retrieving the user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for setting the user's username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    // DO NOT REMOVE THIS!!!! THIS IS HOW FIREBASE SAVES AND ACCESSES PASSWORD USING GETTER/SETTER
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
