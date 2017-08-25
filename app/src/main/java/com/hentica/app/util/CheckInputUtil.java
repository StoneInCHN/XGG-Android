package com.hentica.app.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 输入校验工具，添加一系列规则后，依次校验各输入是否合法
 * 
 * @author mili
 * @createTime 2016-6-6 下午12:42:43
 */
public class CheckInputUtil {

	/** 程序运行环境 */
	private Context mContext;

	/** 检验结果 */
	private boolean mIsVaild = true;

	/** 错误提示 */
	private String mTip;

	/** 创建一个新实例 */
	public static CheckInputUtil create(Context context) {

		return new CheckInputUtil(context);
	}

	/** 构造函数 */
	public CheckInputUtil(Context context) {

		mContext = context;
	}

	/** 是否所有值都有效 */
	public boolean isVaild() {

		return mIsVaild;
	}

	/** 显示错误提示 */
	public void showErrorTip() {

		if (!mIsVaild && mContext != null) {

			if (mTip == null) {

				mTip = "";
			}

			Toast.makeText(mContext, mTip, Toast.LENGTH_SHORT).show();
		}
	}

	/** 校验规则：指定值不为null */
	public void addRuleNotNull(String str, String errorTip) {

		saveFirstInVaild(str != null, errorTip);
	}

	/** 校验规则：指定值不为空 */
	public void addRuleNotEmpty(String str, String errorTip) {

		saveFirstInVaild(!TextUtils.isEmpty(str), errorTip);
	}

	/** 校验规则：两个值不为null且相等 */
	public void addRuleEqualAndNotNull(String str1, String str2, String errorTip) {

		saveFirstInVaild(str1 != null && str1.equals(str2), errorTip);
	}

	/** 校验规则：指定值的最少长度 -1不为限 */
	public void addRuleMustLonger(String str, int minLen, String errorTip) {

		saveFirstInVaild(str == null || str.length() >= minLen || minLen == -1, errorTip);
	}

	/** 校验规则：指定值的最大长度 -1不为限 */
	public void addRuleMustShorter(String str, int maxLen, String errorTip) {

		saveFirstInVaild(str == null || str.length() <= maxLen || maxLen == -1, errorTip);
	}

	/** 校验规则：指定值长度必须在指定范围内 -1为不限 */
	public void addRuleMustLengh(String str, int minLen, int maxLen, String errorTip) {

		addRuleMustLonger(str, minLen, errorTip);
		addRuleMustShorter(str, maxLen, errorTip);
	}

	/** 校验规则：指定值必须为true */
	public void addRuleTrue(boolean val, String errorTip) {

		saveFirstInVaild(val, errorTip);
	}

	/** 判断并记录第一个无效值 */
	private void saveFirstInVaild(boolean isVaild, String errorTip) {

		// 只记录第一个
		if (mIsVaild && !isVaild) {

			mIsVaild = false;
			mTip = errorTip;
		}
	}
}
