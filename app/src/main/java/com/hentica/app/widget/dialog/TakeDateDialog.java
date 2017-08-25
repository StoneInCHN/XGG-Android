package com.hentica.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;


public class TakeDateDialog extends DialogFragment implements OnWheelScrollListener {
	private WheelView wheel1;
	private WheelView wheel2;
	private WheelView wheel3;

	private TimeAdapter adapter1;
	private TimeAdapter adapter2;
	private TimeAdapter adapter3;

	private int selectedSize = 14;
	private int unSelectedSize = 12;

//	private Calendar mCalendar;
	private AQuery query;

	private int currentYear = 0;// 当前年
	private int currentMonth = 0;// 当前月份
	private int currentDay = 0;// 当前天

	private List<Integer> mYears;
	private List<String> mYearsStr;
	private List<Integer> mMonths;
	private List<String> mMonthesStr;
	private List<Integer> mDays;
	private List<String> mDaysStr;

	private int mYearStart = 1900;
	private int mYearEnd = 3000;
	private int mMonthStart = 1;
	private int mMonthEnd = 12;
	private int mDayStart = 1;
	private int mDayEnd = 31;

	private OnTakeDateListener mListener;

	public interface OnTakeDateListener {
		void takeTime(int year, int month, int day);
	}

	public void setListener(OnTakeDateListener listener) {
		this.mListener = listener;
	}

	/**
	 * 设置初始显示时间
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	public void setLastAsDefaultTime(int year, int month, int day) {
		currentYear = year;
		currentMonth = month;
		currentDay = day;
	}

	public void setLastAsDefaultTime() {
		currentYear = mYearEnd;
		currentMonth = mMonthEnd;
		currentDay = mDayEnd;
	}

	/**
	 * 设置默认显示时间是否为当前时间
	 * 
	 * @param isNow
	 */
	public void setDefaultTime(boolean isNow) {
		if (isNow) {
			Calendar calendar = Calendar.getInstance();
			currentYear = calendar.get(Calendar.YEAR);
			currentMonth = calendar.get(Calendar.MONTH) + 1;
			currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		}
	}

