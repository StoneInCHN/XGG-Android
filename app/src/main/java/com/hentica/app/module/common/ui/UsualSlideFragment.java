package com.hentica.app.module.common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.hentica.app.util.FragmentFlipHelper;
import com.hentica.app.util.FragmentFlipHelper.FragmentInfo;
import com.hentica.app.framework.fragment.UsualFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用滑动界面
 * 
 * @author mili
 * @createTime 2016-3-17 上午10:57:29
 */
public abstract class UsualSlideFragment extends UsualFragment {

	/** 滑动界面辅助工具 */
	private FragmentFlipHelper mSlideHelper;

	/** 按下返回键时，是否返回到默认界面 */
	private boolean mIsBackToDefault;

	/** 默认显示的界面，从0开始 */
	private int mDefaultIndex;

	/** viewPager视图id */
	abstract protected int getViewPagerId();

	/** 单选项视图id */
	abstract protected int getRadioGroupId();

	/** 滑块视图id，若无则返回0 */
	protected int getSlideViewId() {
		return 0;
	};

	/**
	 * 填写所有界面信息，添加到指定数据中
	 * 
	 * @param outFragmentInfos
	 *            添加到这个数组中
	 */
	abstract protected void fillFragmentInfos(List<FragmentInfo> outFragmentInfos);

	/** 默认显示第几个界面，从0开始计数 */
	abstract protected int getDefaultIndex();

	/** 切换到某界面了 */
	protected void onFragmentSwitched(FragmentInfo fragmentInfo) {
	}

	/** 按下返回键时，是否返回到默认界面 */
	public boolean isIsBackToDefault() {
		return mIsBackToDefault;
	}

	/** 按下返回键时，是否返回到默认界面 */
	public void setIsBackToDefault(boolean isBackToDefault) {
		mIsBackToDefault = isBackToDefault;
	}

	/** 设置当前显示界面 */
	public void setCurrItem(int position) {

		mSlideHelper.setCurrIndex(position);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// 销毁之前创建的fragment
		this.tryDestoryFragments(savedInstanceState);

		// 初始化界面
		this.initView();
		this.setEvent();
	}

	/** 初始化界面 */
	private void initView() {

		// 默认选中项
		mDefaultIndex = this.getDefaultIndex();

		// 创建辅助对象
		mSlideHelper = new FragmentFlipHelper(this, getViewPagerId(), getRadioGroupId(),
				getSlideViewId(), getDefaultIndex());

		// 获取各个子界面
		List<FragmentInfo> fragmentInfos = new ArrayList<FragmentInfo>();
		this.fillFragmentInfos(fragmentInfos);

		// 添加子界面到滑块辅助类中
		for (FragmentInfo fragmentInfo : fragmentInfos) {

			mSlideHelper.addFragmentInfo(fragmentInfo);
		}

		// 创建视图
		mSlideHelper.createFragments();
	}

	/** 设置事件 */
	private void setEvent() {

		// 切换事件
		mSlideHelper.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				// 透传事件
				FragmentInfo fragmentInfo = mSlideHelper.getCurrFragmentInfo();

				if (fragmentInfo != null && fragmentInfo.mFragment != null) {

					fragmentInfo.mFragment.onSwitched();
				}
				onFragmentSwitched(fragmentInfo);
			}
		});
	}

	/** 尝试销毁以前创建的界面 */
	private void tryDestoryFragments(Bundle savedInstanceState) {

		// 有可能是重启，这时fragment没销毁，要销毁后重新来
		// 若是首次启动
		if (savedInstanceState == null) {
		}
		// 若是重启
		else {

			// 销毁已添加的fragment
			String[] saved = savedInstanceState.getStringArray("showedTags");

			if (saved != null) {

				FragmentManager fm = this.getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();

				for (String tag : saved) {

					ft.remove(fm.findFragmentByTag(tag));
				}
				ft.commit();
			}
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		List<FragmentInfo> allChildFragmentInfos = mSlideHelper.getAllChildFragmentInfos();

		// 保存所有已显示的界面的tag，下次启动时销毁
		if (allChildFragmentInfos != null) {
			String[] save = new String[allChildFragmentInfos.size()];

			for (int i = 0; i < allChildFragmentInfos.size(); i++) {

				FragmentInfo info = allChildFragmentInfos.get(i);
				save[i] = info.mTag;
			}

			outState.putStringArray("showedTags", save);
		}

		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (mSlideHelper.onKeyDown(keyCode, event)) {

			return true;
		}

		// 返回键
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			// 若需要返回到默认界面
			if (mIsBackToDefault) {

				if (mSlideHelper.getCurrIndex() != mDefaultIndex) {
					// 切换到首页

					mSlideHelper.setCurrIndex(mDefaultIndex);
					return true;
				}
			}
		}

		return super.onKeyDown(keyCode, event);
	}
}
