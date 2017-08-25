package com.hentica.app.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 字体工具
 *
 * @author mili
 * @createTime 2016-7-6 上午9:46:06
 */
public class FontUtil {

	/** 初始化，请在application的onCreate()中调用，或在LaunchUtil.onLaunch()中调用 */
	public static void init() {

		// 设置默认字体
//		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//				.setDefaultFontPath(getGlobleTypeFont()).setFontAttrId(R.attr.fontPath).build());
	}

	/**
	 * 包装程序运行环境，使用方法:<br />
	 *
	 * 重写activity的attachBaseContext()，如下:
	 *
	 * <pre>
	 * &#064;Override
	 * protected void attachBaseContext(Context newBase) {
	 * 	super.attachBaseContext(FontUtil.wrap(newBase));
	 * }
	 * </pre>
	 *
	 * @param newBase
	 * @return
	 */
//	public static ContextWrapper wrap(Context newBase) {
//		return CalligraphyContextWrapper.wrap(newBase);
//	}
    public static Context wrap(Context newBase) {
        return newBase;
    }

	/** 取得全局字体 */
	public static String getGlobleTypeFont() {

		return "fonts/simhei.ttf";
	}

	/** 取得全局字体 */
	public static Typeface getGlobleTypeFace(Context context) {

//		final AssetManager assetManager = context.getAssets();
//		return TypefaceUtils.load(assetManager, getGlobleTypeFont());
        return Typeface.DEFAULT;
	}

	/** 为textView应用全局字体 */
	public static boolean applyGlobleFont(final Context context, final TextView textView) {

		return applyFontToTextView(context, textView, getGlobleTypeFont());
	}

	/**
	 * 使用指定字体<br />
	 * Useful for manually fonts to a TextView. Will not default back to font
	 *
	 * @param context
	 *            Context
	 * @param textView
	 *            Not null, TextView to apply to.
	 * @param filePath
	 *            if null/empty will do nothing.
	 * @return true if fonts been applied
	 */
	public static boolean applyFontToTextView(final Context context, final TextView textView,
			final String filePath) {

//		return CalligraphyUtils.applyFontToTextView(context, textView, filePath);
        return false;
	}
}
