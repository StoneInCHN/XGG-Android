package com.hentica.app.module.common.listener;

/**
 * 操作回调
 * Created by kezhong.
 * E-Mail:396926020@qq.com
 * on 2016/10/10 14:57
 */

public interface OnOperatBackListener {

    /**
     * 操作结果回调监听
     * @param isOperatSuccess
     *          true:操作成功，false:操作失败
     */
    void onResult(boolean isOperatSuccess);

}
