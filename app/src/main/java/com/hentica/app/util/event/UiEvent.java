package com.hentica.app.util.event;

import android.app.Activity;

import com.hentica.app.framework.fragment.UsualFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 界面事件
 * 
 * @author mili
 * @createTime 2016-6-6 下午2:19:10
 */
public class UiEvent {

	/** 销毁所有界面，默认除了当前界面 */
	public static class OnDestoryAllUIEvent {
	}

	/** 收集打开的Acitity */
	public static class OnCollectiActivityEvent {

		/** 忽略这些界面对应的Activity */
		private List<Class<? extends UsualFragment>> mIgnoreClasses = new ArrayList<Class<? extends UsualFragment>>();

		/** 已经忽略的Activity */
		private Set<Activity> mIgnoreActivities = new HashSet<Activity>();

		/** 收集到的界面 */
		private Set<Activity> mActivities = new HashSet<Activity>();

		/** 添加忽略的界面 */
		public void addIgnoreClasses(Class<? extends UsualFragment> ignoreClass) {

			if (ignoreClass != null) {

				mIgnoreClasses.add(ignoreClass);
			}
		}

		/** 响应事件 */
		public void handleEvent(UsualFragment fragment) {

			if (fragment != null && fragment.getActivity() != null) {

				// 记录忽略界面
				for (Class<? extends UsualFragment> fragmentClass : mIgnoreClasses) {

					if (fragmentClass.equals(fragment.getClass())) {

						mIgnoreActivities.add(fragment.getActivity());
						break;
					}
				}

				// 记录此界面
				mActivities.add(fragment.getActivity());
			}
		}

		/** 取得收集到的Activity */
		public List<Activity> getActivities() {

			// 移除所有忽略界面
			mActivities.removeAll(mIgnoreActivities);

			return new ArrayList<Activity>(mActivities);
		}

		/** 清空所有规则和界面 */
		public void clear() {

			mIgnoreClasses.clear();
			mIgnoreActivities.clear();
			mActivities.clear();
		}
	}

	/** 跳转到订单 */
	public static class JumpToOrderInfoFragmentEvent {

	}

	/** 订单取消，重新读取订单数据 */
	public static class OrderCanceledEvent {

	}

	/** 文件重新上传，需要重新加载详情界面 */
	public static class OrderDetailReloadEvent {

	}

	/** 重新加载详情界面 */
	public static class OrderDetailReloadNowEvent{

	}

	/** 订单删除事件，显示订单列表界面 */
	public static class OrderDeleteEvent{

	}

	/**
	 * 文件上传结束
	 */
	public static class FileUploadFinish{

	}

	/** 添加车辆事件 */
	public static class AddVehicleEvent{

	}

	/** 添加驾驶证事件 */
	public static class AddLicenseEvent{

	}

	/** 刷新服务主界面 */
	public static class ServiceMainRefreshEvent{
		private boolean refresh;
		public ServiceMainRefreshEvent(boolean refresh){
			this.refresh = refresh;
		}

		public boolean isRefresh() {
			return refresh;
		}
	}

	/** 显示商城界面 */
	public static class ShowStoreFragmentEvent{

	}

	/** 跳转到搜索结果界面 */
	public static class JumpToSearchResultEvent{

	}

}
