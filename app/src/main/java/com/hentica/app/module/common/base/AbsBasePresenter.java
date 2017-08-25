package com.hentica.app.module.common.base;

import android.support.annotation.Nullable;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * Created by Snow on 2017/2/6.
 */

public abstract class AbsBasePresenter {

    protected FragmentListener.UsualViewOperator mOperator;

    public AbsBasePresenter(FragmentListener.UsualViewOperator operator){
        this.mOperator = operator;
    }

    /**
     * 获取UsualViewOperator
     * @return
     */
    @Nullable
    protected final FragmentListener.UsualViewOperator getUsualOperator(){
        return mOperator;
    }
}
