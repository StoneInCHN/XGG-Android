package com.hentica.app.module.mine.view;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.module.entity.mine.ResIdCardData;

/**
 * Created by Snow on 2017/5/28 0028.
 */

public interface BankCardView extends FragmentListener.UsualViewOperator, IdCardView {

    /**
     * 删除成功
     */
    void deleteSuccess();

    /**
     * 设置默认成功
     */
    void setDefaultSuccess();

}
