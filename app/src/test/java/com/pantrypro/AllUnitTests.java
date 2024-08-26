package com.pantrypro;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite to run all unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        RecipeTest.class,
        UserProfileTest.class,
        LoginHandlerTest.class,
        RecipeValidatorTest.class,
        SignupHandlerTest.class
})

public class AllUnitTests {
}
