package com.hentica.app.util;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 一系列checkbox选择辅助工具，与RadioGroup不同的是，其中的checkbox可以取消选择<br />
 * 它占用了子节点的onClicked事件
 * 
 * @author mili
 * @createTime 2016-7-5 下午5:50:23
 */
public class CheckGroupHelper {

	/** 所有子节点 */
	private List<CheckBox> mChildren;

	/**
	 * 设置视图
	 * 
	 * @param parent
	 *            父节点
	 * @param checkBoxIds
	 *            各个子节点checkBox
	 */
	public void setViews(View parent, int... checkBoxIds) {

		int size = (checkBoxIds != null ? checkBoxIds.length : 0);
		mChildren = new ArrayList<CheckBox>(size);

		// 记录所有view
		if (checkBoxIds != null) {

			for (int viewId : checkBoxIds) {

				CheckBox checkBox = (CheckBox) parent.findViewById(viewId);
				mChildren.add(checkBox);
			}
		}

		// 设置各节点事件
		setEvent();
	}

	/** 设置事件 */
	private void setEvent() {

		for (CheckBox checkBox : mChildren) {

			checkBox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					onViewClicked(v.getId());
				}
			});
		}
	}

	/** 某子节点被点击 */
	private void onViewClicked(int checkBoxId) {

		if (mChildren != null) {

			// 取消所有其他按钮
			for (CheckBox checkBox : mChildren) {

				if (checkBox.getId() != checkBoxId) {

					checkBox.setChecked(false);
				}
			}
		}
	}
}
