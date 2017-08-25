package com.hentica.app.widget.view.ptrview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;
import com.hentica.app.util.LogUtils;
import com.fiveixlg.app.customer.R;

/**
 * Created by Snow on 2017/1/11.
 */

public class CustomLoadingLayout extends LoadingLayout {
    AnimationDrawable animationDrawable;

    public CustomLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
    }

    @Override
    protected int getDefaultDrawableResId() {
        LogUtils.v("CustomLoadingLayout", "getDefaultDrawableResId");
        return R.drawable.rebate_1;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {
        LogUtils.v("CustomLoadingLayout", "onLoadingDrawableSet");
    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        //拉动时触发
        LogUtils.v("CustomLoadingLayout", "onPullImpl");
    }

    @Override
    protected void pullToRefreshImpl() {
        Log.v("CustomLoadingLayout", "pullToRefreshImpl");
    }

    @Override
    protected void refreshingImpl() {
        LogUtils.v("CustomLoadingLayout", "refreshingImpl");
        //正在加载时，开始动画
        if(animationDrawable != null) {
            animationDrawable.start();
        }else{
            mHeaderImage.setImageResource(R.drawable.rebate_anim_refreshing);
            animationDrawable = (AnimationDrawable) mHeaderImage.getDrawable();
            animationDrawable.start();
        }
    }

    @Override
    protected void releaseToRefreshImpl() {
        LogUtils.v("CustomLoadingLayout", "releaseToRefreshImpl");
        //松开刷新，设置动画
        mHeaderImage.setImageResource(R.drawable.rebate_anim_refreshing);
        animationDrawable = (AnimationDrawable) mHeaderImage.getDrawable();
        animationDrawable.start();
    }

    @Override
    protected void resetImpl() {
        //动画重置还原
        LogUtils.v("CustomLoadingLayout", "resetImpl");
        mHeaderProgress.setVisibility(View.GONE);
        mHeaderImage.setVisibility(View.VISIBLE);
        mHeaderImage.clearAnimation();
        mHeaderImage.setImageResource(getDefaultDrawableResId());
    }
}
