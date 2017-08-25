package com.hentica.app.test.shadow;

import com.hentica.app.lib.util.PhoneInfo;

import org.robolectric.annotation.Implements;

/**
 * @author mili
 * @createTime 2016/10/18
 */
@Implements(PhoneInfo.class)
public class PhoneInfoShadow {

    /** 检查网络是否开启 */
    public static String getAndroidMac() {
        return "pc mac";
    }

    public static int getAndroidVersioncode() {
        return -100;
    }

    public static String getAndroidVersionname() {
        return "pc version";
    }
}
