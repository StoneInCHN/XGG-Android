package com.hentica.app.module.mine.presenter;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.mine.view.shop.EvaluateReplyView;
import com.hentica.app.util.request.Request;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/11.16:44
 */

public class EvaluateReplyPresetenrImpl implements EvaluateReplyPresetenr {

    private EvaluateReplyView mReplyView;

    public  EvaluateReplyPresetenrImpl(EvaluateReplyView replyView){
        this.mReplyView = replyView;
    }
    @Override
    public void toReply(String orderId, String content) {
        if(TextUtils.isEmpty(content)){
            mReplyView.showToast("回复内容未输入");
            return;
        }
        Request.getOrderSellerReply(ApplicationData.getInstance().getLoginUserId(),
                orderId, content,
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mReplyView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if(isSuccess){
                                    mReplyView.replySuccess();
                                }
                            }
                        }));
    }
}
