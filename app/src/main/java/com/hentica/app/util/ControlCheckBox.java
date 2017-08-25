package com.hentica.app.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/** 可控制是否可选中的checkBox */
public class ControlCheckBox extends CheckBox {

	/** 是否允许手动点击改变选中状态 */
	private boolean mIsEnableTouchedClick = true;

	/** 是否触发手动点击了 */
	private boolean mIsTouchedClicked;

	/** 视图被选中前的事件 */
	public interface BeforeCheckListener {

		/**
		 * 控件是否可被选中
		 * 
		 * @param compoundButton
		 *            视图
		 * @param willCheck
		 *            将要转变到的状态
		 * @return 若返回true，表示可改变选中状态；若返回false，表示不可改变选中状态
		 */
		public boolean beforeCheckChanged(CompoundButton compoundButton, boolean willCheck);
	}

	/** 监听事件，视图将被选中 */
	private BeforeCheckListener mBeforeCheckListener;

	/** 监听事件，视图将被选中 */
	public void setBeforeCheckListener(BeforeCheckListener onBeforeCheckListener) {
		mBeforeCheckListener = onBeforeCheckListener;
	}

	public ControlCheckBox(Context context) {
		super(context);
	}

	public ControlCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ControlCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setChecked(boolean checked) {

		// 若未监听改变前事件，或允许改变状态，则改变选中状态
		if (mBeforeCheckListener == null || mBeforeCheckListener.beforeCheckChanged(this, checked)) {

			super.setChecked(checked);
		}
	}

	/** 是否允许手动点击改变选中状态 */
	public boolean isIsEnableTouchedClick() {
		return mIsEnableTouchedClick;
	}

	/** 是否允许手动点击改变选中状态 */
	public void setIsEnableTouchedClick(boolean isEnableTouchedClick) {
		mIsEnableTouchedClick = isEnableTouchedClick;
	}

	@Override
	public boolean performClick() {

		mIsTouchedClicked = true;
		return super.performClick();
	}

	@Override
	public void toggle() {

		// 若不允许手动切换，且手动事件触发了toggle()方法
		if (!mIsEnableTouchedClick && mIsTouchedClicked == true) {
			// 什么都不做

		} else {

			super.toggle();
		}

		mIsTouchedClicked = false;
	}
}
