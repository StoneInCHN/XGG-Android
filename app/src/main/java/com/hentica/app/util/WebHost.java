package com.hentica.app.util;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * @CreateTime： 2017/8/28
 * @Describe:
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
        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
        if (paySuccessCallBack != null) {
            paySuccessCallBack.onClick();
        }
    }

    public interface PaySuccessCallBack {
        void onClick();
    }
}
