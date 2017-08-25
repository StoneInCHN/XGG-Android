package com.hentica.app.module.mine.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.config.ConfigDataUtl;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.module.entity.type.AgencyLevel;
import com.hentica.app.module.entity.type.SellerStatus;
import com.hentica.app.module.index.IndexPayingFragment;
import com.hentica.app.module.listener.CallbackAdapter;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.mine.presenter.MineProfilePresenter;
import com.hentica.app.module.mine.presenter.MineProfilePresenterImpl;
import com.hentica.app.module.mine.ui.bank.BankCardAddFragment;
import com.hentica.app.module.mine.ui.bank.BankCardFragment;
import com.hentica.app.module.mine.ui.shop.MineShopFragment;
import com.hentica.app.module.mine.ui.shop.MineShopInfoFragment;
import com.hentica.app.module.mine.ui.shop.ShopBusinessCenterFragment;
import com.hentica.app.module.mine.ui.statistics.MineLefenLedouFragment;
import com.hentica.app.module.mine.ui.statistics.MineLefenLedouIndexLedouFragment;
import com.hentica.app.module.mine.ui.statistics.MineLexinFragment;
import com.hentica.app.module.mine.ui.statistics.MineScoreFragment;
import com.hentica.app.module.mine.ui.textcontent.AboutFragment;
import com.hentica.app.module.mine.view.MineProfileView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.GlideUtil;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.VerifyHelper;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrScrollView;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.Subscribe;

import static com.fiveixlg.app.customer.R.id.mine_lnv_suggest;
import static com.fiveixlg.app.customer.R.id.multiply;

