package com.hentica.app.framework.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hentica.app.framework.activity.FrameActivity;
import com.hentica.app.framework.activity.UsualActivity;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.framework.fragment.FragmentListener.OnDispatchTouchEventListener;
import com.hentica.app.framework.fragment.FragmentListener.OnKeyListener;
import com.hentica.app.framework.fragment.FragmentListener.UsualViewOperator;
import com.hentica.app.lib.util.AnimationHelper.AnimationType;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.LogUtils;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.UiEvent.OnCollectiActivityEvent;
import com.hentica.app.util.event.UiEvent.OnDestoryAllUIEvent;
import com.hentica.appbase.famework.widget.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/** 通用fragment */
public class UsualFragment extends Fragment implements OnKeyListener, OnDispatchTouchEventListener,
		UsualViewOperator , OnWindowFocusChanged{

	private RequestPermissionResultListener mPermissionResultListener;

	public void setRequestPermissionResultListener(RequestPermissionResultListener l){
		mPermissionResultListener = l;
	}

	public final String TAG = this.getClass().getSimpleName();

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

	}
	// 由于本类的功能较多，把每一个功能放到一个单独的辅助类中实现

	/** fragment销毁事件 */
	public interface OnFragmentDestoryListener {

		/** fragment被销毁 */
		void onDestory();
	}

	/** 监听界面返回值 */
	public interface OnActivityResultListener {

		/** 界面有返回值了 */
		void onActivityResult(int requestCode, int resultCode, Intent data);
	}

	/** 当前布局显示哪个视图节点上 */
	private int mLayoutId = -1;

	/** 加载对话框辅助工具 */
	private LoadingHelper mLoadingHelper = new LoadingHelper(this);

	/** 按键事件分发辅助工具 */
	private KeyDispatchHelper mKeyDispatchHelper = new KeyDispatchHelper(this);

	/** 触摸事件分发辅助工具 */
	private TouchDispatchHelper mTouchDispatchHelper = new TouchDispatchHelper(this);

	/** 自动隐藏输入法工具 */
	private AutoHideInputHelper mAutoHideInputHelper = new AutoHideInputHelper(this);

	/** 用于保证同一界面只跳转一次的工具 */
	private SingleJumpHelper mSingleJumpHelper = new SingleJumpHelper();

	/** 转场动画辅助工具 */
	private AnimationHelper mAnimationHelper = new AnimationHelper(this);

	/** 恢复时刷新，辅助类 */
	private RefreshOnResume mRefreshOnResumeHelper = new RefreshOnResume(this);

	/** 销毁其他界面，辅助类 */
	private DestoryUiHelper mDestoryUiHelper = new DestoryUiHelper(this);

	/** 返回值监听，辅助类 */
	private ResultHandleHelper mResultHandleHelper = new ResultHandleHelper();

	/** 是否注册了EventBus事件 */
	private boolean mIsRegisterEvent;

	/** 当前界面是否允许重新加载，若允许，则销毁当前界面，并重新创建 */
	private boolean mIsAllowReload = true;

	/** 未登录返回时是否需要结束界面 */
	private boolean mIsFinishWhenBackWithoutLogin = true;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// 注册全局事件
		this.registerEventBus();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(resultCode == Constants.ACTIVITY_RESULT_CODE_BACK_WITHOUT_LOGIN && mIsFinishWhenBackWithoutLogin){
			// 未登录就返回，关闭界面
            LogUtils.d(TAG, "onActivityResult: ");
			finish();
		}

		mSingleJumpHelper.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
		mResultHandleHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onStart() {
		super.onStart();

		// 自动失去焦点
		this.setIsAutoClearForce(true);
		// 自动隐藏输入法
		this.setIsAutoHideInput(true);
	}

	@Override
	public void onResume() {

		mSingleJumpHelper.onResume();
		mRefreshOnResumeHelper.onResume();
		super.onResume();
		mResultHandleHelper.onResume();
	}

	/** 为了event不报错 */
	@Subscribe
	public void onEvent(int code) {
	}

	/** 收到事件，销毁所有界面 */
	@Subscribe
	public void onEvent(OnDestoryAllUIEvent event) {

		// 不显示动画
		this.setEnterAnimationType(AnimationType.kDefault);
		finish();
	}

	/** 收到事件，收集指定Activity */
	@Subscribe
	public void onEvent(OnCollectiActivityEvent event) {

		event.handleEvent(this);
	}

	/** 界面被切换了，由ViewPager负责调用 */
	public void onSwitched() {
	}

	/** 界面恢复了，立即刷新数据，由子类实现 */
	protected void onResumeRefresh() {
	}

	/**
	 * 销毁所有界面
	 *
	 * @param isKeeySelf
	 *            是否保留当前界面
	 */
	protected void destoryAllUis(boolean isKeeySelf) {

		mDestoryUiHelper.destoryAllUis(isKeeySelf);
	}

	/** 销毁所有其他界面 */
	protected void destoryOtherUis(Class<? extends UsualFragment> keepClass) {

		mDestoryUiHelper.destoryOtherUis(keepClass);
	}

	/** 取得对应的Activity的Intent数据 */
	protected Intent getIntent() {

		if (getActivity() != null) {

			return getActivity().getIntent();
		}
		return null;
	}

	/** 绑定点击跳转事件 */
	protected void bindViewJump(int viewId, final Class<? extends UsualFragment> jumpTo) {

		View view = ViewUtil.getHolderView(getView(), viewId);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startFrameActivity(jumpTo);
			}
		});
	}

	/** 注册事件 */
	protected void registerEventBus() {

		if (!mIsRegisterEvent) {

			// 注册事件通知
			EventBus.getDefault().register(this);
		}
		mIsRegisterEvent = true;
	}

	/** 注销全局事件 */
	protected void unregisterEventBus() {

		// 注册事件通知
		EventBus.getDefault().unregister(this);
		mIsRegisterEvent = false;
	}

	/** 显示对话框 */
	@Override
	public void showLoadingDialog() {

		mLoadingHelper.showLoadingDialog();
	}

	/** 取消对话框 */
	@Override
	public void dismissLoadingDialog() {

		mLoadingHelper.dismissLoadingDialog();
	}

	/** 显示提示文字 */
	@Override
	public void showToast(String msg) {

		if (getContext() != null) {

			Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
		}
	}

	/** 弹出登录界面 */
	@Override
	public void onToLogin() {
		FragmentJumpUtil.tryToLogin(getUsualFragment());
	}

	/** 尝试在恢复时刷新，若正在当前界面，则直接刷新 */
	public void setRefreshOnResume() {

		mRefreshOnResumeHelper.tryRefershOnResume();
	}

	/** 当前界面是否允许重新加载，若允许，则销毁当前界面，并重新创建 */
	public void setAllowReload(boolean allow) {

		mIsAllowReload = allow;
	}

	/** 当前界面是否允许重新加载，若允许，则销毁当前界面，并重新创建 */
	public boolean allowReload() {

		return mIsAllowReload;
	}

	/** 未登录返回时是否需要结束界面 */
	public void setFinishWhenBackWithoutLogin(boolean isFinish){
		mIsFinishWhenBackWithoutLogin = isFinish;
	}

	/** 未登录返回时是否需要结束界面 */
	public boolean getFinishWhenBackWithoutLogin(){
		return mIsFinishWhenBackWithoutLogin;
	}

	/** 启动一个公用Activity，以显示fragment */
	public void startFrameActivity(Class<? extends UsualFragment> fragment) {

		this.startFrameActivity(fragment, null);
	}

	/** 设置下个跳转将使用的动画 */
	public void setNextActivityAnimationType(AnimationType animationType) {

		mAnimationHelper.setNextActivityAnimationType(animationType);
	}

	/** 设置入场动画，因为此界面已经加载了，不会影响入场动画，但是会影响相应的出场动画 */
	public void setEnterAnimationType(AnimationType animationType) {

		mAnimationHelper.setEnterAnimationType(animationType);
	}

	/** 启动一个公用Activity */
	public void startFrameActivity(Class<? extends UsualFragment> fragment, Intent intent) {

		// context有可能为空
		Context context = getActivity();

		// Activity为空时不能跳转
		if (context == null) {

			return;
		}

		if (intent == null) {

			intent = new Intent();
		}

		intent.setClass(context, FrameActivity.class);
		intent.putExtra("jumpTo", fragment.getName());
		this.startActivity(intent);
	}

	/** 启动一个公用Activity，以显示fragment */
	public void startFrameActivityForResult(Class<? extends UsualFragment> fragment,
			int requestCode) {

		this.startFrameActivityForResult(fragment, null, requestCode);
	}

	/** 启动一个公用Activity */
	public void startFrameActivityForResult(Class<? extends UsualFragment> fragment, Intent intent,
			int requestCode) {

		// Activity为空时不能跳转
		if (getActivity() == null) {

			return;
		}

		if (intent == null) {

			intent = new Intent();
		}

		intent.setClass(getActivity(), FrameActivity.class);
		intent.putExtra("jumpTo", fragment.getName());

		this.startActivityForResult(intent, requestCode);
	}

	@Override
	public void startActivity(Intent intent) {

		// Activity为空时不能跳转
		if (getActivity() == null) {

			return;
		}

		// 若允许跳转
		if (mSingleJumpHelper.onBeforeStartActivity()) {

			super.startActivity(intent);
		}
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {

		// Activity为空时不能跳转
		if (getActivity() == null) {

			return;
		}

		// 若允许跳转
		if (mSingleJumpHelper.onBeforeStartActivity()) {

			super.startActivityForResult(intent, requestCode);
		}
	}

	@Override
	public void onDestroy() {

		mKeyDispatchHelper.onDestroy();
		mTouchDispatchHelper.onDestroy();

		// 注销事件通知
		this.unregisterEventBus();

		super.onDestroy();
	}

	/** 结束当前Acivity */
	public void finish() {

		if (getActivity() != null) {

			getActivity().finish();
		}
	}

	/** 当前布局显示哪个视图节点上 */
	public int getLayoutId() {
		return mLayoutId;
	}

	/** 当前布局显示哪个视图节点上 */
	public void setLayoutId(int layoutId) {
		mLayoutId = layoutId;
	}

	/** 取得自身对象 */
	@Override
	public UsualFragment getUsualFragment() {
		return this;
	}

	/** 是否自动失去焦点，若是，当触摸到空白区域时，则自动失去焦点 */
	public void setIsAutoClearForce(boolean isAutoClearForce) {

		mAutoHideInputHelper.mIsAutoClearForce = isAutoClearForce;
	}

	/** 是否自动隐藏输入法，若是，当触摸到空白区域时，则隐藏输入法 */
	public void setIsAutoHideInput(boolean isAutoHideInput) {

		mAutoHideInputHelper.mIsAutoHideInput = isAutoHideInput;
	}

	/** 是否一次只允许跳转到一个界面，若设置为true，在请求跳转到其他Activiy后，只有等onResume之后才能跳转到其他界面 */
	public void setIsSingleJump(boolean isSingleJump) {

		mSingleJumpHelper.mIsSingleJump = isSingleJump;
	}

	/** 注册成为接收返回值的界面，会清空之前的接收界面 */
	public void registerForResult() {

		Activity activity = getActivity();
		if (activity != null && activity instanceof UsualActivity) {

			((UsualActivity) activity).setForResultFragment(getUsualFragment());
		}
	}

	/** 按键事件 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		return mKeyDispatchHelper.onKeyDown(keyCode, event);
	}

	/** 设置返回值监听器，全局只有一个值，每次使用后就失效 */
	public void setResultListener(OnActivityResultListener listener) {

		mResultHandleHelper.setResultListener(listener);
	}

	/** 触摸事件，所有UsualFrame都接收到此事件，仅接收事件，不负责事件冒泡 */
	@Override
	public void onDispatchTouchEvent(MotionEvent event) {

		// 传递事件
		mAutoHideInputHelper.onDispatchTouchEvent(event);
		mTouchDispatchHelper.onDispatchTouchEvent(event);
	}

	/** 注册触摸事件 */
	public void registerTouchListener(OnDispatchTouchEventListener listener) {

		mTouchDispatchHelper.registerTouchListener(listener);
	}

	/** 注销触摸事件 */
	public void removeTouchListener(OnDispatchTouchEventListener listener) {

		mTouchDispatchHelper.removeTouchListener(listener);
	}

	/** 注册按键事件 */
	public void registerKeyListener(OnKeyListener listener) {

		mKeyDispatchHelper.registerKeyListener(listener);
	}

	/** 注销按键事件 */
	public void removeKeyListener(OnKeyListener listener) {

		mKeyDispatchHelper.removeKeyListener(listener);
	}

	/**
	 * 添加子fragment
	 *
	 * @param layoutId
	 *            要显示到的节点
	 * @param child
	 *            子fragment
	 */
	public void addChildFragment(int layoutId, UsualFragment child) {

		// 加入视图
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		child.setLayoutId(layoutId);
		ft.add(layoutId, child);
		ft.commit();

		// 注册事件
		this.registerKeyListener(child);
		this.registerTouchListener(child);
	}

	/** 对话框辅助工具 */
	private static class LoadingHelper {

		/** 引用主界面 */
		private UsualFragment mFragment;

		/** 加载对话框 */
		private LoadingDialog mLoadingDialog;

		/** 当前显示的加载对话框个数 */
		private int mLoadingDialogCount;

		/** 构造函数 */
		public LoadingHelper(UsualFragment fragment) {
			mFragment = fragment;
		}

		/** 显示加载框 */
		public void showLoadingDialog() {

			if (mFragment.getActivity() == null) {

				return;
			}

			mLoadingDialogCount++;

			if (mLoadingDialog == null) {

				mLoadingDialog = new LoadingDialog(mFragment.getActivity());
				mLoadingDialog.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface dialog) {

						if (mLoadingDialog != null && mLoadingDialog.getDialog() == dialog) {

							mLoadingDialogCount = 0;
							mLoadingDialog = null;
						}
					}
				});
			}

			if (mLoadingDialog != null) {

				mLoadingDialog.showDialog();
			}
		}

		/** 取消加载框 */
		public void dismissLoadingDialog() {

			mLoadingDialogCount--;

			if (mLoadingDialogCount <= 0) {

				if (mLoadingDialog != null) {

					mLoadingDialog.dismissDialog();
					mLoadingDialog = null;
				}
			}
		}
	}

	/** 按键事件分发，辅助工具 */
	private static class KeyDispatchHelper {

		/** 引用主界面 */
		private UsualFragment mFragment;

		/** 构造函数 */
		public KeyDispatchHelper(UsualFragment fragment) {
			mFragment = fragment;
		}

		/** 事件分发 */
		private List<OnKeyListener> mKeyListeners = new ArrayList<OnKeyListener>();

		/** 清除所有事件 */
		public void onDestroy() {

			Fragment parent = mFragment.getParentFragment();

			// 清空监听事件
			if (parent instanceof UsualFragment) {

				UsualFragment parentUsualFragment = (UsualFragment) parent;
				parentUsualFragment.removeKeyListener(mFragment);
			}

			mKeyListeners.clear();
		}

		/** 某键被按下 */
		public boolean onKeyDown(int keyCode, KeyEvent event) {

			if (mFragment.isVisible()) {

				// 倒序遍历，传递事件
				for (int i = mKeyListeners.size() - 1; i >= 0; i--) {

					if (mKeyListeners.get(i).onKeyDown(keyCode, event)) {

						return true;
					}
				}
			}

			return false;
		}

		/** 监听按键事件 */
		public void registerKeyListener(OnKeyListener listener) {

			if (listener != null) {

				mKeyListeners.add(listener);
			}
		}

		/** 移除按键事件 */
		public void removeKeyListener(OnKeyListener listener) {

			if (listener != null) {

				mKeyListeners.remove(listener);
			}
		}
	}

	/** 触摸事件分发，辅助工具 */
	private static class TouchDispatchHelper {

		/** 引用主界面 */
		private UsualFragment mFragment;

		/** 触摸事件，不冒泡 */
		private List<OnDispatchTouchEventListener> mTouchListeners = new ArrayList<OnDispatchTouchEventListener>();

		/** 构造函数 */
		public TouchDispatchHelper(UsualFragment fragment) {
			mFragment = fragment;
		}

		/** 清空注册事件 */
		public void onDestroy() {

			Fragment parent = mFragment.getParentFragment();

			// 清空监听事件
			if (parent instanceof UsualFragment) {

				UsualFragment parentUsualFragment = (UsualFragment) parent;
				parentUsualFragment.removeTouchListener(mFragment);
			}

			mTouchListeners.clear();
		}

		/** 分发触摸事件 */
		public void onDispatchTouchEvent(MotionEvent event) {

			for (int i = mTouchListeners.size() - 1; i >= 0; i--) {

				mTouchListeners.get(i).onDispatchTouchEvent(event);
			}
		}

		/** 注册触摸事件监听 */
		public void registerTouchListener(OnDispatchTouchEventListener listener) {

			if (listener != null) {

				mTouchListeners.add(listener);
			}
		}

		/** 移除触摸事件监听 */
		public void removeTouchListener(OnDispatchTouchEventListener listener) {

			if (listener != null) {

				mTouchListeners.remove(listener);
			}
		}
	}

	/** 自动隐藏输入法，辅助工具 */
	private static class AutoHideInputHelper {

		/** 引用主界面 */
		private UsualFragment mFragment;

		/** 是否自动失去焦点，若是，当触摸到空白区域时，则自动失去焦点 */
		private boolean mIsAutoClearForce;

		/** 是否自动隐藏输入法，若是，当触摸到空白区域时，则隐藏输入法 */
		private boolean mIsAutoHideInput;

		/** 构造函数 */
		public AutoHideInputHelper(UsualFragment fragment) {

			mFragment = fragment;
		}

		/** 主界面被触摸了 */
		public void onDispatchTouchEvent(MotionEvent event) {

			// 当前界面是显示的情况下，隐藏软键盘
			if (!mFragment.isHidden() && event.getAction() == MotionEvent.ACTION_DOWN) {

				this.tryHideInput(event);
			}
		}

		/** 尝试隐藏软键盘 */
		private void tryHideInput(MotionEvent ev) {

			if (mFragment.getActivity() != null) {

				View view = mFragment.getActivity().getCurrentFocus();

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
				InputMethodManager manager = (InputMethodManager) mFragment.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	/** 保证同一界面只跳转一次，直到界面恢复了 */
	private static class SingleJumpHelper {

		/** 是否一次只允许跳转到一个界面，若设置为true，在请求跳转到其他Activiy后，只有等onResume之后才能跳转到其他界面 */
		private boolean mIsSingleJump = true;

		/** 当前是否调用onResume了 */
		private boolean mIsResume = true;

		/**
		 *
		 */
		public void onResume() {

			// 记录为界面已经恢复了
			mIsResume = true;
		}

		/**
		 * @param requestCode
		 * @param resultCode
		 * @param data
		 */
		public void onActivityResult(int requestCode, int resultCode, Intent data) {

			// 记录为界面已经恢复了
			// 因为此方法在onResume之前，为保证在此方法中可以跳转到其他界面，这里也需要记录
			mIsResume = true;
		}

		/** 跳转界面前置代码，若返回true，则可以继续跳转；若返回false，则不能跳转 */
		public boolean onBeforeStartActivity() {

			// 若定义了单一跳转，且当前界面还未恢复，不允许跳转
			if (mIsSingleJump && !mIsResume) {

				return false;
			}

			// 跳转时标记为未恢复的界面
			mIsResume = false;

			return true;
		}
	}

	/** 转场动画辅助工具 */
	private static class AnimationHelper {

		/** 引用主界面 */
		private UsualFragment mFragment;

		/** 构造函数 */
		public AnimationHelper(UsualFragment fragment) {

			mFragment = fragment;
		}

		/** 设置下个跳转将使用的动画 */
		public void setNextActivityAnimationType(AnimationType animationType) {

			if (mFragment.getActivity() instanceof UsualActivity) {

				UsualActivity activity = (UsualActivity) mFragment.getActivity();

				if (activity != null) {

					activity.setNextActivityAnimationType(animationType);
				}
			}
		}

		/** 设置入场动画，因为此界面已经加载了，不会影响入场动画，但是会影响相应的出场动画 */
		public void setEnterAnimationType(AnimationType animationType) {

			if (mFragment.getActivity() instanceof UsualActivity) {

				UsualActivity activity = (UsualActivity) mFragment.getActivity();

				if (activity != null) {

					activity.setEnterAnimationType(animationType);
				}
			}
		}
	}

	/** 恢复时刷新 */
	private static class RefreshOnResume {

		/** 对应的界面 */
		private UsualFragment mFragment;

		/** 是否在恢复时刷新 */
		private boolean mIsRefreshOnResume;

		/** 构造函数 */
		public RefreshOnResume(UsualFragment fragment) {
			mFragment = fragment;
		}

		/** 尝试刷新，若正在当前界面，则直接刷新，若没在当前界面，则恢复时刷新 */
		public void tryRefershOnResume() {

			if (mFragment.isResumed()) {

				refreshNow();

			} else {

				mIsRefreshOnResume = true;
			}
		}

		/** 界面恢复时调用 */
		public void onResume() {

			if (mIsRefreshOnResume) {
				refreshNow();

				mIsRefreshOnResume = false;
			}
		}

		/** 立即刷新 */
		private void refreshNow() {

			mFragment.onResumeRefresh();
		}
	}

	/** 销毁其他界面，辅助工具 */
	private static class DestoryUiHelper {

		/** 对应的界面 */
		private UsualFragment mFragment;

		/** 构造函数 */
		public DestoryUiHelper(UsualFragment fragment) {
			mFragment = fragment;
		}

		/**
		 * 销毁所有界面
		 *
		 * @param isKeeySelf
		 *            是否保留当前界面
		 */
		private void destoryAllUis(boolean isKeeySelf) {

			destoryOtherUis(isKeeySelf ? mFragment.getClass() : null);
		}

		/** 销毁所有其他界面 */
		private void destoryOtherUis(Class<? extends UsualFragment> keepClass) {

			// 收集除指定界面外的所有界面
			OnCollectiActivityEvent collectiActivityEvent = new OnCollectiActivityEvent();
			if (keepClass != null) {

				collectiActivityEvent.addIgnoreClasses(keepClass);
			}
			EventBus.getDefault().post(collectiActivityEvent);

			// 销毁指定界面
			for (Activity activity : collectiActivityEvent.getActivities()) {

				activity.finish();
			}

			collectiActivityEvent.clear();
		}
	}

	/** 监听结果事件 */
	private static class ResultHandleHelper {

		/** 返回值监听器 */
		private OnActivityResultListener mResultListener;

		/** 设置返回值监听器 */
		public void setResultListener(OnActivityResultListener listener) {

			mResultListener = listener;
		}

		/** 界面返回了 */
		public void onActivityResult(int requestCode, int resultCode, Intent data) {

			if (mResultListener != null) {

				mResultListener.onActivityResult(requestCode, resultCode, data);
				mResultListener = null;
			}
		}

		/** 界面恢复了 */
		public void onResume() {

			mResultListener = null;
		}
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		LogUtils.i("UsualFragment", "onRequestPermissionsResult ：requestCode=" + requestCode);
		if(mPermissionResultListener != null){
			mPermissionResultListener.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}
}
