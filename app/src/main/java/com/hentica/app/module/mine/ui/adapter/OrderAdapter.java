package com.hentica.app.module.mine.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.entity.mine.ResMineOrderListData;
import com.hentica.app.module.entity.type.OrderStatus;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.OrderStatusUtils;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

/**
 * Created by YangChen on 2017/4/6 17:49.
 * E-mail:656762935@qq.com
 */

public class OrderAdapter extends QuickAdapter<ResMineOrderListData> {

    private AQuery mQuery;

    private UsualFragment mParent;
    private boolean isSallerOrder;

    public OrderAdapter(UsualFragment parent, boolean isSallerOrder) {
        mParent = parent;
        this.isSallerOrder = isSallerOrder;
    }

    @Override
    protected int getLayoutRes(int type) {
        return R.layout.mine_orders_item;
    }

    @Override
    protected void fillView(int position, View convertView, ViewGroup parent, final ResMineOrderListData data) {
        mQuery = new AQuery(convertView);
        initSellerInfo(data.getSeller(), convertView);
        mQuery.id(R.id.mine_orders_status).text(OrderStatusUtils.getStatusDes(data.getStatus()));
        mQuery.id(R.id.mine_orders_retun_integral).text("赠送会员积分:" + PriceUtil.format4Decimal(data.getUserScore()));
        HtmlBuilder hb = HtmlBuilder.newInstance();
        hb.append(isSallerOrder ? "让利金额：" : "支付金额:").appendRed("￥" + PriceUtil.format(isSallerOrder ? data.getRebateAmount() : data.getAmount()));
        mQuery.id(R.id.mine_orders_pay_price).text(hb.create());
        mQuery.id(R.id.mine_orders_bottom_line).visibility(position == (getCount() - 1) ? View.GONE : View.VISIBLE);
        mQuery.id(R.id.mine_orders_time).text(String.format("消费时间：%s", DateHelper.stampToDate(data.getCreateDate() + "", "yyyy-MM-dd HH:mm")));
        // 刷新操作按钮显示状态
        refreshOptUI(data.getStatus());
        // 立即评价
        mQuery.id(R.id.mine_orders_comment_btn).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到立即评价列表
                FragmentJumpUtil.toFillEvaluate(mParent, data);
            }
        });
        // 查看评价
        mQuery.id(R.id.mine_orders_see_comment_btn).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到评价详情界面
                FragmentJumpUtil.toEvaluateDetail(mParent, data);
            }
        });
        // 再次消费
        mQuery.id(R.id.mine_orders_buy_agin_btn).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到商家详情
                FragmentJumpUtil.toBusinessDetail(mParent, getSellerId(data));
            }
        });
        //未支付显示操作按钮
        if (OrderStatus.UNPAID.equals(data.getStatus())) {
            mQuery.id(R.id.layout_options).visibility(View.GONE);
            mQuery.id(R.id.mine_orders_retun_integral).text("赠送会员积分:" + PriceUtil.format4Decimal(0));
        } else {
            mQuery.id(R.id.layout_options).visibility(View.VISIBLE);
        }
        convertView.setTag(data);
    }

    /**
     * 初始化店铺信息
     */
    private void initSellerInfo(ResMineOrderListData.SellerBean seller, View rootView) {
        if (seller != null) {
            mQuery.id(R.id.mine_orders_item_name).text(seller.getName());
            ViewUtil.bindImage(rootView, R.id.mine_orders_icon, ApplicationData.getInstance().getImageUrl(seller.getStorePictureUrl()));
        }
    }

    /**
     * 获取商家id
     */
    private String getSellerId(ResMineOrderListData data) {
        return data.getSeller() == null ? "" : data.getSeller().getId() + "";
    }

    /**
     * 刷新操作按钮显示状态
     */
    private void refreshOptUI(String status) {
        if (TextUtils.equals(status, OrderStatus.UNPAID)) {
            mQuery.id(R.id.mine_orders_comment_btn).gone();
            mQuery.id(R.id.mine_orders_see_comment_btn).gone();
        } else if (TextUtils.equals(status, OrderStatus.PAID)) {
            mQuery.id(R.id.mine_orders_comment_btn).visible();
            mQuery.id(R.id.mine_orders_see_comment_btn).gone();
        } else if (TextUtils.equals(status, OrderStatus.FINISHED)) {
            mQuery.id(R.id.mine_orders_comment_btn).gone();
            mQuery.id(R.id.mine_orders_see_comment_btn).visible();
        }
    }
}
