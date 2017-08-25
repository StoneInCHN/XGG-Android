package com.hentica.app.module.mine.ui;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.module.index.PayPwdHelper;
import com.hentica.app.module.listener.CallbackAdapter;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.manager.timedown.TimeDownManager;
import com.hentica.app.module.mine.intf.Transfer;
import com.hentica.app.module.mine.presenter.MineProfilePresenter;
import com.hentica.app.module.mine.presenter.MineProfilePresenterImpl;
import com.hentica.app.module.mine.presenter.TransferPresenter;
import com.hentica.app.module.mine.presenter.TransferPresenterImpl;
import com.hentica.app.module.mine.util.TransferUtils;
import com.hentica.app.module.mine.view.MineProfileView;
import com.hentica.app.module.mine.view.TransferView;
import com.hentica.app.util.TextWatcherAdapter;
import com.hentica.app.widget.view.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 转账界面
 * Created by Snow on 2017/8/14.
 */

public class MineTransferFragment extends BaseFragment implements TransferView, MineProfileView {

    public static final String DATA_TRANSFER_TYPE = "transferType";

    private String mTransferType = "";//转账类型

    @BindView(R.id.edt_transfer_mobile)
    EditText mEdtTransferMobile;
    @BindView(R.id.tv_user_mobile)
    TextView mTvUserMobile;
    @BindView(R.id.edt_sms_code)
    EditText mEdtSmsCode;
    @BindView(R.id.edt_transfer_amount)
    EditText mEdtTransferAmount;
    @BindView(R.id.btn_send_sms)
    Button mBtnSendSms;
    @BindView(R.id.tv_current_amount)
    TextView mTvLabelCurAmount;

    private TransferUtils mTransferUtils;
    private Transfer mTransfer;
    private TransferPresenter mPresenter;
    private PayPwdHelper mPayPwdHelper = PayPwdHelper.newInstance(this);
    private MineProfilePresenter mProfilePresenter = new MineProfilePresenterImpl(this);
    private boolean verifyMobileResult = false;//手机号验证结果
    private String verifyMobileResultMsg = "";

    @Override
    public int getLayoutId() {
        return R.layout.mine_transfer_fragment;
    }

    @Override
    protected void handleIntentData(Intent intent) {
        super.handleIntentData(intent);
        mTransferType = intent.getStringExtra(DATA_TRANSFER_TYPE);
        if (TextUtils.isEmpty(mTransferType)) {
            finish();
            return;
        }
        mTransferUtils = new TransferUtils(mTransferType, mEdtTransferAmount, mTvLabelCurAmount);
        mTransfer = mTransferUtils.getTransfer();
        if (mTransfer == null) {
            finish();
            return;
        }
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setTitleText(mTransfer.title());
    }

    @Override
    protected void initData() {
        mPresenter = new TransferPresenterImpl(this);
        mProfilePresenter.getUserProfile();
    }

    @Override
    protected void initView() {
        //用户手机号
        mTvUserMobile.setText(LoginModel.getInstance().getUserLogin().getCellPhoneNum());
        mEdtTransferAmount.addTextChangedListener(mTransfer);
    }

    @Override
    protected void setEvent() {
        mBtnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sendSmsCode(mTvUserMobile.getText().toString().trim());
            }
        });
        mEdtTransferMobile.addTextChangedListener(new TextWatcherAdapter(){
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 11) {
                    mPresenter.verifyMobile(s.toString().trim());
                }
            }
        });
    }

    @Override
    public void sendSmsResult(boolean success) {
        if (success) {
            showToast("已向手机号发送短信，请注意查收！");
            startCountTimeDown();
        }
    }


    /**
     * 启动倒计时器
     */
    private void startCountTimeDown() {
        int duration = 60;//持续时间60秒
        float step = 0.5f;//间隔1秒
        new TimeDownManager(duration * 1000, (long) (step * 1000)) {

            @Override
            public void onStart() {
                super.onStart();
                setBtnSendSmsCodeEnable(false);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                super.onTick(millisUntilFinished);
                int second = (int) (millisUntilFinished / 1000);
                setBtnSendSmsCodeText(second + "S");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                setBtnSendSmsCodeText("发送");
                setBtnSendSmsCodeEnable(true);
            }
        }.start();
    }

    /**
     * 设置发送按钮文字，发送按钮显示倒计时
     */
    private void setBtnSendSmsCodeText(String text) {
        mBtnSendSms.setText(text);
    }

    /**
     * 设置发送按钮是否可用
     */
    private void setBtnSendSmsCodeEnable(boolean enable) {
        mBtnSendSms.setEnabled(enable);
    }

    @Override
    public void doTransferResult(boolean success) {
        if (success) {
            showToast("操作成功！");
            mEdtTransferMobile.setText("");
            mEdtSmsCode.setText("");
            mEdtTransferAmount.setText("");
            mProfilePresenter.getUserProfile();
        }
    }

    @Override
    public void verifyMobileResult(boolean isSuccess, String msg) {
        verifyMobileResult = isSuccess;
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
        }
    }

    /**
     * 转账
     */
    @OnClick(R.id.btn_transfer)
    void doTransfer() {
        String smsCode = mEdtSmsCode.getText().toString().trim();
        String mobile = mEdtTransferMobile.getText().toString().trim();
        double transAmount = getTransAmount();
        double curAmount = mTransfer.curAmount();
        String msg = checkData(smsCode, mobile, transAmount, curAmount);
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
        } else {
            //验证支付密码
            mPayPwdHelper.checkPayPwd(new CallbackAdapter<Void>(){
                @Override
                public void callback(boolean isSuccess, Void data) {
                    if (isSuccess) {
                        mPresenter.doTransfer(mTransferType,
                                mEdtSmsCode.getText().toString().trim(),
                                mEdtTransferMobile.getText().toString().trim(),
                                getAmount());
                    }
                }
            });
        }
    }

    private String getAmount(){
        return mEdtTransferAmount.getText().toString().trim();
    }

    private double getTransAmount() {
        double result = 0;
        try {
            result = Double.parseDouble(getAmount());
        } catch (NumberFormatException e) {

        }
        return result;
    }

    /**
     * 检查数量是否符合要求
     * @param smsCode
     * @param mobile
     * @param amount
     * @param curAmount
     * @return
     */
    private String checkData(String smsCode, String mobile, double amount, double curAmount){
        String msg = checkMobile(mobile);
        if (!TextUtils.isEmpty(msg)) {
            return msg;
        }
        if (!verifyMobileResult) {
            if (TextUtils.isEmpty(verifyMobileResultMsg)) {
                return "请验证手机号";
            } else {
                return verifyMobileResultMsg;
            }
        }
        if (TextUtils.isEmpty(smsCode)) {
            return "验证码未输入！";
        }
        if (amount == 0) {
            return "转账数量不可为空！";
        }
        if (amount > curAmount) {
            return "转账数量不能大于当前数量！";
        }
        return "";
    }

    private String checkMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return "手机号未输入！";
        }
        if (mobile.length() != 11) {
            return "手机号输入错误！";
        }
        return "";
    }

    @Override
    public void loadProfileSuccess(ResUserProfile userData) {
        mTransferUtils.refreshAmount();
    }
}
