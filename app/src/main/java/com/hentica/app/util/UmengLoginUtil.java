package com.hentica.app.util;

import android.app.Activity;
import android.content.Intent;

import com.hentica.app.framework.fragment.UsualFragment;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 有盟登录辅助类
 * 
 * @author mili
 * @createTime 2016年7月7日 下午8:03:28
 */
public class UmengLoginUtil {

	/**
	 * 微信登录完成
	 */
	public interface OnWeiXinLoginCompleteListener{

		void onSuccess(String openid);

		void onFailed();
	}

	/** 登录完成 */
	public interface OnLoginCompleteListener {

		/**
		 * 登录成功
		 * 
		 * @param account
		 *            账号
		 * @param nickName
		 *            昵称
		 */
		void onLoginSuccess(String account, String nickName);

		/** 登录失败 */
		void onLoginFailed();
	}

	public interface OnLoginCompleteListener2 extends OnLoginCompleteListener{

		void onLoginSuccess(String uuid, String openId, String nickName);

	}

	/** 当前界面 */
	private UsualFragment mFragment;

	private Activity mActivity;

	/** 分享工具 */

	private UMShareAPI mShareAPI;
	/** 构造函数 */
	public UmengLoginUtil(Activity activity) {
		mActivity = activity;
		mShareAPI = UMShareAPI.get(activity);
	}

	/** 构造函数 */
	public UmengLoginUtil(UsualFragment fragment) {

		mFragment = fragment;
		mActivity = mFragment.getActivity();
		mShareAPI = UMShareAPI.get(mActivity);
	}

	/** 登录qq */
	public void loginQQ(OnLoginCompleteListener listener) {
		login(SHARE_MEDIA.QQ, listener);
	}

	/** 登录微信 */
	public void loginWeixin(OnLoginCompleteListener listener) {
		login(SHARE_MEDIA.WEIXIN, listener);
	}

	/** 登录微博 */
	public void loginSina(OnLoginCompleteListener listener) {
		login(SHARE_MEDIA.SINA, listener);
	}

	/** 登录指定平台 */
	public void login(final SHARE_MEDIA platform, final OnLoginCompleteListener listener) {

		// 接收返回事件
//		mFragment.registerForResult();

		mShareAPI.doOauthVerify(mActivity, platform, new UMAuthListener() {
			@Override
			public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
				getPlatformInfo(platform, listener);
			}

			@Override
			public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
				if (listener != null) {

					listener.onLoginFailed();
				}
			}

			@Override
			public void onCancel(SHARE_MEDIA share_media, int i) {
				if (listener != null) {

					listener.onLoginFailed();
				}
			}
		});
	}

	private void getPlatformInfo(final SHARE_MEDIA platform, final OnLoginCompleteListener listener){
		mShareAPI.getPlatformInfo(mActivity, platform, new UMAuthListener() {

			@Override
			public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {

				if (listener != null) {

					listener.onLoginFailed();
				}
			}

			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> arg2) {
				if (listener != null) {
					OnLoginCompleteListener2 listener2 = null;
					if(listener instanceof OnLoginCompleteListener2){
						listener2 = (OnLoginCompleteListener2) listener;
					}
					String openId = arg2.get("openid");
					String uuid = arg2.get("unionid");
					String nickName = arg2.get("screen_name");
					if(nickName == null || nickName.length() == 0){
						nickName = arg2.get("nickname");
					}
					if(listener2 != null){
						listener2.onLoginSuccess(uuid, openId, nickName);
					}

				}
			}

			@Override
			public void onCancel(SHARE_MEDIA arg0, int arg1) {

				if (listener != null) {

					listener.onLoginFailed();
				}
			}
		});
	}

	/**
	 * 微信登录
	 * @param l
     */
	public void loginWeiXin(final OnWeiXinLoginCompleteListener l){
		// 接收返回事件
//		mFragment.registerForResult();
		// 登录
		mShareAPI.doOauthVerify(mActivity, SHARE_MEDIA.WEIXIN, new UMAuthListener() {

			@Override
			public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {

				if (l != null) {

					l.onFailed();
				}
			}

			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> arg2) {

				if (l != null) {

					String account = arg2.get("openid");
					l.onSuccess(account);
				}
			}

			@Override
			public void onCancel(SHARE_MEDIA arg0, int arg1) {

				if (l != null) {

					l.onFailed();
				}
			}
		});
	}

	/** 在Activity返回时调用 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		mShareAPI.onActivityResult(requestCode, resultCode, data);
	}
}
