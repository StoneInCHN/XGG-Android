package com.hentica.app.module.mine.view.shop;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.module.entity.mine.ResMineEvaluateDetailData;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/11.16:24
 */

public interface EvaluateDetailView extends FragmentListener.UsualViewOperator{

    /**
     * 设置评价详情数据
     */
    void setDetailData(ResMineEvaluateDetailData data);

}
