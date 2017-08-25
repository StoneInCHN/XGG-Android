package com.hentica.app.module.mine.view;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.module.entity.mine.ResUserProfile;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/1.13:56
 */

public interface MineProfileView extends FragmentListener.UsualViewOperator{

    /**
     * 获取用户资料成功
     * @param userData
     */
    void loadProfileSuccess(ResUserProfile userData);

}
