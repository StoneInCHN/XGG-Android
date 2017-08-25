package com.hentica.app.widget.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.fiveixlg.app.customer.R;

/**
 * 复合控件，实现文字和图标居中显示的checkbox
 *
 * Created by YangChen on 2017/5/24 14:42.
 * E-mail:656762935@qq.com
 */

public class CustomCheckBox extends FrameLayout {

    private Context mContext;
    //上层控件
    private CheckBox mLevel1CheckBox;
    //下层控件
    private CheckBox mLevel2CheckBox;

    private boolean mEnable;
    /**
     * 文字
     */
    private String mTitleText;
    /**
     * 文字颜色
     */
    private ColorStateList mTitleColor;
    /**
     * 文字大小
     */
    private int mTitleSize = 15;
    /**
     * 右边图标
     */
    private int mTitleDrawableRight;

    public CustomCheckBox(Context context) {
        this(context, null);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        // 属性开始
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.check_box_custom, defStyleAttr,
                0);
        int size = a.getIndexCount();
        for (int i = 0; i < size; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.check_box_custom_android_enabled:
                    mEnable = a.getBoolean(attr, true);
                    break;
                case R.styleable.check_box_custom_boxText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.check_box_custom_boxTextColor:
                    mTitleColor = a.getColorStateList(attr);
                    break;
                case R.styleable.check_box_custom_boxTextSize:
                    mTitleSize = a.getDimensionPixelSize(attr, mTitleSize);
                    break;
                case R.styleable.check_box_custom_boxDrawRight:
                    mTitleDrawableRight = a.getResourceId(attr, 0);
                    break;
            }
        }
        this.initView();
        this.setEvent();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.custom_checkbox_layout, this);
        if (view != null) {
            mLevel1CheckBox = (CheckBox) view.findViewById(R.id.custom_checkbox_level1);
            mLevel2CheckBox = (CheckBox) view.findViewById(R.id.custom_checkbox_level2);
            mLevel2CheckBox.setText(mTitleText);
            mLevel2CheckBox.setTextColor(mTitleColor);
            mLevel2CheckBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
            mLevel2CheckBox.setCompoundDrawablesWithIntrinsicBounds(0, 0, mTitleDrawableRight, 0);
        }
    }

    private void setEvent() {
        mLevel1CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mLevel2CheckBox.setChecked(isChecked);
            }
        });
    }


    public void setOnBoxClickListener(OnClickListener listener) {
        mLevel1CheckBox.setOnClickListener(listener);
    }

    public void setOnBoxCheckedChangedListener(CompoundButton.OnCheckedChangeListener listener) {
        mLevel2CheckBox.setOnCheckedChangeListener(listener);
    }

    public CheckBox getRealCheckBox() {
        return mLevel2CheckBox;
    }

    public CheckBox getTopCheckBox() {
        return mLevel1CheckBox;
    }

    public boolean isChecked() {
        return mLevel1CheckBox.isChecked();
    }

    public void setChecked(boolean checked) {
        mLevel1CheckBox.setChecked(checked);
    }

    public void setTitleDrawableRight(int res) {
        mLevel2CheckBox.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0);
    }

    public void setText(String text){
        mLevel2CheckBox.setText(text);
    }

    public void setBoxEnable(boolean enable) {
        mLevel1CheckBox.setEnabled(enable);
    }
}
