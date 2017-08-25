package com.hentica.app.widget.wheel;

import com.hentica.app.lib.util.TextGetter;

import java.util.List;

/**
 * 时间类型
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.10:31
 */

public interface TimeType<T> {

    /**
     * 从左到右，第1个滚轮数据源
     */
    List<T> getWheel1Datas();

    /**
     * 从左到右，第2个滚轮数据源
     */
    List<T> getWheel2Datas();

    /**
     * 滚轮1数据显示方式
     * @return
     */
    TextGetter<T> getWheel1TextGetter();

    /**
     * 滚轮2数据显示方式
     * @return
     */
    TextGetter<T> getWheel2TextGetter();

}
