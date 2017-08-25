package com.hentica.app.module.mine.view;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/1.13:56
 */

public interface ModifyProfileView extends FragmentListener.UsualViewOperator , MineProfileView, MineLogoutView{

    /**
     * 修改用户信息失败
     */
    void onUpdateFailure();

    /**
     * 获取用户昵称
     * @return
     */
    String getNickName();

    /**
     * 获取用户地区
     * @return
     */
    String getAreaId();
}
