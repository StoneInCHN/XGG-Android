package com.hentica.app.widget.dialog;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.Random;

/**
 * @author mili
 * @createTime 2016年7月21日 下午3:50:49
 */
public class SelfAlertDialogHelper {

	/**
	 * 显示警告框
	 * 
	 * @param manager
	 * @param text
	 *            文本
	 * @param l
	 *            确定事件
	 */
	public static void showDialog(FragmentManager manager, String text, OnClickListener l) {

		showDialog(manager, text, String.valueOf(new Random().nextInt()), l);
	}

	/**
	 * 显示警告框
	 * 
	 * @param manager
	 * @param text
	 *            文本
	 * @param tag
	 *            标记
	 * @param l
	 *            确定事件
	 */
	public static void showDialog(FragmentManager manager, String text, String tag,
			OnClickListener l) {

		SelfAlertDialog dialog = new SelfAlertDialog();
		dialog.setText(text);
		dialog.show(manager, tag);
		dialog.setSureClickListener(l);
	}


	public static void showDialog(FragmentManager manager, String text, String sureBtnText,
								  String cancelBtnText, View.OnClickListener sureBtnClickListener,
								  View.OnClickListener cancelBtnClickListener){
		SelfAlertDialog dialog = new SelfAlertDialog();
		dialog.setText(text);
		dialog.setSureText(sureBtnText);
		dialog.setCancelText(cancelBtnText);
		dialog.setSureClickListener(sureBtnClickListener);
		dialog.setCancelClickListener(cancelBtnClickListener);
		dialog.show(manager, dialog.getClass().getSimpleName());
	}



	public static void showDialog(FragmentManager manager, String text, String sureBtnText,
								  String cancelBtnText, View.OnClickListener sureBtnClickListener){
		SelfAlertDialog dialog = new SelfAlertDialog();
		dialog.setText(text);
		dialog.setSureText(sureBtnText);
		dialog.setCancelText(cancelBtnText);
		dialog.setSureClickListener(sureBtnClickListener);
		dialog.show(manager, dialog.getClass().getSimpleName());
	}

}
