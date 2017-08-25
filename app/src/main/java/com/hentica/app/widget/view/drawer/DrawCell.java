package com.hentica.app.widget.view.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 绘制单元
 * Created by mili on 2016/8/2.
 */
public abstract class DrawCell {

    /**
     * 画笔
     */
    protected Paint mPaint;

    public DrawCell(Paint paint) {
        setPaint(paint);
    }

    /**
     * 设置画笔
     *
     * @param paint 画笔
     */
    public void setPaint(Paint paint) {
        mPaint = paint;
    }


    /**
     * 绘制
     */
    public void onDraw(Canvas canvas) {
    }
}
