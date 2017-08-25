package com.hentica.pay.unionpay;

import com.unionpay.UPPayAssistEx;

import android.app.Activity;
import android.content.Intent;

/**
 * 银联支付工具<br>
 * 接入指南：<br>
 * 1:拷贝sdkPro目录下的UPPayAssistEx.jar到libs目录下<br>
 * 2:根据需要拷贝sdkPro/jar/data.bin至工程的assets目录下<br>
 * 3:根据需要拷贝sdkPro/jar/XXX/XXX.so到libs目录下<br>
 * 4:根据需要拷贝sdkPro/jar/UPPayPluginExPro.jar到工程的libs目录下<br>
 * 5:获取tn后通过UPPayAssistEx.startPay(...)方法调用控件<br>
 */
public class UnionPayUtil {

	/**
	 * 支付回调监听<br>
	 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
	 **/
	public interface OnUnionPayListener {

		/**
		 * 支付成功
		 * 
		 * @param result
		 *            提示文字
		 */
		void onSuccess(String result);

		/**
		 * 支付失败
		 * 
		 * @param result
		 *            提示文字
		 */
		void onFail(String result);

		/**
		 * 支付取消
		 * 
		 * @param result
		 *            提示文字
		 */
		void onCancel(String result);
	}

	/*****************************************************************
	 * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
	 *****************************************************************/
	private String m_strMode = "00";

	protected OnUnionPayListener m_OnUnionPayListener = null;

	public OnUnionPayListener getM_OnUnionPayListener() {
		return m_OnUnionPayListener;
	}

	public void setM_OnUnionPayListener(OnUnionPayListener m_OnUnionPayListener) {
		this.m_OnUnionPayListener = m_OnUnionPayListener;
	}

	/**
	 * @param activity
	 * @param tn
	 *            交易定单号
	 * @param mode
	 *            "00" - 启动银联正式环境 "01" - 连接银联测试环境
	 * @param subject
	 *            定单标题
	 * @param body
	 *            定单内容描述
	 * @param listener
	 *            回调监听
	 */
	public void pay(final Activity activity, String tn, String mode, String subject, String body,
			OnUnionPayListener listener) {

		if (listener != null) {

			this.setM_OnUnionPayListener(listener);
			m_strMode = mode;

			try {
				UPPayAssistEx.startPay(activity, subject, body, tn, m_strMode);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * 在调用的Activity中的onActivityResult()中调用
	 * 
	 * @param intent
	 */
	public void dealResult(Intent intent) {

		String msg = "";
		/*************************************************
		 * 步骤3：处理银联手机支付控件返回的支付结果
		 ************************************************/
		if (intent == null) {

			msg = "支付失败！";
			m_OnUnionPayListener.onFail(msg);
			return;
		}

		/*
		 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
		 */
		String str = intent.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {

			// // 支付成功后，extra中如果存在result_data，取出校验
			// // result_data结构见c）result_data参数说明
			// if (intent.hasExtra("result_data")) {
			// String result = intent.getExtras().getString("result_data");
			// try {
			// JSONObject resultJson = new JSONObject(result);
			// String sign = resultJson.getString("sign");
			// String dataOrg = resultJson.getString("data");
			// // 验签证书同后台验签证书
			// // 此处的verify，商户需送去商户后台做验签
			// boolean ret = RSAUtil.verify(dataOrg, sign, m_strMode);
			// if (ret) {
			// // 验证通过后，显示支付结果
			// msg = "支付成功！";
			// m_OnUnionPayListener.onFail(msg);
			// } else {
			// // 验证不通过后的处理
			// // 建议通过商户后台查询支付结果
			// msg = "支付失败！";
			// m_OnUnionPayListener.onFail(msg);
			// }
			// } catch (JSONException e) {
			// }
			// } else {
			// // 未收到签名信息
			// // 建议通过商户后台查询支付结果
			// msg = "支付成功！";
			// m_OnUnionPayListener.onFail(msg);
			// }

			msg = "支付成功，结果确认中！";
			m_OnUnionPayListener.onFail(msg);

		} else if (str.equalsIgnoreCase("fail")) {

			msg = "支付失败！";
			m_OnUnionPayListener.onFail(msg);

		} else if (str.equalsIgnoreCase("cancel")) {

			msg = "用户取消了支付";
			m_OnUnionPayListener.onCancel(msg);
		}
	}
}
