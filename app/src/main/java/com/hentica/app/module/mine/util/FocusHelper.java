package com.hentica.app.module.mine.util;

import android.view.View;
import android.widget.EditText;

/**
 * Created by Snow on 2017/8/29.
 */

public class FocusHelper {

    public FocusHelper(final EditText editText){
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //，解析地址
                if (!hasFocus) {
                    editText.setFocusable(false);
                    editText.setFocusableInTouchMode(false);
                }
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
            }
        });
    }

}
