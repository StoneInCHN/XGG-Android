package com.hentica.app.widget.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.fiveixlg.app.customer.R;

/**
 * Created by YangChen on 2017/5/9 19:25.
 * E-mail:656762935@qq.com
 */

public class ClearEditText extends EditText {

    private boolean mHasFocus = false;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setEvent();
    }

    private void initView(){
    }

    private void setEvent(){
        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mHasFocus = hasFocus;
                if(hasFocus && !TextUtils.isEmpty(getText())){
                    // 获得焦点
                    setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.rebate_login_delete,0);
                }else {
                    // 失去焦点
                    setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                }
            }
        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(hasFocus() && s.length() > 0){
                    setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.rebate_login_delete,0);
                }else {
                    setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                }
            }
        });

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = getCompoundDrawables()[2];
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(drawable != null ){
                        boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                                && (event.getX() < ((getWidth() - getPaddingRight())));
                        if(touchable){
                            // 执行点击事件
                            setText("");
                        }
                    }
                }
                return false;
            }
        });
    }
}
