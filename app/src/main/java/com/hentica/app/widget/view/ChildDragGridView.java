package com.hentica.app.widget.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 可嵌套到ScrollView中的可拖拽网格
 * @author mili
 * @createTime 2016年7月20日 下午6:41:23
 */
public class ChildDragGridView extends DragGridView {

	public ChildDragGridView(Context context) {
		super(context, null);
	}

	public ChildDragGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChildDragGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
