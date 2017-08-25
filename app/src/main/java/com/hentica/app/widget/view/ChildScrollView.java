package com.hentica.app.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author nnnn
 * @createTime 2016年6月27日 下午5:33:29
 */
public class ChildScrollView extends ScrollView {
	
	private OnScrollChangedListener mListener;
	
	public interface OnScrollChangedListener{
		void onScrollChanged(int h, int v, int oldh, int oldv);
	}
	
	public void setOnScrollChangedListener(OnScrollChangedListener listener){
		this.mListener = listener;
	}
	
	/**
	 * @param context
	 */
	public ChildScrollView(Context context) {
		super(context);
	}
	/**
	 * @param context
	 * @param attrs
	 */
	public ChildScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ChildScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(mListener != null){
			mListener.onScrollChanged(l, t, oldl, oldt);
		}
	}
}
