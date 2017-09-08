package com.hentica.app.module.index;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.baidu.location.BDLocation;
import com.fiveixlg.app.customer.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.RequestPermissionResultListener;
import com.hentica.app.lib.view.ChildGridView;
import com.hentica.app.lib.view.ChildListView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.module.entity.index.IndexBannerListData;
import com.hentica.app.module.entity.index.IndexSellerListData;
import com.hentica.app.module.entity.mine.shop.ResShopCategory;
import com.hentica.app.module.entity.type.SortType;
import com.hentica.app.module.index.adapter.BusinessAdapter;
import com.hentica.app.module.mine.presenter.shop.ShopCategoryPresenter;
import com.hentica.app.module.mine.presenter.shop.ShopCategoryPresenterImpl;
import com.hentica.app.module.mine.view.shop.ShopCategoryView;
import com.hentica.app.util.ArrayListUtil;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.PermissionHelper;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.baidumap.LocationUtils;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.region.Region;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.dialog.SelfAlertDialog;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.lineview.LineViewText;
import com.hentica.app.widget.view.ptrview.CustomPtrScrollView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.hentica.appbase.famework.adapter.QuickPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class IndexMainFragment extends BaseFragment implements ShopCategoryView, RequestPermissionResultListener {
    /**
     * 切换城市
     */
    @BindView(R.id.index_main_city_check)
    CheckBox mCityCheck;
    /**
     * 搜索栏
     */
    @BindView(R.id.index_main_search_edit)
    TextView mSearchBtn;
    /**
     * 二维码
     */
    @BindView(R.id.index_main_scan_btn)
    ImageButton mScanBtn;
    /**
     * 消息
     */
    @BindView(R.id.index_main_msg_btn)
    ImageButton mMsgBtn;
    /**
     * 滑动view
     */
    @BindView(R.id.index_main_sroll_view)
    CustomPtrScrollView mScrollView;
    /**
     * banner的viewpaver
     */
    @BindView(R.id.index_main_banner_view_pager)
    ViewPager mBannerViewPager;
    /**
     * banner指示器
     */
    @BindView(R.id.index_main_banner_pager_indicator)
    CirclePageIndicator mBannerIndicator;
    /**
     * 服务viewpager
     */
    @BindView(R.id.index_main_service_view_pager)
    ViewPager mServiceViewPager;
    /**
     * 服务viewpager指示器
     */
    @BindView(R.id.index_main_service_pager_indicator)
    CirclePageIndicator mServiceIndicator;
    /**
     * 商家列表标题
     */
    @BindView(R.id.index_main_business_list_title)
    LineViewText mBusinessTitleLine;
    /**
     * 商家列表
     */
    @BindView(R.id.index_main_business_list)
    ChildListView mBusinessListView;
    /**
     * 沉浸式状态栏
     */
    @BindView(R.id.common_status_bar)
    View mStatusBar;

    /**
     * banner列表适配器
     */
    private BannerAdapter mBannerAdapter;
    /**
     * 服务列表适配器
     */
    private ServiceAdapter mServiceAdapter;
    /**
     * 商家列表适配器
     */
    private BusinessAdapter mBusinessAdapter;

    /**
     * 当前纬度
     */
    private double mLatitude = -1;
    /**
     * 当前经度
     */
    private double mLongitude = -1;
    /**
     * 当前页数
     */
    private int mPageNum = 1;
    /**
     * 分页大小
     */
    private int mPageSize = 10;
    /**
     * 分类控制器
     */
    private ShopCategoryPresenter mCatPresenter;
    /**
     * 是否成功定位
     */
    private boolean mIsLocated;

    private PermissionHelper.PermissionGrant permissionGrant = new PermissionHelper.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionHelper.CODE_CAMERA:
                    FragmentJumpUtil.toScan(getUsualFragment(), mLatitude, mLongitude);
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.index_main_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    protected TitleView initTitleView() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        mCatPresenter = new ShopCategoryPresenterImpl(this);
        // 请求分类数据
        mCatPresenter.getGategory();
        // 异步加载城市信息
        toLoadCitys();
    }

    @Override
    protected void initView() {
        mBannerAdapter = new BannerAdapter();
        mServiceAdapter = new ServiceAdapter();
        mBusinessAdapter = new BusinessAdapter(getContext());

        mBannerViewPager.setAdapter(mBannerAdapter);
        mServiceViewPager.setAdapter(mServiceAdapter);
        mBusinessListView.setAdapter(mBusinessAdapter);

        bindIndicator(mBannerViewPager, mBannerIndicator, R.color.bg_white, R.color.transparent_gray);
        bindIndicator(mServiceViewPager, mServiceIndicator, R.color.text_red, R.color.text_gray);

        // 获取首页上方图片
        requestTopBanner();
        // 刷新城市信息
        refreshCity();
        // 请求商家列表
        tryLoadBusinessList(false);
    }

    @Override
    protected void setEvent() {
        // 搜索，被点击
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到搜索历史界面
                startFrameActivity(IndexSearchHisFragment.class);
            }
        });
        // 选择城市，被点击
        mCityCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到选择城市界面
                startFrameActivity(IndexSelectCityFragment.class);
            }
        });
        // 商家查看更多，被点击
        mBusinessTitleLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到商家搜索结果界面
                FragmentJumpUtil.toBusinessList(getUsualFragment(), "", "");
            }
        });
        // 商家列表被点击
        mBusinessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到商家详情
                IndexSellerListData data = mBusinessAdapter.getItem(position);
                if (data != null) {
                    // 跳转到商家详情
                    FragmentJumpUtil.toBusinessDetail(getUsualFragment(), data.getSellerId() + "");
                } else {
                    showToast("商家不存在！");
                }
            }
        });
        // 二维码按钮，被点击
        mScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到二维码界面
                PermissionHelper.requestPermission(getUsualFragment(), PermissionHelper.CODE_CAMERA, permissionGrant);
            }
        });
        // 消息按钮，被点击
        mMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到消息列表
                //startFrameActivity(IndexNotifyFragment.class);
            }
        });

        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // 刷新服务列表
                mCatPresenter.getGategory();
                // 刷新商家列表
                requestIndexSellerList(false, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // 加载更多商家列表
                requestIndexSellerList(true, false);
            }
        });
    }

    /**
     * 获取异步AsyncTask获取全部数据
     */
    private void toLoadCitys() {
        AsyncTask<String, String, List<Region>> task = new AsyncTask<String, String, List<Region>>() {

            @Override
            protected List<Region> doInBackground(String... params) {
                List<Region> result = ConfigModel.getInstace().getAllCitys();
                return result;
            }

            @Override
            protected void onPostExecute(List<Region> ts) {
                super.onPostExecute(ts);
                ApplicationData.getInstance().setCitys(ts);
            }
        };
        task.execute("");
    }

    /***************************************** 界面操作 ***********************************************/

    /**
     * 设置banner数据
     */
    private void initBanner(List<IndexBannerListData> data) {
        if (ArrayListUtil.isEmpty(data)) {
            // TODO 测试需要，若没有数据则造一个假数据
            data.add(new IndexBannerListData());
        }
        mBannerAdapter.setDatas(data);
    }

    /**
     * 设置服务数据
     */
    private void initService(List<List<ResShopCategory>> datas) {
        mServiceAdapter.setDatas(datas);
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

    /**
     * 保存当前定位城市
     */
    private void saveLocateCity(String cityName) {
        // 从数据库中查找城市信息
        Region region = ConfigModel.getInstace().getCityRegionByName(cityName);
        // 保存城市信息
        StorageUtil.saveSelectedRegion(region);
        // 更新当前城市
        if (region != null) {
            mCityCheck.setText(region.getName());
        }
    }

    /**
     * 刷新城市信息
     */
    private void refreshCity() {
        Region city = getCity();
        if (city != null) {
            mCityCheck.setText(city.getName());
        }
    }

    /**
     * 获取城市信息
     */
    private Region getCity() {
        Region city = StorageUtil.getSelectedRegion();
        if (city == null) {
            // 若城市为空，则默认保存成都市
            saveLocateCity("成都市");
            city = StorageUtil.getSelectedRegion();
        }
        return city;
    }

    /**
     * 获取地区id
     */
    private String getAreaIds() {
        StringBuilder areaIds = new StringBuilder("");
        Region city = getCity();
        if (city != null) {
            areaIds.append(city.getId() + "").append(",");
            List<Region> areas = ConfigModel.getInstace().findChildRegions(city.getId());
            if (!ArrayListUtil.isEmpty(areas)) {
                for (Region area : areas) {
                    areaIds.append(area.getId() + "").append(",");
                }
            }
        }
        return areaIds.length() > 0 ? areaIds.substring(0, areaIds.length() - 1) : "";
    }

    /**
     * 获取经度
     */
    private String getLatitude(double latitude) {
        return latitude < 0 ? "" : latitude + "";
    }

    /**
     * 获取纬度
     */
    private String getLongitude(double longitude) {
        return longitude < 0 ? "" : longitude + "";
    }

    /***************************************** 网络请求 ***********************************************/

    /**
     * 获取首页图片
     */
    private void requestTopBanner() {
        Request.getBannerTop(ListenerAdapter.createArrayListener(IndexBannerListData.class, new UsualDataBackListener<List<IndexBannerListData>>(this) {
            @Override
            protected void onComplete(boolean isSuccess, List<IndexBannerListData> data) {
                if (isSuccess) {
                    // 请求成功
                    initBanner(data);
                }
            }
        }));
    }


    /**
     * 尝试加载商家列表
     */
    private void tryLoadBusinessList(final boolean isLoadMore) {
        // 不管有无定位，先拉取一次数据
        requestIndexSellerList(isLoadMore, true);
        baiduLocation(isLoadMore);
    }

    private void baiduLocation(final boolean isLoadMore) {
        // 尝试百度定位，若定位成功则重新拉取一次数据
        LocationUtils location = LocationUtils.newInstance(getContext(), new LocationUtils.LocationCallBack() {
            @Override
            public boolean callBack(final BDLocation location) {
                if (location != null && location.getLatitude() != 4.9E-324 && location.getLongitude() != 4.9E-324) {
                    // 定位成功
                    mIsLocated = true;
                    mLatitude = location.getLatitude();
                    mLongitude = location.getLongitude();
                    requestIndexSellerList(isLoadMore, false);
                    // 若本地没有城市信息，保存当前定位的城市
                    Region city = StorageUtil.getSelectedRegion();
                    if (city == null) {
                        saveLocateCity(location.getAddress().city);
                    } else {
                        // 判断当前定位城市是否和保存城市一样
                        if (!TextUtils.equals(location.getAddress().city, city.getName()) && !ApplicationData.getInstance().isCheckSwitchCity()) {
                            // 弹出提示框
                            SelfAlertDialog dialog = new SelfAlertDialog();
                            dialog.setText(String.format("系统定位到您在%s，需要切换至%s吗?", location.getAddress().city, location.getAddress().city));
                            dialog.setSureClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApplicationData.getInstance().setCheckSwitchCity(true);
                                    // 切换城市
                                    saveLocateCity(location.getAddress().city);
                                    // 发送选择城市通知
                                    EventBus.getDefault().post(new DataEvent.OnSelectedCityEvent());
                                }
                            });
                            dialog.setCancelClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApplicationData.getInstance().setCheckSwitchCity(true);
                                }
                            });
                            dialog.show(getChildFragmentManager(), "switchCity");
                        }
                    }
                }
                return true;
            }
        });
        this.setRequestPermissionResultListener(location);
        location.startPermission(this);
    }

    /**
     * 请求商家列表数据
     */
    private void requestIndexSellerList(final boolean isLoadMore, final boolean needJudge) {
        mPageNum = isLoadMore ? ++mPageNum : 1;
        String pageSize = mPageSize + "";
        String areaIds = getAreaIds();
        Request.getSellerList(getLatitude(mLatitude), getLongitude(mLongitude), SortType.DISTANCEASC, null, areaIds, null, null, mPageNum + "", pageSize, ListenerAdapter.createArrayListener(IndexSellerListData.class, new UsualDataBackListener<List<IndexSellerListData>>(this) {
            @Override
            protected void onComplete(boolean isSuccess, List<IndexSellerListData> data) {
                if (isSuccess) {
                    if (needJudge && mIsLocated) {
                        // 若需要判断定位，且已定位成功，则此次请求不作处理
                        return;
                    }
                    mScrollView.onRefreshComplete();
                    PullToRefreshBase.Mode mode = data.size() < mPageNum ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                    mScrollView.setMode(mode);
                    if (isLoadMore) {
                        // 合并数据
                        data = ViewUtil.mergeList(mBusinessAdapter.getDatas(), data);
                    }
                    // 请求成功
                    mBusinessAdapter.setDatas(data);
                }
            }
        }));
        // TODO: 2017/5/23 上架测试用。
//        requestIndexSellerList2(isLoadMore, needJudge);
    }

    /**
     * 请求商家列表数据
     */
    private void requestIndexSellerList2(final boolean isLoadMore, final boolean needJudge) {
        mPageNum = isLoadMore ? ++mPageNum : 1;
        String pageSize = mPageSize + "";
        String areaIds = "2280,2281,2282,2283,2284,2285,2286,2287,2288,2289,2290,2291,2292,2293,2294,2295,2296,2297,2298,2299,3317,3318";
        String latitude = "30.550988";
        String longitude = "104.072818";
        Request.getSellerList(latitude, longitude, SortType.DISTANCEASC, null, areaIds, null, null, mPageNum + "", pageSize, ListenerAdapter.createArrayListener(IndexSellerListData.class, new UsualDataBackListener<List<IndexSellerListData>>(this) {
            @Override
            protected void onComplete(boolean isSuccess, List<IndexSellerListData> data) {
                if (isSuccess) {
                    if (needJudge && mIsLocated) {
                        // 若需要判断定位，且已定位成功，则此次请求不作处理
                        return;
                    }
                    mScrollView.onRefreshComplete();
                    PullToRefreshBase.Mode mode = data.size() < mPageNum ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                    mScrollView.setMode(mode);
                    if (isLoadMore) {
                        // 合并数据
                        data = ViewUtil.mergeList(mBusinessAdapter.getDatas(), data);
                    }
                    // 请求成功
                    mBusinessAdapter.setDatas(data);
                }
            }
        }));
    }

    @Override
    public void setCategory(List<ResShopCategory> datas) {
        // 为数据分组，10个一组
        List<List<ResShopCategory>> groupedCats = groupCatData(datas);
        // 初始化服务列表
        initService(groupedCats);
    }

    /**
     * 分类数据分组
     */
    private List<List<ResShopCategory>> groupCatData(List<ResShopCategory> datas) {
        List<List<ResShopCategory>> groupedData = new ArrayList<>();
        if (!ArrayListUtil.isEmpty(datas)) {
            int mod = datas.size() % 10;
            int count = mod == 0 ? datas.size() / 10 : datas.size() / 10 + 1;
            for (int i = 0; i < count; i++) {
                List<ResShopCategory> item = new ArrayList<>();
                for (int j = 0; j < 10; j++) {
                    int index = 10 * i + j;
                    if (index < datas.size()) {
                        item.add(datas.get(index));
                    }
                }
                groupedData.add(item);
            }
        }
        return groupedData;
    }


    /***************************************** 适配器 ***********************************************/

    /**
     * banner列表适配器
     */
    private class BannerAdapter extends QuickPagerAdapter<IndexBannerListData> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.index_main_banner_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, IndexBannerListData data) {
            String url = ApplicationData.getInstance().getImageUrl(data.getBannerUrl());
            ViewUtil.bindImage(convertView, R.id.index_main_banner_item_img, url, R.drawable.rebate_homepage_banner);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO banner被点击
                }
            });
        }
    }

    /**
     * 服务列表适配器
     */
    private class ServiceAdapter extends QuickPagerAdapter<List<ResShopCategory>> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.index_main_service_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, List<ResShopCategory> data) {
            AQuery query = new AQuery(convertView);
            ChildGridView serviceGrid = (ChildGridView) query.id(R.id.index_main_service_item_grid).getView();
            ServiceContentAdapter adapter = new ServiceContentAdapter();
            serviceGrid.setAdapter(adapter);
            adapter.setDatas(data);
            // 服务列表被点击
            serviceGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ResShopCategory data = (ResShopCategory) view.getTag();
                    // 跳转到商家筛选列表
                    FragmentJumpUtil.toBusinessList(getUsualFragment(), "", data.getId() + "");
                }
            });
        }
    }

    /**
     * 服务列表内容适配器
     */
    private class ServiceContentAdapter extends QuickAdapter<ResShopCategory> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.index_main_service_item_grid_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, ResShopCategory data) {
            AQuery query = new AQuery(convertView);
            String url = ApplicationData.getInstance().getImageUrl(data.getCategoryPicUrl());
            ViewUtil.bindImage(convertView, R.id.index_main_service_item_grid_item_img, url, 0);
            query.id(R.id.index_main_service_item_grid_item_name).text(data.getCategoryName());
            convertView.setTag(data);
        }
    }

    /**
     * 选择城市通知
     */
    @Subscribe
    public void onEvent(DataEvent.OnSelectedCityEvent event) {
        // 刷新城市信息
        refreshCity();
        // 刷新商家列表
        tryLoadBusinessList(false);
    }

    /**
     * 收藏成功
     */
    @Subscribe
    public void onEvent(DataEvent.OnCollectedEvent event) {
        // 刷新商家列表
        //tryLoadBusinessList(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mScrollView != null) {
            mScrollView.pullDownRefresh();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionHelper.requestPermissionsResult(this, requestCode, permissions, grantResults, permissionGrant);
    }
}
