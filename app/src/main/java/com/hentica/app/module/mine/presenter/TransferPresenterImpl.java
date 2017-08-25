package com.hentica.app.module.mine.presenter;

import android.text.TextUtils;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.RebateDataBackListener;
import com.hentica.app.module.common.listener.RebateListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.manager.OperatorListener;
import com.hentica.app.module.manager.sms.ISendSmsManager;
import com.hentica.app.module.manager.sms.SendSmsManagerFactory;
import com.hentica.app.module.manager.sms.SmsType;
import com.hentica.app.module.mine.view.TransferView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/8/14.
 */

public class TransferPresenterImpl implements TransferPresenter {

    private TransferView mTransferView;

    public TransferPresenterImpl(TransferView view) {
        mTransferView = view;
    }

    @Override
    public void sendSmsCode(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            mTransferView.showToast("手机号未输入！");
            return;
        } else if (mobile.length() != 11) {
            mTransferView.showToast("手机号输入错误！");
            return;
        }
        ISendSmsManager manager = SendSmsManagerFactory.getInstance(SmsType.TRANSFER, mTransferView);
        manager.sendSms(mobile, new OperatorListener() {
            @Override
            public void success() {
                mTransferView.sendSmsResult(true);
            }

            @Override
            public void failure() {
                mTransferView.sendSmsResult(false);
            }
        });
    }

    @Override
    public void doTransfer(String transType, String smsCode, String mobile, String amount) {
        String msg = checkData(smsCode, mobile);
        if (!TextUtils.isEmpty(msg)) {
            mTransferView.showToast(msg);
            return;
        }
        Request.getTransferRebateDoTransfer(LoginModel.getInstance().getLoginUserId(), transType,
                smsCode, mobile, amount,
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mTransferView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                mTransferView.doTransferResult(isSuccess);
                            }
                        }));
    }

    @Override
    public void verifyMobile(String mobile) {
        Request.getTransferRebateVerifyReceiver(LoginModel.getInstance().getLoginUserId(), mobile,
                RebateListenerAdapter.createObjectListener(Void.class,
                        new RebateDataBackListener<Void>(mTransferView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {

                            }

                            @Override
                            public void setResult(NetData data) {
                                super.setResult(data);
                                mTransferView.verifyMobileResult(data.isSuccess(), data.getErrMsg());
                            }
                        }));
    }

    /**
     * 检查数量是否符合要求
     * @param smsCode
     * @param mobile
     * @return
     */
    private String checkData(String smsCode, String mobile){
        if (TextUtils.isEmpty(mobile)) {
            return "手机号未输入！";
        }
        if (TextUtils.isEmpty(smsCode)) {
            return "验证码未输入！";
        }
        return "";
    }

}
