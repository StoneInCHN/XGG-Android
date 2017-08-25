package com.hentica.app.module.mine.ui.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.androidquery.AQuery;
import com.fiveixlg.app.customer.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.view.ChildGridView;
import com.hentica.app.lib.view.ChildListView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.RebateDataBackListener;
import com.hentica.app.module.common.listener.RebateListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.module.entity.mine.shop.ResSellerCountData;
import com.hentica.app.module.entity.type.LeScoreType;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.ui.intf.V_ShopBusinessCenter;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.region.Region;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrScrollView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.hentica.appbase.famework.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商家中心 - 商家数界面
 * Created by YangChen on 2017/7/1 11:34.
 * E-mail:656762935@qq.com
 */
@SuppressLint("ValidFragment")
public class ShopBusinessCountFragment extends BaseFragment implements V_ShopBusinessCenter {

    public static final String AREA_ID = "Area_Id";
    public static final String IS_CHILD_VIEW = "Is_Child_View";

    private ChildListView mListView;
    /**
     * 页数
     */
    private int mPageNum;
    /**
     * 每页大小
     */
    private int mPageSize = 10;
    private SellerCountAdapter mAdapter;
    private String mAreaId;
    private boolean mIsVisibleToUser;
    private CustomPtrScrollView mScrollView;
    private boolean mIsChildView;

    // 是否需要刷新
    private boolean mNeedReload = true;

    public ShopBusinessCountFragment() {
    }

    public ShopBusinessCountFragment(String areaId) {
        mAreaId = areaId;
    }

    @Override
    public int getLayoutId() {
        return R.layout.shop_business_count_fragment;
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
        mIsChildView = intentUtil.getBoolean(IS_CHILD_VIEW, false);
        if(mIsChildView){
            // 当是子界面时，才获取地区id
            mAreaId = intentUtil.getString(AREA_ID);
        }
    }

    @Override
    protected void initData() {
        mAdapter = new SellerCountAdapter();
    }

    @Override
    protected void initView() {
        AQuery query = new AQuery(getView());
        mListView = (ChildListView) query.id(R.id.shop_business_count_list).getView();
        query.id(R.id.common_title).visibility(mIsChildView
                ? View.VISIBLE : View.GONE);
        mScrollView = (CustomPtrScrollView) query.id(R.id.shop_business_count_sv).getView();
        mListView.setAdapter(mAdapter);
        ViewUtil.setText(getView(), R.id.shop_business_count_title, "商家总数");
        ListViewUtils.setDefaultEmpty(mListView);
    }

    @Override
    protected void setEvent() {
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // 刷新
                requestSellerCountReport(false, mAreaId);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // 加载更多
                requestSellerCountReport(true, mAreaId);
            }
        });

        // 商家列表被点击
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResSellerCountData data = (ResSellerCountData) view.getTag();
                if(data != null){
                    Region region = ConfigModel.getInstace().getRegionById(data.getId()+"");
                    if(region != null && Constants.CONFIG_DB_IS_CITY.equals(region.getIs_city())){
                        // 跳转到商家数界面
                        FragmentJumpUtil.toBusinessCount(getUsualFragment(), region.getId()+"", true);
                    }
                }
            }
        });
    }

    private void refreshUI(List<ResSellerCountData> data) {
        Region area = ConfigModel.getInstace().getRegionById(mAreaId);
        String areaName = area == null ? "" : area.getName();
        ViewUtil.setText(getView(), R.id.shop_business_count_list_title, String.format("%s商家分布", areaName));
        mAdapter.setDatas(data);
    }


    /**
     * 请求商家数
     */
    public void requestSellerCountReport(final boolean isLoadMore, String areaId) {
        String userId = LoginModel.getInstance().getLoginUserId();
        mPageNum = isLoadMore ? ++mPageNum : 1;
        String pageSize = mPageSize + "";
        Request.getSellerCountReport(userId, mPageNum + "", pageSize, areaId,
                RebateListenerAdapter.createArrayListener(ResSellerCountData.class, new RebateDataBackListener<List<ResSellerCountData>>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<ResSellerCountData> data) {
                        mScrollView.onRefreshComplete();
                        if (isSuccess && data != null && getView() != null) {
                            PullToRefreshBase.Mode mode = data.size() < mPageSize
                                    ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                            mScrollView.setMode(mode);
                            if (isLoadMore) {
                                // 合并数据
                                data = ViewUtil.mergeList(mAdapter.getDatas(), data);
                            }
                            // 请求成功，刷新界面
                            refreshUI(data);
                            mNeedReload = false;
                        }
                    }

                    @Override
                    public void setResult(NetData data) {
                        super.setResult(data);
                        String countDes = data.getErrMsg();
                        ViewUtil.setText(getView(), R.id.shop_business_count_text, countDes);
                    }
                }));
    }

    @Override
    public void onChooseArea(String areaId) {
        mAreaId = areaId;
        mNeedReload = true;
        if (mIsVisibleToUser) {
            // 当前界面正显示，请求界面数据
            requestSellerCountReport(false, mAreaId);
        }
    }

    /**
     * 商家数适配器
     */
    private class SellerCountAdapter extends QuickAdapter<ResSellerCountData> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.shop_business_count_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, ResSellerCountData data) {
            ViewUtil.setText(convertView, R.id.shop_business_count_area, data.getName());
            ViewUtil.setText(convertView, R.id.shop_business_count_amount, data.getCount() + "");
            convertView.setTag(data);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && mNeedReload) {
            // 请求界面数据
            requestSellerCountReport(false, mAreaId);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsChildView) {
            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScrollView.pullDownRefresh();
                }
            }, 100);
        }
    }
}
