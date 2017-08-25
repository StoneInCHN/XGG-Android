package com.hentica.app.widget.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hentica.app.lib.util.CharacterParser;
import com.hentica.app.lib.util.TextGetter;
import com.hentica.app.widget.view.SideBar.OnTouchingLetterChangedListener;

/**
 * 字母表头辅助工具<br />
 * 
 * 用法:<br />
 * 
 * 1.绑定视图:<br />
 * helper.setViews(listView, sideBar, tipText); <br />
 * <br />
 * 
 * 2.设置数据转换规则，用于取拼音和排序<br />
 * helper.setTextGetter(xxx);<br />
 * <br />
 * 
 * 3.设置原始数据，并取得排序后的数据 <br />
 * helper.setPeccancyDatas(mShowDatas); <br />
 * mShowDatas = helper.getSortedDatas(); <br />
 * <br />
 * 
 * 4.取数据<br />
 * 在getView()中调用 helper.refreshLetterView(position, letterView);
 * 
 * @author mili
 * @createTime 2016-6-15 下午3:53:05
 */
public class CharacterHeadHelper<T> {

	/** 原始数据 */
	protected List<T> mDatas;

	/** 排序后的数据 */
	protected List<T> mSortedDatas = new ArrayList<T>();

	/** 默认字符串转换规则 */
	protected TextGetter<T> mDefaultTextGetter = new TextGetter<T>() {

		@Override
		public String getText(T obj) {
			return obj.toString();
		}
	};

	/** 字符串转换规则 */
	protected TextGetter<T> mTextGetter = mDefaultTextGetter;

	/** 拼音缓存 */
	protected Map<String, Character> mCharCacheMap = new HashMap<String, Character>(30);

	/** 下拉刷新列表 */
	protected ListView mListView;

	/** 字母导航 */
	protected SideBar mSideBar;

	/** 正中间提示文字 */
	protected TextView mSelectTipText;

	/**
	 * 绑定字母列表视图
	 * 
	 * @param listView
	 *            列表
	 * @param sideBar
	 *            拼音列表
	 * @param tipText
	 *            提示文字
	 */
	public void bindViews(ListView listView, SideBar sideBar, TextView tipText) {

		mListView = listView;
		mSideBar = sideBar;
		mSelectTipText = tipText;

		this.initView();
		this.setEvent();
	}

	/** 设置数据 */
	public void setDatas(List<T> datas) {

		mDatas = datas;
	}

	/** 设置字母转换规则，用于取字母头并排序 */
	public void setTextGetter(TextGetter<T> textGetter) {

		mTextGetter = textGetter;

		if (mTextGetter == null) {
			mTextGetter = mDefaultTextGetter;
		}
	}

	/** 取得排序后的数据 */
	public List<T> getSortedDatas() {

		this.createSortData(mDatas, mTextGetter);
		return mSortedDatas;
	}

	/**
	 * 刷新字母视图，在adapter的getView中调用
	 * 
	 * @param position
	 *            位置
	 * @param letterTextView
	 *            显示字母的TextView
	 */
	public void refreshLetterView(int position, TextView letterTextView) {

		this.refreshLetterView(position, letterTextView, letterTextView);
	}

	/**
	 * 刷新字母视图，在adapter的getView中调用
	 * 
	 * @param position
	 *            位置
	 * @param letterTextView
	 *            显示字母的TextView
	 * @param letterLayout
	 *            字母头布局
	 */
	public void refreshLetterView(int position, TextView letterTextView, View letterLayout) {

		if (letterTextView != null) {

			// 数据
			boolean isShowLetter = this.isFirstLetter(position);
			char letter = this.getLetterAt(position);
			// 字母布局
			if (letterLayout != null) {

				letterLayout.setVisibility(isShowLetter ? View.VISIBLE : View.GONE);
			}
			// 字母头
			if (isShowLetter) {

				letterTextView.setText(letter + "");
			}
		}
	}

	/** 某位置是否是字母首次出现的问题 */
	public boolean isFirstLetter(int pos) {

		if (pos >= 0 && pos < mSortedDatas.size()) {

			T data = mSortedDatas.get(pos);
			int section = getLetterSection(getLetter(data));

			return section == pos;
		}

		return false;
	}

	/** 取得某位置对应的字母 */
	protected char getLetterAt(int pos) {

		if (mSortedDatas != null && pos >= 0 && pos < mSortedDatas.size()) {

			return getLetter(mSortedDatas.get(pos));
		}
		return '#';
	}

	private void initView() {

		if (mSideBar != null && mSelectTipText != null) {

			mSideBar.setTextView(mSelectTipText);
		}
	}

	private void setEvent() {

		if (mSideBar != null) {

			// 设置右侧触摸监听
			mSideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

				@Override
				public void onTouchingLetterChanged(String s) {

					// 该字母首次出现的位置
					int position = getLetterSection(s.charAt(0));

					// 跳转到指定位置
					if (position != -1) {
						int headerCount = mListView.getHeaderViewsCount();
						// 获取焦点
						mListView.requestFocusFromTouch();
						mListView.setSelection(position + headerCount);

						// 停止滚动
						mListView.smoothScrollBy(0, 0);
					}
				}
			});
		}
	}

	/** 创建排序后的数据 */
	protected void createSortData(List<T> datas, TextGetter<T> textGetter) {

		mSortedDatas.clear();
		if (datas != null) {

			mSortedDatas.addAll(datas);
		}
		if (textGetter != null) {

			Collections.sort(mSortedDatas, new PinyinComparator<T>(textGetter));
		}
	}

	/** 取得某字母首次出现的位置 */
	protected int getLetterSection(char letter) {

		for (int i = 0, size = mSortedDatas.size(); i < size; i++) {

			T data = mSortedDatas.get(i);
			char dataLetter = getLetter(data);

			if (letter == dataLetter) {
				return i;
			}
		}

		return -1;
	}

	/** 取得某数据的首字母 */
	protected char getLetter(T data) {

		if (mTextGetter != null) {

			String name = mTextGetter.getText(data);
			Character cacheChar = mCharCacheMap.get(name);

			// 若未缓存过
			if (cacheChar == null) {

				String pinyin = CharacterParser.getInstance().getSelling(name);

				if (!TextUtils.isEmpty(pinyin)) {

					char letter = pinyin.substring(0, 1).toUpperCase().charAt(0);
					mCharCacheMap.put(name, letter);
					return letter;
				}
			}
			// 若有缓存
			else {
				return cacheChar;
			}
		}

		return '#';
	}

	/** 比较 */
	private static class PinyinComparator<V> implements Comparator<V> {

		private TextGetter<V> mTextGetter;

		/** 构造函数 */
		public PinyinComparator(TextGetter<V> textGetter) {
			mTextGetter = textGetter;
		}

		@Override
		public int compare(V o1, V o2) {

			String o1Text = mTextGetter.getText(o1);
			String o2Text = mTextGetter.getText(o2);

			String pinyin1 = CharacterParser.getInstance().getSelling(o1Text);
			String pinyin2 = CharacterParser.getInstance().getSelling(o2Text);

			return pinyin1.compareTo(pinyin2);
			// String o1Char = (!TextUtils.isEmpty(o1Text) ? o1Text.charAt(0) +
			// "" : "");
			// String o2Char = (!TextUtils.isEmpty(o2Text) ? o2Text.charAt(0) +
			// "" : "");
			// if (o1Char.equals("@") || o2Char.equals("#")) {
			// return -1;
			// } else if (o1Char.equals("#") || o2Char.equals("@")) {
			// return 1;
			// } else {
			// }
		}
	}

}
