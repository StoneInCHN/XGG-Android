/******************************************************	
* Copyright©2016-2016年10月8日 by hentica All Rights Reserved	
* 创建日期：2016年10月8日 下午2:49:24
* 作       者：bbliu@hentica.com	
* 功能描述：错误码
********************************************************/

package com.hentica.app.modules.peccancy.common;

import com.plant.app.common.data.BaseErrCode;

public class ErrCode extends BaseErrCode {

	//车辆
	public static final int ERR_CODE_VEHICLE_NOPERMISSION = 1000; //没有查看权限
	
	//驾驶证
	public static final int ERR_CODE_DEIVELICENSE_NOPERMISSION = 1100; //没有查看权限
	
	//订单
	public static final int ERR_CODE_ORDER_NO_CITY = 1200; //请设置您所在的城市
	public static final int ERR_CODE_ORDER_NO_SERVICE = 1201; //您所在的城市还没有开通这个服务
	public static final int ERR_CODE_ORDER_NO_VECHILE = 1202; //请选择车辆
	public static final int ERR_CODE_ORDER_VECHILE_PEC_CANNOT_DEAL = 1203; //存在不能处理的车辆违章信息
	public static final int ERR_CODE_ORDER_NO_LICENSE = 1204; //请选择驾驶证
	public static final int ERR_CODE_ORDER_NO_ENTRUSTCITY = 1205; //请选择委托城市
	public static final int ERR_CODE_ORDER_NO_PLATE_ISSUE_REASON = 1206; //请选择牌照补办原因
	public static final int ERR_CODE_ORDER_NO_SEND_PRO_TYPE = 1207; //请选择寄送方式
	public static final int ERR_CODE_ORDER_NO_SEND_PRO_ADDR = 1208; //请选择上门取件地址
	public static final int ERR_CODE_ORDER_NO_BACK_PRO_TYPE = 1209; //请选择回寄方式
	public static final int ERR_CODE_ORDER_NO_BACK_PRO_ADDR = 1210; //请选择回寄地址
	public static final int ERR_CODE_ORDER_NO_UPLOAD_PRO = 1211; //请上传需要的资料
	public static final int ERR_CODE_ORDER_NEED_UPLOAD_PRO = 1212; //请上传资料%s
	public static final int ERR_CODE_ORDER_NOT_EXIST = 1213; //订单不存在
	public static final int ERR_CODE_ORDER_HAD_FINISHED = 1214; //订单已经完成
	public static final int ERR_CODE_ORDER_NO_NEED_REUPLOAD = 1215; //订单没有需要重传的资料
	public static final int ERR_CODE_ORDER_HAD_PAYED = 1216; //订单已经支付
	public static final int ERR_CODE_ORDER_HAD_NO_PAYED = 1217; //订单还没有支付
	public static final int ERR_CODE_ORDER_HAD_NO_FINISHED = 1218; //订单还没有完成
}
