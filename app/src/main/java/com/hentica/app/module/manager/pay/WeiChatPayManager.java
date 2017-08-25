package com.hentica.app.module.manager.pay;

import android.app.Activity;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.util.PackageUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.pay.wxapi.WXPayApiUtil;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 微信支付
 * Created by Snow on 2017/1/17.
 */

public class WeiChatPayManager extends AbsPayManager<WeiChatPayData> {

    public WeiChatPayManager(Activity activity) {
        super(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void toPay(WeiChatPayData data) {
        String appId = data.appId;
        String partnerId = data.partnerId;
        String timeStamp = data.timeStamp;
        String nonceStr = data.nonceStr;
        String packageStr = data.packageStr;
        String paySign = data.paySign;
        String prepayId = data.prepayId;

        // 记录微信号到全局变量
        ApplicationData.getInstance().setWxAppId(appId);
        if(!PackageUtil.getInstance().isAppInstalled("com.tencent.mm")){
            if(!UMShareAPI.get(mActivity).isInstall(mActivity, SHARE_MEDIA.WEIXIN)){
                return;
            }
            return;
        }
        WXPayApiUtil wxPayApiUtil = new WXPayApiUtil();
        wxPayApiUtil.pay(mActivity, appId, partnerId, prepayId, nonceStr,
                timeStamp, packageStr, paySign);
    }

    /** 微信支付处理结果通知：来自wxapi.WXPayEntryActivity.java */
    @Subscribe
    public void onEvent(DataEvent.OnWXPayRespEvent event) {
        if (event != null) {
            int errCode = event.getmErrCode();
            switch (errCode) {
                case 0: // 支付成功
                    if(mListener != null){
                        mListener.onSuccess("支付成功");
                    }
                    break;
                case -1: // 支付失败
                    if(mListener != null){
                        mListener.onFailure("支付失败");
                    }
                    break;
                case -2: // 取消支付
                    if(mListener != null){
                        mListener.onFailure("支付失败");
                    }
                default:
                    if(mListener != null){
                        mListener.onFailure("支付失败");
                    }
                    break;
            }
        }
    }

}
