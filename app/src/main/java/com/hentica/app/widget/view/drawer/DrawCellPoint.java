package com.hentica.app.widget.view.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by mili on 2016/8/2.
 */
public class DrawCellPoint extends DrawCell {

	private DrawPoint mPosition = new DrawPoint();
	private float mRadius;

	public DrawCellPoint(DrawPoint pos, float radius, Paint paint) {
		super(paint);

		mPosition.set(pos);
		mRadius = radius;
	}

	@Override
	public void onDraw(Canvas canvas) {

		canvas.drawCircle(mPosition.x, mPosition.y, mRadius, mPaint);
	}
}
