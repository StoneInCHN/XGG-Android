package com.hentica.app.module.mine.view;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * Created by Snow on 2017/7/5.
 */

public interface IOrderDeleteView extends FragmentListener.UsualViewOperator {

    void deleteOrderResult(boolean isSuccess);

}
