package com.hentica.app.module.index.view;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.module.entity.index.IndexPayInfoData;
import com.hentica.app.module.entity.index.IndexPayTypeListData;

import java.util.List;

/**
 * Created by Snow on 2017/6/27.
 */

public interface I_IndexPayView extends FragmentListener.UsualViewOperator{

    /**
     * 设置支付方式数据
     * @param typeDatas
     */
    void setPayTypeDatas(boolean isSuccess, List<IndexPayTypeListData> typeDatas);

    /**
     * 设置支付相关数据
     * @param isSuccess
     * @param infoData
     */
    void setPayInfoDatas(boolean isSuccess, IndexPayInfoData infoData);

}
