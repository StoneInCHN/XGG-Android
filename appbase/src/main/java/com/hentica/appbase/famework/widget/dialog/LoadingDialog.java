package com.hentica.appbase.famework.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hentica.app.appbase.R;

public class LoadingDialog {

	public Dialog mDialog;
	private AnimationDrawable animationDrawable = null;

	public LoadingDialog(Context context) {

		this(context, "");
	}

	public LoadingDialog(Context context, String message) {

		View view = View.inflate(context, R.layout.common_dialog_loading, null);

		TextView text = (TextView) view.findViewById(R.id.progress_message);
		text.setText(message);
		ImageView loadingImage = (ImageView) view.findViewById(R.id.progress_view);
//		loadingImage.setImageResource(R.drawable.animation_loading)
		loadingImage.setBackgroundResource(R.drawable.animation_loading);
		animationDrawable = (AnimationDrawable) loadingImage.getBackground();
		if (animationDrawable != null) {
			animationDrawable.setOneShot(false);
			animationDrawable.start();
		}

		mDialog = new Dialog(context, R.style.usual_dialog);
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(false);

	}

	/** 取得对话框对象 */
	public Dialog getDialog() {
		return mDialog;
	}

	public void showDialog() {
		try {

			mDialog.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setCanceledOnTouchOutside(boolean cancel) {
		mDialog.setCanceledOnTouchOutside(cancel);
	}

	public void dismissDialog() {

		try {

			if (mDialog.isShowing()) {
				mDialog.dismiss();
				animationDrawable.stop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isShowing() {
		if (mDialog.isShowing()) {
			return true;
		}
		return false;
	}

	/** 设置取消事件 */
	public void setOnDismissListener(OnDismissListener listener) {

		if (mDialog != null) {

			mDialog.setOnDismissListener(listener);
		}
	}
}
