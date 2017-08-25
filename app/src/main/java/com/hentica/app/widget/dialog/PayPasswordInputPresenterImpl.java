package com.hentica.app.widget.dialog;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.util.request.Request;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/14.16:48
 */

public class PayPasswordInputPresenterImpl implements PayPasswordInputPresenter {

    private UsualFragment mFragment;

    private PayPasswordCheckView mCheckView;

    public PayPasswordInputPresenterImpl(UsualFragment mFragment, PayPasswordCheckView mCheckView) {
        this.mFragment = mFragment;
        this.mCheckView = mCheckView;
    }

    @Override
    public void setInputPassword(String payPwd) {
        if(TextUtils.isEmpty(payPwd)){
            mFragment.showToast("请输入支付密码！");
            return;
        }
        Request.getEndUserVerifyPayPwd(ApplicationData.getInstance().getLoginUserId(),
                payPwd, ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mFragment) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if (mCheckView != null) mCheckView.setCheckResult(isSuccess);
                            }
                        }));
    }

    public void detachView(){
        mCheckView = null;
    }
}
