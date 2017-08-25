package com.hentica.app.module.mine.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.fiveixlg.app.customer.R;
import com.hentica.app.module.common.adapter.CommonSlideAdapter;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.type.OrderStatus;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.ScaleTransitionPagerTitleView;
import com.hentica.app.widget.view.TabTitle;
import com.hentica.app.widget.view.TitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的订单主界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineOrdersMainFragment extends BaseFragment {

    public static final String[] mTitles = new String[]{"买单", "录单"};

    @BindView(R.id.common_title)
    TitleView mTitleView;

    @BindView(R.id.order_main_tab_title)
    MagicIndicator mIndicator;

    @BindView(R.id.order_main_pager)
    ViewPager mViewPager;

    /** 界面集合 */
    List<MineOrdersFragment> mFragments = new ArrayList<>();

    /** viewpager适配器 */
    private CommonSlideAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.mine_orders_main_fragment;
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
        mAdapter = new CommonSlideAdapter<MineOrdersFragment>(getChildFragmentManager());
        mFragments.add(new MineOrdersFragment(false)); // 买单
        mFragments.add(new MineOrdersFragment(true)); // 录单
        mAdapter.setFragments(mFragments);
    }

    @Override
    protected void initView() {
        mViewPager.setAdapter(mAdapter);
        CommonNavigator navigator = new CommonNavigator(getContext());
        navigator.setAdjustMode(true);
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ScaleTransitionPagerTitleView titleView = new ScaleTransitionPagerTitleView(getContext());
                titleView.setNormalColor(Color.WHITE);
                titleView.setSelectedColor(Color.WHITE);
                titleView.setText(mTitles[i]);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_38));
                titleView.setMinScale(0.737f);
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
                indicator.setColors(Color.WHITE);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(dp2px(3));
                indicator.setLineWidth(dp2px(60));
                return indicator;
            }
        });
        mIndicator.setNavigator(navigator);
        ViewPagerHelper.bind(mIndicator, mViewPager);
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
