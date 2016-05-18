package com.vickyleu.library.Base;

import android.text.TextUtils;


public class Texts {
    public static boolean isEmpty(String str) {
        if (str == null || TextUtils.isEmpty(str) || str.equals("null")) return false;
        return true;
    }

    public static int length(String str) {
        if (str == null || TextUtils.isEmpty(str) || str.equals("null")) return 0;
        return str.length();
    }
}
