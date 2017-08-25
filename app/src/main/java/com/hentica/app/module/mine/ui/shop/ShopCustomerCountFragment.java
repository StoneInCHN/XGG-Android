package com.hentica.app.module.mine.ui.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.androidquery.AQuery;
import com.fiveixlg.app.customer.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.view.ChildListView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.RebateDataBackListener;
import com.hentica.app.module.common.listener.RebateListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.module.entity.mine.shop.ResCustomerCountData;
import com.hentica.app.module.entity.mine.shop.ResSellerCountData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.ui.intf.V_ShopBusinessCenter;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.region.Region;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrScrollView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.hentica.appbase.famework.util.ViewUtil;

import java.util.List;

/**
 * 营业中心 - 消费者数
 * Created by YangChen on 2017/7/1 11:35.
 * E-mail:656762935@qq.com
 */
@SuppressLint("ValidFragment")
public class ShopCustomerCountFragment extends BaseFragment implements V_ShopBusinessCenter {

    public static final String AREA_ID = "Area_Id";
    public static final String IS_CHILD_VIEW = "Is_Child_View";

    private ChildListView mListView;
    private CustomPtrScrollView mScrollView;
    /**
     * 页数
     */
    private int mPageNum;
    /**
     * 每页大小
     */
    private int mPageSize = 10;
    private CustomerCountAdapter mAdapter;
    private String mAreaId;
    private boolean mIsVisibleToUser;
    // 是否需要刷新
    private boolean mNeedReload = true;
    private boolean mIsChildView;

    public ShopCustomerCountFragment() {
    }


    public ShopCustomerCountFragment(String areaId) {
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
        mAreaId = intentUtil.getString(AREA_ID);
        mIsChildView = intentUtil.getBoolean(IS_CHILD_VIEW, false);
    }

    @Override
    protected void initData() {
        mAdapter = new CustomerCountAdapter();
    }

    @Override
    protected void initView() {
        AQuery query = new AQuery(getView());
        query.id(R.id.shop_business_count_list_title).visibility(View.GONE);
        query.id(R.id.common_title).visibility(mIsChildView
                ? View.VISIBLE : View.GONE);
        mScrollView = (CustomPtrScrollView) query.id(R.id.shop_business_count_sv).getView();
        mListView = (ChildListView) query.id(R.id.shop_business_count_list).getView();
        mListView.setAdapter(mAdapter);
        ViewUtil.setText(getView(), R.id.shop_business_count_title, "会员总数");
        ViewUtil.setText(getView(), R.id.shop_business_count_text, "正在统计中");
        //ListViewUtils.setDefaultEmpty(mListView);
    }

    @Override
    protected void setEvent() {
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // 刷新
                //requestEndUserCount(false, mAreaId);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // 加载更多
                //requestEndUserCount(true, mAreaId);
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
                        // 跳转到消费者数界面
                        FragmentJumpUtil.toCustomerCount(getUsualFragment(), region.getId()+"", true);
                    }
                }
            }
        });
    }

    private void refreshUI(List<ResCustomerCountData> data) {
        Region area = ConfigModel.getInstace().getRegionById(mAreaId);
        String areaName = area == null ? "" : area.getName();
        ViewUtil.setText(getView(), R.id.shop_business_count_list_title, String.format("%s消费者分布", areaName));
        mAdapter.setDatas(data);
    }


    /**
     * 请求消费者数
     */
    public void requestEndUserCount(final boolean isLoadMore, String areaId) {
        String userId = LoginModel.getInstance().getLoginUserId();
        mPageNum = isLoadMore ? ++mPageNum : 1;
        String pageSize = mPageSize + "";
        Request.getEndUserCountReport(userId, mPageNum + "", pageSize, areaId,
                RebateListenerAdapter.createArrayListener(ResCustomerCountData.class, new RebateDataBackListener<List<ResCustomerCountData>>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<ResCustomerCountData> data) {
                        mScrollView.onRefreshComplete();
                        if (isSuccess && data != null && getView() != null) {
                            PullToRefreshBase.Mode mode = data.size() < mPageSize
                                    ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                            mScrollView.setMode(mode);
                            if (isLoadMore) {
                                // 合并数据
                                data = ViewUtil.mergeList(mAdapter.getDatas(), data);
                            }
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
//        mAreaId = areaId;
//        mNeedReload = true;
//        if (mIsVisibleToUser) {
//            // 当前界面正显示，请求界面数据
//            requestEndUserCount(false, mAreaId);
//        }
    }

    /**
     * 消费者数适配器
     */
    private class CustomerCountAdapter extends QuickAdapter<ResCustomerCountData> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.shop_business_count_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, ResCustomerCountData data) {
            ViewUtil.setText(convertView, R.id.shop_business_count_area, data.getName());
            ViewUtil.setText(convertView, R.id.shop_business_count_amount, data.getCount() + "");
        }
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        mIsVisibleToUser = isVisibleToUser;
//        if (isVisibleToUser && mNeedReload) {
//            // 请求界面数据
//            requestEndUserCount(false, mAreaId);
//        }
//    }
}
