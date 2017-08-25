package com.hentica.app.module.mine.ui.shop;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.hentica.app.lib.util.TextGetter;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.shop.ResCustomerInfo;
import com.hentica.app.module.entity.mine.shop.ResGenerateOrder;
import com.hentica.app.module.entity.mine.shop.ResSellerInfo;
import com.hentica.app.module.entity.mine.shop.ResShopCartCount;
import com.hentica.app.module.entity.mine.shop.ResShopInfo;
import com.hentica.app.module.mine.presenter.shop.RecordOrderPresenter;
import com.hentica.app.module.mine.presenter.shop.RecordOrderPresenterImpl;
import com.hentica.app.module.mine.view.shop.RecordOrderView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.TextWatcherAdapter;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.hentica.app.widget.dialog.WheelDialogHelper;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.lineview.LineViewEdit;
import com.hentica.app.widget.view.lineview.LineViewHelper;
import com.hentica.app.widget.view.lineview.LineViewText;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Snow on 2017/5/24 0024.
 */

public class MineRecordOrderFragment extends BaseFragment implements RecordOrderView{

    private RecordOrderPresenter mRecordOrderPresenter;

    private ResSellerInfo mSellerInfo;

    private ResCustomerInfo mCustomerInfo;

    public static final String DATA_SHOP_INFO = "ResShopInfo";

    public ResShopInfo mShopInfo;
    /**
     * 店铺折扣数据
     */
    private List<Double> mShopDiscountDatas;
    private double mSelectedDiscount;

