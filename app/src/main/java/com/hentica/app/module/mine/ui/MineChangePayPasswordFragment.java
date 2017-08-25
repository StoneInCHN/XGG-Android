package com.hentica.app.module.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.hentica.app.module.login.presenter.LoginFindPwdPresenter;
import com.hentica.app.module.login.presenter.UpdatePwdPresenterImpl;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.rsa.RsaUtils;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;

/**
 * 修改支付密码界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineChangePayPasswordFragment extends MineChangeLoginPasswordFragment {

    public static final String RESULT_DATA_PAY_PWD = "payPassword";

    @Override
    public int getLayoutId() {
        return R.layout.login_find_pay_pwd_fragment;
    }

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setTitleText("支付密码");
    }

    @Override
    protected LoginFindPwdPresenter getFindPwdPresenter() {
        return new UpdatePwdPresenterImpl(this, true);
    }


    @Override
    public void onFindPwdSuccess() {
        showToast("操作成功！");
        Intent intent = new Intent();
        String encryptPayPwd = RsaUtils.encrypt(getPwd());
        Log.d(TAG, "onFindPwdSuccess: \n" + encryptPayPwd);
        intent.putExtra(RESULT_DATA_PAY_PWD, encryptPayPwd);
        getActivity().setResult(Activity.RESULT_OK, intent);
        EventBus.getDefault().post(new DataEvent.OnToUpdataUserProfileEvent());
        finish();
    }
}
