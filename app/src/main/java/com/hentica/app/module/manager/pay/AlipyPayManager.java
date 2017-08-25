package com.hentica.app.module.manager.pay;

import android.app.Activity;

import com.hentica.pay.alipay.AliPayUtil;

/**
 * 支付宝支付
 * Created by Snow on 2017/1/17.
 */

public class AlipyPayManager extends AbsPayManager<AlipyPayData> {

    public AlipyPayManager(Activity activity) {
        super(activity);
    }

    @Override
    public void toPay(AlipyPayData data) {
        AliPayUtil m_AliPayUtil = new AliPayUtil();
        m_AliPayUtil.pay(mActivity, data.alipyCode, new AliPayUtil.OnAliPayListener() {
            @Override
            public void onPaySucceed(String result) {
                // 支付宝支付成功
                if(mListener != null){
                    mListener.onSuccess(result);
                }
            }
            @Override
            public void onPayFailure(String result) {
                // 支付宝支付失败
                if(mListener != null){
                    mListener.onFailure(result);
                }
            }
            @Override
            public void onPayConfirm(String result) {
                // 支付宝支付成功，确认中
                if(mListener != null){
                    mListener.onFailure(result);
                }
            }
            @Override
            public void onCheckFlag(String result) {
                // 查询终端设备是否存在支付宝认证账户
                if(mListener != null){
                    mListener.onFailure(result);
                }
            }
        });
    }

}
