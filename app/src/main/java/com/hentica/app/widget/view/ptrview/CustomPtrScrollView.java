package com.hentica.app.widget.view.ptrview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;
import com.hentica.app.widget.view.ChildScrollView;

/**
 * 自定义加载动画
 * Created by Snow on 2017/1/12.
 */

public class CustomPtrScrollView extends PullToRefreshScrollView {
    public CustomPtrScrollView(Context context) {
        super(context);
    }

    public CustomPtrScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPtrScrollView(Context context, Mode mode) {
        super(context, mode);
    }


    public CustomPtrScrollView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs) {
        LoadingLayout layout =  new CustomLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
        layout.setVisibility(View.INVISIBLE);
        return layout;
    }

    /**
     * 下拉刷新
     */
    public void pullDownRefresh(){
        if(!isRefreshing()) {
            onReleaseToRefresh();
            setRefreshing();
        }else{
            setRefreshing();
        }
    }

    @Override
    protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
        ScrollView scrollView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            scrollView = new InternalScrollViewSDK9(context, attrs);
        } else {
            scrollView = new ChildScrollView(context, attrs);
        }

        scrollView.setId(com.handmark.pulltorefresh.library.R.id.scrollview);
        return scrollView;
    }

    @TargetApi(9)
    final class InternalScrollViewSDK9 extends ChildScrollView {

        public InternalScrollViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                    scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(CustomPtrScrollView.this, deltaX, scrollX, deltaY, scrollY,
                    getScrollRange(), isTouchEvent);

            return returnValue;
        }

        /**
         * Taken from the AOSP ScrollView source
         */
        private int getScrollRange() {
            int scrollRange = 0;
            if (getChildCount() > 0) {
                View child = getChildAt(0);
                scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
            }
            return scrollRange;
        }
    }
}
