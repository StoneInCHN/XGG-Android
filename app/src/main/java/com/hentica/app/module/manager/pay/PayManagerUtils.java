package com.hentica.app.module.manager.pay;

import android.app.Activity;

import java.util.Map;

/**
 * 第三方支付工具<br >
 * 使用方式：
 * 1.调用{@linkplain #getInstance(Activity, RequestPayData.PayType, IPayListener)} ,获取支付工具实例<br >
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/2/22.17:41
 */

public class PayManagerUtils {

    IPayManager manager;
    private RequestPayData.PayType mType;


    private PayManagerUtils(Activity activity, IPayListener l, RequestPayData.PayType type){
        switch (mType = type){
            case kAlipy:
                manager = new AlipyPayManager(activity);
                break;
            case kWeiChat:
                manager = new WeiChatPayManager(activity);
                break;
            case kWingPay:
                manager = new WingPayManager((activity));
            default:
                return;
        }
        if(manager != null) {
            manager.setListener(l);
        }
    }
    /** 获取支付工具 */
    public static PayManagerUtils getInstance(Activity activity, RequestPayData.PayType type, IPayListener l){
        return new PayManagerUtils(activity, l, type);
    }
    /** 支付 */
    public void toPay(AbsPayData data){
        if(manager == null){
            throw  new RuntimeException("请创建支付方式");
        }
        manager.toPay(data);
    }

}
