/**
 *
 */
package com.hentica.app.util;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.hentica.app.framework.fragment.UsualFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 滑动窗口辅助工具
 *
 * @author Limi
 * @createTime 2016-3-16 下午8:09:49
 */
public class FragmentFlipHelper {

    /**
     * 对应的界面
     */
    private UsualFragment mMainFragment;

    /**
     * viewPager布局id
     */
    private int mViewPagerLayoutId;

    /**
     * 单选布局id
     */
    private int mRadioGroupId;

    /**
     * 滑动视图id
     */
    private int mSlideViewId;

    /**
     * 初始选中第几项
     */
    private int mInitIndex = 0;

    /**
     * 滑动界面
     */
    private ViewPager mViewPager;

    /**
     * 所有的子界面
     */
    private List<FragmentInfo> mAllChildFragmentInfos = new ArrayList<FragmentInfo>();

    /**
     * 当前显示的子界面
     */
    private FragmentInfo mShowedFragmentInfo;

    /**
     * 选中项改变事件
     */
    private OnCheckedChangeListener mOnCheckedChangeListener;

    /**
     * 切换开始事件
     */
    private OnBeforeChangeListener mOnBeforeChangeListener;

    /**
     * 界面锁，用于保证在同一时间，下方tab与ViewPager只能有一个能改变当前界面
     */
    private boolean mPagerLock;

    /**
     * 滑块视图
     */
    private View mSlideView;

    /**
     * tab
     */
    private RadioGroup mTabGroup;

    /**
     * 滑块宽度
     */
    private int mSlideWidth;

    /**
     * 选中时的字体大小
     */
    private int mCheckedSize = -1;

    /**
     * 未选中时的字体大小
     */
    private int mNormalSize = -1;

    /**
     * 一个界面信息
     */
    public static class FragmentInfo {

        /**
         * 对应的界面
         */
        public UsualFragment mFragment;

        /**
         * 对应的按钮id
         */
        public int mRadioId;

        /**
         * 界面标识
         */
        public String mTag;

        /**
         * 构造函数
         */
        public FragmentInfo(UsualFragment fragment, int radioId, String tag) {

            mFragment = fragment;
            mRadioId = radioId;
            mTag = tag;
        }
    }

    /**
     * 切换开始事件
     */
    public interface OnBeforeChangeListener {
        boolean beforeChanged(int checkedId);
    }

    /**
     * 选中项改变事件
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    /**
     * 构造函数
     *
     * @param mainFragment 主界面
     * @param viewPagerId  滑动视图id
     * @param radioGroupId 单选组id
     * @param slideViewId  滑动条id
     * @param defaultIndex 默认选中第几个视图
     */
    public FragmentFlipHelper(UsualFragment mainFragment, int viewPagerId, int radioGroupId,
                              int slideViewId, int defaultIndex) {

        mMainFragment = mainFragment;
        mViewPagerLayoutId = viewPagerId;
        mRadioGroupId = radioGroupId;
        mSlideViewId = slideViewId;
        mInitIndex = defaultIndex;
    }

    /**
     * 添加一个子界面
     */
    public void addFragmentInfo(FragmentInfo fragmentInfo) {

        mAllChildFragmentInfos.add(fragmentInfo);
    }

    /**
     * 创建所有子界面
     */
    public void createFragments() {

        // 初始化界面
        this.initView();
        this.setEvent();
    }

    /**
     * 按键事件，由fragment传递进来
     *
     * @param keyCode 按键码
     * @param event   事件
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 传递给当前子fragment
        if (mShowedFragmentInfo != null && mShowedFragmentInfo.mFragment != null
                && mShowedFragmentInfo.mFragment.onKeyDown(keyCode, event)) {

            return true;
        }
        return false;
    }

    /**
     * 设置当前显示界面
     */
    public void setCurrIndex(int index) {

        this.switchToTab(mAllChildFragmentInfos.get(index).mRadioId, false);
    }

    /**
     * 当前显示的是哪个视图
     */
    public int getCurrIndex() {

        return mViewPager.getCurrentItem();
    }

    public void setMainFragment(UsualFragment mMainFragment) {
        this.mMainFragment = mMainFragment;
    }

    public void setCheckedSize(int mCheckedSize) {
        this.mCheckedSize = mCheckedSize;
    }

    public void setNormalSize(int mNormalSize) {
        this.mNormalSize = mNormalSize;
    }

    /**
     * 取得当前显示的界面的信息
     */
    public FragmentInfo getCurrFragmentInfo() {

        return mShowedFragmentInfo;
    }

