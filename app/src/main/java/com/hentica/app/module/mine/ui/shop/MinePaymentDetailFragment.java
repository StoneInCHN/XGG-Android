package com.hentica.app.module.mine.ui.shop;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fiveixlg.app.customer.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.view.ChildListView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.shop.ResPaymentDetail;
import com.hentica.app.util.ArrayListUtil;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.StringUtil;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrScrollView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.hentica.appbase.famework.util.ViewUtil;
import com.meg7.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by YangChen on 2017/5/28 15:39.
 * E-mail:656762935@qq.com
 */

public class MinePaymentDetailFragment extends BaseFragment {

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.mine_payment_detail_icon)
    ImageView mBankIcon;
    @BindView(R.id.mine_payment_detail_list)
    ChildListView mOrderLv;
    @BindView(R.id.mine_payment_detail_scroll_view)
    CustomPtrScrollView mScrollView;
    public static final String PAYMENT_ID = "PaymentId";

    private OrderAdapter mAdapter;
    private String mPaymentId;

    private int mSize = 20;
    private int mPageNum;

    @Override
    public int getLayoutId() {
        return R.layout.mine_payment_detail_fragment;
    }

    @Override
    protected void handleIntentData(Intent intent) {
        IntentUtil intentUtil = new IntentUtil(intent);
        mPaymentId = intentUtil.getString(PAYMENT_ID);
    }

    @Override
    protected void initData() {
        mAdapter = new OrderAdapter();
    }

    @Override
    protected void initView() {
        mOrderLv.setAdapter(mAdapter);
        requestPaymentDetail(false, false);
    }

    @Override
    protected void setEvent() {
        mTitleView.setOnLeftImageBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // 下拉刷新
                requestPaymentDetail(false, true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                requestPaymentDetail(true, true);
            }
        });
    }

    /** 刷新界面 */
    private void refreshUI(ResPaymentDetail data){
        if(data != null){
            ViewUtil.setText(getView(), R.id.mine_payment_detail_income, String.format("￥%s",PriceUtil.format4Decimal(data.getAmount())));
            ViewUtil.setText(getView(), R.id.mine_payment_detail_sn, StringUtil.getNoNullString(data.getClearingSn()));
            ViewUtil.setText(getView(), R.id.mine_payment_detail_all_income, String.format("￥%s",PriceUtil.format4Decimal(data.getTotalOrderAmount())));
            if(data.getBankCard() != null){
                ViewUtil.setText(getView(), R.id.mine_payment_detail_name, StringUtil.getNoNullString(data.getBankCard().getBankName()));
                ViewUtil.setText(getView(), R.id.mine_payment_detail_card_type, StringUtil.getNoNullString(data.getBankCard().getCardType()));
                ViewUtil.setText(getView(), R.id.mine_payment_detail_card_number, StringUtil.keepLast4(data.getBankCard().getCardNum()));
//                Glide.with(getContext())
//                        .load(data.getBankCard().getBankLogo())
//                        .override(200,200)
//                        .skipMemoryCache(true)
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .into(mBankIcon);
            }
            mAdapter.setDatas(data.getOrders());
        }
    }

    /** 请求货款详情 */
    private void requestPaymentDetail(final boolean isLoadMore, final boolean onlyOrders){
        String userId = ApplicationData.getInstance().getLoginUserId();
        mPageNum = isLoadMore ? ++mPageNum : 1;
        String size = mSize+"";
        Request.getSellerPaymentDetail(userId, mPaymentId, mPageNum+"", size,
                ListenerAdapter.createObjectListener(ResPaymentDetail.class, new UsualDataBackListener<ResPaymentDetail>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, ResPaymentDetail data) {
                        mScrollView.onRefreshComplete();
                        if(isSuccess && data != null){
                            // 请求成功
                            int orderCount = ArrayListUtil.isEmpty(data.getOrders()) ? 0 : data.getOrders().size();
                            PullToRefreshBase.Mode mode = orderCount < mSize ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                            mScrollView.setMode(mode);
                            if(isLoadMore){
                                // 合并数据
                                List<ResPaymentDetail.OrdersBean> list = ViewUtil.mergeList(mAdapter.getDatas(), data.getOrders());
                                data.setOrders(list);
                            }
                            if(!onlyOrders){
                                // 刷新界面
                                refreshUI(data);
                            }else {
                                // 仅刷新订单列表
                                mAdapter.setDatas(data.getOrders());
                            }
                        }
                    }
                }));
    }

    /** 订单适配器 */
    private class OrderAdapter extends QuickAdapter<ResPaymentDetail.OrdersBean>{

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.mine_payment_detail_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, ResPaymentDetail.OrdersBean data) {
            ViewUtil.setText(convertView, R.id.mine_payment_detail_item_time, DateHelper.stampToDate(
                    data.getOrder().getCreateDate()+"", "yyy-MM-dd hh:mm"));
            HtmlBuilder hb = new HtmlBuilder();
            hb.appendNormal("收益：").appendRed(String.format("￥%s", PriceUtil.format4Decimal(data.getOrder().getSellerIncome())));
            ViewUtil.setText(convertView, R.id.mine_payment_detail_item_income, hb.create());
            ViewUtil.setText(convertView, R.id.mine_payment_detail_item_order_sn,String.format("订单编号：%s",StringUtil.getNoNullString(data.getOrder().getSn())));
            HtmlBuilder hb1 = new HtmlBuilder();
            hb1.appendNormal("商家折扣：").appendRed(String.format("%.1f折", data.getOrder().getSellerDiscount()));
            ViewUtil.setText(convertView, R.id.mine_payment_detail_item_discount,hb1.create());
            ViewUtil.setText(convertView, R.id.mine_payment_detail_item_cost, String.format("消费金额：￥%s",PriceUtil.format4Decimal(data.getOrder().getAmount())));
            ViewUtil.setText(convertView, R.id.mine_payment_detail_item_give, String.format("让利金额：￥%s",PriceUtil.format4Decimal(data.getOrder().getRebateAmount())));
        }
    }
}
