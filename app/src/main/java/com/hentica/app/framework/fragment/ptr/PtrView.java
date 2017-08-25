package com.hentica.app.framework.fragment.ptr;

import com.hentica.app.framework.fragment.FragmentListener;

import java.util.List;

/**
 * Created by Snow on 2016/12/29.
 */

public interface PtrView<T> extends FragmentListener.UsualViewOperator{

    /** 获取列表大小 */
    int getListSize();

    /** 设置列表数据 */
    void setListDatas(List<T> datas);

    /** 添加列表数据 */
    void addListDatas(List<T> datas);

}
