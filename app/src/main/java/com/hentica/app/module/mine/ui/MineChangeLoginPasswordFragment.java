package com.hentica.app.module.mine.ui;

import android.app.Activity;

import com.hentica.app.module.login.LoginFindPwdFragment;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.login.presenter.LoginFindPwdPresenter;
import com.hentica.app.module.login.presenter.UpdatePwdPresenterImpl;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

/**
 * 修改密码界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineChangeLoginPasswordFragment extends LoginFindPwdFragment {

    public static final int REQUEST_CODE = 0x1;

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setTitleText("登录密码");
    }

    @Override
    protected LoginFindPwdPresenter getFindPwdPresenter() {
        return new UpdatePwdPresenterImpl(this, false);
    }

    @Override
    protected void initView() {
        super.initView();
        UserLoginData data = LoginModel.getInstance().getUserLogin();
        if(data == null){
            return;
        }
        setViewText(data.getCellPhoneNum(), R.id.account_edt_phone);
    }

    @Override
    protected void setEvent() {
        super.setEvent();
        getViews(R.id.account_edt_phone).setEnabled(false);
    }

    @Override
    public void onFindPwdSuccess() {
        getActivity().setResult(Activity.RESULT_OK);
        super.onFindPwdSuccess();
    }
}
