package com.pantrypro.systemTests;

import static androidx.test.espresso.intent.Intents.intended;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.pantrypro.R;
import com.pantrypro.presentation.LoginActivity;
import com.pantrypro.presentation.SignupActivity;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import com.pantrypro.objects.ErrorMessage;

@RunWith(AndroidJUnit4.class)
public class SignupIT {

    @Rule
    public ActivityScenarioRule<SignupActivity> activityRule = new ActivityScenarioRule<>(SignupActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release Intents after testing
        Intents.release();
    }

    @Test
    public void testValidateFields(){
        String badPassword = "password1";
        String badEmail = "emailtest123";
        String badUsername = "user";
        String goodPassword = "password!1";
        String goodEmail = "email123@test.com";
        String goodUsername = "Username1";

        //Test with bad input; should not go to the login Activity, should stay in sign up activity
        Espresso.onView(ViewMatchers.withId(R.id.signupName)).perform(ViewActions.typeText("Name"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signupEmail)).perform(ViewActions.typeText(badEmail));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signupUsername)).perform(ViewActions.typeText(badUsername));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signupPassword)).perform(ViewActions.typeText(badPassword));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signupConfirmPassword)).perform(ViewActions.typeText("password"));
        Espresso.closeSoftKeyboard();

        Espresso.onView(ViewMatchers.withId(R.id.signupButton)).perform(ViewActions.click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Check if we are still in Signup Activity
        Espresso.onView(ViewMatchers.withId(R.id.signupButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testValidateEmail(){
        String badEmail = "email123";
        Espresso.onView(ViewMatchers.withId(R.id.signupEmail)).perform(ViewActions.typeText(badEmail));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signupButton)).perform(ViewActions.click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.signupEmail)).check(ViewAssertions.matches(ViewMatchers.hasErrorText(ErrorMessage.EMAIL_ERROR)));
    }

    @Test
    public void testValidateUsername(){
        String badUsername = "user";
        Espresso.onView(ViewMatchers.withId(R.id.signupUsername)).perform(ViewActions.typeText(badUsername));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signupButton)).perform(ViewActions.click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.signupUsername)).check(ViewAssertions.matches(ViewMatchers.hasErrorText(ErrorMessage.USERNAME_ERROR)));
    }

    @Test
    public void testValidatePassword(){
        String badPassword = "password";
        Espresso.onView(ViewMatchers.withId(R.id.signupPassword)).perform(ViewActions.typeText(badPassword));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signupButton)).perform(ViewActions.click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.signupPassword)).check(ViewAssertions.matches(ViewMatchers.hasErrorText(ErrorMessage.PASSWORD_ERROR)));
    }

    @Test
    public void testNonMatchPasswords(){
        String password = "password!1";
        String confirmPassword = "password!2";
        Espresso.onView(ViewMatchers.withId(R.id.signupPassword)).perform(ViewActions.typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signupConfirmPassword)).perform(ViewActions.typeText(confirmPassword));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signupButton)).perform(ViewActions.click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.signupConfirmPassword)).check(ViewAssertions.matches(ViewMatchers.hasErrorText(ErrorMessage.PASSWORD_NO_MATCH)));
    }

    @Test
    public void testRedirectText(){
        Espresso.onView(ViewMatchers.withId(R.id.loginRedirectText)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}




