package com.hentica.pay.wxapi;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 微信要绑定apk及其appKey签名，具体实现要在主App中实现，详见具体app的Manifest.xml和包（类）
 */
public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

		// 将该app注册到微信
		msgApi.registerApp("wx426b3015555a46be");
	}
}
