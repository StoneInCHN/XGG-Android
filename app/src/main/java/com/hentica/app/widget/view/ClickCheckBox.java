package com.hentica.app.widget.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by Snow on 2017/6/27.
 */

public class ClickCheckBox extends CheckBox {
    public ClickCheckBox(Context context) {
        super(context);
    }

    public ClickCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ClickCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void toggle() {
        //点击不可切换
        return;
    }
}