    private double discountAmount;
    @Override
    public int getLayoutId() {
        return R.layout.mine_shop_record_order_fragment;
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
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setOnLeftImageBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasChanged()){
                    SelfAlertDialogHelper.showDialog(getFragmentManager(), getString(R.string.alert_dialog_tips), new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }else{
                    finish();
                }
            }
        });
        TextView rightTextBtn =title.getRightTextBtn();
        rightTextBtn.setVisibility(View.VISIBLE);
        rightTextBtn.setText("订单");
        rightTextBtn.setTextColor(getResources().getColor(R.color.text_white));
        rightTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2017/5/24 0024  订单
                FragmentJumpUtil.toShopRecordOrders(getUsualFragment(), mShopInfo);
            }
        });
    }

    /**
     * 判断是否已修改数据
     * @return
     */
    private boolean hasChanged(){
        if(!TextUtils.isEmpty(getCustomerMobile()) ||
                !TextUtils.isEmpty(getAmountString())){
            return true;
        }
        return false;
    }

    @Override
    protected void handleIntentData(Intent intent) {
        super.handleIntentData(intent);
        mShopInfo = new IntentUtil(intent).getObject(DATA_SHOP_INFO, ResShopInfo.class);
    }

    @Override
    protected void initData() {
        mRecordOrderPresenter = new RecordOrderPresenterImpl();
        mRecordOrderPresenter.attachView(this);
        mRecordOrderPresenter.getSellerInfo();//获取店铺信息
    }

    private List<Double> getShopDiscountDatas() {
        List<Double> result = new ArrayList<>();
        for (int i = 19; i >= 0; i--) {
            result.add(i * 0.1 + 8);
        }
        return result;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {
        LineViewEdit edtCustomerMobile = getViews(R.id.lnv_customer_mobile);
        final EditText edtMobile = edtCustomerMobile.getContentTextView();
        edtMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    //失去焦点，查询焦点信息
                    String mobile = edtMobile.getText().toString();
                    if(!TextUtils.isEmpty(mobile)){
                        mRecordOrderPresenter.getCustomerInfo(mobile);
                    }
                }
            }
        });
        LineViewEdit edtCustomAmount = getViews(R.id.lnv_custom_amount);
        final EditText edtAmount = edtCustomAmount.getContentTextView();
        edtAmount.addTextChangedListener(new TextWatcherAdapter(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                int dotIndex = text.indexOf('.');
                if(dotIndex > -1 && text.length() > dotIndex + 3){
                    String result = text.substring(0, text.length() - 1);
                    edtAmount.setText(result);
                    edtAmount.setSelection(result.length());
                }
            }
        });
        //计算让利金额
        edtAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                   return;
                }
                if(mSellerInfo == null){
                    return;
                }
                calculateDiscountAmount();
            }
        });
        //加入购物车按钮
        getViews(R.id.btn_add_shop_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputData()){
                    if(mSellerInfo != null && mCustomerInfo != null){
                        mRecordOrderPresenter.addOrderToShopCart(mSellerInfo.getId(), mCustomerInfo.getId(),
                                getCustomAmount(), mSelectedDiscount);
                    }
                }
            }
        });
        //录单按钮
        getViews(R.id.btn_add_record_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputData()){
                    if(mSellerInfo != null && mCustomerInfo != null){
                        mRecordOrderPresenter.generateOrder(mSellerInfo.getId(), mCustomerInfo.getId(),
                                getCustomAmount(), mSelectedDiscount);
                    }
                }
            }
        });
        //购物车按钮
        getViews(R.id.tv_cart_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  2017/5/25 0025 跳转购物车界面
                if(mSellerInfo != null) {
                    FragmentJumpUtil.toShopCartFragment(getUsualFragment(), mSellerInfo.getId());
                }
            }
        });

        //选择折扣
        getViews(R.id.lnv_seller_discount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDiscount();
            }
        });
    }

    private void calculateDiscountAmount(){
        //未填写金额
//                amount = calculateAmount(amount, mSellerInfo.getDiscount() / 10);//以前计算方式
        discountAmount = calculateAmount(getCustomAmount(), mSelectedDiscount / 10);
        LineViewHelper.setValue(getView(), R.id.lnv_rangli_amount, String.format("%.02f", discountAmount));
    }

    /**
     * 选择行业折扣
     */
    private void selectDiscount() {
        WheelDialogHelper<Double> helper = new WheelDialogHelper(getFragmentManager());
        helper.setTextGetter(new TextGetter<Double>() {
            @Override
            public String getText(Double obj) {
                double value = obj;
                return String.format(Locale.getDefault(), "%.1f折", value);
            }
        });
        helper.setSelectedData(mSelectedDiscount);
        helper.setDatas(mShopDiscountDatas);
        helper.setListener(new WheelDialogHelper.OnWheelSelectedListener<Double>() {
            @Override
            public void onSelected(int index, String showText, Double data) {
                mSelectedDiscount = data;
                if (TextUtils.isEmpty(getAmountString())) {
                    return;
                }
                calculateDiscountAmount();
            }
        });
        LineViewText lnv = getViews(R.id.lnv_seller_discount);
        helper.setEventView(lnv, lnv.getContentTextView());
        lnv.performClick();
    }


    /**
     * 计算让利金额
     */
    private double calculateAmount(double customAmount, double discount){
        double result = customAmount * ( 1 - discount);
        return result;
    }

    /**
     * 获取消费金额
     * @return
     */
    private double getCustomAmount(){
        double amount = 0;
        try{
            amount = Double.parseDouble(getAmountString());
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return amount;
    }

    /**
     * 获取消费者手机号
     * @return
     */
    private String getCustomerMobile(){
        return LineViewHelper.getValue(getView(), R.id.lnv_customer_mobile);
    }

    /**
     * 获取消费金额
     * @return
     */
    private String getAmountString(){
        return LineViewHelper.getValue(getView(), R.id.lnv_custom_amount);
    }

    /**
     * 检查输入数据
     * @return true 资料填写完成, false资料未填写完整
     */
    private boolean checkInputData(){
        boolean result = true;
        if(TextUtils.isEmpty(getCustomerMobile()) || TextUtils.isEmpty(getAmountString())){
            showToast("资料未填写完整！");
            result = false;
        }
        return result;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void setSellerInfo(ResSellerInfo data) {
        mSellerInfo = data;
        if(data != null){
            LineViewHelper.setValue(getView(), R.id.lnv_seller_name, data.getName());
            LineViewHelper.setValue(getView(), R.id.lnv_seller_address, data.getAddress());
            LineViewHelper.setValue(getView(), R.id.lnv_seller_owner, data.getRealName());
            LineViewHelper.setValue(getView(), R.id.lnv_seller_discount, String.format("%s折", data.getDiscount()+""));
            mSelectedDiscount = data.getDiscount();
            mShopDiscountDatas = getShopDiscountDatas();
            setViewText(String.format("购物车(%d)", data.getCartCount()), R.id.tv_cart_count);
        }
    }

    @Override
    public void setCustomerInfo(ResCustomerInfo data) {
        mCustomerInfo = data;
        if(data != null){
            String nickName = data.getNickName();
            if(TextUtils.isEmpty(nickName)){
                nickName = "消费者不存在";
            }
            LineViewHelper.setValue(getView(), R.id.lnv_customer_nickname, nickName);
            LineViewHelper.setValue(getView(), R.id.lnv_customer_realname, data.getRealName());
            setViewVisiable(!TextUtils.isEmpty(data.getRealName()), R.id.lnv_customer_realname);
            setViewVisiable(!TextUtils.isEmpty(data.getRealName()), R.id.divider_customer_realname);
        }
    }

    @Override
    public void addShopCartSuccess(ResShopCartCount data) {
        //刷新购物车数量
        setViewText(String.format("购物车(%d)", data.getCount()), R.id.tv_cart_count);
        clearData();
    }

    /**
     * 清空数据
     */
    private void clearData(){
        mCustomerInfo = null;
        LineViewHelper.setValue(getView(), R.id.lnv_customer_mobile, "");
        LineViewHelper.setValue(getView(), R.id.lnv_customer_nickname, "");
        LineViewHelper.setValue(getView(), R.id.lnv_customer_realname, "");
        setViewVisiable(false, R.id.lnv_customer_realname);
        setViewVisiable(false, R.id.divider_customer_realname);
        LineViewHelper.setValue(getView(), R.id.lnv_custom_amount, "");
        LineViewHelper.setValue(getView(), R.id.lnv_rangli_amount, "");
    }

    @Override
    public void generateOrderSuccess(ResGenerateOrder data) {
        FragmentJumpUtil.toRecordOrderPayFragment(getUsualFragment(), data.getOrderSn(), mShopInfo.getId()+"");
    }

    @Override
    public void finish() {
        super.finish();
        if(mRecordOrderPresenter != null){
            mRecordOrderPresenter.detachView();
        }
    }

    @Subscribe
    public void onEvent(DataEvent.OnCloseRecordOrderFragmentEvent evet){
        finish();
    }
}
