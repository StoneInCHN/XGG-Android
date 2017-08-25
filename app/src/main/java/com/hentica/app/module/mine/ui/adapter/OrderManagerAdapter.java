package com.hentica.app.module.mine.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.entity.mine.shop.ResShopInfo;
import com.hentica.app.module.entity.mine.shop.ResShopOrderItem;
import com.hentica.app.module.entity.type.OrderStatus;
import com.hentica.app.module.mine.model.ShopModel;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.PriceUtil;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/29.11:31
 */

public class OrderManagerAdapter extends QuickAdapter<ResShopOrderItem> {

    private UsualFragment mFragment;
    private OnItemOptionBtnClickListener mItemOptionBtnClickListener;
    private String mStatusDesc;

    public OrderManagerAdapter(UsualFragment fragment, OnItemOptionBtnClickListener l, String statusDesc) {
        this.mFragment = fragment;
        this.mItemOptionBtnClickListener = l;
        mStatusDesc = statusDesc;
    }

    public interface OnItemOptionBtnClickListener {

        void onClick(View view, ViewHolder holder, int pos, final ResShopOrderItem data);
    }

    @Override
    protected int getLayoutRes(int type) {
        return R.layout.mine_order_manager_item;
    }

    @Override
    protected void fillView(int position, final View convertView, ViewGroup parent, final ResShopOrderItem data) {
        switchDividerView(convertView, position);
        ViewHolder holder = getViewHolder(convertView);
        fillViewBaseInfo(holder, position, data);

    }

    /**
     * 切换分隔线显示
     *
     * @param convertView
     * @param position
     */
    private void switchDividerView(View convertView, int position) {
        if (position == 0) {
            convertView.findViewById(R.id.mine_item_top_div_1).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.mine_item_top_div_2).setVisibility(View.GONE);
        } else {
            convertView.findViewById(R.id.mine_item_top_div_1).setVisibility(View.GONE);
            convertView.findViewById(R.id.mine_item_top_div_2).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取ViewHolder
     *
     * @param convertView
     * @return
     */
    private ViewHolder getViewHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        return holder;
    }

    /**
     * 显示界面中基本信息
     *
     * @param holder
     * @param pos
     * @param data
     */
    private void fillViewBaseInfo(final ViewHolder holder, final int pos, final ResShopOrderItem data) {
        holder.mTvCustomerNamer.setText(data.getEndUser().getNickName());//消费者昵称
        holder.mTvDate1.setText(String.format("消费时间：%s", DateHelper.stampToDate(data.getCreateDate() + "", "yyyy-MM-dd HH:mm")));//日期
        holder.mTvOrderSn.setText("订单编号：" + data.getSn());//订单编号
        holder.mTvScore.setText("赠送商家积分：" + PriceUtil.format4Decimal(OrderStatus.UNPAID.equals(data.getStatus()) ? 0 : data.getSellerScore()));//商家积分
        HtmlBuilder hBuilder = HtmlBuilder.newInstance();
        hBuilder.appendNormal(data.isIsSallerOrder() ? "让利金额：" : "支付金额：")
                .appendRed(String.format("￥%s", PriceUtil.format(data.isIsSallerOrder() ? data.getRebateAmount() : data.getAmount())));
        holder.mTvAmount.setText(hBuilder.create());//金额
        fillViewStatus(holder, pos, data);
        setItemOptionEvent(holder, pos, data);
        fillViewByOrderStatus(holder, pos, data);
    }

    /**
     * 显示item状态
     *
     * @param holder
     * @param pos
     * @param data
     */
    private void fillViewStatus(ViewHolder holder, int pos, ResShopOrderItem data) {
        if (!TextUtils.isEmpty(mStatusDesc)) {
            holder.mTvStatus.setText(mStatusDesc);
        } else if (OrderStatus.UNPAID.equals(data.getStatus())) {
            holder.mTvStatus.setText("未支付");
        } else if (data.isIsClearing()) {
            holder.mTvStatus.setText("已结算");
        } else if (OrderStatus.PAID.equals(data.getStatus())) {
            holder.mTvStatus.setText("已支付");
        } else if (OrderStatus.FINISHED.equals(data.getStatus())) {
            holder.mTvStatus.setText("已完成");
        } else {
            holder.mTvStatus.setText("结算中");
        }
    }

    /**
     * 设置按钮事件
     *
     * @param holder
     * @param pos
     * @param data
     */
    private void setItemOptionEvent(final ViewHolder holder, final int pos, final ResShopOrderItem data) {
        setItemOptionClickListener(holder.mBtnReply, holder, pos, data);
        setItemOptionClickListener(holder.mBtnCall, holder, pos, data);
    }

