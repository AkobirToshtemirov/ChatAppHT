package com.pdp.java.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfoValidator {
    public static boolean isValidName(String name) {
        String nameRegex = "^[A-Za-z\\s'-]+$";
        return name.matches(nameRegex);
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
