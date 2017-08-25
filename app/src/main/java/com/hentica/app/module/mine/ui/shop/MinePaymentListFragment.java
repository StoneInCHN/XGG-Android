package com.hentica.app.module.mine.ui.shop;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.RebateDataBackListener;
import com.hentica.app.module.common.listener.RebateListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.shop.ResPaymentList;
import com.hentica.app.module.mine.ui.textcontent.ShopPaymentDescribtionFragment;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.PaymentFilterConditionView;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrListView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.hentica.appbase.famework.util.ViewUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by YangChen on 2017/5/28 14:25.
 * E-mail:656762935@qq.com
 */

public class MinePaymentListFragment extends BaseFragment {

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.mine_payment_list)
    CustomPtrListView mPaymentPtrLv;

    private PaymentAdapter mAdapter;
    private int mSize = 20;
    private int mPageNum;

    private PopupWindow mFilterWindow;

    private long mStartTime;
    private long mEndTime;

    @BindView(R.id.tv_consume_amount)
    TextView mTvConsumeAmount;
    @BindView(R.id.tv_balance_amount)
    TextView mTvBalanceAmount;

    @Override
    public int getLayoutId() {
        return R.layout.mine_payment_list_fragment;
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
        mAdapter = new PaymentAdapter();
    }

    @Override
    protected void initView() {
        mTitleView.setRightTextBtnText("筛选");
        mPaymentPtrLv.setAdapter(mAdapter);
        ListViewUtils.setDefaultEmpty(mPaymentPtrLv.getRefreshableView());
        // 请求店铺货款
        requestPaymentList(false);
        setAmountInfo(0, 0);
    }

    private void setAmountInfo(double consumeAmount, double balanceAmount){
        HtmlBuilder hb =HtmlBuilder.newInstance();
        hb.appendNormal("消费总额（元）").appendNextLine().appendRed(PriceUtil.format4Decimal(consumeAmount));
        mTvConsumeAmount.setText(hb.create());
        hb = HtmlBuilder.newInstance();
        hb.appendNormal("结算金额（元）").appendNextLine().appendRed(PriceUtil.format4Decimal(balanceAmount));
        mTvBalanceAmount.setText(hb.create());
    }

    @Override
    protected void setEvent() {
        // 说明，被点击
        mTitleView.setOnRightTextBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });

        // 列表被点击
        mPaymentPtrLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResPaymentList data = (ResPaymentList) view.getTag();
                // 跳转到货款详情
                if(data != null){
                    FragmentJumpUtil.toPaymentDetail(getUsualFragment(), data.getId()+"");
                }else {
                    showToast("货款不存在！");
                }
            }
        });

        // 下拉刷新
        mPaymentPtrLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                requestPaymentList(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 加载更多
                requestPaymentList(true);
            }
        });
    }

    /**
     * 显示筛选框
     */
    private void showFilterDialog(){
        if (mFilterWindow == null) {
            initFilterDialog();
        }
        mFilterWindow.showAsDropDown(getTitleView());
    }

    private void initFilterDialog(){
        PaymentFilterConditionView view = new PaymentFilterConditionView(getContext());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterWindow.dismiss();
            }
        });
        view.setFilterSelect(new PaymentFilterConditionView.FilterSelect() {
            @Override
            public void selected(long startTime, long endTime) {
                //选择
                mStartTime = startTime;
                mEndTime = endTime;
                mFilterWindow.dismiss();
                requestPaymentList(false);
            }
        });
        mFilterWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mFilterWindow.setFocusable(true);
        mFilterWindow.setOutsideTouchable(true);
    }

    @OnClick(R.id.tv_description)
    public void showDescription(View v){
        startFrameActivity(ShopPaymentDescribtionFragment.class);
    }


    /** 请求货款列表 */
    private void requestPaymentList(final boolean isLoadMore){
        String userId = ApplicationData.getInstance().getLoginUserId();
        mPageNum = isLoadMore ? ++mPageNum : 1;
        String size = mSize+"";
        Request.getSellerPaymentList(userId,
                mStartTime == 0 ? "" : String.valueOf(mStartTime),
                mEndTime == 0 ? "" : String.valueOf(mEndTime),
                mPageNum+"", size,
                RebateListenerAdapter.createArrayListener(ResPaymentList.class, new RebateDataBackListener<List<ResPaymentList>>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<ResPaymentList> data) {
                        mPaymentPtrLv.onRefreshComplete();
                        if(isSuccess){
                            // 请求成功
                            PullToRefreshBase.Mode mode = data.size() < mSize ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                            mPaymentPtrLv.setMode(mode);
                            if(isLoadMore){
                                // 合并数组
                                data = ViewUtil.mergeList(mAdapter.getDatas(), data);
                            }
                            mAdapter.setDatas(data);
                        }
                    }

                    @Override
                    public void setResult(NetData data) {
                        super.setResult(data);
                        if (!TextUtils.isEmpty(data.getErrMsg())){
                            getAmountInfo(data.getErrMsg());
                        }
                    }
                }));
    }

    private void getAmountInfo(String amountInfo){
        if (amountInfo.contains(",")) {
            String[] arrays = amountInfo.split(",");
            if (arrays != null && arrays.length == 2) {
                setAmountInfo(parseDouble(arrays[0]), parseDouble(arrays[1]));
            }
        }
    }

    /**
     * 获取消费金额
     * @return
     */
    private double parseDouble(String value){
        double amount = 0;
        try{
            if (!TextUtils.isEmpty(value)) {
                amount = Double.parseDouble(value);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return amount;
    }

    /** 货款列表适配器 */
    private class PaymentAdapter extends QuickAdapter<ResPaymentList>{

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.mine_payment_list_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, ResPaymentList data) {
            ViewUtil.setText(convertView, R.id.mine_payment_item_sn, String.format("货款编号：%s", data.getClearingSn()));
            ViewUtil.setText(convertView, R.id.mine_payment_item_status_des, data.isIsClearing() ? "已结算" : "未结算");
            ViewUtil.setText(convertView, R.id.mine_payment_item_time, DateHelper.stampToDate(data.getCreateDate()+"", "yyyy-MM-dd hh:mm"));
            ViewUtil.setText(convertView, R.id.mine_payment_item_income, "￥"+ PriceUtil.format4Decimal(data.getAmount()));
            convertView.setTag(data);
        }
    }
}
