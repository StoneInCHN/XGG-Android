package com.hentica.app.module.mine.presenter;

import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.ConfigData;
import com.hentica.app.module.mine.view.TextContentView;
import com.hentica.app.util.request.Request;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.11:45
 */

public class TextContentPresenterImpl implements TextContentPresenter {

    private TextContentView mContentView;

    private String mConfigKey ;

    public TextContentPresenterImpl(String configKey, TextContentView view){
        this.mContentView = view;
        this.mConfigKey = configKey;
    }


    @Override
    public void getContent() {
        Request.getSettingConfigGetConfigByKey(mConfigKey,
                ListenerAdapter.createObjectListener(ConfigData.class,
                        new UsualDataBackListener<ConfigData>(mContentView) {
                            @Override
                            protected void onComplete(boolean isSuccess, ConfigData data) {
                                if(isSuccess){
                                    mContentView.setContent(data.getConfigValue());
                                }
                            }
                        }));
    }
}
