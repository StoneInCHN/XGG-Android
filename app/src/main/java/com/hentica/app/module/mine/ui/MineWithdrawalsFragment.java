package com.hentica.app.module.mine.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.ResBankCardData;
import com.hentica.app.module.entity.mine.ResMineWithdrawalsInfo;
import com.hentica.app.module.index.PayPwdHelper;
import com.hentica.app.module.listener.CallbackAdapter;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.mine.presenter.WithdrawalsBankPresenter;
import com.hentica.app.module.mine.presenter.WithdrawalsBankPresenterImpl;
import com.hentica.app.module.mine.view.WithdrawalsBankView;
import com.hentica.app.module.mine.view.WithdrawalsView;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.StringUtil;
import com.hentica.app.util.TextWatcherAdapter;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.lineview.LineViewEdit;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import butterknife.OnClick;

/**
 * 提现界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineWithdrawalsFragment extends BaseFragment implements WithdrawalsView, WithdrawalsBankView {

    private WithdrawalsBankPresenter mWithdrawalsPresenter;
    private String entityId;
    private double mTotalLefen = 0;
    private ResMineWithdrawalsInfo mWithdrawalsInfo;
    private double mActuallyAmount = 0;//实际提现到账金额

    private TextView mTvActuallyAmount;
    private EditText mEdtAmount;

    private PayPwdHelper mPayPwdHelper = PayPwdHelper.newInstance(this);

    @Override
    public int getLayoutId() {
        return R.layout.mine_withdrawals_fragment;
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
        title.getTitleTextView().setTextColor(getResources().getColor(R.color.text_white));
    }

    @Override
    protected void initData() {
        mWithdrawalsPresenter = new WithdrawalsBankPresenterImpl(this);
        mWithdrawalsPresenter.getWithdrawalsInfo();
        mWithdrawalsPresenter.getDefaultCard();
    }

    @Override
    protected void initView() {
        LineViewEdit lnvAmount = getViews(R.id.withdrawals_lnv_amount);
        mEdtAmount = lnvAmount.getContentTextView();
        mTvActuallyAmount = getViews(R.id.withdrawals_tv_amount);
    }

    @Override
    protected void setEvent() {
        mEdtAmount.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                int dotIndex = text.indexOf('.');
                if(dotIndex > -1 && text.length() > dotIndex + 3){
                    String result = text.substring(0, text.length() - 1);
                    mEdtAmount.setText(result);
                    mEdtAmount.setSelection(result.length());
                } else {
                    calculateActuallyWithdrawalsAmount();
                }
            }
        });
        mEdtAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    return;
                }
                String amountStr = mEdtAmount.getText().toString().trim();
                if (TextUtils.isEmpty(amountStr)) {
                    return;
                }
                double inputAmount = parseStringToDouble(amountStr);
                double limitAmount = parseStringToDouble(mWithdrawalsInfo.getMinLimitAmount());
                double multiple = inputAmount / limitAmount;
                if (((int)multiple) == multiple) {
                    return;
                } else {
                    showToast("提现金额为" + mWithdrawalsInfo.getMinLimitAmount() + "的整数倍");
                    mEdtAmount.setText("");
                }

            }
        });
    }

    /**
     * 提现按钮点击事件
     */
    @OnClick(R.id.withdrawals_btn_withdrawals)
    public void onWithdrawalsBtnclick() {
        if (mTotalLefen < parseStringToDouble(mWithdrawalsInfo.getMinLimitAmount())) {
            showToast(String.format(Locale.getDefault(), "当前乐分小于%s元，不能提现！", mWithdrawalsInfo.getMinLimitAmount()));
            return;
        }
        if (TextUtils.isEmpty(mEdtAmount.getText().toString())) {
            showToast("请输入提现金额！");
            return;
        }
        double withdrawalsAmount = getWithdrawalsAmount();
        if (withdrawalsAmount == 0) {
            showToast("提现金额不能为0！");
            return;
        }
        if (withdrawalsAmount < parseStringToDouble(mWithdrawalsInfo.getTransactionFee())) {
            showToast(String.format(Locale.getDefault(), "手续费为%s元，提现金额不能低于手续费！", mWithdrawalsInfo.getTransactionFee()));
            return;
        }
        if (withdrawalsAmount < parseStringToDouble(mWithdrawalsInfo.getMinLimitAmount())) {
            showToast(String.format(Locale.getDefault(), "提现金额小于%s元，不能提现。", mWithdrawalsInfo.getMinLimitAmount()));
            return;
        }
        if (withdrawalsAmount > mTotalLefen) {
            showToast("当前乐分不足，不能提现。");
            return;
        }
        mPayPwdHelper.checkPayPwd(new CallbackAdapter<Void>() {
            @Override
            public void callback(boolean isSuccess, Void data) {
                withdrawals();
            }

            @Override
            public void onFailed(NetData result) {

            }
        });
    }

    /**
     * 提现
     *
     * @param payPassword
     */
    private void withdrawals(String payPassword) {
        mWithdrawalsPresenter.widthDrawals(entityId, payPassword, getRemark());
    }

    private void withdrawals() {
        mWithdrawalsPresenter.widthDrawals(entityId, getWithdrawalsAmount(), getRemark());
    }

    @Override
    public void setWithdrawalsInfo(boolean result, ResMineWithdrawalsInfo info) {
        if (!result || info == null) {
            showToast("获取信息失败！");
            finish();
        }
        mWithdrawalsInfo = info;
        refreshDrawalsInfo(info);
    }

    /**
     * 显示提现信息
     *
     * @param info
     */
    private void refreshDrawalsInfo(ResMineWithdrawalsInfo info) {
        showWithdrawalsRule(info);
        //显示头像
        //提现乐分
        mTotalLefen = info.getIncomeLeScore() + info.getMotivateLeScore() + info.getAgentLeScore();
        calculateActuallyWithdrawalsAmount();
        mEdtAmount.setHint("提现金额为" + info.getMinLimitAmount() + "的整数倍");
    }

    /**
     * 显示实际到账金额
     */
    private void calculateActuallyWithdrawalsAmount() {
        double amount = getWithdrawalsAmount();
        double translateFee = parseStringToDouble(mWithdrawalsInfo.getTransactionFee());
        mActuallyAmount = amount - translateFee;
        mTvActuallyAmount.setText(String.format("提现实际到账：￥%s", PriceUtil.format(mActuallyAmount < 0 ? 0 : mActuallyAmount)));
    }

    /**
     * 显示提现规则
     *
     * @param info
     */
    private void showWithdrawalsRule(ResMineWithdrawalsInfo info) {
        //显示提现规则
        HtmlBuilder hBuilder = HtmlBuilder.newInstance();
        hBuilder.appendGray(String.format("会员乐分：%s;", PriceUtil.format4Decimal(info.getMotivateLeScore()))).appendNextLine();//会员乐分
        UserLoginData userData = LoginModel.getInstance().getUserLogin();
        if (userData != null) {
            if (userData.getAgent() != null && userData.getAgent().getAgencyLevel() != null) {
                //代理商
                hBuilder.appendGray(String.format("代理商乐分：%s;", PriceUtil.format4Decimal(info.getAgentLeScore()))).appendNextLine();//代理商乐分
            }
            if (userData.isIsSalesman()) {
                //业务员
                hBuilder.appendGray(String.format("业务员乐分：%s;", PriceUtil.format4Decimal(info.getIncomeLeScore()))).appendNextLine();//业务员乐分
            }
        }
        hBuilder.appendGray("提现预计到帐时间为1-3个工作日。");
        if (!TextUtils.isEmpty(info.getMotivateRule())) {
            hBuilder.appendNextLine().appendNextLine()
                    .appendGray("提现规则：").appendNextLine()
                    .appendGray(info.getMotivateRule());
        }
        setViewText(hBuilder.create(), R.id.withdrawals_tv_rules);
    }

    @Override
    public void withdrawalsSuccess() {
        EventBus.getDefault().post(new DataEvent.OnBankCardAddSuccess());
        finish();
    }

    /**
     * 获取提现金额
     *
     * @return
     */
    private double getWithdrawalsAmount() {
        return parseStringToDouble(mEdtAmount.getText().toString());
    }

    /**
     * 将字符串转换成小数
     *
     * @param text
     * @return
     */
    private double parseStringToDouble(String text) {
        double result = 0;
        if (!TextUtils.isEmpty(text)) {
            try {
                result = Double.parseDouble(text);
            } catch (NumberFormatException e) {

            }
        }
        return result;
    }

    /**
     * 获取提现备注信息
     *
     * @return
     */
    private String getRemark() {
        return ((TextView) getViews(R.id.withdrawals_edt_remark)).getText().toString();
    }

    @Override
    public void setBankCardData(ResBankCardData data) {
        if (data == null) {
            return;
        }
        entityId = String.valueOf(data.getId());
        HtmlBuilder builer = HtmlBuilder.newInstance();
        builer.appendColor(data.getBankName(), R.color.text_white).appendNextLine()
                .appendColor(data.getCardType(), R.color.text_white).appendNextLine()
                .appendColor(StringUtil.keepLastWords(data.getCardNum(), '*', 4), R.color.text_white);
        setViewText(builer.create(), R.id.withdrawals_tv_account_name);
    }
}
