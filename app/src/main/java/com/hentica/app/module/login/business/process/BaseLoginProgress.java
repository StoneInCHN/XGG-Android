package com.hentica.app.module.login.business.process;

import android.text.TextUtils;

import com.hentica.app.lib.util.PhoneInfo;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.event.DataEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 登录流程通用操作
 *
 * @author mili
 * @createTime 2016/9/8
 */
public class BaseLoginProgress extends Progress {

    // 获取mac地址
    protected String getMac() {
        String mac = PhoneInfo.getAndroidMac();
        return !TextUtils.isEmpty(mac) ? mac : "unknown";
    }

    // 获取手机model
    protected String getModel() {
        String model = PhoneInfo.getAndroidModel();
        return !TextUtils.isEmpty(model) ? model : "unknown";
    }

    // 登录成功
//    protected void onLoginSuccess(UserLoginData data) {
//
//        // 保存登录账号
//        saveLoginAccount(data);
//
//        // 发出事件
//        boolean isSwitchAccount = true;
//        if (mLoginModel != null) {
//            isSwitchAccount = isNewAccount(mLoginModel.getLastLoginUserId(), data.getUserIdStr());
//        }
//        EventBus.getDefault().post(new DataEvent.OnLoginEvent(isSwitchAccount));
//    }

    // 保存本次登录账号
    private void saveLoginAccount(UserLoginData data) {

        if (mLoginModel != null) {
            mLoginModel.setUserLogin(data);
        }
        StorageUtil.saveLastLoginInfo(data);
    }

    // 是否是新的登录，(而不是掉线重新登录)
    private boolean isNewAccount(String oldUserId, String newUserId) {

        return oldUserId == null || !oldUserId.equals(newUserId);
    }
}
