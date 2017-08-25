package com.hentica.app.module.index;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.module.entity.index.IndexSellerListData;
import com.hentica.app.module.entity.mine.shop.ResShopCategory;
import com.hentica.app.module.entity.type.SortType;
import com.hentica.app.module.index.adapter.BusinessAdapter;
import com.hentica.app.module.index.adapter.FilterAdapter;
import com.hentica.app.module.index.impl.DataGetter;
import com.hentica.app.module.index.view.BusinessFilterView;
import com.hentica.app.module.mine.presenter.shop.ShopCategoryPresenter;
import com.hentica.app.module.mine.presenter.shop.ShopCategoryPresenterImpl;
import com.hentica.app.module.mine.view.shop.ShopCategoryView;
import com.hentica.app.util.ArrayListUtil;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.baidumap.LocationUtils;
import com.hentica.app.util.event.UiEvent;
import com.hentica.app.util.region.Region;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.MaxListView;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrListView;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商家列表界面
 *
 * @author 
 * @createTime 2017-03-23 下午15:13:27
 */
public class IndexBusinessListFragment extends BaseFragment implements ShopCategoryView{

    public static final String KEY_WORDS = "KeyWords";
    public static final String CATEGORY = "Category";

    @BindView(R.id.index_business_list_back_btn)
    ImageButton mBackBtn;
    @BindView(R.id.index_business_list_search_btn)
    TextView mSearchBtn;
    @BindView(R.id.index_business_list_area_check)
    CheckBox mAreaCheck;
    @BindView(R.id.index_business_list_class_check)
    CheckBox mClassCheck;
    @BindView(R.id.index_business_list_priority_check)
    CheckBox mPriorityCheck;
    @BindView(R.id.index_business_list_filter_check)
    CheckBox mFilterCheck;
    @BindView(R.id.index_business_list_ptr_listview)
    CustomPtrListView mPtrListView;
    @BindView(R.id.index_business_list_filter_layout)
    FrameLayout mFilterLayout;
    @BindView(R.id.index_business_list_filter_list)
    MaxListView mFilterLv;
    @BindView(R.id.index_business_list_filter_view)
    BusinessFilterView mBusinessFilterView;
    @BindView(R.id.common_status_bar)
    View mStatusBar;


    /** 筛选的地区信息 */
    private List<Region> mAreas = new ArrayList<>();
    /** 筛选的分类信息 */
    private List<ResShopCategory> mCategorys = new ArrayList<>();
    /** 筛选的优先级信息 */
    private List<FilterData> mPrioritys = new ArrayList<>();
    /** 所有筛选钮 */
    private List<CheckBox> mAllChecks = new ArrayList<>();
    /** 当前选中的地区 */
    private String mSelectedArea = "";
    /** 当前选中的分类 */
    private String mSelectedCat = "";
    /** 当前选中的优先级 */
    private String mSelectedPri = "";
    /** 当前选中的服务 */
    private String mSelectedService = "";
    /** 页数 */
    private int mPageNum;
    /** 每页大小 */
    private int mPageSize = 10;
    /** 搜索关键字 */
    private String mKeyWords;
    /** 筛选列表适配器 */
    private FilterAdapter mFilterAdapter;
    /** 商家列表适配器 */
    private BusinessAdapter mAdapter;
    /**
     * 当前纬度
     */
    private double mLatitude = -1;
    /**
     * 当前经度
     */
    private double mLongitude = -1;

    private ShopCategoryPresenter mCatPresenter;
    /** 是否已成功定位 */
    private boolean mIsLocated;

    @Override
    public int getLayoutId() {
        return R.layout.index_business_list_fragment;
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
        IntentUtil intentUtil = new IntentUtil(intent);
        mKeyWords = intentUtil.getString(KEY_WORDS);
        mSelectedCat = intentUtil.getString(CATEGORY);
    }

    @Override
    protected void initData() {
        mAdapter = new BusinessAdapter(getContext());
        mCatPresenter = new ShopCategoryPresenterImpl(this);
        // 请求分类数据
        mCatPresenter.getGategory();
        createAreaDatas();
        createPriorityDatas();
        initAllChecks();

        tryLoadBusinessList(false);
    }

