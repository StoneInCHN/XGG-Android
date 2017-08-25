package com.hentica.appbase.famework.adapter;


import android.view.View;
import android.view.ViewGroup;

import com.hentica.app.appbase.R;


/**
 * 带ViewHolder的Adapter
 * 
 * @author mili
 * @createTime 2016年7月8日 上午11:30:04
 */
public abstract class QuickViewHolderAdapter<T, H> extends QuickAdapter<T> {

	@Override
	final protected void initView(View convertView) {

		H viewHolder = initAndHolder(convertView);
		convertView.setTag(R.id.viewPositionTag, viewHolder);
	}

	@Override
	final public void fillView(int position, View convertView, ViewGroup parent, T data) {

		fillView(position, convertView, parent, data, getHolderOf(convertView));
	}

	/** 取得某视图对应的holder，若无则返回null */
	@SuppressWarnings("unchecked")
	final protected H getHolderOf(View convertView) {

		return (H) convertView.getTag(R.id.viewPositionTag);
	}

	/** 初始化界面，每个view只会调用一次 */
	protected abstract H initAndHolder(View convertView);

	/** 填充视图 */
	protected abstract void fillView(int position, View convertView, ViewGroup parent, T data,
			H holder);
}
