package com.hentica.app.module.mine.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.mine.presenter.MineWechatBindPresenter;
import com.hentica.app.module.mine.presenter.MineWechatBindPresenterImpl;
import com.hentica.app.module.mine.view.MineWecahtBindView;
import com.hentica.app.util.GlideUtil;
import com.hentica.app.util.UmengLoginUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;

/**
 * 绑定微信界面
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.17:07
 */

public class MineWechatBindFragment extends BaseFragment implements MineWecahtBindView{

    private MineWechatBindPresenter mBindPresenter;

    public static String DATA_NICK_NAME = "nickName";

    private String mStrNickName;

    @Override
    public int getLayoutId() {
        return R.layout.mine_wechat_bind_fragment;
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
        mStrNickName = intent.getStringExtra(DATA_NICK_NAME);
    }

    @Override
    protected void initData() {
        mBindPresenter = new MineWechatBindPresenterImpl(this);
    }

    @Override
    protected void initView() {
        UserLoginData userData = LoginModel.getInstance().getUserLogin();
        if(userData != null){
            ImageView img = getViews(R.id.mine_img_icon);
            GlideUtil.loadHeadIconDefault(getActivity(),
                    ApplicationData.getInstance().getImageUrl(userData.getUserPhoto()),
                    img, R.drawable.rebate_mine_head_bg_acquiescent);
        }
        setViewText(mStrNickName, R.id.bind_tv_nickname);
    }

    @Override
    protected void setEvent() {
        //解除授权
        setViewClickEvent(R.id.bind_btn_unbind, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBindPresenter.unBindWechat();
            }
        });
        //重新授权
        setViewClickEvent(R.id.bind_btn_rebind, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeichatOpenId(new UmengLoginUtil.OnLoginCompleteListener2() {
                    @Override
                    public void onLoginSuccess(String uuid, String openId, String nickName) {
                        mBindPresenter.toBindWechat(openId, nickName);
                        setViewText(nickName, R.id.bind_tv_nickname);
                    }
                    @Override
                    public void onLoginSuccess(String account, String nickName) {}
                    @Override
                    public void onLoginFailed() { }
                });
            }
        });
    }


    /**
     * 获取微信授权信息
     * @param l
     */
    private void getWeichatOpenId(UmengLoginUtil.OnLoginCompleteListener l){
        UmengLoginUtil umengLoginUtil = new UmengLoginUtil(getUsualFragment());
        umengLoginUtil.loginWeixin(l);
    }

    @Override
    public void bindSuccess() {
        showToast("操作成功！");
    }

    @Override
    public void unBindSuccess() {
        showToast("操作成功！");
        EventBus.getDefault().post(new DataEvent.OnToUpdataUserProfileEvent());
        finish();
    }
}
