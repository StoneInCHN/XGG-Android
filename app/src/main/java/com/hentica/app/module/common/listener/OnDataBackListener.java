package com.hentica.app.module.common.listener;

import com.hentica.app.lib.net.NetData;

/**
 * 通用回调，网络数据回来了
 * 
 * @author mili
 * @createTime 2016年7月12日 上午11:39:05
 */
public interface OnDataBackListener<T> {

	/**
	 * 请求开始了
	 */
	void onStart();

	/**
	 * 请求进行中
	 *
	 * @param curr 当前上传 byte
	 * @param max  总大小 byte
	 */
	void onProgress(long curr, long max);

	/**
	 * 网络对象成功返回了
	 * 
	 * @param data
	 *            返回对象
	 */
	void onSuccess(T data);

	/**
	 * 请求失败
	 * 
	 * @param result
	 *            网络请求结果
	 */
	void onFailed(NetData result);
}
