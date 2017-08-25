package com.hentica.app.module.mine.ui.shop;

import android.app.ActionBar;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.PopupWindow;

import com.fiveixlg.app.customer.R;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.login.ResLoginData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.mine.util.BusinessCenterMenuUtil;
import com.hentica.app.util.DateHelper;
import com.hentica.app.widget.view.CustomCheckBox;
import com.hentica.app.widget.view.TitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by YangChen on 2017/7/1 10:41.
 * E-mail:656762935@qq.com
 */

public class ShopBusinessCenterFragment extends BaseFragment {

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.shop_business_center_indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.business_center_pro_check)
    CustomCheckBox mProCheck;
    @BindView(R.id.business_center_city_check)
    CustomCheckBox mCityCheck;
    @BindView(R.id.business_center_county_check)
    CustomCheckBox mCountyCheck;
    @BindView(R.id.shop_business_center_pager)
    ViewPager mViewPager;

    private PagerAdapter mPagerAdapter;
    private CommonNavigator mCommonNavigator;

    private List<FragmenrInfo> mFragments = new ArrayList<>();
    private PopupWindow mPopupWindow;
    private BusinessFilterView mFilterView;
    private BusinessCenterMenuUtil mMenuUtil;

    private ShopTradingVolumeFragment mFragment1;
    private ShopBusinessCountFragment mFragment2;
    private ShopCustomerCountFragment mFragment3;
    private ShopSalesmanCountFragment mFragment4;

    @Override
    public int getLayoutId() {
        return R.layout.shop_business_center_fragment;
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
    protected void initData() {

        ResLoginData.AgentBean agent = getAgent();
        String areaId = agent == null ? "" : agent.getAreaId()+"";
        mFragment1 = new ShopTradingVolumeFragment(areaId);
        mFragment2 = new ShopBusinessCountFragment(areaId);
        mFragment3 = new ShopCustomerCountFragment(areaId);
        mFragment4 = new ShopSalesmanCountFragment(areaId);

        mFragments.add(new FragmenrInfo("交易额", mFragment1));  // 交易额
        mFragments.add(new FragmenrInfo("商家数", mFragment2));  // 商家数
        mFragments.add(new FragmenrInfo("消费者数", mFragment3));  // 消费者数
        if(!isSalesMan()){
            mFragments.add(new FragmenrInfo("业务员数", mFragment4));  // 业务员数
        }
        mPagerAdapter = new PagerAdapter(getChildFragmentManager());

        mFilterView = new BusinessFilterView(getContext(), getUsualFragment());
        mPopupWindow = new PopupWindow(mFilterView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
    }

    @Override
    protected void initView() {

        mTitleView.setRightTextBtnText("筛选");
        mTitleView.getRightTextBtn().setVisibility(View.VISIBLE);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());

        mCommonNavigator = new CommonNavigator(getContext());
        mCommonNavigator.setAdjustMode(true);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context mContext, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(getContext());
                simplePagerTitleView.setText(mFragments.get(index).mNavTitle);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.text_gray));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.text_red));
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.text_28));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context mContext) {
                LinePagerIndicator indicator = new LinePagerIndicator(mContext);
                indicator.setColors(getResources().getColor(R.color.bg_red));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });

        mIndicator.setNavigator(mCommonNavigator);
        mMenuUtil = new BusinessCenterMenuUtil(getContext(), getAgent());
        mMenuUtil.bindViews(mProCheck, mCityCheck, mCountyCheck);
        mMenuUtil.setOnFilterListener(new BusinessCenterMenuUtil.OnFilterListener() {
            @Override
            public void onFilter(String areaId) {
                mFragment1.onChooseArea(areaId);
                mFragment2.onChooseArea(areaId);
                mFragment3.onChooseArea(areaId);
                mFragment4.onChooseArea(areaId);
            }
        });
    }

    @Override
    protected void setEvent() {

        // 筛选被点击
        mTitleView.setOnRightTextBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹出筛选
                mPopupWindow.showAsDropDown(mTitleView);
            }
        });

        mFilterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        mFilterView.setListener(new BusinessFilterView.OnCompleteListener() {
            @Override
            public void onComplete(String startDate, String endDate) {
                Date sDate = DateHelper.getDate(startDate);
                Date eDate = DateHelper.getDate(endDate);
                String sDateStamp = sDate == null ? "" : sDate.getTime()+"";
                String eDateStamp = eDate == null ? "" : eDate.getTime()+"";
                // 选择日期完成
                mFragment1.setFilterDate(sDateStamp, eDateStamp);
                mPopupWindow.dismiss();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCommonNavigator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mCommonNavigator.onPageSelected(position);
                boolean isTradingVolumeFragment = mPagerAdapter.getItem(position) instanceof ShopTradingVolumeFragment;
                mTitleView.getRightTextBtn().setVisibility(isTradingVolumeFragment ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mCommonNavigator.onPageScrollStateChanged(state);
            }
        });
    }

    /** 获取代理商信息 */
    private ResLoginData.AgentBean getAgent(){
        return LoginModel.getInstance().getUserAgent();
    }

    /** 判断用户是否是业务员 */
    private boolean isSalesMan(){
        UserLoginData loginData = LoginModel.getInstance().getUserLogin();
        return loginData == null ? false : loginData.isIsSalesman();
    }

    /** viewpager适配器 */
    private class  PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(mFragments != null && position < mFragments.size()){
                return mFragments.get(position).mFragment;
            }
            throw new IllegalStateException("No fragment at position " + position);
        }

        @Override
        public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }
    }

    /** 界面信息 */
    private class FragmenrInfo{
        String mNavTitle;  // 导航栏标题
        BaseFragment mFragment;

        public FragmenrInfo(String navTitle, BaseFragment fragment){
            mNavTitle = navTitle;
            mFragment = fragment;
        }
    }
}
