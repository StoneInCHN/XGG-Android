package com.hentica.app.framework.fragment;

import android.view.KeyEvent;
import android.view.MotionEvent;

/** 定义一些Fragment常用接口 */
public class FragmentListener {

	/** 按键事件 */
	public static interface OnKeyListener {

		/**
		 * 某键被按下
		 * 
		 * @param keyCode
		 *            键码
		 * @param event
		 *            事件
		 * @return
		 */
		public boolean onKeyDown(int keyCode, KeyEvent event);
	}

	/** 触摸事件 */
	public interface OnDispatchTouchEventListener {

		/**
		 * 触发事件发生了
		 * 
		 * @param ev
		 *            事件
		 */
		public void onDispatchTouchEvent(MotionEvent ev);
	}

	/** 通用界面操作接口 */
	public interface UsualViewOperator {

		/** 显示加载框 */
		public void showLoadingDialog();

		/** 取消加载框 */
		public void dismissLoadingDialog();

		/**
		 * 显示提示
		 * 
		 * @param msg
		 *            要显示的文字
		 */
		public void showToast(String msg);

		/** 弹出登录界面 */
		public void onToLogin();

		/** 取得界面 */
		public UsualFragment getUsualFragment();
	}
}
