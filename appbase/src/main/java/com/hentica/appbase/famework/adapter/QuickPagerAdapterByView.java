package com.hentica.appbase.famework.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.hentica.app.appbase.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 可快速使用的pagerAdapter
 * 
 * @author mili
 * @createTime 2016年7月8日 下午12:33:54
 */
public abstract class QuickPagerAdapterByView<T> extends PagerAdapter {

	/** 点击事件 */
	public static interface OnPageItemClickedListener<T> {

		/**
		 * 某节点被点击
		 * 
		 * @param position
		 * @param data
		 */
		public void onItemClicked(int position, T data);
	}

	/** 所有数据 */
	private List<T> mDatas = new ArrayList<T>();

	/** 所有视图 */
	private SparseArray<View> mViews2 = new SparseArray<View>();

	/** 点击事件 */
	private OnPageItemClickedListener<T> mOnPageItemClickedListener;

	/** 点击事件 */
	public void setOnPageItemClickedListener(
			OnPageItemClickedListener<T> onPageItemClickedListener) {
		mOnPageItemClickedListener = onPageItemClickedListener;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		View convertView = mViews2.get(position);
		convertView = this.getView(position, convertView, container);
		mViews2.put(position, convertView);

		container.addView(convertView);
		return convertView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		View view = mViews2.get(position);
		(container).removeView(view);
	}

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

		List<T> result = new ArrayList<T>();
		if (mDatas != null) {

			result.addAll(mDatas);
		}
		return result;
	}

	/** 移除指定数据 */
	public void remove(T data) {

		if (data != null) {

			mDatas.remove(data);
		}
	}

	/** 移除指定数据 */
	public void remove(Collection<? extends T> datas) {

		if (datas != null) {

			mDatas.remove(datas);
		}
	}

	/** 移除指定数据 */
	public void remove(int position) {

		if (mDatas != null && position >= 0 && position < mDatas.size()) {

			mDatas.remove(position);
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
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public T getItem(int position) {
		return mDatas.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	private View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			convertView = onCreateView(parent, 0);

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

	/**
	 * 创建view
	 * 
	 * @param parent
	 *            父节点
	 * @param type
	 *            类型
	 * @return
	 */
	protected abstract View onCreateView(ViewGroup parent, int type);

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
	protected void bindEvent(final View convertView) {

		// 点击事件
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mOnPageItemClickedListener != null) {

					mOnPageItemClickedListener.onItemClicked(getPositionOf(convertView),
							getDataOf(convertView));
				}
			}
		});
	}
}
