package com.hentica.app.module.index.impl;

/**
 * Created by YangChen on 2017/4/5 21:25.
 * E-mail:656762935@qq.com
 */

/** 数据采集接口 */
public interface DataGetter<T>{
    // 获取显示文字
    String getText(T data);
    // 获取内容值
    String getValue(T data);
}
