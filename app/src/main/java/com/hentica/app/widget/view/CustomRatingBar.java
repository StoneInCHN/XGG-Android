/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hentica.app.widget.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RatingBar;

import com.hentica.app.lib.util.PhoneInfo;
import com.fiveixlg.app.customer.R;

/** 自定义ratingBar，修正高度显示不正常的问题 */
public class CustomRatingBar extends RatingBar {

	public CustomRatingBar(Context context) {
		super(context);
	}

	public CustomRatingBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomRatingBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 预览模式
		if (isInEditMode()) {

			setMeasuredDimension(getMeasuredWidth() + getPaddingLeft() + getPaddingRight(),
					resolveSizeAndState(30, heightMeasureSpec, 0));

		}
		// 正式环境
		else {

			// 计算高度
			Drawable drawable = getResources().getDrawable(R.drawable.room_rating_bar);
			int height = drawable.getIntrinsicHeight();
			height += getPaddingTop() + getPaddingBottom();

			setMeasuredDimension(getMeasuredWidth() + getPaddingLeft() + getPaddingRight(),
					resolveSizeAndState2(height, heightMeasureSpec, 0));
		}
	}

	/** 兼容3.0以下系统 */
	private int resolveSizeAndState2(int size, int measureSpec, int childMeasuredState) {

		// 若是3.0以上版本
		if (PhoneInfo.getAndroidSdk() >= android.os.Build.VERSION_CODES.HONEYCOMB) {

			return super.resolveSizeAndState(size, measureSpec, childMeasuredState);
		}
		// 若是3.0以下版本
		else {

			int result = size;
			int specMode = MeasureSpec.getMode(measureSpec);
			int specSize = MeasureSpec.getSize(measureSpec);
			switch (specMode) {
			case MeasureSpec.UNSPECIFIED:
				result = size;
				break;
			case MeasureSpec.AT_MOST:
				if (specSize < size) {
					result = specSize | MEASURED_STATE_TOO_SMALL;
				} else {
					result = size;
				}
				break;
			case MeasureSpec.EXACTLY:
				result = specSize;
				break;
			}
			return result | (childMeasuredState & MEASURED_STATE_MASK);
		}
	}
}
