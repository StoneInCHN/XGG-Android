package com.hentica.app.util;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;

/**
 * Created by Snow on 2017/8/16.
 */

public class UrlUtils {

    public static String getUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (url.startsWith("http:") || url.startsWith("https:")) {
            return url;
        }
        return ApplicationData.getInstance().getImageUrl(url);
    }

}
