package com.hentica.app.util;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hentica.app.widget.view.ChildScrollView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Snow on 2017/2/17.
 */

public class StickTitle {
    /** 滑动监听的View */
    private ScrollView mScrollView;
    /** 被观察的View */
    private View mObserveView;
    /** 标题 */
    private View mTitleView;

    private float mObserveY;

    /** 滑动处理流程集合 */
    private List<ScrollProgress> mScrollProgresses = new ArrayList<>();

    public StickTitle(@NonNull ScrollView scrollView, @NonNull View observeView, @NonNull  View titleView){
        this.mScrollView = scrollView;
        this.mObserveView = observeView;
        this.mTitleView = titleView;
        init();
    }

    private void init(){
        if(mObserveView != null){
            final ViewTreeObserver obs = mObserveView.getViewTreeObserver();
            obs.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    mObserveY = mObserveView.getY();
                    return true;
                }
            });
        }
        if(mScrollView != null && mScrollView instanceof ChildScrollView){
            //监听滑动事件
            ((ChildScrollView)mScrollView).setOnScrollChangedListener(new ChildScrollView.OnScrollChangedListener() {
                @Override
                public void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(mTitleView != null){
                        int visibile = mTitleView.getVisibility();
                        if(scrollY >= mObserveY){
                            if(visibile != View.VISIBLE){
                                mTitleView.setVisibility(View.VISIBLE);
                            }
                        }else{
                            if(visibile != View.GONE){
                                mTitleView.setVisibility(View.GONE);
                            }
                        }
                    }
                    // 执行所有的处理流程
                    if(!ArrayListUtil.isEmpty(mScrollProgresses)){
                        for(ScrollProgress progress : mScrollProgresses){
                            progress.doObserve(scrollX,scrollY,oldScrollX,oldScrollY);
                        }
                    }
                }
            });
        }
    }

    /** 添加一个滑动处理流程 */
    public void addOneScrollProgress(ScrollProgress progress){
        mScrollProgresses.add(progress);
    }

    public static class ScrollProgress{
        /** 被监听的控件 */
        private TextView mObserveView;
        /** 标题控件 */
        private TextView mTitleView;
        /** 滑动监听 */
        private ObserveListener mListener;
        /** 监听控件Y坐标 */
        private float mObserveY;

        public ScrollProgress(TextView observeView, TextView titleView){
            mObserveView = observeView;
            mTitleView = titleView;
            if(mObserveView != null){
                final ViewTreeObserver obs = mObserveView.getViewTreeObserver();
                obs.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        mObserveY = mObserveView.getY()+mObserveView.getMeasuredHeight();
                        return true;
                    }
                });
            }
        }

        public ScrollProgress(ObserveListener listener){
            mListener = listener;
        }
        /** 执行监听 */
        public void doObserve(int scrollX, int scrollY, int oldScrollX, int oldScrollY){
            if(mTitleView != null){
                int visible = mTitleView.getVisibility();
                if(scrollY >= mObserveY){
                    if(visible != View.VISIBLE){
                        mTitleView.setText(mObserveView.getText());
                        mTitleView.setVisibility(View.VISIBLE);
                    }
                }else {
                    if(visible != View.GONE){
                        mTitleView.setVisibility(View.GONE);
                    }
                }
            }
            if(mListener != null){
                mListener.onObserved(scrollY);
            }
        }
    }
    /** 滑动监听事件 */
    public interface ObserveListener{
        void onObserved(int scrollY);
    }

}
