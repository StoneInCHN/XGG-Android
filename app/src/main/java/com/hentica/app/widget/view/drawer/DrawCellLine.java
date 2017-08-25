package com.hentica.app.widget.view.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.List;

/**
 * 画线
 *
 * @author mili
 */
public class DrawCellLine extends DrawCell {

    // 所有绘制点
//    float[] mAllPoints;
    Path mPath = new Path();

    /**
     * 构造函数，添加线段
     *
     * @param pos1  起始位置
     * @param pos2  结束位置
     * @param paint 画笔
     */
    public DrawCellLine(DrawPoint pos1, DrawPoint pos2, Paint paint) {
        super(paint);

//        mAllPoints = new float[4];
//        mAllPoints[0] = pos1.x;
//        mAllPoints[1] = pos1.y;
//        mAllPoints[2] = pos2.x;
//        mAllPoints[3] = pos2.y;

        mPath.moveTo(pos1.x, pos1.y);
        mPath.lineTo(pos2.x, pos2.y);
    }

    /**
     * 构造函数，添加折线
     *
     * @param allPos 每个点
     * @param paint  画笔
     */
    public DrawCellLine(List<DrawPoint> allPos, Paint paint) {
        super(paint);

//        mAllPoints = new float[allPos.size() * 4];
        for (int i = 0, size = allPos.size(); i < size; i++) {

//            if (i < size - 1) {
//
//                DrawPoint pos = allPos.get(i);
//                DrawPoint pos2 = allPos.get(i + 1);
//
//                mAllPoints[i * 4] = pos.x;
//                mAllPoints[i * 4 + 1] = pos.y;
//                mAllPoints[i * 4 + 2] = pos2.x;
//                mAllPoints[i * 4 + 3] = pos2.y;
//            }

            DrawPoint pos = allPos.get(i);
            if (i == 0){

                mPath.moveTo(pos.x, pos.y);
            }else {

                mPath.lineTo(pos.x, pos.y);
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {

//        if (mAllPoints != null && mAllPoints.length > 0) {
//
//            canvas.drawLines(mAllPoints, mPaint);
//        }
        canvas.drawPath(mPath, mPaint);
    }
}
