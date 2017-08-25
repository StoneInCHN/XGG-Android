package com.hentica.app.test.shadow;

import com.litesuits.http.utils.HttpUtil;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * LiteHttp会用到
 *
 * @author mili
 * @createTime 2016/9/23
 */
@Implements(HttpUtil.class)
public class HttpUtilShadow {

    @Implementation
    public static int getCoresNumbers() {
        return 1;
    }
}