	/** 设置默认显示时间 */
	public void setDefaultTime(String date) {
		if (!TextUtils.isEmpty(date)) {
			if (date.contains("-")) {
				String[] array = parseTime(date);
				try {
					currentYear = Integer.valueOf(array[0]);
					int tmp = Integer.valueOf(array[1]) % 12;
					currentMonth = tmp == 0 ? 12 : tmp;
					currentDay = Integer.valueOf(array[2]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** 设置开始时间 */
	private void setStartTime(String start) {
		if (!TextUtils.isEmpty(start)) {
			if (start.contains("-")) {
				String[] array = parseTime(start);
				try {
					mYearStart = Integer.valueOf(array[0]);
					mMonthStart = Integer.valueOf(array[1]) % 12;
					mDayStart = Integer.valueOf(array[2]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String[] parseTime(String time) {
		return time.split("-");
	}

	/** 设置结束时间 */
	private void setEndTime(String end) {
		if (!TextUtils.isEmpty(end)) {
			if (end.contains("-")) {
				String[] array = parseTime(end);
				try {
					mYearEnd = Integer.valueOf(array[0]);
					mMonthEnd = Integer.valueOf(array[1]) % 12;
					mMonthEnd = mMonthEnd == 0 ? 12 : mMonthEnd;
					mDayEnd = Integer.valueOf(array[2]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 设置开始与结束时间范围 若开始或结束采用默认时间，请输入null或"" 格式为"2016-06-06"
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * 
	 */
	public void setTimeLimite(String start, String end) {
		setStartTime(start);
		setEndTime(end);
	}

	public void setTextSize(int selectedSize, int unSelectedSize) {
		this.selectedSize = selectedSize;
		this.unSelectedSize = unSelectedSize;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.common_dialog_data_picker, container, false);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		this.setStyle(DialogFragment.STYLE_NORMAL, R.style.full_screen_dialog);
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
//		mCalendar = Calendar.newInstance();
		query = new AQuery(getView());
		this.initData();
		this.initView();
		this.setEvent();
		this.start();
	}

	private void initData() {
		createAdapter();
	}

	private void createAdapter() {
		adapter1 = new TimeAdapter(getActivity(), mYearsStr, 0);
		adapter2 = new TimeAdapter(getActivity(), mMonthesStr, 0);
		adapter3 = new TimeAdapter(getActivity(), mDaysStr, 0);
	}

	/**
	 * 获得年的集合
	 */
	private void getYearList() {
		if (mYearEnd < mYearStart) {
			mYearEnd = mYearStart + 1000;
		}
		mYears = new ArrayList<Integer>();
		mYearsStr = new ArrayList<String>();
		for (int i = mYearStart; i <= mYearEnd; i++) {
			mYears.add(i);
			mYearsStr.add(String.format("%04d年", i));
		}
	}

	/**
	 * 获得月份的集合
	 */
	private void getMonthList(int index) {
		mMonths = new ArrayList<Integer>();
		mMonthesStr = new ArrayList<String>();
		if (mYears.size() == 1) {
			if (mMonthEnd < mMonthStart) {
				mMonthEnd = 12;
			}
			addMonth(mMonthStart, mMonthEnd);
		} else if (index == 0) {
			addMonth(mMonthStart, 12);
		} else if (index == mYears.size() - 1) {
			addMonth(1, mMonthEnd);
		} else {
			addMonth(1, 12);
		}
	}

	private void addMonth(int start, int end) {
		mMonths = new ArrayList<Integer>();
		mMonthesStr = new ArrayList<String>();
		for (int i = start; i <= end; i++) {
			mMonths.add(i);
			mMonthesStr.add(String.format("%02d月", i));
		}
	}

	/**
	 * 获得天的集合
	 */
	private void getDayList(int index) {
		int indexYear = wheel1.getCurrentItem();
		int indexMonth = wheel2.getCurrentItem();
		if (mYears.size() == 1 && mMonths.size() == 1) {
			int month = mMonths.get(indexMonth);
			if (mDayEnd < mDayStart) {
				mDayEnd = getMaxDayInMonth(month);
			} else {
				int maxDay = getMaxDayInMonth(month);
				mDayEnd = Math.min(mDayEnd, maxDay);
			}
			addDays(mDayStart, mDayEnd);
		} else if (indexYear == 0 && indexMonth == 0) {
			int month = mMonths.get(indexMonth);
			int end = getMaxDayInMonth(month);
			addDays(mDayStart, end);
		} else if (indexYear == mYears.size() - 1 && indexMonth == mMonths.size() - 1) {
			int month = mMonths.get(indexMonth);
			int maxDay = getMaxDayInMonth(month);
			int end = Math.min(maxDay, mDayEnd);
			addDays(1, end);
		} else {
			int month = mMonths.get(indexMonth);
			int maxDay = getMaxDayInMonth(month);
			addDays(1, maxDay);
		}
	}

	private void addDays(int start, int end) {
		mDays = new ArrayList<Integer>();
		mDaysStr = new ArrayList<String>();
		for (int i = start; i <= end; i++) {
			mDays.add(i);
			mDaysStr.add(String.format("%2d日", i));
		}
	}

	/**
	 * 根据月，得到最大天数
	 * 
	 * @param month
	 * @return
	 */
	private int getMaxDayInMonth(int month) {
		int result = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			result = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			result = 30;
			break;
		case 2:
			if (isLeayYear()) {
				result = 29;
			} else {
				result = 28;
			}
			break;
		}
		return result;
	}

	/** 判断选中的年是否是闰年 */
	private boolean isLeayYear() {
		int index = wheel1.getCurrentItem();
		int year = mYears.get(index);
		boolean result = false;
		if (year % 4 == 0 && year % 100 != 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	private void initView() {
		// 日期
		wheel1 = (WheelView) query.id(R.id.common_dialog_data_year).getView();
		wheel1.setViewAdapter(adapter1);

		wheel2 = (WheelView) query.id(R.id.common_dialog_data_month).getView();
		wheel2.setViewAdapter(adapter2);

		wheel3 = (WheelView) query.id(R.id.common_dialog_data_day).getView();
		wheel3.setViewAdapter(adapter3);
	}

	private void setEvent() {
		wheel1.addScrollingListener(this);
		wheel2.addScrollingListener(this);
		wheel3.addScrollingListener(this);
		setBtnEvent();
	}

	private void setBtnEvent() {
		query.id(R.id.dialog_root).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		query.id(R.id.common_dialog_title_close_btn).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		query.id(R.id.common_dialog_title_complete_btn).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.takeTime(getSelectedYear(), getSelectedMonth(), getSelectedDay());
				}
				dismiss();
			}
		});
	}

	/**
	 * 启动， 设置滚轮初始数据
	 */
	private void start() {
		getYearList();
		adapter1.setData(mYearsStr);
		wheel1.setViewAdapter(adapter1);
		wheel1.setCurrentItem(getCurrentYearIndex());
		getMonthList(wheel1.getCurrentItem());
		adapter2.setData(mMonthesStr);
		wheel2.setViewAdapter(adapter2);
		wheel2.setCurrentItem(getCurrentMonthIndex());
		getDayList(wheel2.getCurrentItem());
		adapter3.setData(mDaysStr);
		wheel3.setViewAdapter(adapter3);
		wheel3.setCurrentItem(getCurrentDayIndex());
	}

	@Override
	public void onResume() {
		super.onResume();
		setTextSize(wheel1.getCurrentItem(), adapter1);
		setTextSize(wheel2.getCurrentItem(), adapter2);
		setTextSize(wheel3.getCurrentItem(), adapter3);
	}

	/** 默认年的索引 */
	private int getCurrentYearIndex() {
		if (currentYear < mYearStart || currentYear > mYearEnd) {
			return 0;
		}
		for (int i = 0, count = mYears.size(); i < count; i++) {
			if (currentYear == mYears.get(i)) {
				return i;
			}
		}
		return 0;
	}

	/** 默认月的索引 */
	private int getCurrentMonthIndex() {
		if (mYears.get(wheel1.getCurrentItem()) != currentYear) {
			return 0;
		}
		for (int i = 0, count = mMonths.size(); i < count; i++) {
			if (currentMonth == mMonths.get(i)) {
				return i;
			}
		}
		return 0;
	}

	private int getCurrentDayIndex() {
		if (mYears.get(wheel1.getCurrentItem()) == currentYear
				&& mMonths.get(wheel2.getCurrentItem()) == currentMonth) {
			for (int i = 0, count = mDays.size(); i < count; i++) {
				if (currentDay == mDays.get(i)) {
					return i;
				}
			}
			return 0;
		} else {
			return 0;
		}
	}

	private int getSelectedYear() {
		return mYears.get(wheel1.getCurrentItem());
	}

	private int getSelectedMonth() {
		return mMonths.get(wheel2.getCurrentItem());
	}

	private int getSelectedDay() {
		return mDays.get(wheel3.getCurrentItem());
	}

	/**
	 * 数据装载器
	 * 
	 * @author nnnn
	 * @createTime 2016年4月1日 下午2:57:25
	 */
	private class TimeAdapter extends AbstractWheelTextAdapter {
		private List<String> mDatas;

		protected TimeAdapter(Context context, List<String> list, int currentIndex) {
			super(context, R.layout.wheel_item_layout, R.id.tempValue, currentIndex, selectedSize,
					unSelectedSize);
			this.mDatas = list;
		}

		@Override
		public int getItemsCount() {
			return mDatas == null ? 0 : mDatas.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return String.valueOf(mDatas.get(index));
		}

		public void setData(List<String> data) {
			this.mDatas = data;
		}
	}

	@Override
	public void onScrollingStarted(WheelView wheel) {

	}

	@Override
	public void onScrollingFinished(WheelView wheel) {
		if (wheel == wheel1) {
			yearChanger();
		} else if (wheel == wheel2) {
			monthChanged();
		} else {
			dayChanged();
		}
	}

	/**
	 * 年发生改变
	 */
	private void yearChanger() {

		int index = wheel1.getCurrentItem();
		setTextSize(index, adapter1);
		getMonthList(index);
		adapter2.setData(mMonthesStr);
		wheel2.setViewAdapter(adapter2);
		wheel2.setCurrentItem(0);
		monthChanged();
	}

	/**
	 * 月改变
	 */
	private void monthChanged() {
		setTextSize(wheel2.getCurrentItem(), adapter2);
		getDayList(wheel2.getCurrentItem());
		adapter3.setData(mDaysStr);
		wheel3.setViewAdapter(adapter3);
		wheel3.setCurrentItem(0);
		dayChanged();
	}

	/**
	 * 日改变
	 */
	private void dayChanged() {
		setTextSize(wheel3.getCurrentItem(), adapter3);
	}

	/**
	 * 设置adapter是item的字体大小
	 * 
	 * @param wheel
	 * @param adapter
	 */
	private void setTextSize(int index, TimeAdapter adapter) {
		String text = adapter.getItemText(index).toString();
		setTextviewSize(text, adapter);
	}

	/**
	 * 设置字体大小
	 * 
	 * @param curriteItemText
	 * @param adapter
	 */
	public void setTextviewSize(String curriteItemText, TimeAdapter adapter) {
		ArrayList<View> arrayList = adapter.getTestViews();
		int size = arrayList.size();
		String currentText;
		for (int i = 0; i < size; i++) {
			TextView textvew = (TextView) arrayList.get(i);
			currentText = textvew.getText().toString();
			if (curriteItemText.equals(currentText)) {
				textvew.setTextSize(selectedSize);
			} else {
				textvew.setTextSize(unSelectedSize);
			}
		}
	}

}
