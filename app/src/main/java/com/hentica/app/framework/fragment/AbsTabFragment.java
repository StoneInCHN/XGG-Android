package com.hentica.app.framework.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.hentica.app.framework.fragment.ptr.AbsPtrFragment;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.widget.view.TabTitle;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 常用带二级标签界面
 * Created by Snow on 2016/12/27.
 */

public abstract class AbsTabFragment extends BaseFragment {

    @BindView(R.id.listen_title)
    TabTitle mTabTitle;

    @BindView(R.id.listen_view_pager)
    ViewPager mViewPager;

    private FragmentAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.common_tab_fragment;
    }

    @Override
    protected TitleView initTitleView() {
        return (TitleView) getView().findViewById(R.id.common_title);
    }

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setTitleText(getFragmentTitle());
    }

    /** 获取界面标题 */
    protected abstract String getFragmentTitle();

    /** 获取初始二级标题文字 */
    protected abstract String[] getInitTitles();

    @Override
    protected void initData() {
        mAdapter = new FragmentAdapter(getFragmentManager());
        mAdapter.setFragments(initPageFragment());
    }

    /** 初始化ViewPager中子界面 */
    protected abstract AbsPtrFragment[] initPageFragment();

    @Override
    protected void initView() {
        initTabTitle(mTabTitle);
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 初始化TabTitle
     */
    protected void initTabTitle(TabTitle tabtitle) {
        if(tabtitle == null){
            return;
        }
        tabtitle.setTitleGetter(new TabTitle.TitleGetter() {
            @Override
            public String[] getTitles() {
                return getInitTitles();
            }
        });
        tabtitle.setTitleTextColor(R.color.text_normal, R.color.text_gray);
        tabtitle.setTitleSize(R.dimen.text_30, R.dimen.text_26);
        tabtitle.bindViewPager(mViewPager);
//        tabtitle.setTitleTabDivBackground(R.drawable.ask_public_line_vertical_short);
    }

    /** 设置TabTitle文字 */
    protected void setTabTitle(String... tabTitles){
        mTabTitle.setTitleText(tabTitles);
    }

    @Override
    protected void setEvent() {

    }

    protected ViewPager getViewPager(){
        return this.mViewPager;
    }

    protected FragmentAdapter getAdapter(){
        return this.mAdapter;
    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {

        List<AbsPtrFragment> mFragments = new ArrayList<>();

        public void setFragments(AbsPtrFragment[] fragments) {
            mFragments.clear();
            if (fragments != null && fragments.length > 0) {
                mFragments.addAll(Arrays.asList(fragments));
            }
            notifyDataSetChanged();
        }

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public AbsPtrFragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    @Override
    public void dismissLoadingDialog() {
        super.dismissLoadingDialog();
        getAdapter().getItem(getViewPager().getCurrentItem()).onRefreshComplete();
    }
}
