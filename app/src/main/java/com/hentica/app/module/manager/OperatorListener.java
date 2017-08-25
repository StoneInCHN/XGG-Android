package com.hentica.app.module.manager;

/**
 * 操作监听
 * Created by Snow on 2017/1/14.
 */

public interface OperatorListener {

    /** 操作成功后的回调 */
    void success();

    /** 操作失败后的回调 */
    void failure();

}
