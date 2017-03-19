package com.example.grupoes.projetoes.util;

import java.util.regex.Pattern;

/**
 * Created by Wesley on 17/03/2017.
 */

public class InputValidator {
    public static final String USERNAME_REGEXP = "^[a-zA-Z0-9_-]{4,15}$";
    public static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$";
    public static final String EMAIL_REGEXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String NAME_REGEXP = "^[a-zA-Z0-9à-úÀ-Ú .,!?_-]{1,20}$";
    public static final String DESCRIPTION_REGEXP = "^[a-zA-Z0-9à-úÀ-Ú .,!?_-]{0,255}$";
    public static final String PRICE_REGEXP = "^[0-9,.]{1,6}$";

    public static boolean isEmailInvalid(String email) {
        return isEmpty(email) || !Pattern.matches(EMAIL_REGEXP, email);
    }

    public static boolean isUsernameInvalid(String username) {
        return isEmpty(username) || !Pattern.matches(USERNAME_REGEXP, username);
    }

    public static boolean isPasswordInvalid(String password) {
        return isEmpty(password) || !Pattern.matches(PASSWORD_REGEXP, password);
    }

    public static boolean isNameInvalid(String name) {
        return isEmpty(name) || !Pattern.matches(NAME_REGEXP, name);
    }

    public static boolean isDescriptionInvalid(String name) {
        return !Pattern.matches(DESCRIPTION_REGEXP, name);
    }

    public static boolean isPriceInvalid(String price) {
        try {
            Double.parseDouble(price);
        } catch(Exception e) {
            return false;
        }

        return isEmpty(price) || !Pattern.matches(PRICE_REGEXP, price);
    }

    public static boolean isEmpty(String target) {
        if (target == null || target.isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean isEmpty(Object target) {
        if (target == null) {
            return true;
        }

        return false;
    }
}