    @Override
    protected void initView() {
        mSearchBtn.setText(mKeyWords);
        mFilterLv.setListViewHeight(800);
        mPtrListView.setAdapter(mAdapter);
        ListViewUtils.setDefaultEmpty(mPtrListView.getRefreshableView());
        ViewUtil.setKeepStatusBar(getView(),mStatusBar,getContext(),true);
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
        // 搜索按钮，被点击
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到搜索历史界面
                startFrameActivity(IndexSearchHisFragment.class);
            }
        });
        // 选择地区被点击
        mAreaCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryShowFilterLv(mAreaCheck,true);
                mFilterAdapter = null;
                mFilterAdapter = new FilterAdapter(new DataGetter<Region>() {
                    @Override
                    public String getText(Region data) {
                        return data.getName();
                    }

                    @Override
                    public String getValue(Region data) {
                        return data.getId();
                    }
                });
                mFilterLv.setAdapter(mFilterAdapter);
                mFilterAdapter.setDefaultVal(mSelectedArea);
                mFilterAdapter.setDatas(mAreas);
            }
        });
        // 选择分类被点击
        mClassCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryShowFilterLv(mClassCheck,true);
                mFilterAdapter = null;
                mFilterAdapter = new FilterAdapter(new DataGetter<ResShopCategory>() {
                    @Override
                    public String getText(ResShopCategory data) {
                        return data.getCategoryName();
                    }

                    @Override
                    public String getValue(ResShopCategory data) {
                        return data.getId()+"";
                    }
                });
                mFilterLv.setAdapter(mFilterAdapter);
                mFilterAdapter.setDefaultVal(mSelectedCat);
                mFilterAdapter.setDatas(mCategorys);
            }
        });
        // 选择优先级被点击
        mPriorityCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryShowFilterLv(mPriorityCheck,true);
                mFilterAdapter = null;
                mFilterAdapter = new FilterAdapter(new DataGetter<FilterData>() {
                    @Override
                    public String getText(FilterData data) {
                        return data.getName();
                    }

                    @Override
                    public String getValue(FilterData data) {
                        return data.getValue();
                    }
                });
                mFilterLv.setAdapter(mFilterAdapter);
                mFilterAdapter.setDefaultVal(mSelectedPri);
                mFilterAdapter.setDatas(mPrioritys);
            }
        });

        // 筛选列表被点击
        mFilterLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) view.getTag(R.id.tagOfValueId);
                String name = (String) view.getTag(R.id.tagOfNameId);
                int checkedId = getSelectedCheckId();
                if(checkedId == mAreaCheck.getId()){
                    // 选择地区
                    mSelectedArea = value;
                    mAreaCheck.setText(name);
                }else if(checkedId == mClassCheck.getId()){
                    // 选择分类
                    mSelectedCat = value;
                    mClassCheck.setText(name);
                }else if(checkedId == mPriorityCheck.getId()){
                    // 选择优先级
                    mSelectedPri = value;
                    mPriorityCheck.setText(name);
                }
                // 隐藏筛选界面
                mFilterLayout.setVisibility(View.GONE);
                // 请求商家列表
                requestIndexSellerList(false,false);
            }
        });

        // 筛选被点击
        mFilterCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryShowFilterLv(mFilterCheck,false);
                mBusinessFilterView.setServiceType(mSelectedService);
            }
        });

        // 筛选面板，完成被点击
        mBusinessFilterView.setListener(new BusinessFilterView.OnCompleteClickListener() {
            @Override
            public void onComplete(String serviceType) {
                mSelectedService = serviceType;
                // 隐藏筛选面板
                mFilterLayout.setVisibility(View.GONE);
                // 请求商家列表
                requestIndexSellerList(false,false);
            }
        });

        // 筛选面板背景，被点击
        mFilterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏筛选界面
                mFilterLayout.setVisibility(View.GONE);
            }
        });

        // 商家列表被点击
        mPtrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到商家详情
                IndexSellerListData data = mAdapter.getItem(position - 1);
                if(data != null){
                    // 跳转到商家详情
                    FragmentJumpUtil.toBusinessDetail(getUsualFragment(),data.getSellerId()+"");
                }else {
                    showToast("商家不存在！");
                }
            }
        });

        // 列表刷新
        mPtrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                requestIndexSellerList(false,false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉加载
                requestIndexSellerList(true,false);
            }
        });
    }

    /**
     * 获取城市id
     */
    private String getCityId(String id, List<Region> areas) {
        StringBuilder cityId = new StringBuilder("");
        cityId.append(id).append(",");
        if (!ArrayListUtil.isEmpty(areas)) {
            for (Region area : areas) {
                if (!TextUtils.equals(area.getId(), id)) {
                    cityId.append(area.getId()).append(",");
                }
            }

        }
        return cityId.length() > 0 ? cityId.substring(0, cityId.length() - 1) : "";
    }


    /**
     * 更新区域id
     */
    private void updateAreasId(Region city, List<Region> areas) {
        final String cityId = city.getId();
        if (!ArrayListUtil.isEmpty(areas)) {
            for (Region area : areas) {
                if (area.getId() == cityId) {
                    // 是城市，加上所有id
                    area.setId(getCityId(cityId, areas));
                } else {
                    // 是区，只加上本区id
                    area.setId(cityId + "," + area.getId());
                }
            }
        }
    }

    /** 创建地区信息 */
    private void createAreaDatas(){
        Region city = StorageUtil.getSelectedRegion();
        if(city != null){
            mAreas = ConfigModel.getInstace().findChildRegions(city.getId());
            city.setName("不限");
            mAreas.add(0,city);
            updateAreasId(city,mAreas);
            mSelectedArea = city.getId();
        }
    }

    /** 创建优先级信息 */
    private void createPriorityDatas(){
        mPrioritys.add(new FilterData("默认排序", SortType.DEFAULT));
        mPrioritys.add(new FilterData("距离优先", SortType.DISTANCEASC));
        mPrioritys.add(new FilterData("好评优先", SortType.SCOREDESC));
        mPrioritys.add(new FilterData("收藏最多", SortType.COLLECTDESC));
        mSelectedPri = SortType.DISTANCEASC;
        mPriorityCheck.setText("距离优先");
    }

    /** 初始化筛选钮集合 */
    private void initAllChecks(){
        mAllChecks.add(mAreaCheck);
        mAllChecks.add(mClassCheck);
        mAllChecks.add(mPriorityCheck);
        mAllChecks.add(mFilterCheck);
    }

    @Override
    public void setCategory(List<ResShopCategory> datas) {
        // 获取分类数据
        mCategorys = datas;
//        if(!ArrayListUtil.isEmpty(mCategorys)){
//            mSelectedCat = mCategorys.get(0).getId()+"";
//        }
    }

    /** 显示筛选列表 */
    private void tryShowFilterLv(CheckBox check,boolean isCommon){
        boolean isChecked = check.isChecked();
        if(isCommon){
            if(!isChecked){
                if(mFilterLayout.getVisibility() == View.VISIBLE){
                    // 隐藏筛选界面
                    mFilterLayout.setVisibility(View.GONE);
                }else {
                    // 显示筛选界面1
                    showFilterUI1();
                }
                check.setChecked(!isChecked);
            }else {
                // 刷新选中按钮状态
                refreshCheckedStatus(check);
                // 显示筛选界面1
                showFilterUI1();
            }
        }else {
            if(!isChecked){
                if(mFilterLayout.getVisibility() == View.VISIBLE){
                    // 隐藏筛选界面
                    mFilterLayout.setVisibility(View.GONE);
                }else {
                    // 显示筛选界面2
                    showFilterUI2();
                }
                check.setChecked(!isChecked);
            }else {
                // 刷新选中按钮状态
                refreshCheckedStatus(check);
                // 显示筛选界面2
                showFilterUI2();
            }
        }

    }

    /** 刷新选中按钮状态 */
    private void refreshCheckedStatus(CheckBox checkBox){
        for(CheckBox cb : mAllChecks){
            cb.setChecked(checkBox.getId() == cb.getId());
        }
    }

    /** 获取当前选中的筛选按钮id */
    private int getSelectedCheckId(){
        for(CheckBox cb : mAllChecks){
            if(cb.isChecked()){
                return cb.getId();
            }
        }
        return 0;
    }

    /** 获取经度 */
    private String getLatitude(){
        return mLatitude < 0 ? "" : mLatitude+"";
    }

    /** 获取纬度 */
    private String getLongitude(){
        return mLongitude < 0 ? "" : mLongitude+"";
    }

    /** 显示筛选界面1 */
    private void showFilterUI1(){
        if(mFilterLayout.getVisibility() != View.VISIBLE){
            mFilterLayout.setVisibility(View.VISIBLE);
        }
        // 显示筛选列表
        if(mFilterLv.getVisibility() != View.VISIBLE){
            mFilterLv.setVisibility(View.VISIBLE);
        }
        // 隐藏筛选界面
        if(mBusinessFilterView.getVisibility() != View.GONE){
            mBusinessFilterView.setVisibility(View.GONE);
        }
    }

    /** 显示筛选列表2 */
    private void showFilterUI2(){
        if(mFilterLayout.getVisibility() != View.VISIBLE){
            mFilterLayout.setVisibility(View.VISIBLE);
        }
        // 隐藏筛选列表
        if(mFilterLv.getVisibility() != View.GONE){
            mFilterLv.setVisibility(View.GONE);
        }
        // 显示筛选界面
        if(mBusinessFilterView.getVisibility() != View.VISIBLE){
            mBusinessFilterView.setVisibility(View.VISIBLE);
        }
    }

    /** 内部类，一般筛选数据结构 */
    private class FilterData{
        private String name;
        private String value;

        public FilterData(){}

        public FilterData(String name,String value){
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String mName) {
            name = mName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String mValue) {
            value = mValue;
        }
    }

    /** 尝试请求商家列表 */
    private void tryLoadBusinessList(final boolean isLoadMore){
        // 先拉取数据
        requestIndexSellerList(isLoadMore,true);
        // 尝试定位，定位成功后刷新数据
        LocationUtils locate = LocationUtils.newInstance(getContext(), new LocationUtils.LocationCallBack() {
            @Override
            public boolean callBack(BDLocation location) {
                if(location != null && location.getLatitude() != 4.9E-324 && location.getLongitude() != 4.9E-324){
                    mIsLocated = true;
                    mLatitude = location.getLatitude();
                    mLongitude = location.getLongitude();
                    requestIndexSellerList(false,false);
                }
                return true;
            }
        });
        this.setRequestPermissionResultListener(locate);
        locate.startPermission(this);
    }

    /**
     * 请求商家列表
     */
    private void requestIndexSellerList(final boolean isLoadMore, final boolean needJudge) {
        mPageNum = isLoadMore ? ++mPageNum : 1;
        String pageSize = mPageSize + "";
        Request.getSellerList(getLatitude(), getLongitude(), mSelectedPri, mSelectedCat, mSelectedArea, mSelectedService, mKeyWords, mPageNum + "", pageSize,
                ListenerAdapter.createArrayListener(IndexSellerListData.class, new UsualDataBackListener<List<IndexSellerListData>>(this) {
            @Override
            protected void onComplete(boolean isSuccess, List<IndexSellerListData> data) {
                if (isSuccess) {
                    if(needJudge && mIsLocated){
                        // 需判断定位，定位成功后，此次请求不做处理
                        return;
                    }
                    // 请求成功
                    mPtrListView.onRefreshComplete();
                    PullToRefreshBase.Mode mode = data.size() < mPageSize ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                    mPtrListView.setMode(mode);
                    if(isLoadMore){
                        // 合并数据
                        data = ViewUtil.mergeList(mAdapter.getDatas(),data);
                    }
                    mAdapter.setDatas(data);
                }
            }
        }));
    }

    /** 搜索历史跳转到搜素结果界面 */
    @Subscribe
    public void onEvent(UiEvent.JumpToSearchResultEvent event){
        // 结束当前界面
        finish();
    }

}
