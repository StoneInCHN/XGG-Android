package com.hentica.app.widget.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 自定义状态栏
 */
public class StatusBarView extends FrameLayout {

    int statusBarHeights;

    public StatusBarView(Context context) {
        this(context, null);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // 计算statusBar高度
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        statusBarHeights = rect.top;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(), statusBarHeights);
    }
}
