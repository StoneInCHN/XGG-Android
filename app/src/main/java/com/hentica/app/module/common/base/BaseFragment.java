package com.hentica.app.module.common.base;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.hentica.app.framework.fragment.RequestPermissionResultListener;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.util.LogUtils;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import butterknife.ButterKnife;

/**
 * Created by kezhong.
 * E-Mail:396926020@qq.com
 * on 2016/10/19 10:33
 */

public abstract class BaseFragment extends UsualFragment {

    protected String TAG = this.getClass().getSimpleName();

    protected AQuery mQuery;

    protected TitleView mTitleView;

    private int mTitleColor = 0;

    private int mTitleRightColor = 0;

    protected Context mContext;

    private View.OnClickListener mTitleBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    protected void setTitleTextColor(@ColorRes int color) {
        this.mTitleColor = color;
    }

    protected void setTitleRightTextColor(@ColorRes int color){
        this.mTitleRightColor = color;
    }

    protected void setTitleBackClickListener(View.OnClickListener mTitleBackClickListener) {
        this.mTitleBackClickListener = mTitleBackClickListener;
    }


    protected TitleView getTitleView(){
        return mTitleView;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        mQuery = new AQuery(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * 布局文件
     * @return
     */
    public abstract @LayoutRes int getLayoutId();



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 初始化界面
        this.handleIntentData(getIntent());
        this.initTitle();
        this.initData();
        this.initView();
        this.setEvent();
    }

    /**
     * 是否有标题
     * @return
     */
    protected boolean hasTitleView(){return false;}

    private void initTitle(){
        mTitleView = initTitleView();
        if(mTitleView == null || !hasTitleView()){
            if(mTitleView != null){
                mTitleView.setVisibility(View.GONE);
            }
            return;
        }
        // 返回按钮，被点击
        mTitleView.setOnLeftImageBtnClickListener(mTitleBackClickListener);
        configTitleValues(mTitleView);
    }

    protected TitleView initTitleView() {
        return null;
    }

    /** 已做空判断 */
    protected void configTitleValues(TitleView title){
        if(title == null){
            return;
        }
    }

    /** 处理Intent中数据 */
    protected void handleIntentData(Intent intent){};

    /** 初始化数据 */
    protected abstract void initData();

    /** 初始化界面 */
    protected abstract void initView();

    /** 设置事件 */
    protected abstract void setEvent();

    /** 设置View的点击事件 */
    protected void setViewClickEvent(@IdRes int viewId, View.OnClickListener l){
        mQuery.id(viewId).clicked(l);
    }

    /** 设置View的点击事件 */
    protected void setViewClickEvent(View.OnClickListener l, @IdRes int... pathId){
        mQuery.id(pathId).clicked(l);
    }

    /**
     * 设置控件是否显示
     * @param viewId
     * @param visiable
     *          true:显示， false:隐藏
     */
    protected void setViewVisiable(@IdRes int viewId, boolean visiable){
        setViewVisiable(visiable, viewId);
    }

    /**
     * 设置控件是否显示
     * @param visiable
     *          true:显示， false:隐藏
     */
    protected void setViewVisiable(boolean visiable, @IdRes int... pathId){
        mQuery.id(pathId).visibility(visiable ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置控件文字
     * @param viewId
     * @param text
     */
    protected void setViewText(@IdRes int viewId, CharSequence text){
        setViewText(text, viewId);
    }

    /**
     * 设置控件文字
     * @param pathId
     * @param text
     */
    protected void setViewText(CharSequence text, @IdRes int... pathId){
        mQuery.id(pathId).text(text);
    }

    /**
     * 设置控件是否可用
     * @param enable
     * @param pathId
     */
    protected void setViewEnable(boolean enable, @IdRes int... pathId){
        mQuery.id(pathId).enabled(enable);
    }

    /**
     * dp2px
     * @param dp
     * @return
     */
    public int dp2px(int dp){
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    /**
     * 获取控件
     * @param id
     * @param <T>
     * @return
     */
    @Nullable
    protected  <T extends View> T getViews(@IdRes int id){
        return (T) mQuery.id(id).getView();
    }

    /** 设置控件点击事件 */
    protected void setViewOnClickListener(@IdRes int id, View.OnClickListener l){
        if(getViews(id) != null){
            getViews(id).setOnClickListener(l);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.i("BaseFragment", "onRequestPermissionsResult : requestCode=" + requestCode);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
