package com.moonlay.litewill.utility;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nandana.samudera on 5/7/2018.
 */

public class TextValidation {
    public static String warning;

    public static boolean emailValidation(String text) {
        if (text.length() > 0) {
            String[] splitEmail = text.split("@");
            String emailAccount = splitEmail[0];
            String lastCharEmailAccount = emailAccount.substring(emailAccount.length() - 1);

            if (Patterns.EMAIL_ADDRESS.matcher(text).matches() && !lastCharEmailAccount.equals(".")) {
                return true;
            } else {
                warning = "Not valid email address";
                return false;
            }

        } else {
            warning = "Field is empty";
            return false;
        }
    }

    public static boolean userNameValidation(String text) {
        if ((text.length() >= 6) && (text.length() <= 25)) {
            return true;
        } else {
            warning = "Username must has at least 6 characters and no more than 25 characters";
            return false;
        }
    }

    public static boolean phoneValidation(String text) {
        char strPhoneNumberGet1st = text.charAt(0);
        if (strPhoneNumberGet1st == '0') {
            text = text.substring(1);
        }

        Pattern pattern;
        Matcher matcher;
        String phonePattern = "^(?=.*[0-9]).{4,12}$";
        pattern = Pattern.compile(phonePattern);
        matcher = pattern.matcher(text);


        if (text.length() > 0) {
            if (!matcher.matches()) {
                warning = "Phone number not valid";
            }
            return matcher.matches();

        } else {
            warning = "Field is empty";
            return false;
        }
    }

    public static boolean securityQuestionValidation(String text) {
        if ((text.length() >= 1) && (text.length() <= 25)) {
            return true;
        } else {
            warning = "Answer must has at least 1 chatacters and no more than 25 characters";
            return false;
        }
    }

    public static boolean passwordValidation(String text) {
        Pattern pattern;
        Matcher matcher;
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[`~!@#$%^&*\\(\\).,+=\\{\\}\\[\\]:;\"\'\\<\\>?/|\\\\_-]).{6,16}$";

        pattern = Pattern.compile(passwordPattern);
        matcher = pattern.matcher(text);

        if (text.length() > 0) {
            if (!matcher.matches()) {
                warning = "Password must contain 3 alphabets with at least 1 capital letter, 3 numeric, and 3 symbols";
            }
            return matcher.matches();

        } else {
            warning = "Field is empty";
            return false;
        }
    }
}
