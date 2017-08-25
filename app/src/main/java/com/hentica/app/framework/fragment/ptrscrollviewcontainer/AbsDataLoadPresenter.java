package com.hentica.app.framework.fragment.ptrscrollviewcontainer;

import com.hentica.app.module.common.base.AbsBasePresenter;
import com.hentica.app.framework.fragment.FragmentListener;

/**
 * Created by Snow on 2017/2/7.
 */

public abstract class AbsDataLoadPresenter<T> extends AbsBasePresenter implements DataLoadPresenter<T> {

    public AbsDataLoadPresenter(FragmentListener.UsualViewOperator operator) {
        super(operator);
    }

}
