package com.hentica.app.module.entity.type;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/1.15:46
 */

public class SellerStatus {

    /** 表示用户无店铺,且无申请记录 */
    public static final String NO = "NO";
    /** 表示用户申请已经通过，拥有店铺 */
    public static final String YES = "YES";
    /** 表示用户申请店铺请求正在审核中 */
    public static final String AUDIT_WAITING = "AUDIT_WAITING";
    /** 表示用户申请店铺请求审核失败，此时返回字段会增加一个applyId */
    public static final String AUDIT_FAILED = "AUDIT_FAILED";

}
