package com.hentica.app.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.hentica.app.lib.view.CustomViewPager;

/**
 * Created by Snow on 2017/1/11.
 */

public class ChildViewPager extends CustomViewPager {
    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 在切换tab的时候，重置ViewPager的高度
     * @param current
     */
    public void resetHeight(int current){
        View view = getChildAt(current);
        MarginLayoutParams params= (MarginLayoutParams) getLayoutParams();
        if(view != null){
            if(params==null){
                params=new MarginLayoutParams(LayoutParams.MATCH_PARENT,view.getMeasuredHeight());
            }else {
                params.height = view.getMeasuredHeight();
            }
            setLayoutParams(params);
        }
    }
}
