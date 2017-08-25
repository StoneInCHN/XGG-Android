package com.hentica.pay.alipay;

import com.alipay.sdk.app.PayTask;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

/**
 * 支付宝支付工具
 *
 */
public class AliPayUtil {

	/** 支付回调事件 */
	public interface OnAliPayListener {

		/**
		 * 支付结果确认中：<br>
		 * 最终交易是否成功以服务端异步通知为准
		 * 
		 * @param result
		 *            提示文字
		 */
		void onPayConfirm(String result);

		/**
		 * 支付成功
		 * 
		 * @param result
		 *            提示文字
		 */
		void onPaySucceed(String result);

		/**
		 * 支付失败：<br>
		 * 用户主动取消支付，或者系统返回的错误
		 * 
		 * @param result
		 *            提示文字
		 */
		void onPayFailure(String result);

		/**
		 * 查询终端设备是否存在支付宝认证账户
		 * 
		 * @param result
		 *            提示文字
		 */
		void onCheckFlag(String result);
	}

	/** 取输入结果事件 */
	protected OnAliPayListener m_OnAliPayListener = null;

	/** 取输入结果事件 */
	public OnAliPayListener getPayListener() {
		return m_OnAliPayListener;
	}

	/** 取输入结果事件 */
	public void setPayListener(OnAliPayListener listener) {
		this.m_OnAliPayListener = listener;
	}

	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				// String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					m_OnAliPayListener.onPaySucceed(payResult.getMemo());
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						m_OnAliPayListener.onPayConfirm(payResult.getMemo());
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						m_OnAliPayListener.onPayFailure(payResult.getMemo());
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				String string = "检查结果为：" + msg.obj;
				m_OnAliPayListener.onCheckFlag(string);
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 * @param activity
	 * @param TradeInfo
	 *            支付交易数据
	 * @param listener
	 */
	public void pay(final Activity activity, final String TradeInfo, OnAliPayListener listener) {
		if (listener != null) {
			this.setPayListener(listener);

			Runnable payRunnable = new Runnable() {

				@Override
				public void run() {

					try {
						final String payInfo = new String(TradeInfo);

						// 构造PayTask 对象
						PayTask alipay = new PayTask(activity);
						// 调用支付接口，获取支付结果
						String result = alipay.pay(payInfo, true);

						Message msg = new Message();
						msg.what = SDK_PAY_FLAG;
						msg.obj = result;
						mHandler.sendMessage(msg);

					} catch (Exception e) {
						// TODO: handle exception
						Log.e("pay.AliPayUtil", e.toString());
					}
				}
			};

			// 必须异步调用
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}
	}

	// /**
	// * check whether the device has authentication alipay account.<br>
	// * 查询终端设备是否存在支付宝认证账户
	// */
	// public void check(final Activity activity) {
	// Runnable checkRunnable = new Runnable() {
	//
	// @Override
	// public void run() {
	//
	// try {
	// // 构造PayTask 对象
	// PayTask payTask = new PayTask(activity);
	// // 调用查询接口，获取查询结果
	// boolean isExist = payTask.checkAccountIfExist();
	//
	// Message msg = new Message();
	// msg.what = SDK_CHECK_FLAG;
	// msg.obj = isExist;
	// mHandler.sendMessage(msg);
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// }
	// };
	//
	// Thread checkThread = new Thread(checkRunnable);
	// checkThread.start();
	// }
}
