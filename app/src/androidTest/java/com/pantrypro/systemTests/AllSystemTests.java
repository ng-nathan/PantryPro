package com.pantrypro.systemTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite to run all system tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginActivityTest.class,
        SignupIT.class,
        SignupTest.class,
        RecipeTest.class,
        SearchTest.class,
        AddandDeleteRecipeTest.class
})
public class AllSystemTests {
}
