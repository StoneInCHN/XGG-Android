package com.hentica.app.module.mine.ui.bank;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.fiveixlg.app.customer.R;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.ResBankCardInfo;
import com.hentica.app.module.manager.timedown.TimeDownManager;
import com.hentica.app.module.mine.presenter.bankcard.VerifyMobilePresenterImpl;
import com.hentica.app.module.mine.view.bank.VerifyMobileView;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.VerifyHelper;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public class BankCardMobileVerifyFragment extends BaseFragment implements VerifyMobileView{

    public static final String DATA_REAL_NAME= "name";//姓名
    public static final String DATA_ID_CARD= "idcard";//身份证
    public static final String DATA_BANK_CARD_ID= "bankcardId";//银行卡id
    public static final String DATA_IS_DEFAULT = "isDefualt";//是否为默认
    public static final String DATA_BANK_CARD_INFO= "BankCardInfoData";//银行卡信息
    public static final String DATA_MOBILE= "mobile";//银行卡信息

    private String mName;
    private String mIdCard;
    private String mBankCardId;
    private ResBankCardInfo mCardInfoData;
    private String mMobile;
    private boolean isDefault;

    private VerifyMobilePresenterImpl mVerifyMobilePresenter;

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_bank_card_verify_mobile;
    }

    @Override
    protected void handleIntentData(Intent intent) {
        super.handleIntentData(intent);
        mName = intent.getStringExtra(DATA_REAL_NAME);
        mIdCard = intent.getStringExtra(DATA_ID_CARD);
        mBankCardId = intent.getStringExtra(DATA_BANK_CARD_ID);
        isDefault = intent.getBooleanExtra(DATA_IS_DEFAULT, isDefault);
        mCardInfoData = new IntentUtil(intent).getObject(DATA_BANK_CARD_INFO, ResBankCardInfo.class);
        mMobile = intent.getStringExtra(DATA_MOBILE);
    }

    @Override
    protected void initData() {
        mVerifyMobilePresenter = new VerifyMobilePresenterImpl(this);
    }

    @Override
    protected void initView() {
        setViewText(String.format(getResources().getString(R.string.tip_bank_card_verify_mobile), mMobile), R.id.tv_tip);
    }

    @Override
    protected void setEvent() {
        setViewClickEvent(R.id.btn_send, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVerifyMobilePresenter.sendSmsCode(mMobile);
            }
        });
        setViewClickEvent(R.id.btn_next, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sms = getSmsCode();
                if(TextUtils.isEmpty(sms)){
                    showToast("验证码未输入！");
                    return;
                }
                mVerifyMobilePresenter.addBankCard(sms, mName, mIdCard, mBankCardId, mCardInfoData.getBank(),
                        mCardInfoData.getNature(), isDefault, mCardInfoData.getLogo(), mMobile);
            }
        });

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                getViews(R.id.btn_send).performClick();
            }
        }, 100);

    }



    /**
     * 获取验证码
     * @return
     */
    private String getSmsCode(){
        return mQuery.id(R.id.edt_code).getText().toString();
    }

    @Override
    public void sendSmsSuccess() {
        showToast("已向手机号发送短信，请注意查收！");
        startCountTimeDown();
    }
    /**
     * 启动倒计时器
     */
    private void startCountTimeDown() {
        int duration = 60;//持续时间60秒
        float step = 0.5f;//间隔1秒
        new TimeDownManager(duration * 1000, (long) (step * 1000)){

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

    /** 设置发送按钮文字，发送按钮显示倒计时 */
    private void setBtnSendSmsCodeText(String text){
        setViewText(text, R.id.btn_send);
    }

    /** 设置发送按钮是否可用 */
    private void setBtnSendSmsCodeEnable(boolean enable){
        mQuery.id(R.id.btn_send).enabled(enable);
    }

    @Override
    public void addBankCardSuccess() {
        showToast("添加银行卡成功");
        VerifyHelper helper = VerifyHelper.getInstance();
        helper.startFragment(getActivity(), true, true);
        EventBus.getDefault().post(new DataEvent.OnBankCardAddSuccess());
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        VerifyHelper.destory();
    }
}
