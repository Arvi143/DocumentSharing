package com.example.arvind.spinner.stringutils;

import android.text.TextUtils;
import android.util.Patterns;

public class Utils {
    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static final boolean isValidPhoneNumber(CharSequence target) {
        if (target.length()!=13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

}

