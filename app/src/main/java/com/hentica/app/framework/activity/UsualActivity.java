package com.hentica.app.framework.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.hentica.app.lib.util.AnimationHelper;
import com.hentica.app.lib.util.AnimationHelper.AnimationType;
import com.hentica.app.util.FontUtil;
import com.hentica.app.util.LogUtils;

import cn.jpush.android.api.JPushInterface;

/** 自定义Activity父类，封装常用方法 */
public class UsualActivity extends FragmentActivity {

	/** 跳转动画辅助工具 */
	private AnimationHelper mAnimationHelper = new AnimationHelper();

	/** 要接收result的子界面 */
	private Fragment mForResultFragment;

	/** 是否在onReume中保留子界面引用。这是为了应对在onResult中再调用startActivityForResult的情况 */
	private boolean mIsKeepResultFragmentOnReume;

	/** 要接收result的子界面 */
	public void setForResultFragment(Fragment forResultFragment) {
		mForResultFragment = forResultFragment;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 若是新创建的，而不是恢复的
		if (savedInstanceState == null) {

			// 触发事件
			if (mAnimationHelper != null) {
				mAnimationHelper.onActiviyCreated(getIntent());
			}
		}

		// 每个activity调用
//		GeTuiPushUtil.getInstance().onAppStart();
		JPushInterface.init(getApplicationContext());
		Log.d("JPushInterface", "initRegistId: " + JPushInterface.getRegistrationID(this));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// 使用临时变量，防止在onResult中又调用startActivityForResult()
		Fragment tmpFragment = mForResultFragment;
		mForResultFragment = null;
		

        int index = requestCode>>16;
        
        if (tmpFragment != null) {
			
        	if (index == 0) {
            	// 这里会传递给子界面
            	// 当index为0时，不会传递给子界面
    			tmpFragment.onActivityResult(requestCode & 0xffff, resultCode, data);
			}
        	// 若index不为0,且是最顶级，Activity会发送result事件，不需要手动操作
        	// 若index不为0,且不是最顶级，则手动发送result事件
        	else if (tmpFragment.getParentFragment() != null) {

				tmpFragment.onActivityResult(requestCode & 0xffff, resultCode, data);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!mIsKeepResultFragmentOnReume) {

			mForResultFragment = null;
			mIsKeepResultFragmentOnReume = false;
		}
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(FontUtil.wrap(newBase));
	}

	@Override
	public void startActivityFromFragment(Fragment fragment, Intent intent,
			int requestCode) {

		// 触发事件
		if (mAnimationHelper != null) {
			mAnimationHelper.beforeStartNewActivity(intent);
		}

		// 记录要接收result的界面
		this.setForResultFragment(fragment);

		// 若界面处于后台状态，则说明是在onResult中又调用了startActivty()，这时要保存这个fragment
		if (!fragment.isResumed()) {
			mIsKeepResultFragmentOnReume = true;
		}

		super.startActivityFromFragment(fragment, intent, requestCode);

		// 触发事件
		if (mAnimationHelper != null) {
			mAnimationHelper.afterStartActivity(getActivity());
		}
	}

	@Override
	public void finish() {
		super.finish();

		// 触发事件
		if (mAnimationHelper != null) {
			mAnimationHelper.afterActivityFinished(getActivity());
		}
	}

	@Override
	public void finishActivity(int requestCode) {
		super.finishActivity(requestCode);

		// 触发事件
		if (mAnimationHelper != null) {
			mAnimationHelper.afterActivityFinished(getActivity());
		}
	}

	/** 跳转动画辅助工具 */
	public AnimationHelper getAnimationHelper() {
		return mAnimationHelper;
	}

	/** 跳转动画辅助工具 */
	public void setAnimationHelper(AnimationHelper animationHelper) {
		mAnimationHelper = animationHelper;
	}

	/** 设置下个跳转将使用的动画 */
	public void setNextActivityAnimationType(AnimationType animationType) {
		if (mAnimationHelper != null) {
			mAnimationHelper.setNextAcitivtyAnimationType(animationType);
		}
	}

	/** 设置入场动画，因为此界面已经加载了，不会影响入场动画，但是会影响相应的出场动画 */
	public void setEnterAnimationType(AnimationType animationType) {
		if (mAnimationHelper != null) {
			mAnimationHelper.setEnterAnimationType(animationType);
		}
	}

	/** 取得自身Activity对象 */
	protected Activity getActivity() {

		return this;
	}

	/** 封装getFragmentManager方法，以兼容低版本 */
	public FragmentManager getUsualFragmentManager() {
		return super.getSupportFragmentManager();
		// return super.getFragmentManager();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		LogUtils.i("UsualActivity", "onRequestPermissionsResult : requestCode = " + requestCode);
	}
}
