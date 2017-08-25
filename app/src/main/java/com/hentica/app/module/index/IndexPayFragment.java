package com.hentica.app.module.index;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bestpay.plugin.Plugin;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.lib.net.Post;
import com.hentica.app.lib.view.ChildListView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.ConfigKey;
import com.hentica.app.module.entity.index.IndexBusinessDetailData;
import com.hentica.app.module.entity.index.IndexPayData;
import com.hentica.app.module.entity.index.IndexPayInfoData;
import com.hentica.app.module.entity.index.IndexPayTypeListData;
import com.hentica.app.module.index.presenter.I_IndexPayPresenter;
import com.hentica.app.module.index.presenter.IndexPayPresenterImpl;
import com.hentica.app.module.index.view.I_IndexPayView;
import com.hentica.app.module.listener.CallbackAdapter;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.manager.pay.AbsPayData;
import com.hentica.app.module.manager.pay.AlipyPayData;
import com.hentica.app.module.manager.pay.IPayListener;
import com.hentica.app.module.manager.pay.PayManagerUtils;
import com.hentica.app.module.manager.pay.RequestPayData;
import com.hentica.app.module.manager.pay.WeiChatPayData;
import com.hentica.app.module.manager.pay.WingPayData;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.StringUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.lineview.LineViewEdit;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.Hashtable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 买单界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class IndexPayFragment extends BaseFragment implements I_IndexPayView {

    public static final String BusinessData = "BusinessData";
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    public static final String SELLERID = "SellerId";

    public String mOrderId = "";
    public double encourageAmount = 0;

    @BindView(R.id.index_pay_logo_img)
    ImageView mLogoImageView;
    @BindView(R.id.index_pay_name)
    TextView mNameTextView;
    @BindView(R.id.index_pay_discount)
    TextView mDiscountTextView;
    @BindView(R.id.index_pay_amount)
    LineViewEdit mPriceLine;
    @BindView(R.id.index_pay_edit_count)
    TextView mInputCountTv;
    @BindView(R.id.index_pay_list)
    ChildListView mPayTypeListView;
    @BindView(R.id.index_pay_remark_edit)
    EditText mRemarkEdit;

    private String mUserId;

    private IndexBusinessDetailData mBusinessData;
    private IndexPayInfoData mPayInfoData;
    private PayTypeAdapter mAdapter;

    private double mLatitude;
    private double mLongitude;
    private long mSellerId = -1;

    /**
     * 是否正在请求网络
     */
    private boolean mIsLoading;

    private I_IndexPayPresenter mIndexPayPresenter;

    private PayPwdHelper mPayPwdHelper = PayPwdHelper.newInstance(this);

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsLoading) {
            dismissLoadingDialog();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.index_pay_fragment;
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
    protected void handleIntentData(Intent intent) {
        IntentUtil intentUtil = new IntentUtil(intent);
        String json = intentUtil.getString(BusinessData);
        mBusinessData = ParseUtil.parseObject(json, IndexBusinessDetailData.class);
        mLatitude = intentUtil.getDouble(LATITUDE, -1);
        mLongitude = intentUtil.getDouble(LONGITUDE, -1);
        String sellerId = intentUtil.getString(SELLERID);
        try {
            mSellerId = Long.parseLong(sellerId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (mSellerId < 0 && mBusinessData != null) {
            mSellerId = mBusinessData.getId();
        }
    }

    @Override
    protected void initData() {
        mAdapter = new PayTypeAdapter();
        mIndexPayPresenter = new IndexPayPresenterImpl();
        mIndexPayPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mPayTypeListView.setAdapter(mAdapter);
        final EditText priceEdit = mPriceLine.getContentTextView();
        priceEdit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        priceEdit.setImeActionLabel("完成", EditorInfo.IME_ACTION_DONE);
        priceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String price = s.toString();
                if (price.contains(".")) {
                    int index = price.indexOf(".");
                    if (price.length() - index > 3) {
                        price = price.substring(0, price.length() - 1);
                        priceEdit.setText(price);
                        priceEdit.setSelection(price.length());
                    }
                }
            }
        });
        mRemarkEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    String content = s.toString();
                    mInputCountTv.setText(String.format("%d/15", content.length()));
                }
            }
        });
        requestPayTypeList();
        // 刷新界面
        refreshUI();
        // 若商家id不为空，则请求商家详情
        if (mSellerId > 0) {
            requestBusinessDetail();
        }
    }

    @Override
    protected void setEvent() {
        mPayTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final IndexPayTypeListData data = (IndexPayTypeListData) view.getTag();
                String msg = checkData(data.getId());
                //检查数据
                if (!TextUtils.isEmpty(msg)) {
                    showToast(msg);
                    return;
                }
                //支付
                double amount = Double.parseDouble(getAmount());
                double deduceAmount = getDeduceAmountValue();
                if (isBeanPay()){
                    //抵扣乐豆
                    if (deduceAmount == amount) {
                        //全额抵扣
                        doPay("4", "乐豆支付");
                    } else if (data.getId() == 5) {
                        checkLeMindPayPassWord(String.valueOf(data.getId()), data.getConfigValue());
                    } else {
                        doPay(String.valueOf(data.getId()), data.getConfigValue());
                    }
                } else {
                    //不抵扣
                    if (data.getId() == 5) {
                        checkLeMindPayPassWord(String.valueOf(data.getId()), data.getConfigValue());
                    } else {
                        doPay(String.valueOf(data.getId()), data.getConfigValue());
                    }
                }
            }
        });
    }

    /**
     * 检查数据
     *
     * @return 错误信息，""或null表示数据正确
     */
    private String checkData(int payTypeId) {
        if (TextUtils.isEmpty(getAmount())) {
            return "消费金额未输入！";
        }
        double amount = Double.parseDouble(getAmount());
        if (amount < 0.01) {
            return "金额不能低于0.01元！";
        }
        if (isBeanPay()) {//开启乐豆抵扣
            double deduceAmount = getDeduceAmountValue();
            if (deduceAmount == 0) {
                return "乐豆抵扣不能为0！";
            }
            if (deduceAmount > amount) {
                return "您的乐豆抵扣大于消费金额！";
            }
            if (deduceAmount > mPayInfoData.getUserCurLeBean()) {
                return "您的乐豆不足！";
            }
            if (payTypeId == 5) {
                if (mPayInfoData.getUserCurLeScore() < (amount - deduceAmount)) {
                    return String.format("您当前乐分为%s，支付失败！",
                            PriceUtil.format4Decimal(mPayInfoData.getUserCurLeScore()));
                }
            }
        } else if (payTypeId == 5) {
            if (mPayInfoData.getUserCurLeScore() < amount) {
                return String.format("您当前乐分为%s，支付失败！",
                        PriceUtil.format4Decimal(mPayInfoData.getUserCurLeScore()));
            }
        }
        return "";
    }

    /**
     * 乐分支付检查支付密码
     *
     * @param payTypeId
     * @param payType
     */

    private void checkLeMindPayPassWord(final String payTypeId, final String payType) {
        mPayPwdHelper.checkPayPwd(new CallbackAdapter<Void>() {
            @Override
            public void callback(boolean isSuccess, Void result) {
                if (isSuccess) {
                    // 乐分支付
                    doPay(payTypeId, payType);
                }
            }
        });
    }

    /**
     * 刷新界面
     */
    private void refreshUI() {
        if (mBusinessData != null) {
            String url = ApplicationData.getInstance().getImageUrl(mBusinessData.getStorePictureUrl());
            ViewUtil.bindImage(getContext(), mLogoImageView, url, R.drawable.rebate_homepage_banner);
            mNameTextView.setText(mBusinessData.getName());
            mDiscountTextView.setText(String.format("消费%s赠送%s积分", mBusinessData.getUnitConsume(), mBusinessData.getRebateScore()));
        }
    }

    /**
     * 请求支付方式
     */
    private void requestPayTypeList() {
        mUserId = ApplicationData.getInstance().getLoginUserId();
        mIsLoading = true;
        mIndexPayPresenter.getPayInfo(mUserId, String.valueOf(mSellerId));
    }

    @Override
    public void setPayTypeDatas(boolean isSuccess, List<IndexPayTypeListData> typeDatas) {
        mIsLoading = false;
        if (isSuccess) {
            mAdapter.setDatas(typeDatas);
        } else {
            showToast("获取支付信息失败！");
            finish();
        }
    }

    @Override
    public void setPayInfoDatas(boolean isSuccess, IndexPayInfoData infoData) {
        mIsLoading = false;
        if (isSuccess && infoData != null) {
            mPayInfoData = infoData;
            mAdapter.setDatas(infoData.getPayType());
            showUserLeBeanInfo(infoData);
        } else {
            showToast("获取支付信息失败！");
            finish();
        }
    }

    /**
     * 显示用户乐豆信息
     */
    private void showUserLeBeanInfo(IndexPayInfoData infoData) {
        if (infoData.isIsBeanPay()) {
            setViewVisiable(true, R.id.layout_index_pay_le_bean_info);
            setViewText(String.format("乐豆余额：%s", PriceUtil.format4Decimal(infoData.getUserCurLeBean())), R.id.tv_le_bean_amount);
        }
    }

    /**
     * 乐豆抵扣开关按钮点击事件
     *
     * @param
     */
    @OnClick(R.id.ckb_le_bean_pay)
    public void switchButtonLeBeanPayClickEvent(final CheckBox ckb) {
        if (!ckb.isChecked()) {
            if (mPayInfoData != null) {
                if (mPayInfoData.getUserCurLeBean() <= 0) {
                    showToast("您当前乐豆为0，无法开启抵扣！");
                } else {
                    mPayPwdHelper.checkPayPwd(new CallbackAdapter<Void>() {
                        @Override
                        public void callback(boolean isSuccess, Void data) {
                            ckb.setChecked(true);
                        }
                    });
                }
            }
        } else {
            ckb.setChecked(false);
        }
    }

    @OnCheckedChanged(R.id.ckb_le_bean_pay)
    public void switchButtonLeBeanPayCheckChange(CompoundButton view, boolean isChecked) {
        getViews(R.id.edt_le_bean_input_number).setEnabled(isChecked);
    }

    /**
     * 乐豆抵扣说明按钮
     *
     * @param v
     */
    @OnClick(R.id.img_tip)
    public void tipBtnClick(View v) {
        FragmentJumpUtil.toTextContentFragment(getUsualFragment(), "说明", ConfigKey.LEBEAN_PAY_DESC);
    }

    /**
     * 支付方式列表适配器
     */
    private class PayTypeAdapter extends QuickAdapter<IndexPayTypeListData> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.index_pay_list_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, IndexPayTypeListData data) {
            AQuery query = new AQuery(convertView);
            TextView titleTv = query.id(R.id.index_pay_list_item_title).getTextView();
            titleTv.setText(data.getConfigValue());
            TextView contentTv = query.id(R.id.index_pay_list_item_content).getTextView();
            convertView.setTag(data);
            switch (data.getId()) {
                case 1:
                    // 微信支付
                    titleTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rebate_homepage_pay_icon_wechat, 0, 0, 0);
                    contentTv.setText("");
                    break;
                case 2:
                    // 支付宝支付
                    titleTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rebate_homepage_pay_icon_alipay, 0, 0, 0);
                    contentTv.setText("");
                    break;
                case 3:
                    // 通联
                    titleTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rebate_homepage_pay_icon_tl, 0, 0, 0);
                    contentTv.setText("");
                    break;
                case 5:
                    // 乐分支付
                    titleTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rebate_homepage_pay_icon_lf, 0, 0, 0);
                    // 当前乐分
                    contentTv.setText("余额" + PriceUtil.format4Decimal(mPayInfoData != null ? mPayInfoData.getUserCurLeScore() : 0));
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * 获取用户id
     */
    private String getUserId() {
        return LoginModel.getInstance().getLoginUserId();
    }

    /**
     * 获取支付金额
     */
    private String getAmount() {
        return mPriceLine.getText().toString();
    }

    /**
     * 获取用余额
     */
    private double getCurLeBean() {
        double leBean = 0;
        UserLoginData loginData = LoginModel.getInstance().getUserLogin();
        if (loginData != null) {
            leBean = loginData.getCurLeBean();
        }
        return leBean;
    }

    /**
     * 获取备注
     */
    private String getRemark() {
        return mRemarkEdit.getText().toString();
    }

    /**
     * 获取商家id
     */
    private String getSellerId() {
        return mBusinessData == null ? "" : mBusinessData.getId() + "";
    }

    /**
     * 执行支付
     */
    private void doPay(String payTypeId, String payInfo) {
        Post.FullListener listener = null;
        if (TextUtils.equals("1", payTypeId)) {
            // 微信支付
            listener = createWeChatPayListener();
        } else if (TextUtils.equals("2", payTypeId)) {
            // 支付宝支付
            listener = createAliPayListener();
        } else if (TextUtils.equals("3", payTypeId)) {
            // 通联银行快捷支付
            listener = createTlCertListener();
        } else if (TextUtils.equals("4", payTypeId)) {
            // 乐豆支付
            listener = createLeBeanPayListener();
        } else if (TextUtils.equals("5", payTypeId)) {
            //乐分支付
            listener = createLeBeanPayListener();
        }
        requestOrderPay(payInfo, payTypeId, getAmount(), isBeanPay(), listener);
    }

    /**
     * 请求订单支付
     */
    private void requestOrderPay(String payType, final String payTypeId, String amount, boolean isBeanPay, Post.FullListener listener) {
        mIsLoading = true;
        Request.getOrderPay(getUserId(), payType, payTypeId, amount, getSellerId(), isBeanPay + "", isBeanPay ? getDeduceAmount() : "", getRemark(), listener);
    }

    /**
     * 创建微信支付回调
     */
    private ListenerAdapter createWeChatPayListener() {
        return ListenerAdapter.createObjectListener(IndexPayData.class, new UsualDataBackListener<IndexPayData>(this) {
            @Override
            protected void onComplete(boolean isSuccess, IndexPayData data) {
                mIsLoading = false;
                if (isSuccess && data != null) {
                    showLoadingDialog();
                    mOrderId = data.getOrderId() + "";
                    encourageAmount = data.getEncourageAmount();
                    PayManagerUtils.getInstance(getActivity(), RequestPayData.PayType.kWeiChat, new IPayListener() {
                        @Override
                        public void onSuccess(String msg) {
                            // 微信支付成功，跳转到订单详情
                            tryToJumpOrderDetail(mOrderId, encourageAmount);
                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    }).toPay(createWeChatPayData(data));
                }
            }
        });
    }

    /**
     * 创建支付宝支付回调
     */
    private ListenerAdapter createAliPayListener() {
        return ListenerAdapter.createObjectListener(IndexPayData.class, new UsualDataBackListener<IndexPayData>(this) {
            @Override
            protected void onComplete(boolean isSuccess, final IndexPayData data) {
                mIsLoading = false;
                if (isSuccess && data != null) {
                    showLoadingDialog();
                    // 请求成功，起调支付宝支付
                    PayManagerUtils.getInstance(getActivity(), RequestPayData.PayType.kAlipy, new IPayListener() {
                        @Override
                        public void onSuccess(String msg) {
                            // 支付宝支付成功，跳转到订单详情
                            tryToJumpOrderDetail(data.getOrderId() + "", data.getEncourageAmount());
                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    }).toPay(createAliPayData(data));
                }
            }
        });
    }

    /**
     * 创建翼支付支付回调
     */
    private ListenerAdapter createWingPayListener() {
        return ListenerAdapter.createObjectListener(IndexPayData.class, new UsualDataBackListener<IndexPayData>(this) {
            @Override
            protected void onComplete(boolean isSuccess, final IndexPayData data) {
                mIsLoading = false;
                if (isSuccess && data != null) {
                    // 请求成功，请求起调翼支付
                    showLoadingDialog();
                    registerForResult();
                    PayManagerUtils.getInstance(getActivity(), RequestPayData.PayType.kWingPay, null).toPay(createWingPayData(data));
                    // 支付回调
                    setResultListener(new OnActivityResultListener() {
                        @Override
                        public void onActivityResult(int requestCode, int resultCode, Intent data1) {
                            dismissLoadingDialog();
                            if (resultCode == Activity.RESULT_OK) {
                                // 翼支付支付成功，跳转到订单详情
                                tryToJumpOrderDetail(data.getOrderId() + "", data.getEncourageAmount());
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 创建通联快捷支付回调
     */
    private ListenerAdapter createTlCertListener() {
        return ListenerAdapter.createObjectListener(IndexPayData.class, new UsualDataBackListener<IndexPayData>(this) {
            @Override
            protected void onComplete(boolean isSuccess, final IndexPayData data) {
                mIsLoading = false;
                if (isSuccess && data != null) {
                    showLoadingDialog();
                    // 请求成功，请求起调通联快捷支付
                    // 拼接网址
                    String url = getTlCertUrl(data);
                    // 请求支付
                    FragmentJumpUtil.toTlPayWeb(getUsualFragment(), "", url, data.getPickupUrl());
                    setResultListener(new OnActivityResultListener() {
                        @Override
                        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
                            if (resultCode == Constants.ACTIVITY_RESULT_CODE_TL_PAY) {
                                // 通联支付成功，跳转到订单详情
                                tryToJumpOrderDetail(data.getOrderId() + "", data.getEncourageAmount());
                            }
                        }
                    });
                }
            }
        });
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
     * 创建乐豆支付回调
     */
    private ListenerAdapter createLeBeanPayListener() {
        return ListenerAdapter.createObjectListener(IndexPayData.class, new UsualDataBackListener<IndexPayData>(this) {
            @Override
            protected void onComplete(boolean isSuccess, IndexPayData data) {
                if (isSuccess) {
                    // 乐豆支付成功，跳转到订单详情
                    tryToJumpOrderDetail(data.getOrderId() + "", data.getEncourageAmount());
                }
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
     * 转换支付宝支付数据
     */
    private AbsPayData createAliPayData(IndexPayData data) {
        AlipyPayData payData = new AlipyPayData();
        payData.setAlipyCode(data.getOrderStr());
        return payData;
    }

    /**
     * 转换翼支付数据
     */
    private AbsPayData createWingPayData(IndexPayData data) {
        WingPayData payData = new WingPayData();
        Hashtable<String, String> map = new Hashtable<>();
        map.put(Plugin.ORDERAMOUNT, data.getORDERAMOUNT());
        map.put(Plugin.CURTYPE, data.getCURTYPE());
        map.put(Plugin.MERCHANTID, data.getMERCHANTID());
        map.put(Plugin.BACKMERCHANTURL, data.getBACKMERCHANTURL());
        map.put(Plugin.ATTACHAMOUNT, data.getATTACHAMOUNT());
        map.put(Plugin.ORDERSEQ, data.getORDERSEQ());
        map.put(Plugin.PRODUCTAMOUNT, data.getPRODUCTAMOUNT());
        map.put(Plugin.USERIP, data.getUSERIP());
        map.put(Plugin.BUSITYPE, data.getBUSITYPE());
        map.put(Plugin.MAC, data.getMAC());
        map.put(Plugin.PRODUCTDESC, data.getPRODUCTDESC());
        map.put(Plugin.CUSTOMERID, data.getCUSTOMERID());
        map.put(Plugin.ORDERTIME, data.getORDERTIME());
        map.put(Plugin.ACCOUNTID, data.getACCOUNTID());
        map.put(Plugin.ORDERREQTRANSEQ, data.getORDERREQTRANSEQ());
        payData.setParamsHashtable(map);
        return payData;
    }

    /**
     * 支付成功，尝试跳转到订单详情
     */
    private void tryToJumpOrderDetail(String orderId, double encourageAmount) {
//        EventBus.getDefault().post(new DataEvent.OnPaySuccess());
//        FragmentJumpUtil.toOrderDetail(getUsualFragment(), orderId, encourageAmount);
        FragmentJumpUtil.toPayingFragment(getUsualFragment(), orderId, encourageAmount);
        finish();
    }

    /**
     * 请求商家详情
     */
    private void requestBusinessDetail() {
        String userId = LoginModel.getInstance().getLoginUserId();
        mIsLoading = true;
        Request.getSellerDetail(userId, String.valueOf(mSellerId), mLatitude + "", mLongitude + "",
                ListenerAdapter.createObjectListener(IndexBusinessDetailData.class, new UsualDataBackListener<IndexBusinessDetailData>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, IndexBusinessDetailData data) {
                        mIsLoading = false;
                        if (isSuccess) {
                            // 请求成功
                            mBusinessData = data;
                            // 刷新界面
                            refreshUI();
                        }

                    }
                }));
    }

    /**
     * 是否可以乐豆抵扣
     *
     * @return
     */
    private boolean isBeanPay() {
        return ((CheckBox) getViews(R.id.ckb_le_bean_pay)).isChecked();
    }

    /**
     * 获取抵扣金额
     *
     * @return
     */
    private double getDeduceAmountValue() {
        double result = 0;
        try {
            result = Double.parseDouble(getDeduceAmount());
        } catch (NumberFormatException e) {

        }
        return result;
    }

    /**
     * 获取抵扣金额输入内容
     *
     * @return
     */
    private String getDeduceAmount() {
        return ((TextView) getViews(R.id.edt_le_bean_input_number)).getText().toString();
    }

    @Subscribe
    public void onEvent(DataEvent.OnLoginEvent event) {
        // 登录事件，重新请求支付方式
        requestPayTypeList();
    }

    @Override
    public void finish() {
        mIndexPayPresenter.detachView(this);
        mPayPwdHelper.destoryInstance();
        super.finish();
    }
}
