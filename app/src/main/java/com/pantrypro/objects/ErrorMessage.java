package com.pantrypro.objects;

/**
 * Error messages for the whole project to use.
 */
public class ErrorMessage {
    public static final String USERNAME_ERROR = "Username must start with a letter, be 8-30 characters long, and can only include letters, numbers, or underscores. No spaces or special characters allowed.";
    public static final String USERNAME_DUP = "Username already exists";
    public static final String USERNAME_EMPTY = "Username cannot be empty";
    public static final String PASSWORD_ERROR = "Password must be at least 8 characters long and include at least one lowercase letter, one uppercase letter, one digit, and one special character from @$!%*?&.";
    public static final String NAME_EMPTY = "Name cannot be empty";
    public static final String EMAIL_ERROR = "Please enter a valid email address in the format name@example.com. Avoid using special characters except for period (.), hyphen (-), or underscore (_)";
    public static final String EMAIL_DUP = "Email is already registered";
    public static final String PASSWORD_EMPTY = "Password cannot be empty";
    public static final String INCORRECT_PASSWORD = "Incorrect password. Please try again";
    public static final String USER_NOT_EXIST = "User does not exist. Please try again";
    public static final String RETYPE_PASSWORD = "Please re-type your password";
    public static final String PASSWORD_NO_MATCH = "Password does not match";
    public static final String INVALID_TITLE = "Title must be at least 3 characters long";
    public static final String INVALID_DURATION = "Duration must be between 0 and 999 minutes";
    public static final String INVALID_INGREDIENTS = "Must add some ingredients";
    public static final String INVALID_STEPS = "Must add some steps";
}
