package com.hentica.app.widget.view.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by mili on 2016/8/2.
 */
public class DrawCellText extends DrawCell {

    private String mText;
    private DrawPoint mPosition = new DrawPoint();

    public DrawCellText(String text, DrawPoint position, Paint paint) {
        super(paint);

        mPosition.set(position);
        mText = text;
    }

    @Override
    public void onDraw(Canvas canvas) {

        if (mText != null) {

            float textWidth = mPaint.measureText(mText);
            float textHeight = mPaint.getTextSize();

            canvas.drawText(mText, mPosition.x - textWidth / 2, mPosition.y + textHeight / 2, mPaint);
        }
    }
}