    /**
     * 取得所有子界面
     */
    public List<FragmentInfo> getAllChildFragmentInfos() {
        return mAllChildFragmentInfos;
    }

    /**
     * 初始化界面
     */
    private void initView() {

        // 注册事件
        for (FragmentInfo fragmentInfo : mAllChildFragmentInfos) {

            mMainFragment.registerTouchListener(fragmentInfo.mFragment);
        }

        // 根视图
        View rootView = mMainFragment.getView();

        // 定义滑块
        if (mSlideViewId != 0) {

            // 滑块视图
            mSlideView = rootView.findViewById(mSlideViewId);

            // 页面数
            int tabCount = mAllChildFragmentInfos.size();

            // 计算滑块宽度
            if (mSlideView != null && tabCount > 0) {

                // 获取屏幕宽度
                DisplayMetrics dm = new DisplayMetrics();
                mMainFragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                int screenWith = dm.widthPixels;

                mSlideWidth = screenWith / mAllChildFragmentInfos.size();
                mSlideView.getLayoutParams().width = mSlideWidth;
            }
        }

        // 定义滑动adapter
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(
                mMainFragment.getChildFragmentManager());
        mViewPager = (ViewPager) mMainFragment.getView().findViewById(mViewPagerLayoutId);
        mViewPager.setOffscreenPageLimit(mAllChildFragmentInfos.size()); // 不销毁
        mViewPager.setAdapter(mViewPagerAdapter);

        // 显示首页
        mViewPager.setCurrentItem(mInitIndex);
        mShowedFragmentInfo = mAllChildFragmentInfos.get(mViewPager.getCurrentItem());

        // 单选框
        mTabGroup = (RadioGroup) rootView.findViewById(mRadioGroupId);
        // 默认选中
        mTabGroup.check(mShowedFragmentInfo.mRadioId);
    }

    /**
     * 设置事件
     */
    private void setEvent() {

        // 单选框
        mTabGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mOnBeforeChangeListener != null) {
                    boolean isIntercepted = mOnBeforeChangeListener.beforeChanged(checkedId);
                    if(isIntercepted){
                        // 拦截后方操作
                        return;
                    }
                }
                // 跳转到相应界面
                switchToTab(checkedId, true);
            }
        });

        // 界面滑动切换了
        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int pos) {

                if (mAllChildFragmentInfos != null && pos < mAllChildFragmentInfos.size()) {

                    // 跳转到相应界面
                    switchToTab(mAllChildFragmentInfos.get(pos).mRadioId, true);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                // 滑动滑块
                if (mSlideView != null) {

                    mSlideView.setTranslationX(mSlideWidth * (position + positionOffset));
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    /**
     * 取得指定id对应第几个视图，若不到则返回-1
     *
     * @param checkedId
     * @return
     */
    private int getCheckedIndex(int checkedId) {

        // 遍历查找
        for (int i = 0; i < mAllChildFragmentInfos.size(); i++) {

            if (checkedId == mAllChildFragmentInfos.get(i).mRadioId) {

                return i;
            }
        }
        return -1;
    }

    /**
     * 切换视图
     *
     * @param checkedId    点击的按钮id，用R.id.viewId来适配
     * @param smoothScroll 是否显示动画
     */
    private void switchToTab(int checkedId, boolean smoothScroll) {

        if (!mPagerLock) {

            mPagerLock = true;

            // 选中指定项
            mTabGroup.check(checkedId);

            // viewpager切换
            int currIndex = this.getCheckedIndex(checkedId);
            if (currIndex >= 0 && currIndex < mAllChildFragmentInfos.size()) {

                mViewPager.setCurrentItem(currIndex, smoothScroll);
                mShowedFragmentInfo = mAllChildFragmentInfos.get(currIndex);
            }

            // 发出事件
            if (mOnCheckedChangeListener != null) {

                mOnCheckedChangeListener.onCheckedChanged(mTabGroup, checkedId);
            }

            mPagerLock = false;
        }
    }

    /**
     * 滑动适配器
     */
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            if (mAllChildFragmentInfos != null && position < mAllChildFragmentInfos.size()) {

                return mAllChildFragmentInfos.get(position).mFragment;
            }
            throw new IllegalStateException("No fragment at position " + position);
        }

        @Override
        public int getCount() {
            return mAllChildFragmentInfos != null ? mAllChildFragmentInfos.size() : 0;
        }
    }

    public void setOnBeforeChangeListener(OnBeforeChangeListener mOnBeforeChangeListener) {
        this.mOnBeforeChangeListener = mOnBeforeChangeListener;
    }
}
