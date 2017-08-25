package com.hentica.app.module.mine.ui.bank;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.google.gson.Gson;
import com.fiveixlg.app.customer.R;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.ResBankCardInfo;
import com.hentica.app.module.entity.mine.ResIdCardData;
import com.hentica.app.module.mine.presenter.bank.IdCardPresenterImpl;
import com.hentica.app.module.mine.presenter.bank.IdCardPresetner;
import com.hentica.app.module.mine.presenter.bankcard.BankCardCheckPresenter;
import com.hentica.app.module.mine.presenter.bankcard.BankCardCheckPresenterImpl;
import com.hentica.app.module.mine.ui.textcontent.BankCardOwnerFragment;
import com.hentica.app.module.mine.view.IdCardView;
import com.hentica.app.module.mine.view.bank.BankCardCheckView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.lineview.LineViewHelper;

import org.greenrobot.eventbus.Subscribe;

/**
 * 添加银行卡界面
 * Created by Snow on 2017/5/27 0027.
 */

public class BankCardAddFragment extends BaseFragment implements BankCardCheckView, IdCardView {

    public static final String DATA_REAL_NAME= "name";//姓名
    public static final String DATA_ID_CARD= "idcard";//身份证
    public static final String DATA_IS_DEFAULT = "isDefault";//是否为默认
    private String mName = "";
    private String mIdCard = "";
    private boolean isDefault = false;
    private BankCardCheckPresenter mBankCardCheckPresenter;

    private IdCardPresetner mIdCardPresetner;

    @Override
    public int getLayoutId() {
        return R.layout.mine_bank_card_add_fragment;
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
        isDefault = intent.getBooleanExtra(DATA_IS_DEFAULT, isDefault);
    }

    @Override
    protected void initData() {
        mBankCardCheckPresenter = new BankCardCheckPresenterImpl(this);
        mIdCardPresetner = new IdCardPresenterImpl(this);
        if(TextUtils.isEmpty(mName) || TextUtils.isEmpty(mIdCard)){
            mIdCardPresetner.getIdCard();
        }
    }

    @Override
    protected void initView() {
        LineViewHelper.setValue(getView(), R.id.lnv_name, mName);
        CheckBox defaultCkb  = getViews(R.id.ckb_isdefault);
        defaultCkb.setChecked(isDefault);
        if(isDefault){
            defaultCkb.setEnabled(false);
        }
    }

    @Override
    protected void setEvent() {
        setViewClickEvent(R.id.btn_next, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBankCardCheckPresenter.checkBankCard(getCardId());
            }
        });
        //说明按钮
        getViews(R.id.img_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFrameActivity(BankCardOwnerFragment.class);
            }
        });
    }

    private String getCardId(){
        return LineViewHelper.getValue(getView(), R.id.lnv_bankcard);
    }

    private boolean isDefault(){
        return mQuery.id(R.id.ckb_isdefault).isChecked();
    }

    @Override
    public void checkSuccess(ResBankCardInfo data) {
        Intent intent = new Intent();
        intent.putExtra(BankCardInfoFragment.DATA_REAL_NAME, mName);
        intent.putExtra(BankCardInfoFragment.DATA_ID_CARD, mIdCard);
        intent.putExtra(BankCardInfoFragment.DATA_IS_DEFAULT, isDefault());
        intent.putExtra(BankCardInfoFragment.DATA_BANK_CARD_ID, getCardId());
        intent.putExtra(BankCardInfoFragment.DATA_BANK_CARD_INFO, new Gson().toJson(data));
        FragmentJumpUtil.toBankCardInfoFragment(getUsualFragment(), intent);
    }

    @Subscribe
    public void onEvent(DataEvent.OnBankCardAddSuccess event){
        finish();
    }

    @Override
    public void setIdCardData(ResIdCardData data) {
        if(data != null){
            mName = data.getRealName();
            LineViewHelper.setValue(getView(), R.id.lnv_name, mName);
            mIdCard = data.getIdCardNo();
        }
    }
}
