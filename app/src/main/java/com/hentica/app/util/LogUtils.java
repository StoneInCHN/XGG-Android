package com.hentica.app.util;

import android.util.Log;

import com.hentica.app.framework.data.ApplicationData;

/**
 * Created by Snow on 2017/5/3.
 */

public class LogUtils {

    private static final String TAG = LogUtils.class.getSimpleName();

    public static void v(String tag, String msg){
        if(ApplicationData.PRINT_LOG){
            Log.v(tag, msg);
        }
    }

    public static void v(String msg){
        if(ApplicationData.PRINT_LOG){
            v(TAG, msg);
        }
    }

    public static void i(String tag, String msg){
        if(ApplicationData.PRINT_LOG){
            Log.i(tag, msg);
        }
    }

    public static void i(String msg){
        if(ApplicationData.PRINT_LOG){
            i(TAG, msg);
        }
    }

    public static void d(String tag, String msg){
        if(ApplicationData.PRINT_LOG){
            Log.d(tag, msg);
        }
    }

    public static void d(String msg){
        if(ApplicationData.PRINT_LOG){
            d(TAG, msg);
        }
    }

    public static void w(String tag, String msg){
        if(ApplicationData.PRINT_LOG){
            Log.w(tag, msg);
        }
    }

    public static void w(String msg){
        if(ApplicationData.PRINT_LOG){
            w(TAG, msg);
        }
    }

    public static void e(String tag, String msg){
        if(ApplicationData.PRINT_LOG){
            Log.e(tag, msg);
        }
    }

    public static void e(String msg){
        if(ApplicationData.PRINT_LOG){
            e(TAG, msg);
        }
    }

}
