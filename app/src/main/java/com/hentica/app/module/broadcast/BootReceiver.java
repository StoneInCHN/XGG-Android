package com.hentica.app.module.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hentica.app.util.LogUtils;

/**
 * Created by Snow on 2017/5/8.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.i("BootReceiver", "onReceive");
        if(intent.getAction().equals("com.hentica.app.donwload.complete")){
            LogUtils.i("BootReceiver", "Download complete");
        }
        if(intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)){
            LogUtils.i("BootReceiver", "ACTION_PACKAGE_ADDED");
        }
        if(intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)){
            LogUtils.i("BootReceiver", "ACTION_PACKAGE_REMOVED");
        }
        if(intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)){
            LogUtils.i("BootReceiver", "ACTION_PACKAGE_REPLACED");
        }
    }
}
