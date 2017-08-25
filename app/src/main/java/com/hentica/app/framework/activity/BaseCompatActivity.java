package com.hentica.app.framework.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.appbase.famework.widget.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2017/5/15.
 */

public abstract class BaseCompatActivity extends AppCompatActivity implements FragmentListener.UsualViewOperator{

    public static final String TAG = BaseCompatActivity.class.getSimpleName();

    private LoadingDialog mLoadingDialog;
    private UsualFragment.OnActivityResultListener mActivityResultListener;
    protected AQuery mQuery;

    private AutoHideInputHelper mAutoHideInputHelper;
    /** 触摸事件分发辅助工具 */
//    private TouchDispatchHelper mTouchDispatchHelper;
    private int loadingCount = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        EventBus.getDefault().register(this);
        mAutoHideInputHelper = new AutoHideInputHelper(this);
        mQuery = new AQuery(this);
//        mTouchDispatchHelper = new TouchDispatchHelper()
        this.initData();
        this.initView();
        this.setEvent();
    }

    /**
     * 获取界面
     * @return
     */
    protected abstract @LayoutRes int getLayoutId();

    protected void initData(){
    }

    protected void initView() {
    }

    protected void setEvent(){

    }

    @Override
    public void showLoadingDialog() {
        loadingCount++;

        if (mLoadingDialog == null) {

            mLoadingDialog = new LoadingDialog(getActivity());
            mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {

                    if (mLoadingDialog != null && mLoadingDialog.getDialog() == dialog) {

                        loadingCount = 0;
                        mLoadingDialog = null;
                    }
                }
            });
        }

        if (mLoadingDialog != null) {

            mLoadingDialog.showDialog();
        }
    }

    @Override
    public void dismissLoadingDialog() {
        loadingCount--;
        if (loadingCount <= 0) {
            if (mLoadingDialog != null) {
                mLoadingDialog.dismissDialog();
                mLoadingDialog = null;
            }
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onToLogin() {
        FragmentJumpUtil.tryToLogin(this);
    }



    @Override
    public UsualFragment getUsualFragment() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mActivityResultListener != null){
            mActivityResultListener.onActivityResult(requestCode, resultCode, data);
            mActivityResultListener = null;
        }
    }

    public void setActivityResultListener(UsualFragment.OnActivityResultListener l){
        mActivityResultListener = l;
    }


    /**
     * 设置控件文字
     * @param pathId
     * @param text
     */
    protected void setViewText(CharSequence text, @IdRes int... pathId){
        AQuery query = new AQuery(this);
        query.id(pathId).text(text);
    }

    protected <T extends View> T getViews(@IdRes int id){
        return (T) findViewById(id);
    }

    protected <T extends View> T getViews(@IdRes int...id){
        return (T) mQuery.id(id).getView();
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
        AQuery query = new AQuery(this);
        query.id(pathId).visibility(visiable ? View.VISIBLE : View.GONE);
    }

    /** 设置控件点击事件 */
    protected void setViewOnClickListener(@IdRes int id, View.OnClickListener l){
        if(getViews(id) != null){
            getViews(id).setOnClickListener(l);
        }
    }

    /** 设置View的点击事件 */
    protected void setViewClickEvent(@IdRes int viewId, View.OnClickListener l){
        mQuery.id(viewId).clicked(l);
    }

    /** 设置View的点击事件 */
    protected void setViewClickEvent(View.OnClickListener l, @IdRes int... pathId){
        mQuery.id(pathId).clicked(l);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected AppCompatActivity getActivity(){
        return this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mAutoHideInputHelper.onDispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 自动失去焦点
        this.setIsAutoClearForce(true);
        // 自动隐藏输入法
        this.setIsAutoHideInput(true);
    }

    /** 是否自动失去焦点，若是，当触摸到空白区域时，则自动失去焦点 */
    public void setIsAutoClearForce(boolean isAutoClearForce) {

        mAutoHideInputHelper.mIsAutoClearForce = isAutoClearForce;
    }

    /** 是否自动隐藏输入法，若是，当触摸到空白区域时，则隐藏输入法 */
    public void setIsAutoHideInput(boolean isAutoHideInput) {

        mAutoHideInputHelper.mIsAutoHideInput = isAutoHideInput;
    }

    /** 自动隐藏输入法，辅助工具 */
    private static class AutoHideInputHelper {

        /** 引用主界面 */

        /** 是否自动失去焦点，若是，当触摸到空白区域时，则自动失去焦点 */
        private boolean mIsAutoClearForce;

        /** 是否自动隐藏输入法，若是，当触摸到空白区域时，则隐藏输入法 */
        private boolean mIsAutoHideInput;

        private Activity mActivity;

        /** 构造函数 */
        public AutoHideInputHelper(Activity activity) {

            mActivity = activity;
        }

        /** 主界面被触摸了 */
        public void onDispatchTouchEvent(MotionEvent event) {

            // 当前界面是显示的情况下，隐藏软键盘
            this.tryHideInput(event);
        }

        /** 尝试隐藏软键盘 */
        private void tryHideInput(MotionEvent ev) {

            if(mActivity != null) {

                View view = mActivity.getCurrentFocus();

                if (view != null && (view instanceof EditText)) {

                    boolean isTouchView = ViewUtil.isTouchView(ev, view);

                    // 失去焦点
                    if (mIsAutoClearForce && !isTouchView) {

                        view.clearFocus();
                    }

                    // 隐藏软键盘
                    if (mIsAutoHideInput && !isTouchView) {

                        hideSoftInput(view);
                    }
                }
            }
        }

        /** 隐藏软键盘 */
        private void hideSoftInput(View v) {
            this.hideSoftInput(v.getWindowToken());
        }

        /** 隐藏软键盘 */
        private void hideSoftInput(IBinder token) {
            if (token != null) {
                InputMethodManager manager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!LoginModel.getInstance().isLogin()){
            finish();
        }
    }

    /** 为了event不报错 */
    @Subscribe
    public void onEvent(int code) {
    }
}
