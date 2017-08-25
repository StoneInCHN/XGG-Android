package com.hentica.app.module.mine.presenter;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.mine.view.MineWecahtBindView;
import com.hentica.app.util.request.Request;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.20:07
 */

public class MineWechatBindPresenterImpl implements MineWechatBindPresenter {
    private MineWecahtBindView mBindView;

    public MineWechatBindPresenterImpl(MineWecahtBindView view) {
        this.mBindView = view;
    }

    @Override
    public void toBindWechat(String openId, String nickName) {
        Request.getEndUserDoAuthByWechat(ApplicationData.getInstance().getLoginUserId(), openId, nickName,
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mBindView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if (isSuccess) {
                                    mBindView.bindSuccess();
                                }
                            }
                        }));
    }

    @Override
    public void unBindWechat() {
        //解绑
        Request.getEndUserCancelAuthByWechat(ApplicationData.getInstance().getLoginUserId(),
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mBindView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if (isSuccess) {
                                    mBindView.unBindSuccess();
                                }
                            }
                        }));
    }

}
