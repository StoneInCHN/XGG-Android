package com.hentica.app.module.mine.presenter;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.view.MineProfileView;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.request.Request;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/5.14:19
 */

public class MineProfilePresenterImpl implements MineProfilePresenter{

    private MineProfileView mProfileView;

    public MineProfilePresenterImpl(MineProfileView profileView){
        this.mProfileView = profileView;
    }

    @Override
    public void getUserProfile() {
        //获取用户资料
        if(!ApplicationData.getInstance().isLogin()){
            mProfileView.dismissLoadingDialog();
            return;
        }
        Request.getEndUserGetUserInfo(ApplicationData.getInstance().getLoginUserId(),
                ListenerAdapter.createObjectListener(ResUserProfile.class,
                        new UsualDataBackListener<ResUserProfile>(mProfileView) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResUserProfile data) {
                                if(isSuccess){
                                    LoginModel.getInstance().setUserLogin(data);
                                    mProfileView.loadProfileSuccess(data);
                                }
                            }
                        }));
    }

}
