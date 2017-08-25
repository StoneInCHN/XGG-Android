package com.hentica.app.module.manager.pay;

/**
 * 支付管理器
 * Created by Snow on 2017/1/17.
 */

public interface IPayManager<T extends AbsPayData> {

    /**
     * 请求支付
     * @param data 支付数据
     */
    void toPay(T data);

    /**
     * 设置支付监听
     * @param l
     */
    void setListener(IPayListener l);

}
