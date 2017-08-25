package com.hentica.app.module.mine.presenter;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/1.13:55
 */

public interface ModifyProfilePresenter {

    /** 修改用户头像 */
    void updateUserPhoto(String file);

    /** 保存用户资料 */
    void updateUserName();

    /** 保存用户地区 */
    void updateLocation();

    /** 退出登录 */
    void toLogout();
}
