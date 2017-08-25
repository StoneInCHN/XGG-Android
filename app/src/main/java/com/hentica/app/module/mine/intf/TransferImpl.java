package com.hentica.app.module.mine.intf;

import android.text.Editable;
import android.text.TextWatcher;

import com.hentica.app.util.TextWatcherAdapter;


/**
 * Created by Snow on 2017/8/14.
 */

public class TransferImpl implements Transfer {

    private String mTitle;
    private String mAmountDesc;
    private double mCurAmount;
    private TextWatcherAdapter mTextWatcherAdapter;

    public TransferImpl(double amount, String amountDesc, String title, TextWatcherAdapter textWatcher){
        this.mCurAmount = amount;
        this.mAmountDesc = amountDesc;
        this.mTitle = title;
        this.mTextWatcherAdapter = textWatcher;
    }

    @Override
    public String title() {
        return mTitle;
    }

    @Override
    public double curAmount() {
        return mCurAmount;
    }

    @Override
    public String curAmountDesc() {
        return mAmountDesc;
    }

    public void setAmountDesc(String amountDesc) {
        mAmountDesc = amountDesc;
    }

    public void setCurAmount(double curAmount) {
        mCurAmount = curAmount;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mTextWatcherAdapter.beforeTextChanged(s, start, count, after);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mTextWatcherAdapter.onTextChanged(s, start, before, count);
    }

    @Override
    public void afterTextChanged(Editable s) {
        mTextWatcherAdapter.afterTextChanged(s);
    }
}
