package com.fiveixlg.app.customer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.pay.R;
import com.hentica.app.util.LogUtils;
import com.hentica.app.util.event.DataEvent.OnWXPayRespEvent;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * 微信支付SDK使用反射来启用支付结果回调显示Activity，所以必须保证类名和包名完全一致！
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";

	private IWXAPI api = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_null_transparent_layout);

		String appId = ApplicationData.getInstance().getWxAppId();
		if (appId != null) {

			try {
				api = WXAPIFactory.createWXAPI(this, appId);
				api.handleIntent(getIntent(), this);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);

		if (api != null) {

			try {
				api.handleIntent(intent, this);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public void onReq(BaseReq req) {

        LogUtils.d(TAG, "onReq, BaseReq openId = " + req.openId);
	}

	@Override
	public void onResp(BaseResp resp) {
        LogUtils.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

			// 微信拉起第三方
			// appresp.errCode == 0 ：表示支付成功
			// resp.errCode == -1 ：表示支付失败
			// resp.errCode == -2 ：表示取消支付
			OnWXPayRespEvent event = new OnWXPayRespEvent(resp.errCode);
			event.setmErrCode(resp.errCode);
			EventBus.getDefault().post(event);
			this.finish();
		}
	}

}