/**
 * 我的主界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineMainFragment extends BaseFragment implements View.OnClickListener, MineProfileView {

    private CustomPtrScrollView mPtrScrollView;

    private MineProfilePresenter mProfilePresenter;

    private long count;

    private UserLoginData mUserLoginData;

    @Override
    public int getLayoutId() {
        return R.layout.mine_main_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    protected TitleView initTitleView() {
        return null;
    }

    @Override
    protected void initData() {
        mProfilePresenter = new MineProfilePresenterImpl(this);
    }

    @Override
    protected void initView() {
        mPtrScrollView = getViews(R.id.mine_ptr_scv);

        ViewUtil.setStatusBarVisible(getView(), getViews(R.id.mine_status_bar), getContext(), true);
        //用户登录，获取用户数据
        if (LoginModel.getInstance().isLogin()) {
            mProfilePresenter.getUserProfile();
        } else {
            refreshUI(null);
        }
        ConfigDataUtl configDataUtl = ConfigDataUtl.getInstance();
        //获取客服电话
        configDataUtl.getServicePhone(new CallbackAdapter<String>() {
            @Override
            public void callback(boolean isSuccess, String data) {
                super.callback(isSuccess, data);
                if (isSuccess) {
                    setViewText(data, R.id.mine_tv_phone);
                }
            }
        });
        //获取营业时间
        configDataUtl.getBusinessTime(new CallbackAdapter<String>() {
            @Override
            public void callback(boolean isSuccess, String data) {
                super.callback(isSuccess, data);
                if (isSuccess) {
                    setViewText(String.format("注：服务时间为%s", data), R.id.mine_tv_time);
                }
            }
        });
    }

    /**
     * 显示用户数据
     *
     * @param profileData 为null表示用户未登录
     */
    private void refreshUI(UserLoginData profileData) {
        mUserLoginData = profileData;
        if (profileData != null) {
            //显示登录数据
            getViews(R.id.mine_layout_profile).setEnabled(false);//登录——不可点击
            setViewVisiable(false, R.id.mine_tv_login_tip);//隐藏点击登录提示
            setViewVisiable(true, R.id.mine_layout_profile_detail);//显示用户信息详情
            setViewVisiable(true, R.id.mine_btn_edit_profile);//显示修改资料按钮
            setUserProfile(profileData);
        } else {
            //显示未登录数据
            ((ImageView) getViews(R.id.mine_img_icon)).setImageResource(R.drawable.rebate_mine_head_bg_acquiescent);//设置默认头像
            getViews(R.id.mine_layout_profile).setEnabled(true);//未登录——可点击
            setViewVisiable(true, R.id.mine_tv_login_tip);//显示点击登录提示
            setViewVisiable(false, R.id.mine_layout_profile_detail);//隐藏用户信息详情
            setViewVisiable(false, R.id.mine_btn_edit_profile);//隐藏修改资料按钮

            //显示用户积分数量
            setViewText("积分\n" + PriceUtil.format(0), R.id.mine_opt_scores);
            //显示用户乐心数量
            setViewText("乐心\n" + 0, R.id.mine_opt_lx);
            //显示用户乐豆数量
            setViewText("乐豆\n" + PriceUtil.format(0), R.id.mine_opt_ld);
            //显示用户乐分数量
            setViewText("乐分\n" + PriceUtil.format(0), R.id.mine_opt_lf);
            setViewVisiable(false, R.id.layout_member_info);
        }
    }

    /**
     * 设置用户信息
     */
    private void setUserProfile(UserLoginData userData) {
        if (userData == null) {
            return;
        }
        ImageView imgIcon = getViews(R.id.mine_img_icon);
        GlideUtil.loadHeadIconDefault(mContext, ApplicationData.getInstance().getImageUrl(userData.getUserPhoto()), imgIcon, R.drawable.rebate_mine_head_bg_acquiescent);//用户头像
        //是商家时，显示商家标志
        setViewVisiable(SellerStatus.YES.equals(userData.getSellerStatus()), R.id.mine_profile_tv_seller_label);
        //代理商
        setUserAgentInfo(userData);
        //昵称
        setViewText(userData.getNickName(), R.id.mine_profile_tv_name);
        //电话
        setViewText(userData.getCellPhoneNum(), R.id.mine_profile_tv_phone);
        //推荐人
        if (!TextUtils.isEmpty(userData.getRecommender())) {
            StringBuilder sBuilder = new StringBuilder("推荐人：").append(userData.getRecommender());
            setViewText(sBuilder.toString(), R.id.mine_profile_tv_recommond);
            setViewVisiable(true, R.id.mine_profile_tv_recommond);
        } else {
            setViewVisiable(false, R.id.mine_profile_tv_recommond);
        }
        //显示用户积分数量
        setViewText("积分\n" + PriceUtil.format(userData.getCurScore()), R.id.mine_opt_scores);
        //显示用户乐心数量
        setViewText("乐心\n" + userData.getCurLeMind(), R.id.mine_opt_lx);
        //显示用户乐豆数量
        setViewText("乐豆\n" + PriceUtil.format(userData.getCurLeBean()), R.id.mine_opt_ld);
        //显示用户乐分数量
        setViewText("乐分\n" + PriceUtil.format(userData.getCurLeScore()), R.id.mine_opt_lf);
        boolean isSeller = false;
        if(userData.getSellerStatus() != null && !userData.getSellerStatus().equals(SellerStatus.NO)){
            isSeller = true;
        }
        //显示我是商家
        setViewVisiable(isSeller, R.id.mine_lnv_seller);
        setViewVisiable(isSeller, R.id.divider_seller);
        boolean isSalesman = userData.isIsSalesman();
        //显示我是业务员
        setViewVisiable(isSalesman, R.id.mine_lnv_salesman);
        setViewVisiable(isSalesman, R.id.divider_sales_man);
        //显示我是代理商
        boolean isAgent = false;
        if (userData.getAgent() != null && !TextUtils.isEmpty(userData.getAgent().getAgencyLevel())){
            isAgent = true;
        }
        setViewVisiable(isAgent, R.id.mine_lnv_agency);
        setViewVisiable(isAgent, R.id.divider_agency);
        if(isSeller || isSalesman || isAgent){
            setViewVisiable(true, R.id.layout_member_info);
        }else{
            setViewVisiable(false, R.id.layout_member_info);
        }

    }

    /**
     * 设置用户代理商信息
     */
    private void setUserAgentInfo(UserLoginData userData) {
        TextView agency = getViews(R.id.mine_profile_tv_agent_label);
        agency.setVisibility(View.GONE);
        if (userData == null) {
            return;
        }
        if (userData.getAgent() == null) {
            return;
        }
        //是代理商，显示代理商标志T
        String agencyLevel = userData.getAgent().getAgencyLevel();
        if (TextUtils.isEmpty(agencyLevel)) {
            return;
        }
        agency.setVisibility(View.VISIBLE);
        String text = "";
        int imgId = R.drawable.rebate_mine_agent1;
        if (AgencyLevel.PROVINCE.equals(agencyLevel)) {
            //省代理
            text = "省代理";
            imgId = R.drawable.rebate_mine_agent4;
        } else if (AgencyLevel.CITY.equals(agencyLevel)) {
            //市代理
            text = "市代理";
            imgId = R.drawable.rebate_mine_agent3;
        } else if (AgencyLevel.COUNTY.equals(agencyLevel)) {
            //县代理
            text = "县代理";
            imgId = R.drawable.rebate_mine_agent2;
        } else if (AgencyLevel.TOWN.equals(agencyLevel)) {
            //镇代理
            text = "乡代理";
            imgId = R.drawable.rebate_mine_agent1;
        }
        setViewText(text, R.id.mine_profile_tv_agent_label);
        agency.setBackgroundResource(imgId);
    }

    @Override
    protected void setEvent() {
        getViews(R.id.mine_lnv_suggest).setOnClickListener(this);
        getViews(R.id.mine_lnv_orders).setOnClickListener(this);
        getViews(R.id.mine_lnv_shop).setOnClickListener(this);
        getViews(R.id.mine_lnv_collections).setOnClickListener(this);
        getViews(R.id.mine_lnv_helps).setOnClickListener(this);
        getViews(R.id.mine_lnv_about).setOnClickListener(this);
        getViews(R.id.mine_lnv_setting).setOnClickListener(this);
        getViews(R.id.mine_lnv_services).setOnClickListener(this);
        getViews(R.id.mine_opt_scores).setOnClickListener(this);
        getViews(R.id.mine_opt_lx).setOnClickListener(this);
        getViews(R.id.mine_opt_ld).setOnClickListener(this);
        getViews(R.id.mine_opt_lf).setOnClickListener(this);
        getViews(R.id.mine_btn_edit_profile).setOnClickListener(this);
        getViews(R.id.mine_lnv_seller).setOnClickListener(this);
        getViews(R.id.mine_lnv_salesman).setOnClickListener(this);
        getViews(R.id.mine_lnv_agency).setOnClickListener(this);
        getViews(R.id.mine_lnv_bank_card).setOnClickListener(this);
        getViews(R.id.mine_layout_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                未登录跳转登录界面
                if (!LoginModel.getInstance().isLogin()) {
                    toLogin();
                }
            }
        });
        //下拉刷新ScrollView
        mPtrScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mProfilePresenter.getUserProfile();
            }
        });

    }

    /**
     * 检查用户是否登录，以确认是否可以进行下一步操作
     *
     * @param checkLogin true，要检查是否登录，根据登录结果进行送料，false，不需要检查可以直接进行下一步
     * @return
     */
    private boolean canContinue(boolean checkLogin) {
        if (checkLogin) {
            //检查是否登录
            return LoginModel.getInstance().isLogin();
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case mine_lnv_suggest://推荐有礼
                if (canContinue(true)) {
                    startFrameActivity(MineRecommendFragment.class);
                } else {
                    toLogin();
                }
                break;
            case R.id.mine_lnv_orders://我的订单
                if (canContinue(true)) {
                    // 跳转到订单列表
//                    startFrameActivity(MineOrdersFragment.class);
                    startFrameActivity(MineOrdersMainFragment.class);
                } else {
                    toLogin();
                }
                break;
//            case R.id.mine_lnv_shop://我的店铺
            case R.id.mine_lnv_seller://我的店铺
                if (canContinue(true)) {
                    toMyShop();
                } else {
                    toLogin();
                }
                count++;
                break;
            case R.id.mine_lnv_collections://我的收藏
                if (canContinue(true)) {
                    startFrameActivity(MineCollectFragment.class);
                } else {
                    toLogin();
                }
                break;
            case R.id.mine_lnv_helps://用户帮助
                startFrameActivity(MineHelpFragment.class);
                break;
            case R.id.mine_lnv_about://关于我们
                startFrameActivity(AboutFragment.class);
                break;
            case R.id.mine_lnv_setting://设置
                startFrameActivity(MineSettingFragment.class);
                break;
            case R.id.mine_lnv_services://客服电话
                toCallServices();
                break;
            case R.id.mine_opt_scores://积分
                if (canContinue(true)) {
                    startFrameActivity(MineScoreFragment.class);
                }
                break;
            case R.id.mine_opt_lx://乐心
                if (canContinue(true)) {
                    startFrameActivity(MineLexinFragment.class);
                }
                break;
            case R.id.mine_opt_ld://乐豆
                if (canContinue(true)) {
                    startFrameActivity(MineLefenLedouIndexLedouFragment.class);
                }
                break;
            case R.id.mine_opt_lf://乐分
                if (canContinue(true)) {
                    startFrameActivity(MineLefenLedouFragment.class);
                }
                break;
            case R.id.mine_btn_edit_profile:
//                startFrameActivity(MineProfileFragment.class);
                startActivity(new Intent(getContext(), MineProfileActivity.class));
                break;
            case R.id.mine_lnv_salesman://我是业务员
                startFrameActivity(MineSuggestedShopFragment.class);
                break;
            case R.id.mine_lnv_agency:// 我是代理商
                startFrameActivity(ShopBusinessCenterFragment.class);
                break;
            case R.id.mine_lnv_bank_card://我的银行卡
                if(canContinue(true)){
                    if(mUserLoginData != null){
                        VerifyHelper helper = VerifyHelper.newInstance();
                        helper.initData(BankCardFragment.class, null);
                        helper.startFragment(getActivity(), mUserLoginData.isAuth());
                    }
                }else{
                    toLogin();
                }
                break;
        }
    }

    /**
     * 跳转我的店铺
     */
    private void toMyShop() {
        //
        UserLoginData userData = LoginModel.getInstance().getUserLogin();
        if (TextUtils.equals(SellerStatus.YES, userData.getSellerStatus())) {
            // 拥有店铺
            startFrameActivity(MineShopInfoFragment.class);
        } else {
            startFrameActivity(MineShopFragment.class);
        }
    }

    /**
     * 联系客服
     */
    private void toCallServices() {
        String phone = ApplicationData.getInstance().getmServicePhone();
        FragmentJumpUtil.toCalling(getUsualFragment(), phone);
    }

    /**
     * 跳转登录界面
     */
    private void toLogin() {
        FragmentJumpUtil.tryToLogin(getUsualFragment());
    }

    @Subscribe
    public void onEvent(DataEvent.OnLoginEvent event) {
        mProfilePresenter.getUserProfile();
    }

    @Subscribe
    public void onEvent(DataEvent.OnLoginInvaildEvent event) {
        // 用户退出登录
        refreshUI(null);
    }


    @Override
    public void onStart() {
        super.onStart();
        refreshUserProfile();
    }

    private void refreshUserProfile() {
        if (canContinue(true)) {
            mPtrScrollView.pullDownRefresh();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            refreshUserProfile();
        }
    }

    @Override
    public void loadProfileSuccess(ResUserProfile userData) {
        refreshUI(userData);
    }

    //-----------------------------------------接收EventBus事件--------------------------------------

    @Subscribe
    public void onEvent(DataEvent.OnToUpdataUserProfileEvent event) {
        //更新用户资料
        mProfilePresenter.getUserProfile();
    }

    @Override
    public void dismissLoadingDialog() {
        super.dismissLoadingDialog();
        mPtrScrollView.onRefreshComplete();
    }
}
