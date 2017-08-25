package com.hentica.app.module.mine.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.fiveixlg.app.customer.R;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.shop.ResShopInfo;
import com.hentica.app.module.mine.model.ShopModel;
import com.hentica.app.module.mine.ui.adapter.QuickFragmentPagerAdapter;
import com.hentica.app.util.ColorUtils;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.ScaleTransitionPagerTitleView;
import com.hentica.app.widget.view.TitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单管理界面
 * intent.putExtra(DATA_SHOP_INFO, ResShopInfo)
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineOrderManagerMainFragment extends BaseFragment{

    public static final String DATA_SHOP_INFO = "ResShopInfo";
    private final String[] mTitles = {"买单", "录单"};
    private ResShopInfo mShopInfo;

    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.order_main_pager)
    ViewPager mViewPager;
    private OrderPagerAdapter mAdapter;

    public static final String PAGE_NUMBER = "pageNumber";
    private int mIndex = 0;

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_order_manager_main_fragment;
    }

    @Override
    protected void handleIntentData(Intent intent) {
        super.handleIntentData(intent);
        mShopInfo = new IntentUtil(intent).getObject(DATA_SHOP_INFO, ResShopInfo.class);
		mShopInfo = ShopModel.getInstance().getShopInfo();
        mIndex = intent.getIntExtra(PAGE_NUMBER, mIndex);
        mIndex = mIndex % mTitles.length;
    }

    @Override
    protected void initData() {
        mAdapter = new OrderPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected void initView() {
        initTabIndicator();
        initTabFragments();
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }

    /**
     * 初始化tab指示器
     */
    private void initTabIndicator(){
        CommonNavigator navigator = new CommonNavigator(getContext());
        navigator.setAdjustMode(true);
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ScaleTransitionPagerTitleView title = new ScaleTransitionPagerTitleView(getContext());
                title.setText(mTitles[i]);
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(i);
                    }
                });
                title.setNormalColor(Color.parseColor(ColorUtils.getColorResourceString(getContext(), R.color.text_white)));
                title.setSelectedColor(Color.parseColor(ColorUtils.getColorResourceString(getContext(), R.color.text_white)));
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_38));
                title.setMinScale(0.737f);
                return title;
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
    }

    /**
     * 初始化Tab界面
     */
    private void initTabFragments(){
        List<MineOrderManagerFragment> fragments = new ArrayList<>();
        fragments.add(new MineOrderManagerFragment(mShopInfo, false));
        fragments.add(new MineOrderManagerFragment(mShopInfo, true));
        mAdapter.setFragments(fragments);
    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPager.setCurrentItem(mIndex);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Subscribe
    public void onEvent(DataEvent.OnCloseRecordOrderFragmentEvent event){
        finish();
    }

    private class OrderPagerAdapter extends QuickFragmentPagerAdapter<MineOrderManagerFragment> {

        public OrderPagerAdapter(FragmentManager fm) {
            super(fm);
        }
    }

}
