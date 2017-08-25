package com.hentica.app.test.util;

/**
 * Created by Snow on 2017/8/14.
 */

public class SubStringUtils {

    public static void main(String[] args) {
        String str = "99.8";
        int dotIndex = str.indexOf(".");
        if (dotIndex >= 0 ) {
            if (dotIndex + 3 < str.length()) {
                System.out.println(str.substring(0, dotIndex + 3));
            } else {
                System.out.println(str);
            }
        }
    }

}
