package com.hentica.app.module.mine.ui.shop;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.fiveixlg.app.customer.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.lib.view.ChildListView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.shop.ResConsumeAmountData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.ui.intf.V_ShopBusinessCenter;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrListView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.hentica.appbase.famework.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 营业中心 - 交易额界面
 * Created by YangChen on 2017/7/1 11:33.
 * E-mail:656762935@qq.com
 */
@SuppressLint("ValidFragment")
public class ShopTradingVolumeFragment extends BaseFragment implements V_ShopBusinessCenter{

    /** 页数 */
    private int mPageNum;
    /** 每页大小 */
    private int mPageSize = 10;
    private CustomPtrListView mListView;
    private String mAreaId;
    private String mStartDateStamp = "";
    private String mEndDateStamp = "";
    // 是否需要刷新
    private boolean mNeedReload = true;

    private BusinessCountAdapter mCountAdapter;

    // 当前界面是否可见
    private boolean mIsVisibleToUser;

    public ShopTradingVolumeFragment(String areaId){
        mAreaId = areaId;
    }

    @Override
    public int getLayoutId() {
        return R.layout.shop_trading_volume_fragment;
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
        mCountAdapter = new BusinessCountAdapter();
    }

    @Override
    protected void initView() {
        AQuery query = new AQuery(getView());
        mListView = (CustomPtrListView) query.id(R.id.shop_trading_volume_list).getView();
        mListView.setAdapter(mCountAdapter);
        ListViewUtils.setDefaultEmpty(mListView.getRefreshableView());
    }

    @Override
    protected void setEvent() {
        // 下拉刷新
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 刷新
                requestConsumeCount(false, mAreaId);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 加载更多
                requestConsumeCount(true, mAreaId);
            }
        });
    }
    /** 交易额适配器 */
    private class BusinessCountAdapter extends QuickAdapter<ResConsumeAmountData> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.shop_trading_volume_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, ResConsumeAmountData data) {
            AQuery query = new AQuery(convertView);
            ChildListView listView = (ChildListView) query.id(R.id.shop_trading_list).getView();
            query.id(R.id.shop_trading_volume_date).text(data.getDate());
            query.id(R.id.shop_trading_volume_total).text(String.format("￥%s", PriceUtil.format4Decimal(data.getTotalAmount())));
            BusinessAdapter adapter = new BusinessAdapter();
            adapter.setDatas(data.getDiscountAmounts());
            listView.setAdapter(adapter);
        }
    }

    /** 筛选了地区 */
    @Override
    public void onChooseArea(String areaId){
        mAreaId = areaId;
        mNeedReload = true;
        if(mIsVisibleToUser){
            // 当前界面正显示，请求界面数据
            requestConsumeCount(false, mAreaId);
        }
    }

    /** 选择了筛选时间 */
    public void setFilterDate(String startDate, String endDate){
        mStartDateStamp = startDate;
        mEndDateStamp = endDate;
        requestConsumeCount(false, mAreaId);
    }

    /** 交易适配器 */
    private class BusinessAdapter extends QuickAdapter<ResConsumeAmountData.DiscountAmountsBean>{

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.shop_trading_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, ResConsumeAmountData.DiscountAmountsBean data) {
            ViewUtil.setText(convertView, R.id.shop_trading_discount, String.format("%.1f折", data.getDiscount()));
            ViewUtil.setText(convertView, R.id.shop_trading_money, String.format("￥%s",PriceUtil.format4Decimal(data.getAmount())));
        }
    }

    /** 请求交易额 */
    private void requestConsumeCount(final boolean isLoadMore, String areaId){
        String userId = LoginModel.getInstance().getLoginUserId();
        mPageNum = isLoadMore ? ++mPageNum : 1;
        String pageSize = mPageSize + "";
        Request.getConsumeAmountReport(userId, mPageNum+"", pageSize, areaId, mStartDateStamp, mEndDateStamp,
                ListenerAdapter.createArrayListener(ResConsumeAmountData.class, new UsualDataBackListener<List<ResConsumeAmountData>>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<ResConsumeAmountData> data) {
                        mListView.onRefreshComplete();
                        if(isSuccess && data != null && getView() != null){
                            PullToRefreshBase.Mode mode = data.size() < mPageSize
                                    ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                            mListView.setMode(mode);
                            if(isLoadMore){
                                mCountAdapter.addAll(data);
                            }else {
                                mCountAdapter.setDatas(data);
                            }
                            mNeedReload = false;
                        }
                    }
                }));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        if(isVisibleToUser && mNeedReload){
            // 请求界面数据
            requestConsumeCount(false, mAreaId);

        }
    }
}
