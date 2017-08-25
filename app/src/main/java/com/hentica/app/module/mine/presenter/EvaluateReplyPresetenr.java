package com.hentica.app.module.mine.presenter;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/11.16:44
 */

public interface EvaluateReplyPresetenr {

    /**
     * 回复评价
     * @param orderId 评价记录id
     * @param content
     */
    void toReply(String orderId, String content);

}
