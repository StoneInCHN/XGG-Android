package com.hentica.app.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * intent工具
 * 
 * @author mili
 * @createTime 2016年7月9日 上午10:48:45
 */
public class IntentUtil {

	/** 对应的intent */
	private Intent mIntent;

	/** 构造函数 */
	public IntentUtil(Intent intent) {

		mIntent = intent;
	}

	/** 构造函数 */
	public IntentUtil(Activity activity) {

		if (activity != null) {

			mIntent = activity.getIntent();
		}
	}

	/** 构造函数 */
	public IntentUtil(Fragment fragment) {

		if (fragment != null && fragment.getActivity() != null) {

			mIntent = fragment.getActivity().getIntent();
		}
	}

	/**
	 * 保存一个对象
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {

		if (mIntent != null) {

			mIntent.putExtra(key, ParseUtil.toJsonString(value));
		}
	}

	/** 取得intent中的字符串 */
	public String getString(String key) {
		return mIntent != null ? mIntent.getStringExtra(key) : null;
	}

	/** 取得intent中的布尔值 */
	public boolean getBoolean(String key, boolean def) {
		return mIntent != null ? mIntent.getBooleanExtra(key, def) : def;
	}

	/** 取得intent中的长整形值 */
	public long getLong(String key, long def){
		return mIntent != null ?mIntent.getLongExtra(key, def) : def;
	}

	public int getInt(String key, int def){
		return mIntent != null ? mIntent.getIntExtra(key, def) : def;
	}

	/** 取得intent中的double类型值 */
	public double getDouble(String key, double def){
		return mIntent != null ? mIntent.getDoubleExtra(key,def) : def;
	}

	/** 取得intent中的float类型值 */
	public float getFloat(String key, float def){
		return mIntent != null ? mIntent.getFloatExtra(key,def) : def;
	}

	/**
	 * 取得intent中的对象，String对象可以直接用 intentUtil.getString(key)
	 * 
	 * @param key
	 * @param class1
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObject(String key, Class<T> class1) {

		if (class1 == String.class) {
			return (T) getString(key);
		}

		if (mIntent != null) {

			String json = mIntent.getStringExtra(key);
			return ParseUtil.parseObject(json, class1);
		}
		return null;
	}

	/**
	 * 取得intent中的数组
	 * 
	 * @param key
	 * @param class1
	 * @return
	 */
	public <T> List<T> getList(String key, Class<T> class1) {

		if (mIntent != null) {

			String json = mIntent.getStringExtra(key);
			return ParseUtil.parseArray(json, class1);
		}
		return null;
	}

	/** 取得intent */
	public Intent getIntent() {
		return mIntent;
	}
}
