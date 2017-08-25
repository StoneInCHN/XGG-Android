package com.hentica.app.widget.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hentica.app.util.StatusBarUtil;
import com.hentica.app.util.ViewUtil;
import com.fiveixlg.app.customer.R;

/**
 * 通用标题视图
 */
public class TitleView extends FrameLayout {

    // 标题元素：标题文字
    public static final int TITLE_TEXT = 0x00000001;
    // 标题元素：左文字按钮
    public static final int TITLE_LEFT_TEXT_BTN = 0x00000002;
    // 标题元素：右文字按钮
    public static final int TITLE_RIGHT_TEXT_BTN = 0x00000004;
    // 标题元素：左图片按钮
    public static final int TITLE_LEFT_IMG_BTN = 0x00000008;
    // 标题元素：右图片按钮
    public static final int TITLE_RIGHT_IMG_BTN = 0x00000010;

    // 标题
    private TextView mTitleTextView;
    // 左文字按钮
    private TextView mLeftTextBtn;
    // 右文字按钮
    private TextView mRightTextBtn;
    // 左图片按钮
    private ImageButton mLeftImageBtn;
    // 右图片按钮
    private ImageButton mRightImageBtn;
    // 沉浸式状态栏
    private View mStatusBarView;
    // 底部分割线
    private TextView mBottomLineView;

    public TitleView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    // 初始化
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        // 初始化子界面
        this.initSubView(context);

        // 可见元素
        int visibleElement = TITLE_TEXT | TITLE_LEFT_IMG_BTN;
        // 保留状态栏
        boolean keepStatusBar = true;

