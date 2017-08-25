package com.hentica.app.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/25.19:29
 */

public class PasswordEditText extends EditText {

    private int mWidth;//宽
    private int mHeight;//高
    public static final int PASSWORD_LENGTH = 6;//密码长度

    private int mPwdLength;//当前密码长度

    public PasswordEditText(Context context) {
        super(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBorder(canvas);
        drawDivider(canvas);
        drawPassword(canvas);
    }

    /**
     * 画边框
     * @param canvas
     */
    private void drawBorder(Canvas canvas){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1f);
//        Path path = new Path();
//        path.lineTo(0, 0);
//        path.lineTo(0, mWidth);
//        path.lineTo(mWidth,mHeight);
//        path.lineTo(0 ,mHeight);
//        path.close();
//        canvas.drawPath(path, paint);
        canvas.drawLine(0, 0, mWidth, 0, paint);
        canvas.drawLine(mWidth, 0, mWidth, mHeight, paint);
        canvas.drawLine(mWidth, mHeight, 0, mHeight, paint);
        canvas.drawLine(0, 0, 0, mHeight, paint);
    }

    /**
     * 画分隔线
     * @param canvas
     */
    private void drawDivider(Canvas canvas){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1f);
        for(int i = 1; i < PASSWORD_LENGTH; i++){
            int x = mWidth / PASSWORD_LENGTH * i;
            canvas.drawLine(x, 0, x, mHeight, paint);
        }
    }


    /**
     * 画密码
     * @param canvas
     */
    private void drawPassword(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        for(int i = 0; i < mPwdLength; i++){
            int x = (int) (mWidth / PASSWORD_LENGTH * ( i + 0.5f));
            int y = mHeight / 2;
            canvas.drawCircle(x, y, 20, paint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(text.length() > PASSWORD_LENGTH){
            String newText = text.subSequence(0, PASSWORD_LENGTH).toString();
            setText(newText);
            setSelection(PASSWORD_LENGTH);
            return;
        }
        mPwdLength = text.length();
        invalidate();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }
}
