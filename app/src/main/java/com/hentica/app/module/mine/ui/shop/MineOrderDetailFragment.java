package com.hentica.app.module.mine.ui.shop;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineOrderListData;
import com.hentica.app.module.entity.mine.shop.ResShopOrderItem;
import com.hentica.app.module.entity.type.OrderStatus;
import com.hentica.app.module.entity.type.ShopOrderStatus;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.mine.model.ShopModel;
import com.hentica.app.module.mine.ui.MineOrdersMainFragment;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.OrderStatusUtils;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.dialog.EncourageDialog;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * 订单详情界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineOrderDetailFragment extends BaseFragment {

    public static final String SHOPORDERDATA = "ShopOrderData";
    public static final String USERORDERDATA = "UserOrderData";
    public static final String ORDER_ID = "OrderId";
    public static final String ENCOURAGE_AMOUNT = "EncourageAmount";

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.mine_order_detail_return_score)
    TextView mReturnScoreTv;
    @BindView(R.id.mine_order_detail_icon)
    ImageView mIconView;
    @BindView(R.id.mine_order_detail_name)
    TextView mNameTv;
    @BindView(R.id.mine_order_detail_status)
    TextView mStatusTv;
    @BindView(R.id.mine_order_detail_pay_price)
    TextView mPriceTv;
    @BindView(R.id.mine_oreder_detail_order_sn)
    TextView mOrderSnTv;
    @BindView(R.id.mine_oreder_detail_time)
    TextView mOrderTimeTv;
    @BindView(R.id.mine_oreder_detail_remark)
    TextView mRemarkTv;
    @BindView(R.id.mine_order_detail_opt_layout1)
    LinearLayout mShopOrderOptLayout;
    @BindView(R.id.mine_order_detail_reply_btn)
    TextView mReplyBtn;
    @BindView(R.id.mine_order_detail_phone_btn)
    TextView mPhoneBtn;
    @BindView(R.id.mine_order_detail_to_comment_btn)
    TextView mCommentBtn;
    @BindView(R.id.mine_order_detail_see_comment_btn)
    TextView mSeeCommentBtn;
    @BindView(R.id.mine_order_detail_business_layout)
    RelativeLayout mBusinessLayout;
    @BindView(R.id.mine_order_detail_next_icon)
    ImageView mNextIcon;

    /**
     * 店铺订单信息
     */
    private ResShopOrderItem mShopOrderInfo;
    /**
     * 用户订单信息
     */
    private ResMineOrderListData mUserOrderInfo;
    /**
     * 订单id，用于支付成功跳转订单详情
     */
    private String mOrderId;
    /**
     * 鼓励金金额
     */
    private double mEncourageAmount;

    private EncourageDialog dialog = null;

    @Override
    public int getLayoutId() {
        return R.layout.mine_order_detail_fragment;
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
        String json1 = intentUtil.getString(SHOPORDERDATA);
        String json2 = intentUtil.getString(USERORDERDATA);
        mShopOrderInfo = ParseUtil.parseObject(json1, ResShopOrderItem.class);
        mUserOrderInfo = ParseUtil.parseObject(json2, ResMineOrderListData.class);
        mOrderId = intentUtil.getString(ORDER_ID);
        mEncourageAmount = intentUtil.getDouble(ENCOURAGE_AMOUNT, 0);
    }

    @Override
    protected void initData() {
        OrderDetailHelper.getInstance().setExist(true);
    }

    @Override
    protected void initView() {
        if (mShopOrderInfo != null) {
            // 店铺订单详情
            refreshShopOrderUI(mShopOrderInfo);
        } else if (mUserOrderInfo != null) {
            // 用户订单详情
            refreshUserOrderUI(mUserOrderInfo);
        }
        // 订单id不为空，请求订单详情
        if (!TextUtils.isEmpty(mOrderId)) {
            requestUserOrderDetail();
        }
    }

    @Override
    protected void setEvent() {
        // 返回按钮，被点击
        mTitleView.setOnLeftImageBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 立即评价，被点击
        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到立即评价界面
                FragmentJumpUtil.toFillEvaluate(getUsualFragment(), mUserOrderInfo);
            }
        });
        // 查看评价，被点击
        mSeeCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到评价详情界面
                FragmentJumpUtil.toEvaluateDetail(getUsualFragment(), mUserOrderInfo);
            }
        });
        // 联系电话，被点击
        mPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShopOrderInfo != null) {
                    //打电话
                    FragmentJumpUtil.toCalling(getUsualFragment(), mShopOrderInfo.getEndUser().getCellPhoneNum());
                }
            }
        });
        // 店铺信息被点击
        mBusinessLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserOrderInfo != null) {
                    // 跳转到商家详情
                    FragmentJumpUtil.toBusinessDetail(getUsualFragment(), getSellerId());
                }
            }
        });
    }

    /**
     * 获取商家id
     */
    private String getSellerId() {
        return (mUserOrderInfo != null && mUserOrderInfo.getSeller() != null) ? mUserOrderInfo.getSeller().getId() + "" : "";
    }

    /**
     * 刷新店铺订单界面
     */
    private void refreshShopOrderUI(ResShopOrderItem data) {
        if (data != null) {
            mReturnScoreTv.setText(PriceUtil.format4Decimal(data.getSellerScore()));//商家积分
            refreshShopUser(data.getEndUser());
            HtmlBuilder hb = HtmlBuilder.newInstance()
                    .append(data.isIsSallerOrder() ? "让利金额：" : "支付金额:")
                    .appendRed("￥" + PriceUtil.format(data.isIsSallerOrder() ? data.getRebateAmount() : data.getAmount()));
            mPriceTv.setText(hb.create());
            mOrderSnTv.setText(data.getSn());
            mOrderTimeTv.setText(DateHelper.stampToDate(data.getCreateDate() + "", "yyyy-MM-dd HH:mm"));
            mRemarkTv.setText(data.getRemark());
            // 显示立即回复和联系电话
            mShopOrderOptLayout.setVisibility(View.VISIBLE);
            mCommentBtn.setVisibility(View.GONE);
            mSeeCommentBtn.setVisibility(View.GONE);
            setViewText("赠送商家积分", R.id.mine_order_detail_score_tip);
            mStatusTv.setText(OrderStatusUtils.getStatusDesc(data.getStatus(), data.isIsClearing()));
            refreshOptUI();
        }
    }

    /**
     * 刷新用户订单界面
     */
    private void refreshUserOrderUI(ResMineOrderListData data) {
        if (data != null) {
            refreshUserSeller(data.getSeller());
            HtmlBuilder hb = HtmlBuilder.newInstance()
                    .append(data.isSallerOrder() ? "让利金额：" : "支付金额:")
                    .appendRed("￥" + PriceUtil.format(data.isSallerOrder() ? data.getRebateAmount() : data.getAmount()));
            if (OrderStatus.UNPAID.equals(data.getStatus())) {
                mReturnScoreTv.setText(PriceUtil.format4Decimal(0));
                setViewVisiable(false, R.id.layout_options);
            } else {
                mReturnScoreTv.setText(PriceUtil.format4Decimal(data.getUserScore()));
            }
            mPriceTv.setText(hb.create());
            mOrderSnTv.setText(data.getSn());
            mOrderTimeTv.setText(DateHelper.stampToDate(data.getCreateDate() + "", "yyyy-MM-dd HH:mm"));
            mRemarkTv.setText(data.getRemark());
            setViewText("赠送会员积分", R.id.mine_order_detail_score_tip);
            refreshOptUI();
            // 若有鼓励金，弹出鼓励金界面
            if (mEncourageAmount > 0) {
                if (dialog == null) {
                    dialog = new EncourageDialog(getUsualFragment());
                    dialog.setEncourageAmount(mEncourageAmount);
                    dialog.show(getFragmentManager(), "encourage");
                }
            }
            mStatusTv.setText(OrderStatusUtils.getStatusDes(data.getStatus()));
        }
    }


    /**
     * 刷新店铺订单用户信息
     */
    private void refreshShopUser(ResShopOrderItem.EndUserBean user) {
        if (user != null) {
            ViewUtil.bindImage(getView(), R.id.mine_order_detail_icon, ApplicationData.getInstance().getImageUrl(user.getUserPhoto()));
            mNameTv.setText(user.getNickName());
            mNextIcon.setVisibility(View.GONE);
        }
    }

    /**
     * 刷新用户订单店铺信息
     */
    private void refreshUserSeller(ResMineOrderListData.SellerBean seller) {
        if (seller != null) {
            ViewUtil.bindImage(getView(), R.id.mine_order_detail_icon, ApplicationData.getInstance().getImageUrl(seller.getStorePictureUrl()));
            mNameTv.setText(seller.getName());
            mNextIcon.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 刷新按钮状态
     */
    private void refreshOptUI() {
        if (mShopOrderInfo != null) {
            // 店铺订单详情，显示立即回复和联系电话
            mShopOrderOptLayout.setVisibility(View.VISIBLE);
            mCommentBtn.setVisibility(View.GONE);
            mSeeCommentBtn.setVisibility(View.GONE);
            initReplyBtn();
        } else if (mUserOrderInfo != null) {
            // 用户订单详情
            mShopOrderOptLayout.setVisibility(View.GONE);
            // 判断用户是否评论
            if (mUserOrderInfo.getEvaluate() != null && !TextUtils.isEmpty(mUserOrderInfo.getEvaluate().getContent())) {
                mCommentBtn.setVisibility(View.GONE);
                mSeeCommentBtn.setVisibility(View.VISIBLE);
            } else {
                mCommentBtn.setVisibility(View.VISIBLE);
                mSeeCommentBtn.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 初始化回复按钮
     */
    private void initReplyBtn() {
        if (ShopOrderStatus.FINISHED.equals(mShopOrderInfo.getStatus())) {
            //状态FINISHED 且 sellerReply == null，表示用户已评论，商家未评论
            if (mShopOrderInfo.getEvaluate().getSellerReply() == null) {
                // 立即回复，被点击
                mReplyBtn.setText("立即回复");
                mReplyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentJumpUtil.toReplyEvaluate(getUsualFragment(), mShopOrderInfo.getId());
                    }
                });
            } else {
                //商家已评论
                mReplyBtn.setText("查看回复");
                mReplyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentJumpUtil.toEvaluateDetail(getUsualFragment(), mShopOrderInfo.getId(), ShopModel.getInstance().getShopInfo());
                    }
                });
            }
            mReplyBtn.setVisibility(View.VISIBLE);
        } else {
            mReplyBtn.setVisibility(View.GONE);
        }
    }

    /**
     * 获取用户id
     */
    private String getUserId() {
        UserLoginData loginData = LoginModel.getInstance().getUserLogin();
        return loginData == null ? "" : loginData.getId() + "";
    }

    /**
     * 请求用户订单详情
     */
    private void requestUserOrderDetail() {
        String userId = getUserId();
        Request.getUserOrderDetail(userId, mOrderId,
                ListenerAdapter.createObjectListener(ResMineOrderListData.class, new UsualDataBackListener<ResMineOrderListData>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, ResMineOrderListData data) {
                        if (isSuccess) {
                            mUserOrderInfo = data;
                            refreshUserOrderUI(mUserOrderInfo);
                        }
                    }
                }));
    }

    /**
     * 商家回复成功
     */
    @Subscribe
    public void onEvent(DataEvent.OnShopReplySuccessEvent event) {
        finish();
    }

    /**
     * 评价成功
     */
    @Subscribe
    public void onEvent(DataEvent.OnEvaluatedEvent event) {
        // 刷新订单详情
        requestUserOrderDetail();
    }

    @Override
    public void finish() {
        if (!TextUtils.isEmpty(mOrderId)) {
            // 跳转到订单列表
            startFrameActivity(MineOrdersMainFragment.class);
        }
        super.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        OrderDetailHelper helper = OrderDetailHelper.getInstance();
        helper.setExist(false);
        helper.destory();
        helper = null;
        super.onDestroy();
    }
}
