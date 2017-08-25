package com.hentica.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.config.ConfigDataUtl;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.util.TextWatcherAdapter;
import com.hentica.app.util.request.Request;
import com.hentica.app.util.rsa.RsaUtils;
import com.hentica.app.widget.view.PasswordEditText;
import com.fiveixlg.app.customer.R;

/**
 * 支付密码检查对话框
 * Created by YangChen on 2017/4/11 19:40.
 * E-mail:656762935@qq.com
 */

public class PayPasswordInputDialog extends DialogFragment implements PayPasswordCheckView{

    /** 密码输入框 */
    private EditText mPasswordEdit;

    /** 取消按钮事件 */
    protected View.OnClickListener mCloseClickListener;

    /** 监听对话框取消事件 */
    protected PayPwdCheckListener mPayPwdCheckListener;

    private PayPasswordInputPresenter mInputPresenter;

    @Override
    public void setCheckResult(boolean isCheckOut) {
        if(mPayPwdCheckListener != null){
            mPayPwdCheckListener.result(isCheckOut);
        }
    }

    /** 支付密码检查监听 */
    public interface PayPwdCheckListener {

        /**
         * 检查
         * @param isCheckOut true：通过，false：未通过
         */
        void result(boolean isCheckOut);
    }

    private UsualFragment mFragment;

    public PayPasswordInputDialog(){

    }

    public void setFromUsualFragment(UsualFragment from){
        this.mFragment = from;
    }

    /**
     * 设置密码处理方式
     * @param presenter
     */
    public void setPayPwdInputPresenter(PayPasswordInputPresenter presenter){
        this.mInputPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.index_pay_dialog, null);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.setStyle(STYLE_NORMAL, R.style.full_screen_dialog);
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
        //设置事件
        setEvent();
    }

    private void initData(){

    }

    private void initView(){
        AQuery query = new AQuery(getView());
        mPasswordEdit = query.id(R.id.pay_dialog_password_edit).getEditText();
    }

    private void setEvent(){
        AQuery query = new AQuery(getView());
        // 点击空白处消失
        query.clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        query.id(R.id.pay_dialog_password_edit).getEditText()
                .addTextChangedListener(new TextWatcherAdapter(){
                    @Override
                    public void afterTextChanged(Editable s) {
                        super.afterTextChanged(s);
                        if( s.length() >= PasswordEditText.PASSWORD_LENGTH){
                            onCompleteBtnEvent();
                        }
                    }
                });
        // 关闭按钮
        query.id(R.id.pay_dialog_close_btn).clicked(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 取消界面
                dismiss();

                // 发出事件
                if (mCloseClickListener != null) {
                    mCloseClickListener.onClick(v);
                }
            }
        });
    }

    /**
     * 完成按钮事件
     */
    protected void onCompleteBtnEvent(){
        //隐藏输入键盘
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mPasswordEdit.getWindowToken(), 0);
        ConfigDataUtl configDataUtl = ConfigDataUtl.getInstance();
        configDataUtl.getRsaPublicKey(new Callback<String>() {
            @Override
            public void callback(boolean isSuccess, String data) {
                String pwd = getPayPwd();
                pwd = RsaUtils.encrypt(pwd);
                if(mInputPresenter != null) {
                    mInputPresenter.setInputPassword(pwd);
                }
            }

            @Override
            public void onFailed(NetData result) {

            }
        });
    }


    public String getPayPwd() {
        return mPasswordEdit.getText().toString();
    }

    public void setCloseClickListener(View.OnClickListener mCloseClickListener) {
        this.mCloseClickListener = mCloseClickListener;
    }

    /**
     * 设置支付密码检查监听
     * @param l
     */
    public void setPayPwdCheckListener(PayPwdCheckListener l) {
        this.mPayPwdCheckListener = l;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.showSoftInput(mPasswordEdit, 0);
            }
        }, 100);
    }

    @Override
    public void dismiss() {
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mPasswordEdit.getWindowToken(), 0);
        super.dismiss();
    }


}
