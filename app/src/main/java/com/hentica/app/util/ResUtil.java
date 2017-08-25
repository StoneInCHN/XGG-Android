package com.hentica.app.util;

import android.content.Context;
import android.support.annotation.ColorRes;

/**
 * Created by Snow on 2017/1/9.
 */

public class ResUtil {

    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }

    public static int getColor(@ColorRes int id){
        if(mContext == null){
            return 0;
        }
        return mContext.getResources().getColor(id);
    }
}
