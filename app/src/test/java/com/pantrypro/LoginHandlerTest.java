package com.pantrypro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.widget.EditText;

import com.pantrypro.logic.LoginHandler;
import com.pantrypro.objects.Recipe;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the LoginHandler class.
 */
public class LoginHandlerTest {

    String userName = null;
    String password = null;

    @Before
    public void setup() {
        System.out.println("Starting test for LoginHandler");

        userName = "username";
        password = "password";
    }

    @Test
    public void testCreateLoginHandler() {
        System.out.println("\nStarting testCreateLoginHandler");

        LoginHandler login = new LoginHandler(userName, password);
        assertNotNull(login);
        assertEquals("username", login.getUsername());
        assertEquals("password", login.getPassword());

        System.out.println("Finished testCreateLoginHandler");
    }

    @Test
    public void testValidation(){
        System.out.println("\nStarting validationUsername");

        LoginHandler login = new LoginHandler(userName, password);
        LoginHandler badLogin = new LoginHandler(null, null);

        // Both validation methods return false if the respective field is empty
        assertTrue(login.validationUsername());
        assertFalse(badLogin.validationUsername());

        System.out.println("\nStarting validationPassword");

        assertTrue(login.validationPassword());
        assertFalse(badLogin.validationPassword());
    }

}
