package com.hentica.app.widget.view.lineview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;


public class LineViewMine extends RelativeLayout {

	/** 行标题控件 */
	private TextView mLineTitleText;

	/** 行属性控件 */
	private TextView mLineContentText;

	/** 左侧图片 */
	private ImageView mLeftImageView;

	/** 右侧图片 */
	private ImageView mRightImageView;

	/** 底部分割线 */
	private ImageView mBottomLineView;

	/** 行标题 */
	private String mLineTitle;

	/** 行内容 */
	private String mLineContent;

	/** 右侧图片 */
	private int mRightIcon;

	/** 左侧图片 */
	private int mLeftIcon;

	/** 是否显示内容文字 */
	private boolean mShowContent;

	/** 是否显示左侧图标 */
	private boolean mShowLeftIcon;

	/** 是否显示右侧图片 */
	private boolean mShowRightIcon;

	/** 是否显示下方 */
	private boolean mShowBottomLine;

	public LineViewMine(Context context) {
		this(context, null);
	}

	public LineViewMine(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LineViewMine(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		/** 解析属性 */
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MineMainLine);
		if (ta != null) {
			mLineTitle = ta.getString(R.styleable.MineMainLine_lineTitle);
			mLineContent = ta.getString(R.styleable.MineMainLine_lineContent);
			mRightIcon = ta.getResourceId(R.styleable.MineMainLine_lineRightIcon, 0);
			mLeftIcon = ta.getResourceId(R.styleable.MineMainLine_lineLeftIcon,0);
			mShowContent = ta.getBoolean(R.styleable.MineMainLine_showContent, true);
			mShowLeftIcon = ta.getBoolean(R.styleable.MineMainLine_showLeftIcon,true);
			mShowRightIcon = ta.getBoolean(R.styleable.MineMainLine_showRightIcon,true);
			mShowBottomLine = ta.getBoolean(R.styleable.MineMainLine_showBottomLine,true);
			ta.recycle();
		}
		View view = View.inflate(context, R.layout.common_mine_main_line_view, this);
		if (view != null) {
			mLineTitleText = (TextView) view.findViewById(R.id.common_line_title_text);
			mLineContentText = (TextView) view.findViewById(R.id.common_line_content_text);
			mLeftImageView = (ImageView) view.findViewById(R.id.common_line_left_img);
			mRightImageView = (ImageView) view.findViewById(R.id.common_line_right_img);
			mBottomLineView = (ImageView) view.findViewById(R.id.common_line_bottom_line_img);

			mLineTitleText.setText(mLineTitle);
			mLineContentText.setText(mLineContent);
			mLeftImageView.setBackgroundResource(mLeftIcon);
			mRightImageView.setBackgroundResource(mRightIcon);
			mLineContentText.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
			mLeftImageView.setVisibility(mShowLeftIcon ? View.VISIBLE : View.GONE);
			mRightImageView.setVisibility(mShowRightIcon ? View.VISIBLE : View.GONE);
			mBottomLineView.setVisibility(mShowBottomLine ? View.VISIBLE : View.GONE);
		}
	}

	/** 设置右侧图片 */
	public void setRightIcon(int rightIcon) {
		mRightImageView.setBackgroundResource(rightIcon);
	}

	/** 设置左侧图片 */
	public void setLeftIcon(int leftIcon){
		mLeftImageView.setBackgroundResource(leftIcon);
	}

	/** 设置标题 */
	public void setTitleText(String title) {
		mLineTitleText.setText(title);
	}

	/** 设置内容文字 */
	public void setContentText(String content) {
		mLineContentText.setText(content);
	}

	/** 内容文字 */
	public String getContentText() {
		return mLineContentText.getText().toString();
	}

	/** 获得文本内容控件 */
	public TextView getContentView(){
		return mLineContentText;
	}
}
