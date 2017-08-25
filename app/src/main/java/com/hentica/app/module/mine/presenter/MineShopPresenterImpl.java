package com.hentica.app.module.mine.presenter;

import com.google.gson.Gson;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.framework.fragment.ptrscrollviewcontainer.DataLoadPresenter;
import com.hentica.app.module.common.base.AbsBasePresenter;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.request.Request;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/28.14:12
 */

public class MineShopPresenterImpl extends AbsBasePresenter implements DataLoadPresenter<ResUserProfile> {

    public MineShopPresenterImpl(FragmentListener.UsualViewOperator operator) {
        super(operator);
    }

    @Override
    public void loadData(final Callback<ResUserProfile> l, String... params) {
        //获取用户资料
        Request.getEndUserGetUserInfo(ApplicationData.getInstance().getLoginUserId(),
                ListenerAdapter.createObjectListener(ResUserProfile.class,
                        new UsualDataBackListener<ResUserProfile>(getUsualOperator()) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResUserProfile data) {
                                if(isSuccess){
                                    LoginModel.getInstance().setUserLogin(data);
                                }else{
                                    String profile = new Gson().toJson(LoginModel.getInstance().getUserLogin());
                                    data = ParseUtil.parseObject(profile, ResUserProfile.class);
                                }
                                if(l != null){
                                    l.callback(isSuccess, data);
                                }
                            }
                        }));
    }
}
