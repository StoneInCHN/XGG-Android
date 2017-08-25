package com.hentica.app.util;

import android.content.Context;
import android.support.annotation.ColorRes;

import com.litesuits.http.utils.HexUtil;

import java.math.BigInteger;

/**
 * Created by Snow on 2017/7/4.
 */

public class ColorUtils {

    public static String getColorResourceString(Context context, @ColorRes int colorId){
        if (context == null) {
            throw new RuntimeException("context must not be null!");
        }
        int color = context.getResources().getColor(colorId);
        String colorStr = Integer.toHexString(color);
        if (colorStr.length() > 6) {
            colorStr = colorStr.substring(colorStr.length() - 6);
        }
        String result = String.format("#%s", colorStr);
        return result;
    }

}
