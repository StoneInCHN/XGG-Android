package com.hentica.app.widget.view.lineview;

import java.util.Map;

import com.androidquery.AQuery;
import com.hentica.app.util.ViewUtil;

import android.view.View;

/**
 * 行视图辅助工具
 * 
 * @author mili
 * @createTime 2016-6-13 下午5:46:36
 */
public class LineViewHelper {

	/** 设置LineView的值 */
	public static void setValue(AQuery query, int layoutId, String text) {
		if(query != null) {
			LineViewText lineViewText = (LineViewText) query.id(layoutId).getView();
			lineViewText.setText(text);
		}
	}

	/** 设置LineView的值 */
	public static void setValue(View parent, int layoutId, String text) {

		LineViewText lineViewText = ViewUtil.getHolderView(parent, layoutId);
		lineViewText.setText(text);
	}

	/** 取指定LineView节点的值 */
	public static String getValue(View parent, int layoutId) {

		LineViewText lineViewText = ViewUtil.getHolderView(parent, layoutId);
		return lineViewText.getText().toString();
	}

	/** 取指定LineView节点的值 */
	public static String getValue(AQuery query, int layoutId) {
		LineViewText lineViewText = (LineViewText) query.id(layoutId).getView();
		return lineViewText.getText().toString();
	}

	/** 设置LineView的标题 */
	public static void setTitle(View parent, int layoutId, String text) {

		LineViewText lineViewText = ViewUtil.getHolderView(parent, layoutId);
		lineViewText.setTitle(text);
	}

	/**
	 * 批量设置LineView的值
	 * 
	 * @param parent
	 *            父节点
	 * @param textes
	 *            key:节点id value:显示值
	 */
	public static void setValues(View parent, Map<Integer, String> textes) {

		if (textes != null) {

			for (Map.Entry<Integer, String> textEntry : textes.entrySet()) {

				setValue(parent, textEntry.getKey(), textEntry.getValue());
			}
		}
	}
}
