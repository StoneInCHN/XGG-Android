package com.hentica.app.module.mine.ui.shop;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fiveixlg.app.customer.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.fragment.ptr.AbsPtrFragment;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.entity.mine.shop.ResShopInfo;
import com.hentica.app.module.entity.mine.shop.ResShopOrderItem;
import com.hentica.app.module.entity.type.OrderStatus;
import com.hentica.app.module.mine.model.ShopModel;
import com.hentica.app.module.mine.presenter.OrderManagerPtrPresenter;
import com.hentica.app.module.mine.ui.adapter.OrderManagerAdapter;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.appbase.famework.adapter.QuickAdapter;

import java.util.List;

/**
 * 买单
 * Created by Snow on 2017/7/5.
 */

@SuppressLint("ValidFragment")
public class MineOrderPageFragment extends AbsPtrFragment<ResShopOrderItem> implements PtrView<ResShopOrderItem>, OrderManagerAdapter.OnItemOptionBtnClickListener{

    protected OrderManagerPtrPresenter mPtrPresenter;
    protected ResShopInfo mShopInfo;
    protected String orderStatus;
    protected String isClear;
    protected String mStatusDesc;

    /**
     *
     * @param orderStatus 订单状态 "UNPAID"未支付, "PAID"已支付待评价 , "FINISHED"评价后，已完成
     * @param isClearing  结算状态(结算中:false;已结算:true)
     */
    public MineOrderPageFragment(ResShopInfo shopInfo, String orderStatus, String isClearing, String statusDesc) {
        mShopInfo = shopInfo;
        this.orderStatus = orderStatus;
        this.isClear = isClearing;
        this.mStatusDesc = statusDesc;
    }

    @Override
    protected QuickAdapter<ResShopOrderItem> createListViewAdapter() {
        return new OrderManagerAdapter(this, this, mStatusDesc);
    }

    @Override
    protected void initData() {
        super.initData();
        mPtrPresenter = new OrderManagerPtrPresenter(this, String.valueOf(mShopInfo.getId()), false, orderStatus, isClear);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ResShopOrderItem itemData = getItem(position - 1);
        FragmentJumpUtil.toOrderDetail(getUsualFragment(), null, itemData);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        mPtrPresenter.onRefresh();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        mPtrPresenter.onLoadMore();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPtrPresenter.onRefresh();
    }

    @Override
    public void setListDatas(List<ResShopOrderItem> datas) {
        super.setDatas(datas);
    }

    @Override
    public void addListDatas(List<ResShopOrderItem> datas) {
        super.addDatas(datas);
    }

    @Override
    public void onClick(View view, OrderManagerAdapter.ViewHolder holder, int pos, ResShopOrderItem data) {
        switch (view.getId()) {
            case R.id.item_btn_reply:
                onEvaluateBtnEvent(data);
                break;
            case R.id.item_btn_call:
                toDial(data);
                break;
        }
    }

    /**
     * 回复与查看回复
     * @param data
     */
    protected void onEvaluateBtnEvent (ResShopOrderItem data){
        if (OrderStatus.FINISHED.equals(data.getStatus())) {
            if (data.getEvaluate().getSellerReply() == null) {
                //回复评论
                FragmentJumpUtil.toReplyEvaluate(getUsualFragment(), data.getId());
            } else {
                //查看评论
                FragmentJumpUtil.toEvaluateDetail(getUsualFragment(), data.getId(), ShopModel.getInstance().getShopInfo());
            }
        }
    }

    /**
     * 打电话
     * @param data
     */
    protected void toDial(ResShopOrderItem data){
        FragmentJumpUtil.toDial(getContext(), data.getEndUser().getCellPhoneNum());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mPtrPresenter != null) {
            mPtrPresenter.onRefresh();
        }
    }
}
