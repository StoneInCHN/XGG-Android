/******************************************************	
* Copyright©2016-2016年10月8日 by plant All Rights Reserved	
* 创建日期：2016年10月8日 下午2:28:13
* 作       者：bbliu@plant.com	
* 功能描述：基础错误码
********************************************************/

package com.plant.app.common.data;

public class BaseErrCode {

	// 通用
	public static final int ERR_CODE_PARAM_ERR = -1; // 参数异常
	public static final int ERR_CODE_SUCCESS = 0; // 成功
	public static final int ERR_CODE_FAILED = 1; // 失败
	public static final int ERR_CODE_SIGNED_ERR = 10; // 鉴权失败
	public static final int ERR_CODE_PROTOCOL_FORMAT_ERR = 50; // 协议格式错误

	// 用户
	public static final int ERR_CODE_USER_NEED_LOGIN = 100; // 需要登陆
	public static final int ERR_CODE_USER_LOGIN_OTHER = 101; // 在其他地方登陆了
	public static final int ERR_CODE_USER_LOGIN_TYPE_NOTEXIST = 102; // 登录方式不存在
	public static final int ERR_CODE_USER_MOBILE_NOTEXIST = 103; // 账号不存在
	public static final int ERR_CODE_USER_LOGINPWD_ERR = 104; // 登录密码错误请确认
	public static final int ERR_CODE_USER_MOBILE_ISNULL = 105; // 请输入手机号码
	public static final int ERR_CODE_USER_SMSCODE_ERR = 106; // 短信验证码错误
	public static final int ERR_CODE_USER_IN_BLACK = 107; // 账号已经冻结
	public static final int ERR_CODE_USER_NEED_BINDMOBILE = 108; // 需要绑定手机号码
	public static final int ERR_CODE_USER_MOBILE_EXIST = 109; //手机号码已经注册了

	// 支付相关
	public static final int ERR_CODE_PAYMENT_PAYFEE_ERR = 200; // 支付金额异常
	public static final int ERR_CODE_PAYMENT_CLOSED = 201; // 支付方式已经关闭

}
