package com.hentica.app.module.mine.presenter;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.view.ModifyProfileView;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.request.Request;

import org.greenrobot.eventbus.EventBus;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/1.14:00
 */

public class ModifyProfilePresenterImpl implements ModifyProfilePresenter {

    private ModifyProfileView mProfileView;


    public ModifyProfilePresenterImpl(ModifyProfileView profileView) {
        this.mProfileView = profileView;
    }

    @Override
    public void updateUserPhoto(String file) {
        //上传头像
        Request.getEndUserEditUserPhoto(ApplicationData.getInstance().getLoginUserId(), file,
                ListenerAdapter.createObjectListener(ResUserProfile.class,
                        new UsualDataBackListener<ResUserProfile>(mProfileView) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResUserProfile data) {
                                if (isSuccess) {
                                    mProfileView.loadProfileSuccess(data);
//                                    LoginModel.getInstance().setUserLogin(data);//保存用户信息
//                                    EventBus.getDefault().post(new DataEvent.OnToUpdataUserProfileEvent());
                                } else {
                                    //上传头像失败
                                    mProfileView.onUpdateFailure();
                                }
                            }
                        }));
    }

    @Override
    public void updateUserName() {
        //修改用户昵称
        Request.getEndUserEditUserInfo(ApplicationData.getInstance().getLoginUserId(), mProfileView.getNickName(), null,
                ListenerAdapter.createObjectListener(ResUserProfile.class,
                        new UsualDataBackListener<ResUserProfile>(mProfileView){
                            @Override
                            protected void onComplete(boolean isSuccess, ResUserProfile data) {
                                if (isSuccess) {
                                    mProfileView.loadProfileSuccess(data);
                                    LoginModel.getInstance().setUserLogin(data);//保存用户信息
                                    EventBus.getDefault().post(new DataEvent.OnToUpdataUserProfileEvent());
                                }else{
                                    mProfileView.onUpdateFailure();
                                }
                            }
                        }
                ));
    }

    @Override
    public void updateLocation() {
        //修改用户所在地
        Request.getEndUserEditUserInfo(ApplicationData.getInstance().getLoginUserId(), null, mProfileView.getAreaId(),
                ListenerAdapter.createObjectListener(ResUserProfile.class,
                        new UsualDataBackListener<ResUserProfile>(mProfileView){
                            @Override
                            protected void onComplete(boolean isSuccess, ResUserProfile data) {
                                if (isSuccess) {
                                    mProfileView.loadProfileSuccess(data);
                                    LoginModel.getInstance().setUserLogin(data);//保存用户信息
                                    EventBus.getDefault().post(new DataEvent.OnToUpdataUserProfileEvent());
                                }else{
                                    mProfileView.onUpdateFailure();
                                }
                            }
                        }
                ));
    }

    @Override
    public void toLogout() {
        Request.getEndUserLogout(ApplicationData.getInstance().getLoginUserId(),
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mProfileView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if (isSuccess) {
                                    mProfileView.logoutSuccess();
                                }
                            }
                        }));
    }
}