        // 读取属性
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleViewAttr, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {

                // 标题
                case R.styleable.TitleViewAttr_ctTitleText:
                    setTitleText(a.getString(attr));
                    break;

                // 可见项
                case R.styleable.TitleViewAttr_ctTitleVisibleBtns:
                    visibleElement = a.getInt(attr, visibleElement);
                    break;

                // 左图片按钮
                case R.styleable.TitleViewAttr_ctTitleLeftImg:
                    setLeftImageBtnImg(a.getResourceId(attr, 0));
                    break;

                // 右图片按钮
                case R.styleable.TitleViewAttr_ctTitleRightImg:
                    setRightImageBtnImg(a.getResourceId(attr, 0));
                    break;

                // 左文字按钮
                case R.styleable.TitleViewAttr_ctTitleLeftText:
                    setLeftTextBtnText(a.getString(attr));
                    break;

                // 右文字按钮
                case R.styleable.TitleViewAttr_ctTitleRightText:
                    setRightTextBtnText(a.getString(attr));
                    break;

                // 保留状态栏
                case R.styleable.TitleViewAttr_ctTitleKeepStatusBar:
                    keepStatusBar = a.getBoolean(attr, true);
                    break;
            }
        }
        a.recycle();

        setVisibleElement(visibleElement);
        setKeepStatusBar(keepStatusBar);
    }

    // 初始化子界面
    private void initSubView(Context context) {

        // 加入到当前布局中
        View.inflate(context, R.layout.common_title, this);

        mTitleTextView = ViewUtil.getHolderView(this, R.id.common_title_text);
        mLeftTextBtn = ViewUtil.getHolderView(this, R.id.common_title_left_btn);
        mLeftImageBtn = ViewUtil.getHolderView(this, R.id.common_title_left_btn_back);
        mRightTextBtn = ViewUtil.getHolderView(this, R.id.common_title_right_btn);
        mRightImageBtn = ViewUtil.getHolderView(this, R.id.common_title_right_btn_img);
        mStatusBarView = ViewUtil.getHolderView(this, R.id.title_status_bar_view);
        mBottomLineView = ViewUtil.getHolderView(this,R.id.common_title_under_line);
    }

    /**
     * 标题
     */
    public void setTitleText(CharSequence text) {

        mTitleTextView.setText(text);
    }

    /**
     * 左按钮文字
     */
    public void setLeftTextBtnText(CharSequence text) {

        mLeftTextBtn.setText(text);
    }

    /**
     * 右按钮文字
     */
    public void setRightTextBtnText(CharSequence text) {

        mRightTextBtn.setText(text);
    }

    /**
     * 左按钮图片
     */
    public void setLeftImageBtnImg(int resId) {

        mLeftImageBtn.setImageResource(resId);
    }

    /**
     * 右按钮图片
     */
    public void setRightImageBtnImg(int resId) {

        mRightImageBtn.setImageResource(resId);
    }

    /**
     * 是否保留状态栏
     */
    public void setKeepStatusBar(boolean keepStatusBar) {

        if (this.isInEditMode()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            mStatusBarView.setVisibility(keepStatusBar ? VISIBLE : GONE);
            setStatusBarVisible(getContext(), keepStatusBar);

        } else {

            mStatusBarView.setVisibility(GONE);
        }
    }

    // 设置系统状态栏可见性
    private void setStatusBarVisible(Context context, boolean visible) {

        if (this.isInEditMode()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            if (context != null) {

                StatusBarUtil.setTranslucentStatus((Activity) getContext(), visible);
                if (visible) {
                    StatusBarUtil.setTitleHeight(getContext(), mStatusBarView);
                }
            }
        }
    }

    /**
     * 设置可见项
     *
     * @param visibleElement 可见项，取值为 TITLE_TEXT, TITLE_LEFT_TEXT_BTN, TITLE_RIGHT_TEXT_BTN, TITLE_LEFT_IMG_BTN, TITLE_RIGHT_IMG_BTN
     */
    public void setVisibleElement(int visibleElement) {

        int[] elements = {TITLE_TEXT, TITLE_LEFT_TEXT_BTN, TITLE_RIGHT_TEXT_BTN, TITLE_LEFT_IMG_BTN, TITLE_RIGHT_IMG_BTN};
        View[] views = {mTitleTextView, mLeftTextBtn, mRightTextBtn, mLeftImageBtn, mRightImageBtn};

        if (elements.length == views.length) {

            for (int i = 0; i < elements.length; i++) {

                boolean visible = ((visibleElement & elements[i]) > 0);
                views[i].setVisibility(visible ? VISIBLE : GONE);
            }
        } else {
            throw new RuntimeException("元素数组与视图数组长度应该一致");
        }
    }

    /**
     * 标题文字
     */
    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    /**
     * 左文字按钮
     */
    public TextView getLeftTextBtn() {
        return mLeftTextBtn;
    }

    /**
     * 右文字按钮
     */
    public TextView getRightTextBtn() {
        return mRightTextBtn;
    }

    /**
     * 左图片按钮
     */
    public ImageButton getLeftImageBtn() {
        return mLeftImageBtn;
    }

    /**
     * 右图片按钮
     */
    public ImageButton getRightImageBtn() {
        return mRightImageBtn;
    }

    /**
     *  底部分割线
     */
    public TextView getBottomLineView(){ return mBottomLineView; }

    /**
     * 左文字按钮点击事件
     */
    public void setOnLeftTextBtnClickListener(OnClickListener listener) {

        getLeftTextBtn().setOnClickListener(listener);
    }

    /**
     * 左图片按钮点击事件
     */
    public void setOnLeftImageBtnClickListener(OnClickListener listener) {

        getLeftImageBtn().setOnClickListener(listener);
    }

    /**
     * 右文字按钮点击事件
     */
    public void setOnRightTextBtnClickListener(OnClickListener listener) {

        getRightTextBtn().setOnClickListener(listener);
    }

    /**
     * 右图片按钮点击事件
     */
    public void setOnRightImageBtnClickListener(OnClickListener listener) {

        getRightImageBtn().setOnClickListener(listener);
    }
}
