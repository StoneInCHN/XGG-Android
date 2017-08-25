package com.hentica.appbase.famework.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author mili
 * @createTime 2016年7月8日 下午12:33:54
 */
public abstract class QuickPagerAdapter<T> extends QuickPagerAdapterByView<T> {

	/** 取得布局文件 */
	protected abstract int getLayoutRes(int type);

	@Override
	final protected View onCreateView(ViewGroup parent, int type) {
		return View.inflate(parent.getContext(), getLayoutRes(type), null);
	}
}
