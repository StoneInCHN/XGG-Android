package com.hentica.app.module.mine.view;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.module.entity.mine.ResIdCardData;

/**
 * Created by Snow on 2017/6/2 0002.
 */

public interface IdCardView extends FragmentListener.UsualViewOperator{

    /**
     * 设置身份证信息
     * @param data
     */
    void setIdCardData(ResIdCardData data);

}
