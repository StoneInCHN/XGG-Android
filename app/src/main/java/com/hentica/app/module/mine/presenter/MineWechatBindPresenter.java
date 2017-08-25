package com.hentica.app.module.mine.presenter;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.19:59
 */

public interface MineWechatBindPresenter {

    /**
     * 绑定微信
     * @param openId
     * @param nickName
     */
    void toBindWechat(String openId, String nickName);

    /**
     * 解绑
     */
    void unBindWechat();

}
