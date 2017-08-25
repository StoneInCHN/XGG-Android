package com.hentica.app.module.manager.loginmanager;

import com.hentica.app.module.listener.Callback;

/**
 * 登录请求<br >
 * Req：请求登录数据结果，Res：结果返回数据结果
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/9.16:13
 */

public abstract class LoginRequest<Req, Res> {

    private Req mRequestParams;

    public LoginRequest(Req reqParams){
        this.mRequestParams = reqParams;
    }

    /** 获取登录请求参数 */
    protected Req getReqParams(){
        return mRequestParams;
    }

    /**
     *
     * @param l
     */
    public abstract void requestLogin(Callback<Res> l);

}
