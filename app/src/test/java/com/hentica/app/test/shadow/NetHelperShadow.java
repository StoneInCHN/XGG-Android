package com.hentica.app.test.shadow;

import android.content.Context;

import com.hentica.app.lib.util.NetHelper;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * @author mili
 * @createTime 2016/10/13
 */
@Implements(NetHelper.class)
public class NetHelperShadow {

    /** 检查网络是否开启 */
    @Implementation
    public static boolean isNetWork(Context context) {
        return true;
    }
}
