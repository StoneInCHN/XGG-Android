package com.hentica.app.module.index;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.adapter.CommonSlideAdapter;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.index.IndexBusinessDetailData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.ImageGalleryUtils;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.StickTitle;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.baidumap.LocationUtils;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.ChildScrollView;
import com.hentica.app.widget.view.ChildViewPager;
import com.hentica.app.widget.view.TabTitle;
import com.hentica.app.widget.view.TitleView;
import com.hentica.appbase.famework.adapter.QuickPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商家详情界面
 *
 * @author 
 * @createTime 2017-03-23 下午15:13:27
 */
public class IndexBusinessDetailFragment extends BaseFragment {

    public static final String SELLERID = "SellerId";

    /** 上方布局 */
    @BindView(R.id.index_business_detail_top_layout)
    FrameLayout mTopLayout;
    /** 隐藏布局 */
    @BindView(R.id.index_business_detail_hide_layout)
    LinearLayout mHideLayout;
    /** 上方viewpager */
    @BindView(R.id.index_business_detail_top_view_pager)
    ViewPager mTopViewPager;
    /** 上方pager指示器 */
    @BindView(R.id.index_business_detail_pager_indicator)
    CirclePageIndicator mPageIndicator;
    /** 滑动界面 */
    @BindView(R.id.index_business_detail_scroll_view)
    ChildScrollView mScrollView;
    /** 返回按钮 */
    @BindView(R.id.index_business_detail_back_btn)
    ImageButton mBackBtn;
    /** 上方图片 */
    @BindView(R.id.index_business_detail_top_img)
    ImageView mTopImageView;
    /** 标签 */
    @BindView(R.id.index_business_detail_label)
    TextView mLabelTextView;
    /** 乐豆抵扣标签 */
    @BindView(R.id.img_bean_pay)
    View mLabelBeanPay;
    /** 名称 */
    @BindView(R.id.index_business_detail_name)
    TextView mNameTextView;
    /** 折扣 */
    @BindView(R.id.index_business_detail_discount)
    TextView mDiscountTextView;
    /** 人均价格 */
    @BindView(R.id.index_business_detail_price)
    TextView mPriceTextView;
    /** 评分 */
    @BindView(R.id.index_business_detail_score)
    TextView mScoreTextView;
    /** 地址 */
    @BindView(R.id.index_business_detail_location)
    TextView mAddrTextView;
    /** 距离 */
    @BindView(R.id.index_business_detail_distance)
    TextView mDistanceTextView;
    @BindView(R.id.index_business_detail_location_layout)
    RelativeLayout mLocationBtn;
    /** 电话按钮 */
    @BindView(R.id.index_business_detail_call_btn)
    TextView mPhoneBtn;
    /** 切换指示器 */
    @BindView(R.id.index_business_detail_tab_title)
    TabTitle mTabTitle;
    /** 切换指示器2 */
    @BindView(R.id.index_business_detail_tab_title2)
    TabTitle mTabTitle2;
    /** viewpager */
    @BindView(R.id.index_business_detail_view_pager)
    ChildViewPager mViewPager;
    /** 收藏 */
    @BindView(R.id.index_business_detail_collect_check)
    CheckBox mCollectCheck;
    /** 立即买单 */
    @BindView(R.id.index_business_detail_pay_btn)
    TextView mPayBtn;
    /** viewpager适配器 */
    private CommonSlideAdapter mAdapter;
    /** 界面滑动辅助工具 */
    private StickTitle mStickTitle;
    private float mObserveY;
    /** 商家id */
     private String mSellerId;
    /** 商家详情数据 */
    private IndexBusinessDetailData mBusinessDetailData;
    /** banner列表适配器 */
    private BannerAdapter mBannerAdapter;
    /** 服务评论界面 */
    private IndexBusinessDetailCommentFragment mFragment1;
    /** 店铺简介界面 */
    private IndexBusinessDetailIntroduceFragment mFragment2;
    /** 经度 */
    private double mLatitude = -1;
    /** 纬度 */
    private double mLongitude = -1;
    /** 是否已成功定位 */
    private boolean mIsLocated;
    @Override
    public int getLayoutId() {

        return R.layout.index_business_detail_fragment;
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
    protected void handleIntentData(Intent intent) {
        // 商家详情数据
        IntentUtil intentUtil = new IntentUtil(getIntent());
        mSellerId = intentUtil.getString(SELLERID);
    }

    @Override
    protected void initData() {
        mBannerAdapter = new BannerAdapter();
        mAdapter = new CommonSlideAdapter<BaseFragment>(getChildFragmentManager());
        List<BaseFragment> fragments = new ArrayList<>();
        mFragment1 = new IndexBusinessDetailCommentFragment(mSellerId);
        mFragment2 = new IndexBusinessDetailIntroduceFragment(getBusinessTime(),getFeaturedService(),getDescription());
        fragments.add(mFragment1);
        fragments.add(mFragment2);
        mAdapter.setFragments(fragments);
        mStickTitle = new StickTitle(mScrollView,null,null);
        final ViewTreeObserver obs = mTopLayout.getViewTreeObserver();
        obs.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mObserveY = mTabTitle.getY() + mTabTitle.getMeasuredHeight() - mHideLayout.getMeasuredHeight();
                return true;
            }
        });
    }

    @Override
    protected void initView() {
        mTopViewPager.setAdapter(mBannerAdapter);
        mViewPager.setAdapter(mAdapter);
        bindIndicator(mTopViewPager,mPageIndicator, R.color.bg_white, R.color.transparent_gray);
        initTabTitle(mTabTitle);
        initTabTitle(mTabTitle2);
        tryGetBusinessDetail();
    }

    @Override
    protected void setEvent() {
        // 返回按钮，被点击
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 添加滑动监听事件
        mStickTitle.addOneScrollProgress(new StickTitle.ScrollProgress(new StickTitle.ObserveListener() {
            @Override
            public void onObserved(int scrollY) {
                if(scrollY > mObserveY){
                    mHideLayout.setVisibility(View.VISIBLE);
                }else {
                    mHideLayout.setVisibility(View.GONE);
                }
            }
        }));
        // 商家地址，被点击
        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到商家定位
                FragmentJumpUtil.toBusinessLocation(getUsualFragment(),mLatitude,mLongitude,mBusinessDetailData);
            }
        });
        // 商家电话被点击
        mPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = getBusinessPhone();
                if(TextUtils.isEmpty(phone)){
                    showToast("暂无商家电话信息！");
                }else {
                    // 跳转到拨号界面
                    FragmentJumpUtil.toCalling(getUsualFragment(),phone);
                }
            }
        });
        // 收藏，被点击
        mCollectCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 请求收藏店铺
                requestCollect(mCollectCheck.isChecked());
            }
        });
        // 立即买单
        mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到买单界面
                FragmentJumpUtil.toPayFragment(getUsualFragment(),mBusinessDetailData);
            }
        });
        // viewpager切换
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 重置高度
                mViewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /** 刷新界面 */
    private void refresUI(){
        if(mBusinessDetailData != null){
            mTitleView.setTitleText(mBusinessDetailData.getName());
            String url = ApplicationData.getInstance().getImageUrl(mBusinessDetailData.getStorePictureUrl());
            ViewUtil.bindImage(getContext(),mTopImageView,url,R.drawable.rebate_homepage_banner);
            mLabelTextView.setText(getLabel());
            mLabelBeanPay.setVisibility(isBeanPay() ? View.VISIBLE : View.GONE);
            mNameTextView.setText(mBusinessDetailData.getName());
            mDiscountTextView.setText(String.format("消费%s赠送%s积分",mBusinessDetailData.getUnitConsume(),mBusinessDetailData.getRebateScore()));
            mPriceTextView.setText(getAvgPrice());
            mScoreTextView.setText(String.format("总分%s分",mBusinessDetailData.getRateScore()));
            mAddrTextView.setText(mBusinessDetailData.getAddress());
            mDistanceTextView.setText(mBusinessDetailData.getDistance() == null ? "" : getDistance());
            mCollectCheck.setText(String.format("收藏(%d)",mBusinessDetailData.getFavoriteNum()));
            mPhoneBtn.setText(mBusinessDetailData.getBusinessTime());
            mCollectCheck.setChecked(mBusinessDetailData.isUserCollected());
            mBannerAdapter.setDatas(mBusinessDetailData.getEnvImgs());

            mFragment1.setRate(mBusinessDetailData.getRateScore());
            mFragment1.setScore(String.format("%s分",mBusinessDetailData.getRateScore()));

            mFragment2.setBusinessTime(getBusinessTime());
            mFragment2.setFeaturedService(getFeaturedService());
            mFragment2.setDescription(getDescription());
        }
    }

    /**
     * 绑定分页指示器
     */
    private void bindIndicator(ViewPager pager, CirclePageIndicator indicator, int fillColor, int pageColor) {
        indicator.setViewPager(pager);
        indicator.setFillColor(getResources().getColor(fillColor));
        indicator.setPageColor(getResources().getColor(pageColor));
        indicator.setBackgroundColor(0x00888888);
        indicator.setStrokeWidth(0);
    }

    /** 初始化切换指示器 */
    private void initTabTitle(TabTitle tabTitle){
        if(tabTitle != null){
            tabTitle.setTitleGetter(new TabTitle.TitleGetter() {
                @Override
                public String[] getTitles() {
                    return new String[]{"服务评论", "店铺简介"};
                }
            });
            tabTitle.setTitleTabDivBackground(R.drawable.rebate_public_line2);
            tabTitle.setIndicatorBackground(R.drawable.rebate_homepage_title_choose);
            tabTitle.setTitleTextColor(R.color.text_red, R.color.text_title_black);
            tabTitle.setTitleSize(R.dimen.text_26, R.dimen.text_26);
            tabTitle.bindViewPager(mViewPager);
        }
    }

    /** 获取营业时间段 */
    private String getBusinessTime(){
        return mBusinessDetailData == null ? "" : mBusinessDetailData.getBusinessTime();
    }

    /** 获取服务 */
    private String getFeaturedService(){
        return mBusinessDetailData == null ? "" : mBusinessDetailData.getFeaturedService();
    }

    /** 获取简介 */
    private String getDescription(){
        return mBusinessDetailData == null ? "" : mBusinessDetailData.getDescription();
    }

    /** 获取商家电话 */
    private String getBusinessPhone(){
        return mBusinessDetailData == null ? "" : mBusinessDetailData.getStorePhone();
    }

    /** 获取商家标签 */
    private String getLabel(){
        return mBusinessDetailData.getSellerCategory() == null ? "" : mBusinessDetailData.getSellerCategory().getCategoryName();
    }

    private boolean isBeanPay(){
        return mBusinessDetailData.isBeanPay();
    }

    /** 获取经度 */
    private String getLatitude(){
        return  mLatitude < 0 ? "" : mLatitude+"";
    }

    /** 获取纬度 */
    private String getLongitude(){
        return mLongitude < 0 ? "" : mLongitude+"";
    }

    /** 获取人均消费 */
    private String getAvgPrice(){
        String avgPrice = (mBusinessDetailData != null && !TextUtils.isEmpty(mBusinessDetailData.getAvgPrice())) ? mBusinessDetailData.getAvgPrice() : "1";
        return  String.format("人均%s元",avgPrice);
    }

    /** 获取距离 */
    private String getDistance(){
        return (mBusinessDetailData != null && mBusinessDetailData.getDistance() != null) ? mBusinessDetailData.getDistance()+"km" : "";
    }

    /** 尝试请求商家详情 */
    private void tryGetBusinessDetail(){
        // 首先拉取一次数据
        getBusinessDetail(true);
        // 尝试定位，若定位成功再拉取一次数据
        LocationUtils locate = LocationUtils.newInstance(getContext(), new LocationUtils.LocationCallBack() {
            @Override
            public boolean callBack(BDLocation location) {
                if(location != null && location.getLatitude() != 4.9E-324 && location.getLongitude() != 4.9E-324){
                    mIsLocated = true;
                    mLatitude = location.getLatitude();
                    mLongitude = location.getLongitude();
                    getBusinessDetail(false);
                }
                return true;
            }
        });
        this.setRequestPermissionResultListener(locate);
        locate.startPermission(this);
    }

    /** 获取当前收藏数 */
    private int getCurrCollectCount(boolean isCollect){
        int count = 0;
        if(mBusinessDetailData != null){
            if(mBusinessDetailData.isUserCollected()){
                // 之前已收藏
                count = isCollect ? mBusinessDetailData.getFavoriteNum() : mBusinessDetailData.getFavoriteNum() - 1;
            }else {
                // 之前未收藏
                count = isCollect ? mBusinessDetailData.getFavoriteNum() + 1 : mBusinessDetailData.getFavoriteNum();
            }
        }
        return count;
    }

    /** 请求商家详情 */
    private void getBusinessDetail(final boolean needJudge){
        String userId = LoginModel.getInstance().getLoginUserId();
        Request.getSellerDetail(userId, mSellerId,getLatitude(),getLongitude(),
                ListenerAdapter.createObjectListener(IndexBusinessDetailData.class, new UsualDataBackListener<IndexBusinessDetailData>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, IndexBusinessDetailData data) {
                        if(isSuccess){
                            if(needJudge && mIsLocated){
                                // 需判断定位，定位成功后，此次请求不做处理
                                return;
                            }
                            // 请求成功
                            mBusinessDetailData = data;
                            // 刷新界面
                            refresUI();
                        }
                    }
                }));
    }

    /** 请求收藏商家 */
    private void requestCollect(final boolean isFavorite){
        String userId = LoginModel.getInstance().getLoginUserId();
        Request.getEndUserFavoriteSeller(userId,mSellerId,isFavorite+"",
                ListenerAdapter.createObjectListener(Object.class, new UsualDataBackListener<Object>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, Object data) {
                        if(isSuccess){
                            showToast("操作成功！");
                            // 收藏数+1或者-1
                            int favorite = getCurrCollectCount(isFavorite);
                            mCollectCheck.setText(String.format("收藏(%d)",favorite));
                            // 发送刷新通知
                            EventBus.getDefault().post(new DataEvent.OnCollectedEvent());
                        }else {
                            mCollectCheck.setChecked(!mCollectCheck.isChecked());
                        }
                    }
                }));
    }

    /**
     * banner列表适配器
     */
    private class BannerAdapter extends QuickPagerAdapter<String> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.index_main_banner_item;
        }

        @Override
        protected void fillView(final int position, View convertView, ViewGroup parent, String data) {
            String url = ApplicationData.getInstance().getImageUrl(data);
            ViewUtil.bindImage(convertView, R.id.index_main_banner_item_img, url);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> imgs = ImageGalleryUtils.wrapImageUrls(mBannerAdapter.getDatas());
                    FragmentJumpUtil.toImageGallery(getUsualFragment(), imgs, position);
                }
            });
        }
    }

    /** 支付成功事件 */
    @Subscribe
    public void onEvent(DataEvent.OnPaySuccess event){
        finish();
    }
}
