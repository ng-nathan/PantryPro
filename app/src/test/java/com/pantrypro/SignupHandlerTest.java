package com.pantrypro;

import com.pantrypro.logic.SignupHandler;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;

public class SignupHandlerTest {
    private SignupHandler user;
    @Before
    public void setup() {
        System.out.println("Starting test for SignupHandler");
        user = new SignupHandler("Name", "email@gmail.com", "Username", "Password1!", "Password1!");
    }

    @Test
    public void testInvalidUsername() {
        System.out.println("\nStarting testInvalidUsername");

        assertFalse(user.validationUsername(""));
        assertFalse(user.validationUsername("123456789")); //No letters
        assertFalse(user.validationUsername("short"));
        assertFalse(user.validationUsername("ThisUsernameIsTooLongThereIsNoWaySomeoneWouldMakeOneThisLong123"));
        assertFalse(user.validationUsername("Can'tH@veSpec!alCharacters:)"));
        assertFalse(user.validationUsername("You cannot have spaces"));

        System.out.println("Finished testInvalidUsername");
    }


    @Test
    public void testInvalidEmail() {
        System.out.println("\nStarting testInvalidEmail");

        assertFalse(user.validationEmail(""));
        assertFalse(user.validationEmail("IncorrectSymbol#gmail.com"));
        assertFalse(user.validationEmail("@NoUsername.com"));
        assertFalse(user.validationEmail("NoDomain@.com"));
        assertFalse(user.validationEmail("email@gmailDotCom"));
        assertFalse(user.validationEmail("spaces in an email@not allowed.com"));

        System.out.println("Finished testInvalidEmail");
    }
    @Test
    public void testInvalidPassword() {
        System.out.println("\nStarting testInvalidPassword");

        assertFalse(user.validationPassword(""));
        assertFalse(user.validationPassword("nocapital123!"));
        assertFalse(user.validationPassword("#NoNumber"));
        assertFalse(user.validationPassword("Short!2"));
        assertFalse(user.validationPassword("ONLYCAPITAL1!"));
        assertFalse(user.validationPassword("Passwords c@nt have sp4ces either"));

        System.out.println("Finished testInvalidPassword");
    }

    @Test
    public void testValidValues() {
        System.out.println("\nStarting testValidValues");

        assert(user.validationUsername("Username"));
        assert(user.validationEmail("email@gmail.com"));
        assert(user.validationPassword("Password1!"));

        System.out.println("Finished testValidValues");
    }
}
