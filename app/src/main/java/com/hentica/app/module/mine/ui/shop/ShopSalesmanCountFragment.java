package com.hentica.app.module.mine.ui.shop;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.androidquery.AQuery;
import com.fiveixlg.app.customer.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.view.ChildListView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.RebateDataBackListener;
import com.hentica.app.module.common.listener.RebateListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.login.ResLoginData;
import com.hentica.app.module.entity.mine.shop.ResCustomerCountData;
import com.hentica.app.module.entity.mine.shop.ResSalesmanCountData;
import com.hentica.app.module.entity.type.AgencyLevel;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.ui.intf.V_ShopBusinessCenter;
import com.hentica.app.util.ArrayListUtil;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.StringUtil;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrScrollView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.hentica.appbase.famework.util.ViewUtil;
import com.meg7.widget.CircleImageView;

import java.util.List;

/**
 * 营业中心 - 业务员数
 * Created by YangChen on 2017/7/1 11:37.
 * E-mail:656762935@qq.com
 */
@SuppressLint("ValidFragment")
public class ShopSalesmanCountFragment extends BaseFragment implements V_ShopBusinessCenter {

    private ChildListView mListView;
    private CustomPtrScrollView mScrollView;
    private SalesmanCountAdapter mAdapter;
    private SalesmanCountAdapter2 mAdapter2;
    /**
     * 页数
     */
    private int mPageNum;
    /**
     * 每页大小
     */
    private int mPageSize = 10;
    private String mAreaId;
    private boolean mIsVisibleToUser;
    // 是否需要刷新
    private boolean mNeedReload = true;

    public ShopSalesmanCountFragment(String areaId) {
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
    protected void initData() {
        mAdapter = new SalesmanCountAdapter();
        mAdapter2 = new SalesmanCountAdapter2();
    }

    @Override
    protected void initView() {
        AQuery query = new AQuery(getView());
        mScrollView = (CustomPtrScrollView) query.id(R.id.shop_business_count_sv).getView();
        mListView = (ChildListView) query.id(R.id.shop_business_count_list).getView();
        ViewUtil.setText(getView(), R.id.shop_business_count_title, "业务员总数");
        ViewUtil.setText(getView(), R.id.shop_business_count_text, "正在统计中");
//        ListViewUtils.setDefaultEmpty(mListView);
    }

    @Override
    protected void setEvent() {
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // 刷新
//                if(!isCountyAgent()){
//                    requestSalesmanCount(false, mAreaId);
//                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // 加载更多
//                if(!isCountyAgent()){
//                    requestSalesmanCount(true, mAreaId);
//                }
            }
        });
    }

    /** 获取区代业务员列表数据 */
    private List<ResSalesmanCountData.ListBean> getCountyAgentSalesmanList(List<ResSalesmanCountData> data){
        List<ResSalesmanCountData.ListBean> list = null;
        if(!ArrayListUtil.isEmpty(data)){
            ResSalesmanCountData salesmanData = data.get(0);
            list = salesmanData.getList();
        }
        return list;
    }

    /**
     * 请求业务员数
     */
    public void requestSalesmanCount(final boolean isLoadMore, String areaId) {
        String userId = LoginModel.getInstance().getLoginUserId();
        mPageNum = isLoadMore ? ++mPageNum : 1;
        String pageSize = mPageSize + "";
        Request.getEndUserCountReport(userId, mPageNum + "", pageSize, areaId,
                RebateListenerAdapter.createArrayListener(ResSalesmanCountData.class, new RebateDataBackListener<List<ResSalesmanCountData>>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<ResSalesmanCountData> data) {
                        mScrollView.onRefreshComplete();
                        if (isSuccess && data != null && getView() != null) {
                            ResLoginData.AgentBean agent = getAgent();
                            String agentLevel = agent == null ? "" : agent.getAgencyLevel();
                            if(isCountyAgent()){
                                // 区代
                                List<ResSalesmanCountData.ListBean> list = getCountyAgentSalesmanList(data);
                                mAdapter2.setDatas(list);
                                mListView.setAdapter(mAdapter2);
                            }else {
                                // 非区代
                                PullToRefreshBase.Mode mode = data.size() < mPageSize
                                        ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                                mScrollView.setMode(mode);
                                if(isLoadMore){
                                    // 合并数据
                                    data = ViewUtil.mergeList(mAdapter.getDatas(), data);
                                }
                                mAdapter.setDatas(data);
                                mListView.setAdapter(mAdapter);
                            }
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

    /**
     * 获取代理商信息
     */
    private ResLoginData.AgentBean getAgent() {
        return LoginModel.getInstance().getUserAgent();
    }

    /**
     * 判断当前代理是否是区代
     */
    private boolean isCountyAgent(){
        ResLoginData.AgentBean agent = getAgent();
        String agentLevel = agent == null ? "" : agent.getAgencyLevel();
        return AgencyLevel.COUNTY.equals(agentLevel);
    }

    @Override
    public void onChooseArea(String areaId) {
//        mAreaId = areaId;
//        mNeedReload = true;
//        if (mIsVisibleToUser) {
//            // 当前界面正显示，请求界面数据
//            requestSalesmanCount(false, mAreaId);
//        }
    }

    /**
     * 业务员数适配器(非区代)
     */
    private class SalesmanCountAdapter extends QuickAdapter<ResSalesmanCountData> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.shop_business_count_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, ResSalesmanCountData data) {
            ViewUtil.setText(convertView, R.id.shop_business_count_area, data.getName());
            ViewUtil.setText(convertView, R.id.shop_business_count_amount, data.getCount() + "");
        }
    }

    /**
     * 业务员数适配器(区代)
     */
    private class SalesmanCountAdapter2 extends QuickAdapter<ResSalesmanCountData.ListBean> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.shop_business_count_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, final ResSalesmanCountData.ListBean data) {
            AQuery query = new AQuery(convertView);
            final String phoneNum = StringUtil.getNoNullString(data.getCellPhoneNum());
            CircleImageView imageView = (CircleImageView) query.id(R.id.shop_business_count_head).getView();
            imageView.setVisibility(View.VISIBLE);
            ViewUtil.bindImage(getContext(), imageView, data.getUserPhoto(), R.drawable.rebate_mine_comment_picture);
            ViewUtil.setText(convertView, R.id.shop_business_count_area, data.getNickName());
            ViewUtil.setText(convertView, R.id.shop_business_count_amount, phoneNum);
            query.id(R.id.shop_business_count_amount).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(!TextUtils.isEmpty(phoneNum)){
                       // 跳转到拨号界面
                       FragmentJumpUtil.toCalling(getUsualFragment(), data.getCellPhoneNum());
                   }
                }
            });
        }
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        mIsVisibleToUser = isVisibleToUser;
//        if (isVisibleToUser && mNeedReload) {
//            // 请求界面数据
//            requestSalesmanCount(false, mAreaId);
//
//        }
//    }
}
