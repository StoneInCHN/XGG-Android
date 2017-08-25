package com.hentica.app.module.mine.ui;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.fragment.ptr.AbsPtrFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineOrderListData;
import com.hentica.app.module.entity.type.OrderStatus;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.ui.adapter.OrderAdapter;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.request.Request;
import com.hentica.appbase.famework.adapter.QuickAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * 通用订单列表界面
 * Created by YangChen on 2017/4/6 17:45.
 * E-mail:656762935@qq.com
 */

@SuppressLint("ValidFragment")
public class MineCommonOrderFragment extends AbsPtrFragment {

    /** 列表适配器 */
    private OrderAdapter mOrderAdapter;
    /** 订单状态 */
    private String mStatus;

    /** 是否需要刷新 */
    boolean mNeedReload = true;

    /** 当前页数 */
    private int mPageNum;
    /** 每页大小 */
    private int mPageSize = 10;

    private boolean isSallerOrder = false;

    public MineCommonOrderFragment(String status, boolean isSallerOrder){
        mStatus = status;
        this.isSallerOrder = isSallerOrder;
    }

    @Override
    protected QuickAdapter createListViewAdapter() {
        mOrderAdapter = new OrderAdapter(getUsualFragment(), isSallerOrder);
        return mOrderAdapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ResMineOrderListData data = (ResMineOrderListData) view.getTag();
        FragmentJumpUtil.toOrderDetail(getUsualFragment(),data,null);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        // 下拉刷新
        requestOrderList(false,true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        // 上拉加载
        requestOrderList(true,false);
    }

    public OrderAdapter getOrderAdapter(){
        return mOrderAdapter;
    }

    public String getStatus(){
        return mStatus;
    }

    public void onRefreshComplete(){
        mPtrLv.onRefreshComplete();
    }

    public void setMode(PullToRefreshBase.Mode mode){
        mPtrLv.setMode(mode);
    }

    public List<ResMineOrderListData> getDatas(){
        return mOrderAdapter.getDatas();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && mNeedReload){
            // 当前界面显示，并且需要刷新
            requestOrderList(false,false);
        }
    }

    /** 请求订单列表 */
    private void requestOrderList(final boolean isLoadMore,final boolean needReload){
        String userId = LoginModel.getInstance().getLoginUserId();
        mPageNum = isLoadMore ? ++mPageNum : 1;
        String pageSize = mPageSize + "";
        Request.getOrderGetOrderUnderUser(userId,mStatus, isSallerOrder, mPageNum+"",pageSize,
                ListenerAdapter.createArrayListener(ResMineOrderListData.class, new UsualDataBackListener<List<ResMineOrderListData>>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<ResMineOrderListData> data) {
                        if (isSuccess) {
                            // 请求成功
                            PullToRefreshBase.Mode mode = data.size() < mPageSize ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                            if(isLoadMore){
                                // 合并数据
                                data = ViewUtil.mergeList(getDatas(),data);
                            }
                            setDatas(data);
                            setMode(mode);
                            // 设为不需要刷新
                            mNeedReload = needReload;
                        }
                    }
                }));
    }

    /** 评论成功事件 */
    @Subscribe
    public void onEvent(DataEvent.OnEvaluatedEvent event){
        // 刷新界面
        requestOrderList(false,false);
    }

}
