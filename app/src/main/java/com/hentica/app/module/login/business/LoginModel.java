package com.hentica.app.module.login.business;

import android.text.TextUtils;

import com.hentica.app.module.entity.login.ResLoginData;
import com.hentica.app.module.login.business.process.AutoLoginProgress;
import com.hentica.app.module.login.business.process.FindPwdProgress;
import com.hentica.app.module.login.business.process.ModifyPwdProgress;
import com.hentica.app.module.login.business.process.Progress;
import com.hentica.app.module.login.business.process.PwdLoginProgress;
import com.hentica.app.module.login.business.process.RegisterProgress;
import com.hentica.app.module.login.business.process.SmsLoginProgress;
import com.hentica.app.module.login.business.process.ThirdpartLoginProgress;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.event.DataEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 登录数据层，单例<br />
 * <br />
 * 登录流程:<br />
 * app有多种登录方式，每种登录方式视为一个流程，各个流程之间互不干扰。<br />
 * 要开始一个流程，调用 startXXX()方法，会返回一个流程对象。<br />
 * 每个流程对象有一个id，开启新界面后，可以调用 findProgressById(id) 取得流程对象<br />
 * <br />
 * 数据服务:<br />
 * 提供登录模块所需数据<br />
 */
public class LoginModel {

    static {
        String token = StorageUtil.getLoginToken();
        UserLoginData loginData = StorageUtil.getLastLoginInfo();
        LoginModel.getInstance().setToken(token);
        LoginModel.getInstance().setUserLogin(loginData);
    }

    // 单例
    private static LoginModel mLoginModel;

    // 缓存流程
    private Map<String, Progress> mProgressCache = new WeakHashMap<>();

    // 上次登录成功的用户，只记录自动失效的id，不记录主动退出的id
    private String mLastLoginUserid;

    // 用户登录数据
    private UserLoginData mUserLoginData;

    private String mToken = "";

    /**
     * 取得单例
     */
    public static LoginModel getInstance() {
        if(mLoginModel == null){
            synchronized (LoginModel.class){
                if(mLoginModel == null){
                    mLoginModel = new LoginModel();
                }
            }
        }
        return mLoginModel;
    }

    // 构造函数不开放
    private LoginModel() {
    }

    /**
     * 找到指定流程
     */
    public Progress findProgressById(String id) {

        return mProgressCache.get(id);
    }

    /**
     * 新建流程，注册
     */
    public RegisterProgress startRegister() {

        return putProgress(new RegisterProgress());
    }

    /**
     * 新建流程，短信验证码登录
     */
    public SmsLoginProgress startLoginBySms() {

        return putProgress(new SmsLoginProgress());
    }

    /**
     * 新建流程，密码登录
     */
    public PwdLoginProgress startLoginByPwd() {

        return putProgress(new PwdLoginProgress());
    }

    /**
     * 新建流程，第三方登录
     */
    public ThirdpartLoginProgress startPartLogin() {

        return putProgress(new ThirdpartLoginProgress());
    }

    /**
     * 新建流程，找回密码
     */
    public FindPwdProgress startFindPwd() {

        return putProgress(new FindPwdProgress());
    }

    /**
     * 自动登录
     */
    public AutoLoginProgress startLoginByAuto() {
        return putProgress(new AutoLoginProgress());
    }

    /**
     * 新建流程，修改密码
     */
    public ModifyPwdProgress startModifyPwd() {

        return putProgress(new ModifyPwdProgress());
    }

    /**
     * 取得上次保存的登录信息
     */
    public UserLoginData getStoredUserLogin() {

        return StorageUtil.getLastLoginInfo();
    }

    public void setToken(String token){
        this.mToken = token;
        StorageUtil.saveLoginToken(token);
    }

    public String getToken(){
        return this.mToken;
    }

    /**
     * 登录信息
     */
    public UserLoginData getUserLogin() {

        return mUserLoginData;
    }

    /**
     * 登录信息
     */
    public void setUserLogin(UserLoginData userLogin) {
        mUserLoginData = userLogin;
        saveUserLoginInfo(mUserLoginData);
    }

    /** 获取代理商信息 */
    public ResLoginData.AgentBean getUserAgent(){
        return mUserLoginData == null ? null : mUserLoginData.getAgent();
    }

    /** 保存登录信息 */
    public void saveUserLoginInfo(UserLoginData userLogin){
        StorageUtil.saveLastLoginInfo(userLogin);
    }

    /**
     * 用户是否登录了
     */
    public boolean isLogin() {
        //通过token 判断是否登录
        return !TextUtils.isEmpty(mToken);
    }

    /**
     * 取得登录用户id
     */
    public String getLoginUserId() {

        return mUserLoginData == null ? "" : mUserLoginData.getId()+"";
    }

    /**
     * 取得登录签名key，用于数据传输加密
     */
    public String getLoginSignKey() {

        return "";
    }

    /**
     * 取得登录session
     */
    public String getLoginSession() {

        return "";
    }

    /**
     * 上次登录成功的用户
     */
    public String getLastLoginUserId() {
        return mLastLoginUserid;
    }

    /**
     * 登录失效
     *
     * @param isByUser 是否是用户主动退出的
     */
    public void logout(boolean isByUser) {

        if (mUserLoginData != null) {
            // TODO 记录上次登录成功的用户名
            //mLastLoginUserid = mUserLoginData.getUserIdStr();
        }
        setUserLogin(null);
        setToken(null);
        // 若是主动退出的
        if (isByUser) {
            // 不保存上次登录的用户名(运行内存)
            mLastLoginUserid = null;
            // TODO 清除保存的密码(数据库)
            //当空保存用户信息
            StorageUtil.saveLastLoginInfo(null);
        }

        // 发出事件
        EventBus.getDefault().post(new DataEvent.OnLoginInvaildEvent(isByUser));
    }

    // 缓存一个Progress
    private <T extends Progress> T putProgress(T progress) {

        mProgressCache.put(progress.getId(), progress);
        progress.setLoginModel(this);
        return progress;
    }

}
