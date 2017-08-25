package com.hentica.app.module.manager.pay;

/**
 * 支付操作后回调
 * Created by Snow on 2017/1/17.
 */

public interface IPayListener {

    /**
     * 成功后回调
     * @param msg 消息
     */
    void onSuccess(String msg);

    /**
     * 失败后回调
     * @param msg 消息
     */
    void onFailure(String msg);

}
