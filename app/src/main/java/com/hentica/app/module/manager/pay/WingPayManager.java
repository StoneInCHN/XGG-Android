package com.hentica.app.module.manager.pay;

import android.app.Activity;

import com.bestpay.plugin.Plugin;


/**
 * Created by YangChen on 2017/5/16 17:14.
 * E-mail:656762935@qq.com
 */

public class WingPayManager extends AbsPayManager<WingPayData> {

    public WingPayManager(Activity activity) {
        super(activity);
    }

    @Override
    public void toPay(WingPayData data) {
        // 调用翼支付SDK
        Plugin.pay(mActivity,data.getParamsHashtable());
    }
}
