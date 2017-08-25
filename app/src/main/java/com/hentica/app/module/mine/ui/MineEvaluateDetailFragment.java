package com.hentica.app.module.mine.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.view.ChildGridView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineEvaluateDetailData;
import com.hentica.app.module.entity.mine.ResMineOrderListData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.ui.adapter.ImageAdapter;
import com.hentica.app.module.mine.ui.shop.OrderDetailHelper;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.hentica.appbase.famework.util.ListUtils;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 评价详情界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineEvaluateDetailFragment extends BaseFragment {

    public static final String ORDERINFO = "OrderInfo";
    public static final String DATA_ORDER_ID = "orderId";
    public static final String DATA_SHOP_PHOTO_URL = "photoUrl";
    public static final String DATA_SHOP_NAME = "shopName";
    public static final String DATA_SHOP_ADDRESS = "shopAddress";

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.mine_evaluate_detail_icon)
    ImageView mIconView;
    @BindView(R.id.mine_evaluate_detail_name)
    TextView mNameTv;
    @BindView(R.id.mine_evaluate_detail_location)
    TextView mLocationTv;
    @BindView(R.id.mine_evaluate_detail_rating_bar)
    RatingBar mRatingBar;
    @BindView(R.id.mine_evaluate_detail_content)
    TextView mContentTv;
    @BindView(R.id.mine_evaluate_detail_photo_gridview)
    ChildGridView mImageGv;
    @BindView(R.id.mine_evaluate_detail_reply_text)
    TextView mReplyTv;

    private String mShopUrl = "";
    private String mShopName = "";
    private String mShopAddress = "";
    private long mOrderId = 0;

    /** 订单信息 */
    private ResMineOrderListData mOrderData;
    /** 图片列表适配器 */
    private ImageAdapter mImageAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.mine_evaluate_detail_fragment;
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
        mOrderId = intentUtil.getLong(DATA_ORDER_ID, mOrderId);
        mShopUrl = intentUtil.getString(DATA_SHOP_PHOTO_URL);
        mShopName = intentUtil.getString(DATA_SHOP_NAME);
        mShopAddress = intentUtil.getString(DATA_SHOP_ADDRESS);
        mOrderData = intentUtil.getObject(ORDERINFO, ResMineOrderListData.class);
    }

    @Override
    protected void initData() {
        mImageAdapter = new ImageAdapter();
    }

    @Override
    protected void initView() {
        mImageGv.setAdapter(mImageAdapter);
        // 请求评论详情
        requestEvaluateDetail();
        refreshSellerInfo();
    }

    @Override
    protected void setEvent() {
        mImageGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到大图
                FragmentJumpUtil.toImageGallery(getUsualFragment(),mImageAdapter.getDatas(),position);
            }
        });
    }

    @Override
    public void finish() {
        if(mOrderData != null && !OrderDetailHelper.getInstance().isExist()){
            FragmentJumpUtil.toOrderDetail(getUsualFragment(), mOrderData, null);
        }
        super.finish();
    }

    /**
     * 刷新商家信息
     */
    private void refreshSellerInfo() {
            ViewUtil.bindImage(getView(), R.id.mine_evaluate_detail_icon, ApplicationData.getInstance().getImageUrl(mShopUrl));
            mNameTv.setText(mShopName);
            // 地址
            mLocationTv.setText(mShopAddress);
    }

    /** 刷新界面 */
    private void refreshUI(ResMineEvaluateDetailData data){
        if(data != null){
            mRatingBar.setRating(data.getScore());
            mContentTv.setText(data.getContent());
            mImageAdapter.setDatas(wrapImagesUrl(data.getEvaluateImages()));
            mReplyTv.setText("商家回复："+data.getSellerReply());
            if(TextUtils.isEmpty(data.getSellerReply())){
                mReplyTv.setVisibility(View.GONE);
            }
        }
    }


    private List<String> wrapImagesUrl(List<String> images){
        List<String> result = new ArrayList<>();
        if(!ListUtils.isEmpty(images)) {
            for (String image : images) {
                result.add(ApplicationData.getInstance().getImageUrl(image));
            }
        }
        return result;
    }

    /**
     * 获取订单id
     */
    private String getOrderId() {
        return  mOrderData != null ? mOrderData.getId()+"" : mOrderId + "";
    }

    /**
     * 获取用户id
     */
    private String getUserId() {
        return LoginModel.getInstance().getLoginUserId();
    }

    /** 请求列表评论详情 */
    private void requestEvaluateDetail(){
        String orderId = getOrderId();
        String userId = getUserId();
        Request.getOrderGetEvaluateByOrder(userId,orderId,
                ListenerAdapter.createObjectListener(ResMineEvaluateDetailData.class, new UsualDataBackListener<ResMineEvaluateDetailData>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, ResMineEvaluateDetailData data) {
                        if(isSuccess){
                            // 请求成功
                            refreshUI(data);
                        }
                    }
                }));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /** 评论成功事件 */
    @Subscribe
    public void onEvent(DataEvent.OnEvaluatedEvent event){
        // 刷新界面
        requestEvaluateDetail();
    }
}
