package com.hentica.app.module.mine.intf;

import android.text.TextWatcher;


/**
 * Created by Snow on 2017/8/14.
 */

public interface Transfer extends TextWatcher {

    String title();

    /**
     * 获取当前数量
     * @return
     */
    double curAmount();

    /**
     * 当前数量描述
     * @return
     */
    String curAmountDesc();

}
