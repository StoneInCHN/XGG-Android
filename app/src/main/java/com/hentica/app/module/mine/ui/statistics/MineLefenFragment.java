package com.hentica.app.module.mine.ui.statistics;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.module.entity.type.AgencyLevel;
import com.hentica.app.module.entity.type.LeScoreType;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.mine.presenter.MineProfilePresenter;
import com.hentica.app.module.mine.presenter.MineProfilePresenterImpl;
import com.hentica.app.module.mine.presenter.MineWechatBindPresenter;
import com.hentica.app.module.mine.presenter.MineWechatBindPresenterImpl;
import com.hentica.app.module.mine.ui.MineWithdrawalsFragment;
import com.hentica.app.module.mine.ui.adapter.QuickFragmentPagerAdapter;
import com.hentica.app.module.mine.view.MineProfileView;
import com.hentica.app.module.mine.view.MineWecahtBindView;
import com.hentica.app.util.ColorUtils;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.UmengLoginUtil;
import com.hentica.app.util.VerifyHelper;
import com.hentica.app.util.event.DataEvent;
import com.fiveixlg.app.customer.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 乐分界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineLefenFragment extends BaseFragment implements MineWecahtBindView, MineProfileView {

    private String mTitles[];
    private MineWechatBindPresenter mBindPresenter;
    private MineProfilePresenter mProfilePresenter;

    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.lefen_pager)
    ViewPager mLefenPager;
    LefenPageAdapter mAdapter;

    private List<MineLefenStatisticsFragment> mMineLefenStatisticsFragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.mine_statistics_list_lefen_fragment;
    }

    @Override
    protected void initData() {
        mBindPresenter = new MineWechatBindPresenterImpl(this);
        mProfilePresenter = new MineProfilePresenterImpl(this);
        mProfilePresenter.getUserProfile();

        UserLoginData userData = LoginModel.getInstance().getUserLogin();
        if (userData == null) {
            finish();
        } else {
            initTitlesFragment(userData);
        }
        initTabNavigator();
        mAdapter = new LefenPageAdapter(getFragmentManager());
        mAdapter.setFragments(mMineLefenStatisticsFragments);
        mLefenPager.setAdapter(mAdapter);
    }

    /**
     * 初始化界面标题与子界面
     * @param userData
     */
    private void initTitlesFragment(UserLoginData userData){
        mMineLefenStatisticsFragments.clear();
        List<String> titles = new ArrayList<>();
        titles.add("分红");
        mMineLefenStatisticsFragments.add(new MineLefenStatisticsFragment(LeScoreType.BONUS));
        if (userData.getAgent() != null) {
            String userAgentLevel = userData.getAgent().getAgencyLevel();
            if (AgencyLevel.PROVINCE.equals(userAgentLevel)) {
                titles.add("省代理");
            } else if (AgencyLevel.CITY.equals(userAgentLevel)) {
                titles.add("市代理");
            } else if (AgencyLevel.COUNTY.equals(userAgentLevel)) {
                titles.add("区代理");
            } else if (AgencyLevel.TOWN.equals(userAgentLevel)) {
                titles.add("镇代理");
            } else {
                titles.add("代理");
            }
            mMineLefenStatisticsFragments.add(new MineLefenStatisticsFragment(LeScoreType.AGENT));
        }
        if (userData.isIsSalesman()) {
            titles.add("业务员");
            mMineLefenStatisticsFragments.add(new MineLefenStatisticsFragment(LeScoreType.RECOMMEND_SELLER));
        }
        titles.add("好友");
        mMineLefenStatisticsFragments.add(new MineLefenStatisticsFragment(LeScoreType.RECOMMEND_USER));
        titles.add("消费");
        mMineLefenStatisticsFragments.add(new MineLefenStatisticsFragment(LeScoreType.CONSUME));
        titles.add("提现");
        mMineLefenStatisticsFragments.add(new MineLefenStatisticsFragment(LeScoreType.WITHDRAW));
        titles.add("转账");
        mMineLefenStatisticsFragments.add(new MineLefenStatisticsFragment(LeScoreType.TRANSFER));
        mTitles = titles.toArray(new String[titles.size()]);
    }

    private void initTabNavigator(){
        CommonNavigator navigator = new CommonNavigator(getContext());
        navigator.setAdjustMode(mTitles.length <= 5);
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                SimplePagerTitleView title = new SimplePagerTitleView(getContext());
                title.setText(mTitles[i]);
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLefenPager.setCurrentItem(i);
                    }
                });
                title.setNormalColor(Color.parseColor(ColorUtils.getColorResourceString(getContext(), R.color.text_black)));
                title.setSelectedColor(Color.parseColor(ColorUtils.getColorResourceString(getContext(), R.color.text_red)));
                return title;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(getContext());
                indicator.setLineWidth(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setLineHeight(dp2px(3));
                indicator.setColors(getResources().getColor(R.color.text_red));
                return indicator;
            }
        });
        mIndicator.setNavigator(navigator);
        ViewPagerHelper.bind(mIndicator, mLefenPager);
    }

    @Override
    protected void initView() {
        refreshUI();
    }

    @Override
    protected void setEvent() {
        //提现按钮
        setViewClickEvent(R.id.lefen_btn_withdrawals, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoginData user = LoginModel.getInstance().getUserLogin();
                if (user != null) {
                    toWithdrawalsFragment();
                }
            }
        });
        setViewClickEvent(R.id.lefen_btn_transfer, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoginData user = LoginModel.getInstance().getUserLogin();
                if (user != null) {
                    FragmentJumpUtil.toTransferFragment(getUsualFragment(), 2);
                }
            }
        });
    }

    /**
     * 去提现界面
     */
    private void toWithdrawalsFragment() {
        UserLoginData data = LoginModel.getInstance().getUserLogin();
        if (data == null) {
            finish();
        } else {
            VerifyHelper helper = VerifyHelper.newInstance();
            helper.initData(MineWithdrawalsFragment.class, null);
            helper.startFragment(getActivity(), data.isAuth(), data.isOwnBankCard());
        }
    }

    /**
     * 绑定微信
     */
    private void bindWechat() {
        getWeichatOpenId(new UmengLoginUtil.OnLoginCompleteListener2() {
            @Override
            public void onLoginSuccess(String uuid, String openId, String nickName) {
                mBindPresenter.toBindWechat(openId, nickName);
            }

            @Override
            public void onLoginSuccess(String account, String nickName) {
            }

            @Override
            public void onLoginFailed() {
            }
        });
    }

    /**
     * 获取微信授权信息
     *
     * @param l
     */
    private void getWeichatOpenId(UmengLoginUtil.OnLoginCompleteListener l) {
        UmengLoginUtil umengLoginUtil = new UmengLoginUtil(getUsualFragment());
        umengLoginUtil.loginWeixin(l);
    }

    private void refreshUI() {
        TextView mTvTotal = getViews(getStatisticsItemLeft());//累计积分
        TextView mTvTotalValue = getViews(getStatisticsItemLeftValue());
        TextView mTvCurrent = getViews(getStatisticsItemRight());//当前积分
        TextView mTvCurrentValue = getViews(getStatisticsItemRightValue());
        mTvTotal.setText("累计乐分");
        mTvCurrent.setText("当前乐分");
        UserLoginData userData = LoginModel.getInstance().getUserLogin();
        mTvTotalValue.setText(PriceUtil.format4Decimal(userData == null ? 0 : userData.getTotalLeScore()));
        mTvCurrentValue.setText(PriceUtil.format4Decimal(userData == null ? 0 : userData.getCurLeScore()));
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshUI();
    }

    /**
     * 获取左侧统计标签
     */
    protected
    @IdRes
    int getStatisticsItemLeft() {
        return R.id.mine_statistics_tv_label_1;
    }

    /**
     * 获取左侧统计显示值控件
     */
    protected
    @IdRes
    int getStatisticsItemLeftValue() {
        return R.id.mine_statistics_tv_value_1;
    }

    /**
     * 获取右侧统计项标签
     */
    protected
    @IdRes
    int getStatisticsItemRight() {
        return R.id.mine_statistics_tv_label_2;
    }

    /**
     * 获取右侧统计显示值控件
     */
    protected
    @IdRes
    int getStatisticsItemRightValue() {
        return R.id.mine_statistics_tv_value_2;
    }

    public void onEvent(DataEvent.OnLoginEvent event) {
        refreshUI();
    }

    @Override
    public void bindSuccess() {
        toWithdrawalsFragment();
    }

    @Override
    public void unBindSuccess() {

    }

    @Subscribe
    public void onEvent(DataEvent.OnBankCardAddSuccess event) {
        //更新用户资料
        mProfilePresenter.getUserProfile();
    }

    @Override
    public void loadProfileSuccess(ResUserProfile userData) {
        refreshUI();
    }

    private class LefenPageAdapter extends QuickFragmentPagerAdapter<MineLefenStatisticsFragment> {
        public LefenPageAdapter(FragmentManager fm) {
            super(fm);
        }
    }
}
