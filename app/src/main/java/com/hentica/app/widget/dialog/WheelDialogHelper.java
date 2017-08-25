package com.hentica.app.widget.dialog;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.hentica.app.lib.util.TextGetter;
import com.hentica.app.widget.dialog.SingleWheelDialog.OnSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 滚轮辅助工具    SingleWheelDialog
 * 
 * @author mili
 * @createTime 2016-6-13 下午8:20:47
 */
public class WheelDialogHelper<T> {

	/** 滚轮选中监听器 */
	public interface OnWheelSelectedListener<T> {

		/**
		 * 被选中了
		 * 
		 * @param index
		 *            选中号
		 * @param showText
		 *            选中的显示文本
		 * @param data
		 *            选中的数据
		 */
		public void onSelected(int index, String showText, T data);
	}

	/** 所有要显示的数据 */
	private List<T> mDatas;

	/** 当前选中的数据 */
	private T mSelectedData;

	/** 用于把数据到转换为显示文本 */
	private TextGetter<T> mTextGetter;
	
	/** 用于显示对话框 */
	private FragmentManager mFragmentManager;

	/** 对话框标记 */
	private String mTag;

	/** 显示选中值 */
	private TextView mShowTextView;

	/** 监听选中事件 */
	private OnWheelSelectedListener<T> mListener;

	/** 构造函数 */
	public WheelDialogHelper(FragmentManager fragmentManager) {

		mFragmentManager = fragmentManager;
		mTag = "单选对话框";
	}

	/** 构造函数 */
	public WheelDialogHelper(FragmentManager fragmentManager, String tag) {

		mFragmentManager = fragmentManager;
		mTag = tag;
	}

	/** 设置显示数据 */
	public void setDatas(List<T> datas) {

		mDatas = datas;
	}

	/** 设置文字转换规则 */
	public void setTextGetter(TextGetter<T> textGetter) {

		mTextGetter = textGetter;
	}

	/** 设置选中项，请在显示前调用 */
	public void setSelectedData(T data) {

		mSelectedData = data;
	}

	/** 取得显示数据 */
	public T getSelectedData() {

		return mSelectedData;
	}

	/** 监听选中事件 */
	public void setListener(OnWheelSelectedListener<T> listener) {
		mListener = listener;
	}

	
	/**
	 * 绑定视图，以触发弹出事件
	 * 
	 * @param button
	 *            点击button弹出对话框
	 * @param showView
	 *            显示选中的值
	 */
	public void setEventView(View button, TextView showView) {

		mShowTextView = showView;
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialog();
			}
		});
	}

	/** 显示对话框 */
	public void showDialog() {

		this.createDefaultTextGetter();
		int size = (mDatas != null ? mDatas.size() : 0);

		List<WheelBaseData<T>> datas = new ArrayList<WheelBaseData<T>>(size);
		for (T data : mDatas) {

			String showText = mTextGetter.getText(data);
			datas.add(new WheelBaseData<T>(showText, data));
		}

		SingleWheelDialog<T> dialog = new SingleWheelDialog<T>();
		dialog.setDatas(datas);
		dialog.setDefaultSelected(mSelectedData);
		dialog.setListener(new OnSelectedListener<T>() {

			@Override
			public void getSelected(WheelBaseData<T> data, int index) {

				setSelectedData(data.getData());

				if (mShowTextView != null) {
					mShowTextView.setText(data.getShowText());
					mShowTextView.setTag(data.getData());
				}

				// 透传事件
				if (mListener != null) {

					mListener.onSelected(index, data.getShowText(), data.getData());
				}
			}
		});

		dialog.show(mFragmentManager, mTag);
	}

	/** 创建默认取数据工具 */
	private void createDefaultTextGetter() {

		if (mTextGetter == null) {

			mTextGetter = new TextGetter<T>() {

				@Override
				public String getText(T obj) {
					return obj.toString();
				}
			};
		}
	}
}
