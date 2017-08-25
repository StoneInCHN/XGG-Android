package com.hentica.app.module.mine.ui.shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.fragment.CommonPtrFragment;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.shop.ResShopInfo;
import com.hentica.app.module.entity.mine.shop.ResShopOrderItem;
import com.hentica.app.module.entity.type.ClearStatus;
import com.hentica.app.module.entity.type.OrderStatus;
import com.hentica.app.module.mine.presenter.OrderManagerPtrPresenter;
import com.hentica.app.module.mine.presenter.shop.ShopInfoPresenter;
import com.hentica.app.module.mine.presenter.shop.ShopInfoPresenterImpl;
import com.hentica.app.module.mine.ui.adapter.OrderManagerAdapter;
import com.hentica.app.module.mine.ui.adapter.QuickFragmentPagerAdapter;
import com.hentica.app.module.mine.view.shop.ShopInfoView;
import com.hentica.app.util.ColorUtils;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.PriceUtil;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单管理界面
 * intent.putExtra(DATA_SHOP_INFO, ResShopInfo)
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
@SuppressLint("ValidFragment")
public class MineOrderManagerFragment extends BaseFragment implements ShopInfoView {
    private String[] mTitles;
    private ShopInfoPresenter mShopInfoPresenter;
    private ResShopInfo mShopInfo;
    private List<MineOrderPageFragment> mMineOrderPageFragments = new ArrayList<>();

    @BindView(R.id.order_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.indicator)
    MagicIndicator mIndicator;

    private boolean isSallerOrder;

    private OrderPageAdapter mAdapter;

    public MineOrderManagerFragment(ResShopInfo shopInfo, boolean isSallerOrder) {
        mShopInfo = shopInfo;
        this.isSallerOrder = isSallerOrder;
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_order_manager_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    protected void initData() {
        mShopInfoPresenter = new ShopInfoPresenterImpl(this);
        mShopInfoPresenter.getShopInfo();
        mAdapter = new OrderPageAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected void initView() {
        initTitleFragments();
        initNavigator();
        ViewPagerHelper.bind(mIndicator, mViewPager);
        refreshUI(mShopInfo);
    }

    /**
     * 初始化Tab标题
     */
    private void initTitleFragments(){
        mMineOrderPageFragments.clear();
        if (!isSallerOrder) {
            mTitles = new String[]{"全部", "已支付", "已完成", "结算中", "已结算"};
            mMineOrderPageFragments.add(new MineOrderPageFragment(mShopInfo, OrderStatus.ALL, ClearStatus.ALL, ""));
            mMineOrderPageFragments.add(new MineOrderPageFragment(mShopInfo, OrderStatus.PAID, ClearStatus.ALL, "已支付"));
            mMineOrderPageFragments.add(new MineOrderPageFragment(mShopInfo, OrderStatus.FINISHED, ClearStatus.ALL, "已完成"));
            mMineOrderPageFragments.add(new MineOrderPageFragment(mShopInfo, OrderStatus.ALL, ClearStatus.IS_CLEARING, "结算中"));
            mMineOrderPageFragments.add(new MineOrderPageFragment(mShopInfo, OrderStatus.ALL, ClearStatus.FINISHED, "已结算"));
        } else {
            mTitles = new String[]{"全部", "未支付", "已支付", "已完成"};
            mMineOrderPageFragments.add(new MineOrderSallerPageFragment(mShopInfo, OrderStatus.ALL, ClearStatus.ALL, ""));
            mMineOrderPageFragments.add(new MineOrderSallerPageFragment(mShopInfo, OrderStatus.UNPAID, ClearStatus.ALL, "未支付"));
            mMineOrderPageFragments.add(new MineOrderSallerPageFragment(mShopInfo, OrderStatus.PAID, ClearStatus.ALL, "已支付"));
            mMineOrderPageFragments.add(new MineOrderSallerPageFragment(mShopInfo, OrderStatus.FINISHED, ClearStatus.ALL, "已完成"));
        }
        mAdapter.setFragments(mMineOrderPageFragments);
    }

    private void initNavigator(){
        CommonNavigator navigator = new CommonNavigator(getContext());
        navigator.setAdjustMode(true);
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
                        mViewPager.setCurrentItem(i);
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
    }

    @Override
    protected void setEvent() {

    }

    private void refreshUI(ResShopInfo mShopInfo){
        if(mShopInfo  != null){
            if (!isSallerOrder) {
                HtmlBuilder htmlBuilder = HtmlBuilder.newInstance();
                //订单数量
                htmlBuilder.appendNormal("支付订单总数").appendNextLine().appendRed(String.valueOf(mShopInfo.getTotalOrderNum()));
                setViewText(htmlBuilder.create(), R.id.order_tv_order_count);
                //总金额
                htmlBuilder = HtmlBuilder.newInstance();
                htmlBuilder.appendNormal("支付总额(元)").appendNextLine().appendRed(PriceUtil.format(mShopInfo.getTotalOrderAmount()));
                setViewText(htmlBuilder.create(), R.id.order_tv_amount);
                //未结算金额
                htmlBuilder = HtmlBuilder.newInstance();
                htmlBuilder.appendNormal("未结算(元)").appendNextLine().appendRed(PriceUtil.format(mShopInfo.getUnClearingAmount()));
                setViewText(htmlBuilder.create(), R.id.order_tv_unclear_amount);
            } else {
                HtmlBuilder htmlBuilder = HtmlBuilder.newInstance();
                //订单数量
                htmlBuilder.appendNormal("支付订单总数").appendNextLine().appendRed(String.valueOf(mShopInfo.getTotalSellerOrderNum()));
                setViewText(htmlBuilder.create(), R.id.order_tv_order_count);
                //总金额
                htmlBuilder = HtmlBuilder.newInstance();
                htmlBuilder.appendNormal("支付总额(元)").appendNextLine().appendRed(PriceUtil.format(mShopInfo.getTotalSellerOrderAmount()));
                setViewText(htmlBuilder.create(), R.id.order_tv_amount);
                setViewVisiable(false, R.id.order_tv_unclear_amount);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mShopInfo == null){
            mShopInfoPresenter.getShopInfo();
        }else{
//            if(mPtrPresenter != null) {
//                mPtrPresenter.onRefresh();
//            }
        }
    }

    @Override
    public void setShopInfoData(ResShopInfo data) {
        if(mShopInfo == null && data != null){
            mShopInfo = data;
//            if(mPtrPresenter != null){
//                mPtrPresenter.setEntityId(mShopInfo.getId()+"");
//                mPtrPresenter.onRefresh();
//            }
            refreshUI(mShopInfo);
        }
    }

    @Override
    public void failure() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mShopInfoPresenter != null) {
            mShopInfoPresenter.getShopInfo();
        }
    }

    private class OrderPageAdapter extends QuickFragmentPagerAdapter<MineOrderPageFragment>{
        public OrderPageAdapter(FragmentManager fm) {
            super(fm);
        }
    }

}
