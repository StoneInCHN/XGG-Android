package com.hentica.app.widget.dialog;

/** 滚轮数据基本元素 */
public class WheelBaseData<T> {
	/** 显示文本 */
	private String showText;
	/** 自定义数据 */
	private T data;

	public WheelBaseData() {
		super();
	}

	public WheelBaseData(String showText, T data) {
		super();
		this.showText = showText;
		this.data = data;
	}

	/** 显示文本 */
	public String getShowText() {
		return showText;
	}

	/** 显示文本 */
	public void setShowText(String showText) {
		this.showText = showText;
	}

	/** 自定义数据 */
	public T getData() {
		return data;
	}

	/** 自定义数据 */
	public void setData(T data) {
		this.data = data;
	}

}