    private void setItemOptionClickListener(View v, final ViewHolder holder, final int pos, final ResShopOrderItem data) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemOptionBtnClickListener != null)
                    mItemOptionBtnClickListener.onClick(v, holder, pos, data);
            }
        });
    }

    /**
     * 根据订单状态显示界面
     *
     * @param holder
     * @param pos
     * @param data
     */
    private void fillViewByOrderStatus(ViewHolder holder, int pos, final ResShopOrderItem data) {
        if (!data.isIsSallerOrder()) {//买单订单
            fillViewNormalOrder(holder, pos, data);
        } else {
            fillViewSallerOrder(holder, pos, data);
        }
    }

    /**
     * 买单
     *
     * @param holder
     * @param pos
     * @param data
     */
    private void fillViewNormalOrder(ViewHolder holder, int pos, final ResShopOrderItem data) {
        if (OrderStatus.UNPAID.equals(data.getStatus())) {
            //未支付
            holder.mItemView.findViewById(R.id.layout_options).setVisibility(View.GONE);
            holder.mItemView.findViewById(R.id.layout_options_divider).setVisibility(View.GONE);
            holder.mTvScore.setText("赠送商家积分：" + PriceUtil.format4Decimal(0));//订单编号//商家积分
        } else {
            holder.mItemView.findViewById(R.id.layout_options).setVisibility(View.VISIBLE);
            holder.mItemView.findViewById(R.id.layout_options_divider).setVisibility(View.VISIBLE);
            holder.mBtnCall.setVisibility(View.VISIBLE);
            holder.mBtnReply.setVisibility(OrderStatus.FINISHED.equals(data.getStatus()) ? View.VISIBLE : View.GONE);
        }
        fillViewOptions(holder, pos, data);
    }

    /**
     * 文字按钮
     *
     * @param holder
     * @param pos
     * @param data
     */
    private void fillViewOptions(final ViewHolder holder, final int pos, final ResShopOrderItem data) {
        holder.mBtnCall.setText("联系电话");
        if (OrderStatus.FINISHED.equals(data.getStatus())) {
            //状态FINISHED 且 sellerReply == null，表示用户已评论，商家未评论
            holder.mBtnReply.setVisibility(View.VISIBLE);
            if (data.getEvaluate().getSellerReply() == null) {
                holder.mBtnReply.setText("立即回复");
            } else {
                //商家已评论
                holder.mBtnReply.setText("查看回复");
            }
        }
    }

    /**
     * 商家录单
     *
     * @param holder
     * @param pos
     * @param data
     */
    private void fillViewSallerOrder(final ViewHolder holder, final int pos, final ResShopOrderItem data) {
        holder.mItemView.findViewById(R.id.layout_options).setVisibility(View.VISIBLE);
        holder.mItemView.findViewById(R.id.layout_options_divider).setVisibility(View.VISIBLE);
        holder.mBtnReply.setVisibility(View.VISIBLE);
        holder.mBtnCall.setVisibility(View.VISIBLE);
        if (OrderStatus.UNPAID.equals(data.getStatus())) {//未支付订单
            holder.mBtnReply.setText("删除订单");
            holder.mBtnCall.setText("立即支付");
        } else {
            holder.mBtnReply.setVisibility(OrderStatus.FINISHED.equals(data.getStatus()) ? View.VISIBLE : View.GONE);
            fillViewOptions(holder, pos, data);
        }
    }

    public class ViewHolder {
        View mItemView;
        TextView mTvCustomerNamer;
        TextView mTvStatus;
        TextView mTvDate1;
        TextView mTvOrderSn;
        TextView mTvScore;
        TextView mTvAmount;
        Button mBtnReply;
        Button mBtnCall;

        public ViewHolder(View view) {
            mItemView = view;
            mTvCustomerNamer = (TextView) view.findViewById(R.id.item_tv_customer_name);
            mTvStatus = (TextView) view.findViewById(R.id.item_tv_status);
            mTvDate1 = (TextView) view.findViewById(R.id.item_tv_date1);
            mTvOrderSn = (TextView) view.findViewById(R.id.item_tv_sn);
            mTvScore = (TextView) view.findViewById(R.id.item_tv_score);
            mTvAmount = (TextView) view.findViewById(R.id.item_tv_amount);
            mBtnReply = (Button) view.findViewById(R.id.item_btn_reply);
            mBtnCall = (Button) view.findViewById(R.id.item_btn_call);
        }
    }

}
