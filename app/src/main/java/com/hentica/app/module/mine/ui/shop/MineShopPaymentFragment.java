package com.hentica.app.module.mine.ui.shop;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.framework.fragment.CommonPtrFragment;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.lib.net.Post;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.index.IndexPayData;
import com.hentica.app.module.entity.index.IndexPayTypeListData;
import com.hentica.app.module.manager.pay.AbsPayData;
import com.hentica.app.module.manager.pay.AlipyPayData;
import com.hentica.app.module.manager.pay.IPayListener;
import com.hentica.app.module.manager.pay.PayManagerUtils;
import com.hentica.app.module.manager.pay.RequestPayData;
import com.hentica.app.module.manager.pay.WeiChatPayData;
import com.hentica.app.module.mine.presenter.shop.PaymentPtrPresenter;
import com.hentica.app.module.mine.ui.adapter.PaymentAdapter;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.StringUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.hentica.app.widget.view.TitleView;
import com.hentica.appbase.famework.adapter.QuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public class MineShopPaymentFragment extends CommonPtrFragment<IndexPayTypeListData> implements PtrView<IndexPayTypeListData>{

    private PaymentPtrPresenter mPaymentPtrPresenter;
    public static final String DATA_ORDER_SN = "orderSn";//订单编号
    public static final String DATA_SELLER_ID = "sellerId";//订单编号
    private String mOrderSn = "";
    private String mSellerId = "";

    /** 是否正在请求网络 */
    private boolean mIsLoading;

    @Override
    public void onResume() {
        super.onResume();
        if(!mIsLoading){
            dismissLoadingDialog();
        }
    }

    @Override
    protected void initView() {
        super.initView();
    }


    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setOnLeftImageBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog(){
        SelfAlertDialogHelper.showDialog(getFragmentManager(), "确定要取消支付吗？", "马上支付", "我再想想",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
                , new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转录单订单管理
                        EventBus.getDefault().post(new DataEvent.OnCloseRecordOrderFragmentEvent());
                        FragmentJumpUtil.toShopRecordOrders(getUsualFragment(), null);
                        finish();
                    }
                });
    }

    @Override
    protected void handleIntentData(Intent intent) {
        IntentUtil intentUtil = new IntentUtil(intent);
        mOrderSn = intentUtil.getString(DATA_ORDER_SN);
        mSellerId = intentUtil.getString(DATA_SELLER_ID);
    }

    @Override
    protected void initData() {
        super.initData();
        mPaymentPtrPresenter = new PaymentPtrPresenter(this);
        mPaymentPtrPresenter.onRefresh();
    }

    @Override
    protected String getFragmentTitle() {
        return "支付";
    }

    @Override
    public QuickAdapter<IndexPayTypeListData> createAdapter() {
        return new PaymentAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPtrListView().setMode(PullToRefreshBase.Mode.DISABLED);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final IndexPayTypeListData data = (IndexPayTypeListData) view.getTag();
        doPay(data.getId() + "", data.getConfigValue());
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void setListDatas(List<IndexPayTypeListData> datas) {
        setDatas(datas);
    }

    @Override
    public void addListDatas(List<IndexPayTypeListData> datas) {

    }

    /**
     * 执行支付
     */
    private void doPay(String payTypeId, String payInfo) {
        if (TextUtils.equals("1", payTypeId)) {
            // 微信支付
            requestPaySellerOrder(payInfo, payTypeId, createWeChatPayListener());
        } else if (TextUtils.equals("2", payTypeId)) {
            // 支付宝支付
            requestPaySellerOrder(payInfo, payTypeId, createAliPayListener());
        } else if (TextUtils.equals("3", payTypeId)) {
            // 通联银行快捷支付
            requestPaySellerOrder(payInfo, payTypeId, createTlCertListener());
        }
    }

    /**
     * 创建微信支付回调
     */
    private ListenerAdapter createWeChatPayListener() {
        return ListenerAdapter.createObjectListener(IndexPayData.class, new UsualDataBackListener<IndexPayData>(this, true, true, false) {
            @Override
            protected void onComplete(boolean isSuccess, IndexPayData data) {
                if (isSuccess && data != null) {
                    PayManagerUtils.getInstance(getActivity(), RequestPayData.PayType.kWeiChat, new IPayListener() {
                        @Override
                        public void onSuccess(String msg) {
                            // 微信支付成功，跳转到订单详情
                            dismissLoadingDialog();
                            tryToJumpOrderDetail();
                        }

                        @Override
                        public void onFailure(String msg) {
                            dismissLoadingDialog();
                        }
                    }).toPay(createWeChatPayData(data));
                }
                mIsLoading = false;
            }
        });
    }

    /**
     * 转换微信支付数据
     */
    private AbsPayData createWeChatPayData(IndexPayData data) {
        WeiChatPayData payData = new WeiChatPayData();
        payData.setAppId(data.getAppid());
        payData.setNonceStr(data.getNoncestr());
        payData.setPackageStr(data.getPackageX());
        payData.setPartnerId(data.getPartnerid());
        payData.setPaySign(data.getSign());
        payData.setPrepayId(data.getPrepayid());
        payData.setTimeStamp(data.getTimestamp());
        return payData;
    }

    /**
     * 创建支付宝支付回调
     */
    private ListenerAdapter createAliPayListener() {
        return ListenerAdapter.createObjectListener(IndexPayData.class, new UsualDataBackListener<IndexPayData>(this, true, true, false) {
            @Override
            protected void onComplete(boolean isSuccess, final IndexPayData data) {
                if (isSuccess && data != null) {
                    showLoadingDialog();
                    // 请求成功，起调支付宝支付
                    PayManagerUtils.getInstance(getActivity(), RequestPayData.PayType.kAlipy, new IPayListener() {
                        @Override
                        public void onSuccess(String msg) {
                            // 支付宝支付成功，跳转到订单管理
                            dismissLoadingDialog();
                            tryToJumpOrderDetail();
                        }

                        @Override
                        public void onFailure(String msg) {
                            dismissLoadingDialog();
                        }
                    }).toPay(createAliPayData(data));
                }
                mIsLoading = false;
            }
        });
    }

    /**
     * 创建通联快捷支付回调
     */
    private ListenerAdapter createTlCertListener() {
        return ListenerAdapter.createObjectListener(IndexPayData.class, new UsualDataBackListener<IndexPayData>(this, true, true, false) {
            @Override
            protected void onComplete(boolean isSuccess, final IndexPayData data) {
                if (isSuccess && data != null) {
                    // 请求成功，请求起调通联快捷支付
                    // 拼接网址
                    String url = getTlCertUrl(data);
                    // 请求支付
                    FragmentJumpUtil.toTlPayWeb(getUsualFragment(),"",url,data.getPickupUrl());
                    setResultListener(new OnActivityResultListener() {
                        @Override
                        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
                            dismissLoadingDialog();
                            if(resultCode == Constants.ACTIVITY_RESULT_CODE_TL_PAY){
                                // 通联支付成功，跳转到订单管理
                                tryToJumpOrderDetail();
                            }
                        }
                    });
                }
                mIsLoading = false;
            }
        });
    }

    /**
     * 转换支付宝支付数据
     */
    private AbsPayData createAliPayData(IndexPayData data) {
        AlipyPayData payData = new AlipyPayData();
        payData.setAlipyCode(data.getOrderStr());
        return payData;
    }

    /**
     * 获取通联网址
     */
    private String getTlCertUrl(IndexPayData data) {
        StringBuilder sb = new StringBuilder();
        if (data != null) {
            sb.append(data.getPayH5orderUrl()).append("?")
                    .append("inputCharset").append("=").append(StringUtil.getNoNullString(data.getInputCharset())).append("&")
                    .append("pickupUrl").append("=").append(StringUtil.getNoNullString(data.getPickupUrl())).append("&")
                    .append("receiveUrl").append("=").append(StringUtil.getNoNullString(data.getReceiveUrl())).append("&")
                    .append("version").append("=").append(StringUtil.getNoNullString(data.getVersion())).append("&")
                    .append("language").append("=").append(StringUtil.getNoNullString(data.getLanguage())).append("&")
                    .append("signType").append("=").append(StringUtil.getNoNullString(data.getSignType())).append("&")
                    .append("merchantId").append("=").append(StringUtil.getNoNullString(data.getMerchantId())).append("&")
                    .append("orderNo").append("=").append(StringUtil.getNoNullString(data.getOrderNo())).append("&")
                    .append("orderAmount").append("=").append(StringUtil.getNoNullString(data.getOrderAmount())).append("&")
                    .append("orderCurrency").append("=").append(StringUtil.getNoNullString(data.getOrderCurrency())).append("&")
                    .append("orderDatetime").append("=").append(StringUtil.getNoNullString(data.getOrderDatetime())).append("&")
                    .append("productName").append("=").append(StringUtil.getNoNullString(data.getProductName())).append("&")
                    .append("ext1").append("=").append(StringUtil.getNoNullString(data.getExt1())).append("&")
                    .append("payType").append("=").append(StringUtil.getNoNullString(data.getPayType())).append("&")
                    .append("signMsg").append("=").append(StringUtil.getNoNullString(data.getSignMsg()));
        }
        return sb.toString();
    }

    /**
     * 支付成功，尝试跳转到订单管理
     */
    private void tryToJumpOrderDetail() {
        EventBus.getDefault().post(new DataEvent.OnCloseRecordOrderFragmentEvent());
        // 跳转到订单管理界面
        FragmentJumpUtil.toRecordPayingFragment(getUsualFragment());
        finish();
    }

    /** 请求录单支付 */
    private void requestPaySellerOrder(String payType, String payTypeId, Post.FullListener listener){
        String userId = ApplicationData.getInstance().getLoginUserId();
        mIsLoading = true;
        showLoadingDialog();
        Request.getPaySellerOrder(userId, payType, payTypeId, mSellerId, mOrderSn,listener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showAlertDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
