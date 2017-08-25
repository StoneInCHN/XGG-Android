package com.hentica.app.framework.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.lib.util.CrashHandler;
import com.hentica.app.lib.view.FloatPostViewHelper;
import com.fiveixlg.app.customer.R;

/**
 * 空Activity，里面显示了一个Framgment<br />
 * 通过intent决定显示哪个界面
 */
public class FrameActivity extends UsualActivity {

    /**
     * 加载的界面
     */
    protected UsualFragment mFragment;

    /**
     * fragment tag
     */
    protected String TAG_FRAGMENT = "TAG_FRAGMENT";

    /**
     * 是否需要重新加载界面
     */
    private boolean mNeedReloadFragment;

    /**
     * Activity是否已销毁
     */
    private boolean mIsDestoryed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.common_null_layout);

        // 若是新创建的，而不是恢复的
        if (savedInstanceState == null) {

            // 创建子界面
            this.createFragment();
        }
        // 若是恢复过来的
        else {

            // 恢复其中的fragment
            FragmentManager fm = this.getUsualFragmentManager();
            mFragment = (UsualFragment) fm.findFragmentByTag(TAG_FRAGMENT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 添加网络测试界面
        FloatPostViewHelper.getInstance().addToActivity(this, R.id.activity_null_layout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 移除网络测试界面
        FloatPostViewHelper.getInstance().removeFloatView(this, R.id.activity_null_layout);

        mIsDestoryed = true;
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        if (mNeedReloadFragment) {
            mNeedReloadFragment = false;

            this.reloadFragmentNow();
        }
    }

    /**
     * 创建界面
     */
    private void createFragment() {

        Intent intent = this.getIntent();
        if (intent != null) {

            // 界面跳转
            String jumpToName = intent.getStringExtra("jumpTo");

            // 创建fragment对象，并加入视图
            if (jumpToName != null) {

                try {
                    UsualFragment fragment = (UsualFragment) Class.forName(jumpToName)
                            .newInstance();
                    this.setFragment(fragment);

                } catch (Exception e) {
                    CrashHandler.getInstance().handleException("创建Fragment失败", e);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置要显示的fragment
     */
    protected void setFragment(UsualFragment fragment) {

        // 加入到界面
        if (fragment != null) {

            this.getUsualFragmentManager().beginTransaction()
                    .add(getLayoutId(), fragment, TAG_FRAGMENT).commit();
            fragment.setLayoutId(getLayoutId());
        }
        mFragment = fragment;
    }

    /**
     * 取得布局id
     */
    protected int getLayoutId() {

        return R.id.activity_null_layout;
    }

    @Override
    public boolean isDestroyed() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {

            return super.isDestroyed();
        } else {

            return mIsDestoryed;
        }
    }

    /**
     * 重新加载界面
     */
    public void reloadFragment() {

        // 若子界面还未恢复
        if (!mFragment.isResumed()) {

            // 标记为恢复时重新加载
            mNeedReloadFragment = true;
        }
        // 若子界面正在显示中
        else {

            // 立即重新加载
            reloadFragmentNow();
        }
    }

    /**
     * 重新加载界面
     */
    private void reloadFragmentNow() {

        // 若此界面允许重新加载
        if (mFragment != null && mFragment.allowReload()) {

            // 移除以前的界面
            this.getUsualFragmentManager().beginTransaction().remove(mFragment).commit();

            // 创建新界面
            createFragment();

            // 重新添加网络测试界面
            FloatPostViewHelper.getInstance().addToActivity(this, R.id.activity_null_layout);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (mFragment != null) {

            mFragment.onDispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mFragment != null && mFragment.onKeyDown(keyCode, event)) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(mFragment != null){
            mFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
