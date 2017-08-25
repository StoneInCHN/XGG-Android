package com.hentica.app.module.mine.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hentica.app.module.common.adapter.CommonSlideAdapter;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.type.OrderStatus;
import com.hentica.app.util.ColorUtils;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TabTitle;
import com.hentica.app.widget.view.TitleView;
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
 * 我的订单界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
@SuppressLint("ValidFragment")
public class MineOrdersFragment extends BaseFragment {

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.mine_orders_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    /** 界面集合 */
    List<MineCommonOrderFragment> mFragments = new ArrayList<>();
    private boolean isSallerOrders = false;
    /** viewpager适配器 */
    private CommonSlideAdapter mAdapter;

    private String[] mTitls ={"全部", "未支付", "待评价", "已完成"};

    public MineOrdersFragment(boolean isSallerOrders) {
        this.isSallerOrders = isSallerOrders;
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_orders_fragment;
    }

    @Override
    protected void initData() {
        mAdapter = new CommonSlideAdapter<BaseFragment>(getChildFragmentManager());
        mFragments.add(new MineCommonOrderFragment(OrderStatus.ALL, isSallerOrders)); // 全部订单
        mFragments.add(new MineCommonOrderFragment(OrderStatus.UNPAID, isSallerOrders)); // 全部订单
        mFragments.add(new MineCommonOrderFragment(OrderStatus.PAID, isSallerOrders)); // 待评价订单
        mFragments.add(new MineCommonOrderFragment(OrderStatus.FINISHED, isSallerOrders)); // 已完成订单
        mAdapter.setFragments(mFragments);
    }

    @Override
    protected void initView() {
        mViewPager.setAdapter(mAdapter);
        initIndicator(mIndicator);
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }

    private void initIndicator(MagicIndicator indicator){
        if (indicator == null) {
            return;
        }
        CommonNavigator navigator = new CommonNavigator(getContext());
        navigator.setAdjustMode(true);
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitls.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                SimplePagerTitleView titleView = new SimplePagerTitleView(getContext());
                titleView.setText(mTitls[i]);
                titleView.setSelectedColor(Color.parseColor(ColorUtils.getColorResourceString(getContext(), R.color.text_red)));
                titleView.setNormalColor(Color.parseColor(ColorUtils.getColorResourceString(getContext(), R.color.text_black)));
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_28));
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(i);
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(getContext());
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setLineWidth(dp2px(3));
                indicator.setColors(getResources().getColor(R.color.text_red));
                indicator.setPadding(getResources().getDimensionPixelOffset(R.dimen.view_padding), 0,
                        getResources().getDimensionPixelOffset(R.dimen.view_padding), 0);
                return indicator;
            }
        });
        indicator.setNavigator(navigator);
    }

    @Override
    protected void setEvent() {

    }

    /** 支付成功 */
    @Subscribe
    public void onEvent(DataEvent.OnPaySuccess event){
        finish();
    }
}
