package com.fiveixlg.app.customer;

import com.hentica.app.framework.data.ApplicationData;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/** 将该app注册到微信 */
public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		String appId = ApplicationData.getInstance().getWxAppId();
		if (appId != null) {

			final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
			// 将该app注册到微信
			msgApi.registerApp(appId);
		}
	}
}
