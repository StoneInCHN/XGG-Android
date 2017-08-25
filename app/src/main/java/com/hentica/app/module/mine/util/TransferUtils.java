package com.hentica.app.module.mine.util;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.mine.intf.Transfer;
import com.hentica.app.module.mine.intf.TransferImpl;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.TextWatcherAdapter;

/**
 * Created by Snow on 2017/8/14.
 */

public class TransferUtils {

    private TransferImpl mTransfer;
    private String type;
    private TextView mLabel;

    public TransferUtils(String type, EditText amount, TextView label) {
        if (amount == null || label == null) {
            throw new IllegalStateException("amount or label not be null!");
        }
        this.type = type;
        this.mLabel = label;
        UserLoginData data = LoginModel.getInstance().getUserLogin();
        if ("LE_MIND".equals(type)) {
            mTransfer = new TransferImpl(data.getCurLeMind(),
                    "当前乐心：" + data.getCurLeMind(),
                    "转账乐心",
                    new SingleWatcher(amount));
            amount.setInputType(EditorInfo.TYPE_CLASS_NUMBER|EditorInfo.TYPE_NUMBER_FLAG_SIGNED);
        } else if ("LE_BEAN".equals(type)) {
            mTransfer = new TransferImpl(data.getCurLeBean(),
                    "当前乐豆：" + PriceUtil.format4Decimal(data.getCurLeBean()),
                    "转账乐豆",
                    new Decimal4Watcher(amount));
        } else if ("LE_SCORE".equals(type)) {
            mTransfer = new TransferImpl(data.getCurLeScore(),
                    "当前乐分：" + PriceUtil.format4Decimal(data.getCurLeScore()),
                    "转账乐分",
                    new Decimal4Watcher(amount));
        }
        if (mTransfer != null) {
            label.setText(mTransfer.curAmountDesc());
        }
    }

    public Transfer getTransfer() {
        return mTransfer;
    }

    public void refreshAmount(){
        UserLoginData data = LoginModel.getInstance().getUserLogin();
        if ("LE_MIND".equals(type)) {
            mTransfer.setAmountDesc("当前乐心：" + data.getCurLeMind());
            mTransfer.setCurAmount(data.getCurLeMind());
        } else if ("LE_BEAN".equals(type)) {
            mTransfer.setAmountDesc("当前乐豆：" + PriceUtil.format4Decimal(data.getCurLeBean()));
            mTransfer.setCurAmount(data.getCurLeBean());
        } else if ("LE_SCORE".equals(type)) {
            mTransfer.setAmountDesc("当前乐分：" + PriceUtil.format4Decimal(data.getCurLeScore()));
            mTransfer.setCurAmount(data.getCurLeScore());
        }
        mLabel.setText(mTransfer.curAmountDesc());
    }

    private class Decimal4Watcher extends TextWatcherAdapter{
        private EditText mEdt;

        public Decimal4Watcher(EditText edt){
            this.mEdt = edt;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = s.toString();
            if (!text.contains(".")) {
                return;
            }
            //带小数点
            int dotIndex = text.indexOf(".");
            if (dotIndex + 5 < text.length()) {
                text = text.substring(0, dotIndex + 5);
                mEdt.setText(text);
                mEdt.setSelection(text.length());
            }
        }
    }

    private class SingleWatcher extends TextWatcherAdapter {
        private EditText mEdt;

        public SingleWatcher(EditText edt){
            this.mEdt = edt;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = s.toString();
            if (text.contains("-")) {
                text = text.replace("-", "");
                mEdt.setText(text);
                mEdt.setSelection(text.length());
            }
        }
    }

}
