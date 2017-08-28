package com.hentica.app.module.mine.ui.shop;

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
import com.hentica.app.module.mine.presenter.IOrderDeletePresenter;
import com.hentica.app.module.mine.presenter.OrderDeletePresenterImpl;
import com.hentica.app.module.mine.presenter.OrderManagerPtrPresenter;
import com.hentica.app.module.mine.ui.adapter.OrderManagerAdapter;
import com.hentica.app.module.mine.view.IOrderDeleteView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.widget.dialog.SelfAlertDialog;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.hentica.appbase.famework.adapter.QuickAdapter;

import java.util.List;

/**
 * 录单订单
 * Created by Snow on 2017/7/5.
 */

public class MineOrderSallerPageFragment extends MineOrderPageFragment implements IOrderDeleteView {
    private IOrderDeletePresenter mOrderDeletePresenter;

    /**
     * @param orderStatus 订单状态 "UNPAID"未支付, "PAID"已支付待评价 , "FINISHED"评价后，已完成
     * @param isClearing  结算状态(结算中:false;已结算:true)
     */
    public MineOrderSallerPageFragment(ResShopInfo shopInfo, String orderStatus, String isClearing, String statusDesc) {
        super(shopInfo, orderStatus, isClearing, statusDesc);
    }

    @Override
    protected void initData() {
        mPtrPresenter = new OrderManagerPtrPresenter(this, String.valueOf(mShopInfo.getId()), true, orderStatus, isClear);
        mOrderDeletePresenter = new OrderDeletePresenterImpl();
        mOrderDeletePresenter.attachView(this);
    }

    @Override
    public void onClick(View view, OrderManagerAdapter.ViewHolder holder, int pos, ResShopOrderItem data) {
        switch (view.getId()) {
            case R.id.item_btn_reply:
                if (OrderStatus.UNPAID.equals(data.getStatus())) {
                    //删除订单
                    deleteOrder(data);
                } else {
                    //回复与查看
                    onEvaluateBtnEvent(data);
                }
                break;
            case R.id.item_btn_call:
                if (OrderStatus.UNPAID.equals(data.getStatus())) {
                    //立即支付
                    toPayOrder(data);
                } else {
                    //联系电话
                    toDial(data);
                }
                break;
        }
    }

    private void deleteOrder(final ResShopOrderItem data) {
        SelfAlertDialogHelper.showDialog(getFragmentManager(), "确定要删除此订单吗？删除后不可恢复！", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderDeletePresenter.deleteOrder(String.valueOf(data.getId()));
            }
        });
    }

    private void toPayOrder(ResShopOrderItem data) {
        FragmentJumpUtil.toRecordOrderPayFragment(getUsualFragment(), data.getSn(), String.valueOf(data.getSeller().getId()), data.getSeller().getName(), data.getAmount() + "");
    }

    @Override
    public void deleteOrderResult(boolean isSuccess) {
        if (isSuccess) {
            showToast("操作成功！");
            if (mPtrPresenter != null) {
                mPtrPresenter.onRefresh();
            }
        } else {
            showToast("操作失败！");
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (mOrderDeletePresenter != null) mOrderDeletePresenter.detachView();
    }
}
