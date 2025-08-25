package com.eventdriven.common.util;

public class ValidationUtil {

    public static boolean isEmailValid(String email) {
        return email != null && email.matches("^(.+)@(.+)$");
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() >= 6;
    }
}