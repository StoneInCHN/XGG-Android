package com.hentica.app.widget.view.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用绘制界面
 * Created by mili on 2016/8/2.
 */
public class DrawerView extends View {

    // 绘制内容列表
    private List<DrawCell> mDrawCells = new ArrayList<>();

    public DrawerView(Context context) {
        super(context, null);
        init();
    }

    public DrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    // 初始化
    private void init() {
    }

    /**
     * 添加点
     *
     * @param x      坐标
     * @param y      坐标
     * @param radius 半径
     * @param paint  画笔
     */
    public void addPoint(float x, float y, float radius, Paint paint) {

        mDrawCells.add(new DrawCellPoint(new DrawPoint(x, y), radius, paint));
    }

    /**
     * 添加线段
     *
     * @param x1    坐标
     * @param y1    坐标
     * @param x2    坐标
     * @param y2    坐标
     * @param paint 画笔
     */
    public void addLine(float x1, float y1, float x2, float y2, Paint paint) {

        mDrawCells.add(new DrawCellLine(new DrawPoint(x1, y1), new DrawPoint(x2, y2), paint));
    }

    /**
     * 添加折线
     *
     * @param allPos 每个点
     * @param paint  画笔
     */
    public void addLines(List<DrawPoint> allPos, Paint paint) {

        mDrawCells.add(new DrawCellLine(allPos, paint));
    }

    /**
     * 添加文字
     *
     * @param text  文字
     * @param x     坐标
     * @param y     坐标
     * @param paint 画笔
     */
    public void addText(String text, float x, float y, Paint paint) {

        mDrawCells.add(new DrawCellText(text, new DrawPoint(x, y), paint));
    }

    /**
     * 添加图片
     *
     * @param imgRes 图片资源
     * @param paint  画笔
     */
    public void addImage(int imgRes, float x, float y, Paint paint) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgRes);
        addImage(bitmap, x, y, paint);
    }

    /**
     * 添加图片
     *
     * @param bitmap 图片资源
     * @param paint  画笔
     */
    public void addImage(Bitmap bitmap, float x, float y, Paint paint) {

        mDrawCells.add(new DrawCellImage(bitmap, new DrawPoint(x, y), paint));
    }

    /**
     * 清空绘制信息
     */
    public void clear() {

        mDrawCells.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (DrawCell cell : mDrawCells) {

            cell.onDraw(canvas);
        }
    }
}
