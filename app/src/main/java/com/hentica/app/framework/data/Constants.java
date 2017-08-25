package com.hentica.app.framework.data;

/**
 * 常量
 */
public class Constants {

    /**
     * 网络返回值，需要登录
     */
    public static final int POST_RESULT_CODE_NEED_LOGIN = 100;

    /**
     * 网络返回值，在其他地方登录了
     */
    public static final int POST_RESULT_CODE_OTHER_LOGIN = 101;

    /** Token 失效 */
    public static final int FAIL_TOKEN_TIMEOUT = 0004;
//    public static final int FAIL_TOKEN_INVALIDATE = 3000;
    /** 账号被挤线下 */
    public static final int FAIL_TOKEN_OTHER_LOGIN = 0x0008;
    /** 会员可以绑定 */
    public static final int CODE_BIND_MEMBER = 0x0009;
    /** 是商家，不能绑定 */
    public static final int CODE_CAN_NOT_BIND = 0x3E8;
    /******************************* 【地区配置数据库】相关定义 ********************************/
    /**
     * 【地区配置数据库】：国家id
     */
    public static final String CONFIG_DB_COUNTRY_ID_CHINA = "";
    /** 城市 */
    public static final String CONFIG_DB_IS_CITY = "1";

    /*******************************  带返回数据界面跳转请求id  **********************************/
    /** 选择提醒时间 */
    public static final int ACTIVITY_REQUEST_CODE_SELECT_NOTICE_TIME = 10001;

    /** 绑定手机号 */
    public static final int ACTIVITY_REQUEST_CODE_BIND_PHONE_NUMBER = 10002;

    /** 通联支付 */
    public static final int ACTIVITY_REQUEST_CODE_TL_PAY = 10003;


    /*******************************  带返回数据界面跳转响应id  **********************************/
    /** 选择提醒时间返回 */
    public static final int ACTIVITY_RESULT_CODE_SELECT_NOTICE_TIME = 11001;

    /** 绑定手机号返回 */
    public static final int ACTIVITY_RESULT_CODE_BIND_PHONE_NUMBER = 11002;

    /** 登录界面，未登录返回 */
    public static final int ACTIVITY_RESULT_CODE_BACK_WITHOUT_LOGIN = 11003;
    /** 重新登录成功 */
    public static final int ACTIVITY_RESULT_CODE_RELOGIN_SUCCESS = 12001;
    /** 需要重新登录 */
    public static final int ACTIVITY_REQUEST_CODE_NEED_RELOGIN = 12001;

    /** 通联支付 */
    public static final int ACTIVITY_RESULT_CODE_TL_PAY = 11004;

    public static final String BUGLY_ID = BugConfig.BUGLY_CRASH_ID;

    public static final String DEBUG_LOG_URL = BugConfig.DEBUG_LOG_URL;


    public static final String JUHE_BANKCARD_APPKEY = "6436f789374de2722ea5047079908e4a";
}

/**
 * Bug配置
 */
class BugConfig{
    //bugly崩溃id
    public static final String BUGLY_CRASH_ID = "b68b04417a";
    //内网，开发崩溃日志
    public static final String DEBUG_LOG_URL = "http://192.167.1.83/logs/uploadException.php?app=xgg";
}
