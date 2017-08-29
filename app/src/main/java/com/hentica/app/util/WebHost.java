package com.hentica.app.util;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * @CreateTime： 2017/8/28
 * @Describe:  九派支付成功后与h5页面的交互
 * @Author： 曾强
 */

public class WebHost {
    public Context context;
    private PaySuccessCallBack paySuccessCallBack;

    public void setPaySuccessCallBack(PaySuccessCallBack paySuccessCallBack) {
        this.paySuccessCallBack = paySuccessCallBack;
    }

    public WebHost(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void show() {
        if (paySuccessCallBack != null) {
            paySuccessCallBack.onClick();
        }
    }

    public interface PaySuccessCallBack {
        void onClick();
    }
}
