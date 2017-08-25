package com.hentica.app.widget.view.ptrview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

/**
 * 自定义加载动画
 * Created by Snow on 2017/1/11.
 */

public class CustomPtrListView extends PullToRefreshListView {

    public CustomPtrListView(Context context) {
        super(context);
    }

    public CustomPtrListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPtrListView(Context context, Mode mode) {
        super(context, mode);
    }

    public CustomPtrListView(Context context, Mode mode, AnimationStyle style) {
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

}
