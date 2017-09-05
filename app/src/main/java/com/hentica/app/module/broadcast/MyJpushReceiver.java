package com.hentica.app.module.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.jpush.android.api.JPushInterface;

/**
 * @CreateTime： 2017/9/4
 * @Describe: 极光推送广播
 * @Author： 曾强
 */

public class MyJpushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyJpushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            LogUtils.e(TAG, "JPush用户注册成功" + intent.getExtras().getString(JPushInterface.EXTRA_REGISTRATION_ID));
//            String regId = intent.getExtras().getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            LogUtils.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//            LogUtils.e(TAG, "接受到推送下来的自定义消息" + "------------" + intent.getExtras().getString(JPushInterface.EXTRA_TITLE)
//                    + "\n" + intent.getExtras().getString(JPushInterface.EXTRA_MESSAGE));
//            String msg = intent.getExtras().getString(JPushInterface.EXTRA_EXTRA);
//            try {
//                JSONObject jsonObject = new JSONObject(msg);
//                String title = jsonObject.optString("title");
//                LogUtils.e(TAG, msg + "\n" + title);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            LogUtils.e(TAG, "接受到推送下来的通知" + "---------" + intent.getExtras().getString(JPushInterface.EXTRA_ALERT));
//            String msg = intent.getExtras().getString(JPushInterface.EXTRA_EXTRA);
//            try {
//                JSONObject jsonObject = new JSONObject(msg);
//                String title = jsonObject.optString("title");
//                LogUtils.e(TAG, msg + "\n" + title);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            LogUtils.e(TAG, "用户点击打开了通知");
        }

    }

}
