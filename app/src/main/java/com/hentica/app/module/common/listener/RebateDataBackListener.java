package com.hentica.app.module.common.listener;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.lib.net.NetData;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.11:22
 */

public abstract class RebateDataBackListener<T> extends UsualDataBackListener<T> {

    public RebateDataBackListener(FragmentListener.UsualViewOperator fragment) {
        super(fragment);
    }

    public RebateDataBackListener(FragmentListener.UsualViewOperator fragment, boolean isTipError, boolean isToLogin, boolean isShowLoading) {
        super(fragment, isTipError, isToLogin, isShowLoading);
    }

    public void extralData(String extral){

    }

    public void setResult(NetData data){

    }

}
