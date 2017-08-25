package com.hentica.app.module.update;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.entity.ResAppUpdateData;
import com.hentica.app.module.listener.Callback;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/11.20:19
 */

public class AppUpdateModel {

    private static AppUpdateModel mInstance = null;

    private CheckUpdatePresenter mPresenter;

    private UpdateAppView mUpdateView;

    private boolean showloading;

    private AppUpdateModel(){
        setCheckPresenter(new CheckUpdatePresenterImpl());
    }

    /**
     * 获取更新实例
     * @return
     */
    public static AppUpdateModel getInstance(){
        if(mInstance == null){
            synchronized (AppUpdateModel.class){
                if(mInstance == null){
                    mInstance = new AppUpdateModel();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置检查更新的界面
     * @param view
     */
    public void setCheckUpdateView(UpdateAppView view, boolean showloading){
        mUpdateView = view;
        this.showloading = showloading;
    }

    /**
     * 设置检查更新方式
     * @param presenter
     */
    private void setCheckPresenter(CheckUpdatePresenter presenter){
        this.mPresenter = presenter;
    }

    public void check(){
        if(mPresenter != null){
            if(showloading){
                mUpdateView.showLoadingDialog();
            }
            mPresenter.checkUpdate(new Callback<ResAppUpdateData>() {
                @Override
                public void callback(boolean isSuccess, ResAppUpdateData data) {
                    if(showloading){
                        mUpdateView.dismissLoadingDialog();
                    }
                    if(isSuccess){
                        mUpdateView.setUpdateCheckData(data);
                    }else{
                        mUpdateView.setUpdateCheckData(null);
                    }
                }

                @Override
                public void onFailed(NetData result) {

                }
            });
        }
    }

    public void destory(){
        if(mPresenter != null){
            mPresenter = null;
        }
        mInstance = null;
    }

}
