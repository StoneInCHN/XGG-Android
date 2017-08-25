package com.hentica.app.module.mine.ui.statistics;

import android.support.v4.view.ViewPager;

import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.mine.ui.adapter.QuickFragmentPagerAdapter;
import com.hentica.app.widget.view.TabTitle;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 乐分乐豆界面，默认显示乐分
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineLefenLedouFragment extends BaseFragment {

    private TabTitle mTabTitle;

    private ViewPager mViewPager;
    private QuickFragmentPagerAdapter<BaseFragment> mPageAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.mine_lefen_fragment;
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
        mPageAdapter = new QuickFragmentPagerAdapter(getFragmentManager());
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new MineLefenFragment());
        fragments.add(new MineLedouFragment());
        mPageAdapter.setFragments(fragments);
    }

    @Override
    protected void initView() {
        mViewPager = getViews(R.id.mine_lefen_pager);
        mViewPager.setAdapter(mPageAdapter);
        mTabTitle = getViews(R.id.mine_lefen_tab_title);
        configTabTitle(mTabTitle);
    }

    protected void configTabTitle(TabTitle tabTitle) {
        tabTitle.setTitleGetter(new TabTitle.TitleGetter() {
            @Override
            public String[] getTitles() {
                return new String[]{"乐分", "乐豆"};
            }
        });
        tabTitle.setIndicatorBackground(R.drawable.rebate_homepage_title_choose);
        tabTitle.setTitleTextColor(R.color.text_white, R.color.text_white);
        tabTitle.setTitleSize(R.dimen.text_38, R.dimen.text_28);
        tabTitle.bindViewPager(mViewPager);
    }

    @Override
    protected void setEvent() {

    }


}
