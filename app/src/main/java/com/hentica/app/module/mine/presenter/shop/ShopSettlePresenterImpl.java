package com.hentica.app.module.mine.presenter.shop;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.view.shop.ShopSettleView;
import com.hentica.app.util.request.Request;
import com.hentica.appbase.famework.util.ListUtils;

/**
 * 店铺申请入驻
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/1.17:08
 */

public class ShopSettlePresenterImpl implements ShopSettlePresenter {

    private ShopSettleView mShopSettleView;

    public ShopSettlePresenterImpl(ShopSettleView mShopSettleView) {
        this.mShopSettleView = mShopSettleView;
    }

    @Override
    public void toSettle() {
        String msg = checkData();
        if (!TextUtils.isEmpty(msg)) {
            mShopSettleView.showToast(msg);
            return;
        }
        AsyncTask<Void, Void, Void> task =  new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                mShopSettleView.showLoadingDialog();
            }

            @Override
            protected Void doInBackground(Void... params) {
                while (true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(!mShopSettleView.inParseLocation()){
                        break;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                settle();
                mShopSettleView.dismissLoadingDialog();
            }
        };
        //判断是否还在解析地址
        if(mShopSettleView.inParseLocation()){
            task.execute();
        }else{
            settle();
        }
    }

    private void settle(){
        Request.getSellerApply(ApplicationData.getInstance().getLoginUserId(),
                mShopSettleView.getApplyId(),
                mShopSettleView.getShopName(),
                mShopSettleView.getPhone(),
                mShopSettleView.getAddress(),
                mShopSettleView.getShopPhone(),
                mShopSettleView.getLicenseNumber(),
                mShopSettleView.getShopDescripte(),
                mShopSettleView.getLatitude(),
                mShopSettleView.getLongitude(),
                mShopSettleView.getDiscount(),
                mShopSettleView.getAreaId(),
                mShopSettleView.getCategoryId(),
                mShopSettleView.getLogoPath(),
                mShopSettleView.getLicensePhotoPath(),
                mShopSettleView.getShopEnvPhotosPath(),
                mShopSettleView.getCommitmentPhotosPath(),
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mShopSettleView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if (isSuccess) {
                                    mShopSettleView.onSuccess();
                                }
                            }
                        }));
    }

    private String checkData() {
        String msg = "资料未填写完整！";
        if (TextUtils.isEmpty(mShopSettleView.getPhone())) {
            //检查手机号
            return msg;
        }
        if (TextUtils.isEmpty(mShopSettleView.getShopName())) {
            //检查店铺名称
            return msg;
        }
        if (TextUtils.isEmpty(mShopSettleView.getCategoryId())) {
            //检查行业类型
            return msg;
        }
        if (TextUtils.isEmpty(mShopSettleView.getDiscount())) {
            //检查折扣
            return msg;
        }
        if (TextUtils.isEmpty(mShopSettleView.getShopPhone())) {
            //检查店铺电话
            return msg;
        }
        if (TextUtils.isEmpty(mShopSettleView.getAreaId())) {
            //检查所在地
            return msg;
        }
        if (TextUtils.isEmpty(mShopSettleView.getAddress())) {
            //检查地址
            return msg;
        }
        if (TextUtils.isEmpty(mShopSettleView.getLicenseNumber())) {
            //检查地址
            return msg;
        }
        if (TextUtils.isEmpty(mShopSettleView.getLogoPath())) {
            //检查logo
            return msg;
        }
        if (TextUtils.isEmpty(mShopSettleView.getLicensePhotoPath())) {
            //检查营业执照
            return msg;
        }
        if (ListUtils.isEmpty(mShopSettleView.getShopEnvPhotosPath())) {
            //检查环境照片
            return msg;
        }
        if (ListUtils.isEmpty(mShopSettleView.getCommitmentPhotosPath()) || mShopSettleView.getCommitmentPhotosPath().size() < 2) {
            //检查商家承诺书照片
            return msg;
        }
        return "";
    }
}
