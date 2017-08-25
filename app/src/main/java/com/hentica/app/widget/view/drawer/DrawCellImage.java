package com.hentica.app.widget.view.drawer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by mili on 2016/8/2.
 */
public class DrawCellImage extends DrawCell {

    private Bitmap mBitmap;
    private DrawPoint mPosition = new DrawPoint();

    public DrawCellImage(Bitmap bitmap, DrawPoint pos, Paint paint) {
        super(paint);
        mBitmap = bitmap;
        mPosition.set(pos);
    }

    @Override
    public void onDraw(Canvas canvas) {

        if (mBitmap != null) {

            canvas.drawBitmap(mBitmap, mPosition.x - mBitmap.getWidth() / 2, mPosition.y - mBitmap.getHeight() / 2, mPaint);
        }
    }
}
