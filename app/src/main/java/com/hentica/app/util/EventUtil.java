package com.hentica.app.util;

import com.hentica.app.util.event.UiEvent.OnDestoryAllUIEvent;
import com.hentica.app.framework.fragment.UsualFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * 用于发出常用事件
 * 
 * @author mili
 * @createTime 2016-6-6 下午2:29:47
 */
public class EventUtil {

	/**
	 * 销毁所有界面
	 * 
	 * @param from
	 *            从哪个界面发出的事件
	 * @param keepSelf
	 *            是否保存当前界面
	 */
	public static void destoryAllUi(UsualFragment from, boolean keepSelf) {

		OnDestoryAllUIEvent event = new OnDestoryAllUIEvent();
		EventBus.getDefault().post(event);
	}
}
