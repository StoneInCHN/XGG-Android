package com.hentica.app.util;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * TextView工具
 * Created by Snow on 2017/1/11.
 */

public class TvUtils {

    /**
     * 设置TextView文字，如果文字为null或""，则隐藏TextView
     * @param tv
     * @param text
     */
    public static void setTextViewText(TextView tv, String text){
        if(TextUtils.isEmpty(text)){
            tv.setVisibility(View.GONE);
        }else{
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

}
