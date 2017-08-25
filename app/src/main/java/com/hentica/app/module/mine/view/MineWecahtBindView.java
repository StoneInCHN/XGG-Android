package com.hentica.app.module.mine.view;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.20:11
 */

public interface MineWecahtBindView extends FragmentListener.UsualViewOperator{

    /**
     * 绑定成功
     */
    void bindSuccess();

    /**
     * 解绑成功
     */
    void unBindSuccess();

}
