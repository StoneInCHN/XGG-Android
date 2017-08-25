package com.hentica.app.widget.view.lineview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hentica.app.util.FontUtil;
import com.fiveixlg.app.customer.R;

//import com.hentica.app.util.FontUtil;

/**
 * 分两边视图，左边标题，右边文字
 *
 * @author mili
 * @createTime 2016-6-13 上午11:56:17
 */
public class LineViewText extends FrameLayout {

    /**
     * 标题
     */
    private TextView mTitleTextView;

    /**
     * 内容
     */
    private TextView mContentTextView;

    /**
     * 内容左边距
     */
    private int mContentPaddingLeft;

    public LineViewText(Context context) {
        super(context);
    }

    public LineViewText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineViewText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 标题
        addChilcView(context, defStyle);
        // 属性开始
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.line_view_text, defStyle,
                0);

        // 标题数据
        String titleText = "";
        String titleHint = "";
        ColorStateList titleColor = null;
        ColorStateList titleColorHint = null;
        int titleSize = 15;
        int titlePadding = 0;
        boolean titlePaddingDefined = false;
        int titlePaddingLeft = 0, titlePaddingTop = 0, titlePaddingRight = 0, titlePaddingBottom = 0;
        Drawable titleDrawableLeft = null, titleDrawableTop = null, titleDrawableRight = null, titleDrawableBottom = null;
        int titleDrawablePadding = 0;
        boolean titleSingleLine = false;

        // 内容数据
        String contentText = "";
        String contentHint = "";
        ColorStateList contentColor = null;
        ColorStateList contentColorHint = null;
        int contentSize = 15;
        int contentPadding = 0;
        boolean contentPaddingDefined = false;
        int contentPaddingLeft = 0, contentPaddingTop = 0, contentPaddingRight = 0, contentPaddingBottom = 0;
        Drawable contentDrawableLeft = null, contentDrawableTop = null, contentDrawableRight = null, contentDrawableBottom = null;
        int contentDrawablePadding = 0;
        boolean contentSingleLine = false;
        int contentInputType = -1;

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {

                // 行属性
                case R.styleable.line_view_text_android_enabled:
                    setEnabled(a.getBoolean(attr, false));
                    break;

                // 标题
                case R.styleable.line_view_text_lineTitleText:
                    titleText = a.getString(attr);
                    break;
                case R.styleable.line_view_text_lineTitleHint:
                    titleHint = a.getString(attr);
                    break;
                case R.styleable.line_view_text_lineTitleTextColor:
                    titleColor = a.getColorStateList(attr);
                    break;
                case R.styleable.line_view_text_lineTitleTextColorHint:
                    titleColorHint = a.getColorStateList(attr);
                    break;
                case R.styleable.line_view_text_lineTitleTextSize:
                    titleSize = a.getDimensionPixelSize(attr, titleSize);
                    break;
                case R.styleable.line_view_text_lineTitlePadding:
                    titlePadding = a.getDimensionPixelSize(attr, titlePadding);
                    titlePaddingDefined = true;
                    break;
                case R.styleable.line_view_text_lineTitlePaddingLeft:
                    titlePaddingLeft = a.getDimensionPixelSize(attr, titlePaddingLeft);
                    break;
                case R.styleable.line_view_text_lineTitlePaddingTop:
                    titlePaddingTop = a.getDimensionPixelSize(attr, titlePaddingTop);
                    break;
                case R.styleable.line_view_text_lineTitlePaddingRight:
                    titlePaddingRight = a.getDimensionPixelSize(attr, titlePaddingRight);
                    break;
                case R.styleable.line_view_text_lineTitlePaddingBottom:
                    titlePaddingBottom = a.getDimensionPixelSize(attr, titlePaddingBottom);
                    break;
                case R.styleable.line_view_text_lineTitleDrawableLeft:
                    titleDrawableLeft = a.getDrawable(attr);
                    break;
                case R.styleable.line_view_text_lineTitleDrawableTop:
                    titleDrawableTop = a.getDrawable(attr);
                    break;
                case R.styleable.line_view_text_lineTitleDrawableRight:
                    titleDrawableRight = a.getDrawable(attr);
                    break;
                case R.styleable.line_view_text_lineTitleDrawableBottom:
                    titleDrawableBottom = a.getDrawable(attr);
                    break;
                case R.styleable.line_view_text_lineTitleDrawablePadding:
                    titleDrawablePadding = a.getDimensionPixelSize(attr, titleDrawablePadding);
                    break;
                case R.styleable.line_view_text_lineTitleSingleLine:
                    titleSingleLine = a.getBoolean(attr, false);
                    break;
                case R.styleable.line_view_text_lineTitleClickable:
                    mTitleTextView.setClickable(a.getBoolean(attr, false));
                    break;

                // 内容
                case R.styleable.line_view_text_lineContentText:
                    contentText = a.getString(attr);
                    break;
                case R.styleable.line_view_text_lineContentHint:
                    contentHint = a.getString(attr);
                    break;
                case R.styleable.line_view_text_lineContentTextColor:
                    contentColor = a.getColorStateList(attr);
                    break;
                case R.styleable.line_view_text_lineContentTextColorHint:
                    contentColorHint = a.getColorStateList(attr);
                    break;
                case R.styleable.line_view_text_lineContentTextSize:
                    contentSize = a.getDimensionPixelSize(attr, contentSize);
                    break;
                case R.styleable.line_view_text_lineContentPadding:
                    contentPadding = a.getDimensionPixelSize(attr, contentPadding);
                    contentPaddingDefined = true;
                    break;
                case R.styleable.line_view_text_lineContentPaddingLeft:
                    contentPaddingLeft = a.getDimensionPixelSize(attr, contentPaddingLeft);
                    break;
                case R.styleable.line_view_text_lineContentPaddingTop:
                    contentPaddingTop = a.getDimensionPixelSize(attr, contentPaddingTop);
                    break;
                case R.styleable.line_view_text_lineContentPaddingRight:
                    contentPaddingRight = a.getDimensionPixelSize(attr, contentPaddingRight);
                    break;
                case R.styleable.line_view_text_lineContentPaddingBottom:
                    contentPaddingBottom = a.getDimensionPixelSize(attr, contentPaddingBottom);
                    break;
                case R.styleable.line_view_text_lineContentDrawableLeft:
                    contentDrawableLeft = a.getDrawable(attr);
                    break;
                case R.styleable.line_view_text_lineContentDrawableTop:
                    contentDrawableTop = a.getDrawable(attr);
                    break;
                case R.styleable.line_view_text_lineContentDrawableRight:
                    contentDrawableRight = a.getDrawable(attr);
                    break;
                case R.styleable.line_view_text_lineContentDrawableBottom:
                    contentDrawableBottom = a.getDrawable(attr);
                    break;
                case R.styleable.line_view_text_lineContentDrawablePadding:
                    contentDrawablePadding = a.getDimensionPixelSize(attr, contentDrawablePadding);
                    break;
                case R.styleable.line_view_text_lineContentSingleLine:
                    contentSingleLine = a.getBoolean(attr, false);
                    break;
                case R.styleable.line_view_text_lineContentClickable:
                    mContentTextView.setClickable(a.getBoolean(attr, false));
                    break;
                case R.styleable.line_view_text_lineContentInputType:
                    contentInputType = a.getInteger(attr, -1);
                    break;
            }
        }

        a.recycle();

        // 设置属性
        if (titlePaddingDefined) {

            titlePaddingLeft = titlePaddingTop = titlePaddingRight = titlePaddingBottom = titlePadding;
        }
        if (contentPaddingDefined) {

            contentPaddingLeft = contentPaddingTop = contentPaddingRight = contentPaddingBottom = contentPadding;
        }
        mContentPaddingLeft = contentPaddingLeft;

        // 设置标题属性
        mTitleTextView.setGravity(Gravity.CENTER_VERTICAL);
        mTitleTextView.setText(titleText);
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        mTitleTextView.setHint(titleHint);
        if (titleColor != null) {

            mTitleTextView.setTextColor(titleColor);
        }
        if (titleColorHint != null) {

            mTitleTextView.setHintTextColor(titleColorHint);
        }
        mTitleTextView.setPadding(titlePaddingLeft, titlePaddingTop, titlePaddingRight,
                titlePaddingBottom);
        mTitleTextView.setCompoundDrawablesWithIntrinsicBounds(titleDrawableLeft, titleDrawableTop,
                titleDrawableRight, titleDrawableBottom);
        mTitleTextView.setCompoundDrawablePadding(titleDrawablePadding);
        mTitleTextView.setSingleLine(titleSingleLine);
        // 标题字体
        if (!this.isInEditMode()) {

            FontUtil.applyGlobleFont(context, mTitleTextView);
        }

        // 设置内容属性
        mContentTextView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mContentTextView.setText(contentText);
        mContentTextView.setHint(contentHint);
        mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentSize);
        if (contentColor != null) {

            mContentTextView.setTextColor(contentColor);
        }
        if (contentColorHint != null) {

            mContentTextView.setHintTextColor(contentColorHint);
        }
        mContentTextView.setPadding(contentPaddingLeft, contentPaddingTop, contentPaddingRight,
                contentPaddingBottom);
        mContentTextView.setCompoundDrawablesWithIntrinsicBounds(contentDrawableLeft,
                contentDrawableTop, contentDrawableRight, contentDrawableBottom);
        mContentTextView.setCompoundDrawablePadding(contentDrawablePadding);
        mContentTextView.setSingleLine(contentSingleLine);
        mContentTextView.setEllipsize(TextUtils.TruncateAt.END);
        if (contentInputType > 0) {

            mContentTextView.setInputType(contentInputType);
        }
        // 内容字体
        if (!this.isInEditMode()) {

            FontUtil.applyGlobleFont(context, mContentTextView);
        }

        this.setClickable(true);
    }

    protected void addChilcView(Context context, int defStyle){
        FrameLayout.LayoutParams titleParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.LEFT | Gravity.CENTER_VERTICAL);
        mTitleTextView = createTitleTextView(context, defStyle);
        mTitleTextView.setLayoutParams(titleParams);
        this.addView(mTitleTextView);

        // 内容
        FrameLayout.LayoutParams contentParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT,
                Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mContentTextView = createContentTextView(context, defStyle);
        mContentTextView.setLayoutParams(contentParams);
        mContentTextView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        this.addView(mContentTextView);
    }

    /**
     * 创建标题
     */
    protected TextView createTitleTextView(Context context, int defStyle) {
        return new TextView(context, null, defStyle);
    }

    /**
     * 创建内容
     */
    protected TextView createContentTextView(Context context, int defStyle) {
        return new TextView(context, null, defStyle);
    }

    /**
     * 取得标题
     */
    public TextView getTitleTextView() {

        return mTitleTextView;
    }

    /**
     * 取得内容
     */
    public TextView getContentTextView() {

        return mContentTextView;
    }

    /**
     * 设置标题
     */
    public void setTitle(CharSequence text) {

        mTitleTextView.setText(text);
    }

    /**
     * 取得标题
     */
    public CharSequence getTitle() {
        return mTitleTextView.getText();
    }

    /**
     * 设置文字
     */
    public void setText(CharSequence text) {

        mContentTextView.setText(text);
    }

    /**
     * 取得文字
     */
    public CharSequence getText() {

        return mContentTextView.getText();
    }

    /**
     * 设置左边图片
     */
    public void setDrawableLeft(int imgId){
        mTitleTextView.setCompoundDrawablesWithIntrinsicBounds(imgId,0,0,0);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mTitleTextView.setEnabled(enabled);
        mContentTextView.setEnabled(enabled);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 内容左边距
        int paddingLeft = mContentPaddingLeft + mTitleTextView.getMeasuredWidth();
        if (mContentTextView.getPaddingLeft() != paddingLeft) {

            mContentTextView.setPadding(paddingLeft, mContentTextView.getPaddingTop(),
                    mContentTextView.getPaddingRight(), mContentTextView.getPaddingBottom());
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    ;
}
