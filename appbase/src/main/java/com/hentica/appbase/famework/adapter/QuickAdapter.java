package com.hentica.appbase.famework.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hentica.app.appbase.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 快速设置的adapter，绑定一个list
 * 
 * @author mili
 * @createTime 2016-6-30 下午3:52:12
 */
public abstract class QuickAdapter<T> extends BaseAdapter {

	/** 所有数据 */
	private List<T> mDatas = new ArrayList<T>();

	/** 添加一条数据 */
	public void add(T obj) {

		if (obj != null) {

			mDatas.add(obj);
			notifyDataSetChanged();
		}
	}

	/** 批量添加数据 */
	public void addAll(Collection<? extends T> datas) {

		if (datas != null) {

			mDatas.addAll(datas);
			notifyDataSetChanged();
		}
	}

	/** 设置所有要显示的数据 */
	public void setDatas(Collection<? extends T> datas) {

		mDatas.clear();
		if (datas != null) {

			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}

	/** 取得所有数据 */
	public List<T> getDatas() {

		return new ArrayList<>(mDatas);
	}

	/** 移除指定数据 */
	public void remove(T data) {

		if (data != null) {

			mDatas.remove(data);
			notifyDataSetChanged();
		}
	}

	/** 移除指定数据 */
	public void remove(Collection<? extends T> datas) {

		if (datas != null) {

			mDatas.remove(datas);
			notifyDataSetChanged();
		}
	}

	/** 移除指定数据 */
	public void remove(int position) {

		if (mDatas != null && position >= 0 && position < mDatas.size()) {

			mDatas.remove(position);
			notifyDataSetChanged();
		}
	}

	/** 清空所有数据 */
	public void clear() {

		mDatas.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	final public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			int type = getItemViewType(position);
//			convertView = View.inflate(parent.getContext(), getLayoutRes(type), null);
			convertView = LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(type), parent, false);

			// 记录view的位置
			setViewPosition(convertView, position);

			// 初始化界面
			initView(convertView);

			// 设置事件
			bindEvent(convertView);
		}

		// 记录view的位置
		setViewPosition(convertView, position);

		// 填充界面
		fillView(position, convertView, parent, getItem(position));

		return convertView;
	}

	/**
	 * 记录view的位置
	 * 
	 * @param view
	 * @param position
	 */
	final protected void setViewPosition(View view, int position) {

		view.setTag(R.id.viewPositionTag, position);
	}

	/** 取得某视图对应的位置，若无则返回-1 */
	final protected int getPositionOf(View convertView) {

		Integer pos = (Integer) convertView.getTag(R.id.viewPositionTag);
		return pos != null ? pos : -1;
	}

	/** 取得某视图对应的数据，若无则返回null */
	final protected T getDataOf(View convertView) {

		int pos = this.getPositionOf(convertView);

		if (pos != -1) {

			return this.getItem(pos);
		}
		return null;
	}

	/** 取得布局文件 */
	protected abstract int getLayoutRes(int type);

	/**
	 * 填充界面
	 * 
	 * @param position
	 *            位置
	 * @param convertView
	 *            视图
	 * @param data
	 *            对应位置的数据
	 * @return
	 */
	protected abstract void fillView(int position, View convertView, ViewGroup parent, T data);

	/** 初始化界面，每个view只会调用一次 */
	protected void initView(View convertView) {
	}

	/** 设置事件，只会调用一次，可在事件中使用 getPositionOf(view) 来获取当前位置 */
	protected void bindEvent(View convertView) {
	}
}
