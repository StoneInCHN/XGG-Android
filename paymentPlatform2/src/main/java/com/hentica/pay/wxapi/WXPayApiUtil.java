package com.hentica.pay.wxapi;

import android.app.Activity;
import android.util.Log;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信要绑定apk及其appKey签名，具体实现要在主App中实现，详见具体app的Manifest.xml和包（类）<br>
 * .fragment.H01_PayTypeListFragment.java .wxapi.WXPayEntryActivity.java
 */
public class WXPayApiUtil {

	private IWXAPI api = null;

	public IWXAPI getApi() {
		return api;
	}

	public void setApi(IWXAPI api) {
		this.api = api;
	}

	public void pay(final Activity activity, String appId, String partnerId,
			String prepayId, String nonceStr, String timeStamp,
			String packageValue, String sign) {

		if (api != null) {
			api = null;
		}

		PayReq req = new PayReq();
		req.appId = appId;
		req.partnerId = partnerId;
		req.prepayId = prepayId;
		req.nonceStr = nonceStr;
		req.timeStamp = timeStamp;
		req.packageValue = packageValue;
		req.sign = sign;
		req.extData = "app data"; // optional
		try {
			api = WXAPIFactory.createWXAPI(activity, appId);
			// 将该app注册到微信，注册成功后该应用将显示在微信的app列表中
			api.registerApp(appId);
			api.sendReq(req);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
