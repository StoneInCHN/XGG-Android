package com.hentica.app.module.bonus;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.bonus.BonusNationData;
import com.hentica.app.module.entity.bonus.BonusPeopleData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 分红主界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class BonusMainFragment extends BaseFragment {

    @BindView(R.id.bonus_main_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.bonus_main_indicator_group)
    RadioGroup mIndicatorGroup;
    private BonusAdapter mAdapter;
    private List<View> mViews;
    /**
     * 我的分红
     */
    private BonusMineFragment mFragment1;
    /**
     * 全国分红
     */
    private BonusCountryFragment mFragment2;

    private ViewHandler handler = new ViewHandler(new WeakReference<BonusMainFragment>(this));

    /** 分页id集合 */
    private ArrayList<Integer> mRadioIds;

    private float screenRatio = 0;

    @Override
    public int getLayoutId() {
        return R.layout.bonus_main_fragment;
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
        mViews = new ArrayList<>();
        mFragment1 = new BonusMineFragment(getContext());
        mFragment2 = new BonusCountryFragment(getContext());
        mViews.add(mFragment1);
        mViews.add(mFragment2);
        mViews.add(mFragment1);
        mViews.add(mFragment2);
        mAdapter = new BonusAdapter(mViews);
        mRadioIds = new ArrayList<>();
        mRadioIds.add(R.id.bonus_main_indicator_radio1);
        mRadioIds.add(R.id.bonus_main_indicator_radio2);
    }

    @Override
    protected void initView() {
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - 1);
        handler.currentItem = Integer.MAX_VALUE / 2 - 1;
        // 请求我的分红
        requestMineBonus();
        // 请求全国分红
        requestNationBonus();
        ViewTreeObserver vbo = getView().getViewTreeObserver();
        vbo.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getView().getViewTreeObserver().removeOnPreDrawListener(this);
                screenRatio = getView().getMeasuredHeight() / (float)BonusConstans.BACKGROUND_HEIGHT;//计算缩放比例
                View indicator = getViews(R.id.bonus_main_indicator_group);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) indicator.getLayoutParams();
                lp.setMargins(0, 0, 0, (int) (screenRatio * BonusConstans.INDICATOR_PADDING_BOTTOM));
                return true;
            }
        });
        //
    }



    @Override
    protected void setEvent() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //配合Adapter的currentItem字段进行设置。
            @Override
            public void onPageSelected(int arg0) {
                handler.sendMessage(Message.obtain(handler, ViewHandler.MSG_PAGE_CHANGED, arg0, 0));
                //  页号求模，取得真实页数
                int mod = arg0 % 2;
                // 设置分页指示器
                setIndicator(mod);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            //覆写该方法实现轮播效果的暂停和恢复
            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        handler.sendEmptyMessage(ViewHandler.MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(ViewHandler.MSG_UPDATE_IMAGE, ViewHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 分红界面适配器
     */
    private class BonusAdapter extends PagerAdapter {

        private List<View> mViews;

        public BonusAdapter(List<View> views) {
            mViews = views;
        }

        @Override
        public int getCount() {
            // 设置大小为最大，使用户看不到边界
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 覆盖，不用remove
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //对ViewPager页号求模取出View列表中要显示的项
            position %= mViews.size();
            if (position < 0) {
                position = mViews.size() + position;
            }
            View view = mViews.get(position);
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            try{
                container.addView(view);
            }catch (Exception e){

            }
            //add listeners here if necessary
            return view;
        }

        public void setViews(List<View> mViews) {
            this.mViews = mViews;
        }
    }


    private static class ViewHandler extends Handler {

        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED = 4;

        //轮播间隔时间
        protected static final long MSG_DELAY = 4000;

        //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<BonusMainFragment> weakReference;
        private int currentItem = 0;

        protected ViewHandler(WeakReference<BonusMainFragment> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BonusMainFragment fragment = weakReference.get();
            if (fragment == null) {
                //Activity已经回收，无需再处理UI了
                return;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (fragment.handler.hasMessages(MSG_UPDATE_IMAGE)) {
                fragment.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    fragment.mViewPager.setCurrentItem(currentItem);
                    //准备下次播放
                    fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    }

    private void setIndicator(int index){
        int currId = mRadioIds.get(index);
        mIndicatorGroup.check(currId);
    }

    /**
     * 请求我的分红
     */
    private void requestMineBonus() {
        String userId = LoginModel.getInstance().getLoginUserId();
        Request.getReportGetUserBonusReport(userId,
                ListenerAdapter.createObjectListener(BonusPeopleData.class, new UsualDataBackListener<BonusPeopleData>(this, false, false, false) {
                    @Override
                    protected void onComplete(boolean isSuccess, BonusPeopleData data) {
                        if (isSuccess) {
                            // 请求成功
                            mFragment1.refreshUI(data);
                        }
                    }
                }));
    }

    /**
     * 请求全国分红
     */
    private void requestNationBonus() {
        String userId = LoginModel.getInstance().getLoginUserId();
        Request.getReportGetNationBonusReport(userId,
                ListenerAdapter.createObjectListener(BonusNationData.class, new UsualDataBackListener<BonusNationData>(this, false, false, false) {
                    @Override
                    protected void onComplete(boolean isSuccess, BonusNationData data) {
                        if (isSuccess) {
                            // 请求成功，刷新UI
                            mFragment2.refreshUI(data);
                        }
                    }
                }));
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 界面显示，开启自动轮播
            handler.sendEmptyMessageDelayed(ViewHandler.MSG_UPDATE_IMAGE, ViewHandler.MSG_DELAY);
            // 请求我的分红
            requestMineBonus();
            // 请求全国分红
            requestNationBonus();
        }
    }

    /**
     * 登录事件
     */
    @Subscribe
    public void onEvent(DataEvent.OnLoginEvent event) {
        // 请求我的分红
        requestMineBonus();
        // 请求全国分红
        requestNationBonus();
    }

    /**
     * 退出登录事件
     */
    @Subscribe
    public void onEvent(DataEvent.OnLoginInvaildEvent event) {
        // 刷新我的账户信息
        mFragment1.refreshAccount();
        mFragment1.resetUI();
    }
}
