package com.hentica.app.module.login.business.process;

import com.hentica.app.module.login.business.LoginModel;

/**
 * 流程
 */
public class Progress {

    // 登录业务层
    protected LoginModel mLoginModel;

    // 自增id
    private static int mAutoIncrementId = 1;

    // 流程id
    private String mId;

    // 构造函数
    public Progress() {

        mId = mAutoIncrementId + "";
        mAutoIncrementId++;
    }

    /**
     * 登录业务层
     */
    public void setLoginModel(LoginModel loginModel) {
        mLoginModel = loginModel;
    }

    /**
     * 流程唯一id
     */
    public String getId() {
        return mId;
    }
}
