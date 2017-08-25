package com.hentica.app.widget.view.lineview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.hentica.app.widget.view.ClearEditText;

/**
 * 输入行
 * 
 * @author mili
 * @createTime 2016-6-13 上午11:45:31
 */
public class LineViewEdit extends LineViewText {

	public LineViewEdit(Context context) {
		super(context);
	}

	public LineViewEdit(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LineViewEdit(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected EditText createContentTextView(Context context, int defStyle) {
		EditText editText = new ClearEditText(context, null, defStyle);
		editText.setFocusableInTouchMode(true);
		return editText;
	}

	@Override
	public EditText getContentTextView() {
		return (EditText) super.getContentTextView();
	}
}
