package com.hentica.app.module.mine.presenter;

import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineHelpDetail;
import com.hentica.app.module.mine.view.TextContentView;
import com.hentica.app.util.request.Request;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.11:45
 */

public class HelpTextContentPresenterImpl implements TextContentPresenter {

    private TextContentView mContentView;

    private String mEntityId ;

    public HelpTextContentPresenterImpl(String entityId, TextContentView view){
        this.mContentView = view;
        this.mEntityId = entityId;
    }


    @Override
    public void getContent() {
        Request.getSettingConfigUserHelpDetail(mEntityId,
                ListenerAdapter.createObjectListener(ResMineHelpDetail.class,
                        new UsualDataBackListener<ResMineHelpDetail>(mContentView) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResMineHelpDetail data) {
                                if(isSuccess){
                                    mContentView.setContent(data.getContent());
                                }
                            }
                        }));
    }
}
