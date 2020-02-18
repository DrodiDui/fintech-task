package by.kapitonov.fintech.task.util;


import java.util.regex.Pattern;

public class UserAccountValidator {

    private static final Pattern usernamePattern = Pattern.compile("^[A-Za-z]{3,16}$");
    private static final Pattern passwordPattern = Pattern.compile("^[A-Za-z0-9]{3,16}$");


    public static boolean isValidUsername(String username) {
        return usernamePattern.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        return passwordPattern.matcher(password).matches();
    }
}