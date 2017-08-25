package com.hentica.app.widget.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;

import com.androidquery.AQuery;
import com.fiveixlg.app.customer.R;


public class SelfAlertDialog extends DialogFragment {

	/** 监听对话框取消事件 */
	public interface OnDismissListener {

		/** 对话框被取消了 */
		public void onDismiss(SelfAlertDialog dialog);
	}

	/** 确定按钮事件 */
	protected OnClickListener mSureClickListener;

	/** 取消按钮事件 */
	protected OnClickListener mCancelClickListener;

	/** dismiss事件 */
	protected OnDismissListener mDismissListener;

	/** 显示文字 */
	protected String mContentText;

	/** 标题文字 */
	protected String mTitleText;

	/** 确定按钮文字 */
	protected String mSureText;

	/** 取消按钮文字 */
	protected String mCancelText;

	protected boolean isOnlySureBtn = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.common_dialog_alert, container, false);
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		this.setStyle(STYLE_NORMAL, R.style.alert_dialog);
		Dialog dialog = super.onCreateDialog(savedInstanceState);

		return dialog;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// 初始化界面
		this.initView();
		this.setEvent();
	}

	/** 初始化界面 */
	protected void initView() {

		AQuery query = new AQuery(getView());

		query.id(R.id.dialog_alert_content_text).text(mContentText);

		if (mTitleText != null) {

			query.id(R.id.dialog_alert_title_text).text(mTitleText);
		}

		if (mSureText != null) {

			query.id(R.id.dialog_alert_sure_btn).text(mSureText);
		}
		if (mCancelText != null) {

			query.id(R.id.dialog_alert_cancel_btn).text(mCancelText);
		}

		if (isOnlySureBtn) {
			query.id(R.id.dialog_alert_cancel_btn).visibility(View.GONE);
		}
	}

	/** 设置事件 */
	protected void setEvent() {

		AQuery query = new AQuery(getView());

		// 点击空白处消失
		getDialog().setCanceledOnTouchOutside(true);
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 确定按钮
		query.id(R.id.dialog_alert_sure_btn).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 取消界面
				dismiss();

				// 发出事件
				if (mSureClickListener != null) {

					mSureClickListener.onClick(v);
				}
			}
		});

		query.id(R.id.dialog_alert_title_img_close).clicked(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		// 取消按钮
		query.id(R.id.dialog_alert_cancel_btn).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 取消界面
				dismiss();

				// 发出事件
				if (mCancelClickListener != null) {

					mCancelClickListener.onClick(v);
				}
			}
		});
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);

		if (mDismissListener != null) {

			mDismissListener.onDismiss(this);
		}
	}

	/** 设置显示文字 */
	public void setText(String text) {

		mContentText = text;
	}

	/** 确定按钮文字 */
	public void setSureText(String text) {

		mSureText = text;
	}

	/** 取消按钮文字 */
	public void setCancelText(String text) {

		mCancelText = text;
	}

	/** 设置确定事件 */
	public void setSureClickListener(OnClickListener l) {

		mSureClickListener = l;
	}

	/** 设置取消事件 */
	public void setCancelClickListener(OnClickListener l) {

		mCancelClickListener = l;
	}

	/** dismiss事件 */
	public void setOnDismissListener(OnDismissListener dismissListener) {
		mDismissListener = dismissListener;
	}

	/** 设置对话框标题 */
	public void setTitleText(String titleText) {
		mTitleText = titleText;
	}

	public void setOnlySureBtn(boolean flag) {
		isOnlySureBtn = flag;
	}

	@Override
	public void show(FragmentManager manager, String tag) {
		FragmentTransaction ft = manager.beginTransaction();
		ft.add(this, tag);
		ft.commitAllowingStateLoss();
	}

	@Override
	public int show(FragmentTransaction transaction, String tag) {
		return super.show(transaction, tag);
	}
}
