package com.hentica.app.widget.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Snow on 2017/7/4.
 */

public class OuterViewPager extends ViewPager {

    private float oldX, oldY, mLastInterceptX, mLastInterceptY;

    public OuterViewPager(Context context) {
        super(context);
    }

    public OuterViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        float newX = ev.getX();
        float newY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                float dX = newX - mLastInterceptX;
                float dY = newY - mLastInterceptY;
                if (Math.abs(dY) > Math.abs(dX)) {
                    intercept = false;
                } else {
                    if (dX > 0 && getCurrentItem() > 0) {
                        intercept = true;
                    } else if (dX < 0 && getCurrentItem() < getAdapter().getCount() -1) {
                        intercept = true;
                    }
                }
                break;
            default:
                intercept = false;
                break;
        }
        oldX = newX;
        oldY = newY;
        mLastInterceptX = newX;
        mLastInterceptY = newY;
        return intercept;
    }
}
