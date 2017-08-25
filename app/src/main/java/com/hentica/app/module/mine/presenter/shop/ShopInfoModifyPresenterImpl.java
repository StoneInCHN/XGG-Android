package com.hentica.app.module.mine.presenter.shop;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.mine.view.shop.ShopInfoModifyView;
import com.hentica.app.util.request.Request;
import com.hentica.appbase.famework.util.ListUtils;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.13:47
 */

public class ShopInfoModifyPresenterImpl implements ShopInfoModifyPresenter {

    private ShopInfoModifyView mModifyView;

    public ShopInfoModifyPresenterImpl(ShopInfoModifyView modifyView) {
        this.mModifyView = modifyView;
    }

    @Override
    public void toModify() {
        String msg = checkDatas();
        if (!TextUtils.isEmpty(msg)) {
            mModifyView.showToast(msg);
            return;
        }
        AsyncTask<Void, Void, Void> task =  new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                mModifyView.showLoadingDialog();
            }

            @Override
            protected Void doInBackground(Void... params) {
                while (true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(!mModifyView.inParseLocation()){
                        break;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                modify();
                mModifyView.dismissLoadingDialog();
            }
        };
        //判断是否还在解析地址
        if(mModifyView.inParseLocation()){
            task.execute();
        }else{
            modify();
        }
    }

    private void modify(){
        Request.getSellerEditInfo(ApplicationData.getInstance().getLoginUserId(),
                mModifyView.getSellerId(),
                mModifyView.getName(),
                mModifyView.getAnverage(),
                mModifyView.getBusinessTime(),
                mModifyView.getAddress(),
                mModifyView.getService(),
                mModifyView.getPhone(),
                mModifyView.getDescription(),
                mModifyView.getAreaId(),
                mModifyView.getLatitude(),
                mModifyView.getLongitude(),
                mModifyView.isLogoModify() ? mModifyView.getLogoPath() : null,
                mModifyView.isEnvironmentPhotoModify() ? mModifyView.getEnvironment() : null,
                ListenerAdapter.createObjectListener(Void.class, new UsualDataBackListener<Void>(mModifyView) {
                    @Override
                    protected void onComplete(boolean isSuccess, Void data) {
                        if (isSuccess){
                            mModifyView.modifySuccess();
                        }
                    }
                })
        );
    }

    /**
     * 资料数据是否填写完整
     *
     * @return 返回错误信息, null或""表示数据填写完整
     */
    private String checkDatas() {
        String msg = "资料未填写完整！";
        if (mModifyView.isLogoModify() && TextUtils.isEmpty(mModifyView.getLogoPath())) {
            return msg;
        }
        if (TextUtils.isEmpty(mModifyView.getAnverage())) {
            return msg;
        }
        if (TextUtils.isEmpty(mModifyView.getBusinessTime())) {
            return msg;
        }
        if (TextUtils.isEmpty(mModifyView.getPhone())) {
            return msg;
        }
        if (TextUtils.isEmpty(mModifyView.getName())) {
            return msg;
        }
        if (TextUtils.isEmpty(mModifyView.getAddress())) {
            return msg;
        }
        if (mModifyView.isEnvironmentPhotoModify() && ListUtils.isEmpty(mModifyView.getEnvironment())) {
            return msg;
        }
        return "";
    }
}
