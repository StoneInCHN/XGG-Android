package com.hentica.app.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class EditCountWatcher implements TextWatcher {

	/** 最大字数 */
	private int mMaxCount;

	/** 编辑框 */
	private EditText mContentEdit;

	/** 输出结果的文本控件 */
	private TextView mTipView;

	/** 超出字数监听 */
	private OverCountListener mListener;

	/** 超出字数监听 */
	public interface OverCountListener {
		public void onOverCount();
	}

	public EditCountWatcher(int maxCount, EditText contentEdit) {
		mMaxCount = maxCount;
		mContentEdit = contentEdit;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		String content = s.toString();
		// 当前字数
		int currCount = content.length();
		if (currCount > mMaxCount) {

			// 超出最大字数
			// 当前光标位置
			int selection = mContentEdit.getSelectionEnd();
			// 需要截取的字符串长度
			int delLenth = currCount - mMaxCount;
			content = StringUtil.removeChar(content, selection, mMaxCount);
			mContentEdit.setText(content);
			mContentEdit.setSelection(selection - delLenth);
			// 字数超出监听
			if (mListener != null) {
				mListener.onOverCount();
			}
			return;
		}

		// 提示文本
		String tipText = String.format("%d/%d个字", currCount, mMaxCount);
		if (mTipView != null) {
			mTipView.setText(tipText);
		}

	}

	/** 输出结果的文本控件 */
	public void setTipView(TextView tipView) {
		mTipView = tipView;
	}

	/** 超出字数监听 */
	public void setOverCountListener(OverCountListener listener) {
		mListener = listener;
	}

}
