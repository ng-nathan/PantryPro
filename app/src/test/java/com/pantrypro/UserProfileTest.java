package com.pantrypro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.pantrypro.objects.UserProfile;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the UserProfile class.
 */
public class UserProfileTest {
    @Before
    public void setup() {
        System.out.println("Starting test for UserProfile");
    }

    @Test
    public void testCreateEmptyUserProfile() {
        System.out.println("\nStarting testCreateEmptyUserProfile");

        UserProfile user = new UserProfile();

        assertNotEquals(null, user);

        System.out.println("Finished testCreateEmptyUserProfile");
    }

    @Test
    public void testCreateUserProfile() {
        System.out.println("\nStarting testCreateUserProfile");

        UserProfile user = new UserProfile("Jonny",
                "testemail@gmail.com",
                "Jonny Bakes",
                "securePa$$word");


        System.out.println("Finished testCreateUserProfile");
    }

    @Test
    public void testUpdateProfile() {
        System.out.println("\nStarting testUpdateProfile");

        UserProfile user = new UserProfile("Jonny",
                "testemail@gmail.com",
                "Jonny Bakes",
                "securePa$$word");



        System.out.println("Finished testUpdateProfile");
    }
}
