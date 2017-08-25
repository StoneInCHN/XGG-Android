package com.hentica.app.module.mine.ui.bank;

import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.fiveixlg.app.customer.R;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.ResBankCardInfo;
import com.hentica.app.module.mine.presenter.bankcard.VerifyCardPresenter;
import com.hentica.app.module.mine.presenter.bankcard.VerifyCardPresenterImpl;
import com.hentica.app.module.mine.ui.textcontent.BankCardMobileFragment;
import com.hentica.app.module.mine.ui.textcontent.BankCardOwnerFragment;
import com.hentica.app.module.mine.ui.textcontent.BankCardServiceAgreementFragment;
import com.hentica.app.module.mine.view.bank.BankCardVerifyView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.lineview.LineViewEdit;
import com.hentica.app.widget.view.lineview.LineViewHelper;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public class BankCardInfoFragment extends BaseFragment implements BankCardVerifyView{

    public static final String DATA_REAL_NAME= "name";//姓名
    public static final String DATA_ID_CARD= "idcard";//身份证
    public static final String DATA_IS_DEFAULT = "isDefualt";//是否为默认
    public static final String DATA_BANK_CARD_ID= "bankcardId";//银行卡id
    public static final String DATA_BANK_CARD_INFO= "BankCardInfoData";//银行卡信息

    private String mName;
    private String mIdCard;
    private String mBankCardId;
    private boolean isDefualt = false;
    private ResBankCardInfo mCardInfoData;

    private VerifyCardPresenter mVerifyCardPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.mine_bank_card_info_fragment;
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
    protected void handleIntentData(Intent intent) {
        super.handleIntentData(intent);
        mName = intent.getStringExtra(DATA_REAL_NAME);
        mIdCard = intent.getStringExtra(DATA_ID_CARD);
        mBankCardId = intent.getStringExtra(DATA_BANK_CARD_ID);
        isDefualt = intent.getBooleanExtra(DATA_IS_DEFAULT, isDefualt);
        mCardInfoData = new IntentUtil(intent).getObject(DATA_BANK_CARD_INFO, ResBankCardInfo.class);
    }

    @Override
    protected void initData() {
        mVerifyCardPresenter = new VerifyCardPresenterImpl(this);
    }

    @Override
    protected void initView() {
        HtmlBuilder builder = HtmlBuilder.newInstance();
        builder.appendNormal("同意").appendRed("《服务协议》");
        setViewText(builder.create(), R.id.btn_license);
        LineViewHelper.setValue(getView(), R.id.lnv_bankcard_type, mCardInfoData.getBank() + " " + mCardInfoData.getNature());
        LineViewEdit lnvPhone = getViews(R.id.lnv_bankcard_mobile);
        EditText edtPhone = lnvPhone.getContentTextView();
        edtPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
    }

    @Override
    protected void setEvent() {
        setViewClickEvent(R.id.btn_license, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("服务协议");
            }
        });
        setViewClickEvent(R.id.btn_next, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = getMobile();
                if(TextUtils.isEmpty(mobile)){
                    showToast("手机号未填写！");
                    return;
                }
                if(mobile.length() != 11){
                    showToast("手机号输入有误！");
                }
                mVerifyCardPresenter.VerifyCard(mName, mBankCardId, mIdCard, mobile);
            }
        });

        //说明按钮
        getViews(R.id.img_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFrameActivity(BankCardMobileFragment.class);
            }
        });
        //服务协议
        getViews(R.id.btn_license).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFrameActivity(BankCardServiceAgreementFragment.class);
            }
        });
    }

    /**
     * 获取手机号
     * @return
     */
    private String getMobile(){
        return LineViewHelper.getValue(getView(), R.id.lnv_bankcard_mobile);
    }

    @Override
    public void verifySuccess() {
        Intent intent = new Intent();
        intent.putExtra(BankCardMobileVerifyFragment.DATA_REAL_NAME, mName);
        intent.putExtra(BankCardMobileVerifyFragment.DATA_ID_CARD, mIdCard);
        intent.putExtra(BankCardMobileVerifyFragment.DATA_BANK_CARD_ID, mBankCardId);
        intent.putExtra(BankCardMobileVerifyFragment.DATA_IS_DEFAULT, isDefualt);
        intent.putExtra(BankCardMobileVerifyFragment.DATA_BANK_CARD_INFO, new Gson().toJson(mCardInfoData));
        intent.putExtra(BankCardMobileVerifyFragment.DATA_MOBILE, getMobile());
        FragmentJumpUtil.toBankCardVerifyMobileFragment(getUsualFragment(), intent);
    }

    @Override
    public void setNextBtnEnable(boolean enable) {
        getViews(R.id.btn_next).setEnabled(enable);
    }

    @Subscribe
    public void onEvent(DataEvent.OnBankCardAddSuccess event){
        finish();
    }

    @Override
    public void finish() {
        if (mVerifyCardPresenter != null) {
            mVerifyCardPresenter.detachView();
        }
        super.finish();
    }
}
