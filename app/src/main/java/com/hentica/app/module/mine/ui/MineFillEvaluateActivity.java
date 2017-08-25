package com.hentica.app.module.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hentica.app.framework.activity.BaseCompatActivity;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.view.ChildGridView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineOrderListData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.ui.adapter.PhotoAdapter;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发表评价界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineFillEvaluateActivity extends BaseCompatActivity {

    public static String ORDERINFO = "OrderInfo";

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.mine_fill_value_icon)
    ImageView mIconImageView;
    @BindView(R.id.mine_fill_evaluate_name)
    TextView mNameTextView;
    @BindView(R.id.mine_fill_evaluate_location)
    TextView mLocationTextView;
    @BindView(R.id.mine_fill_evaluate_rating_bar)
    RatingBar mRatingBar;
    @BindView(R.id.mine_fill_evaluate_edit)
    EditText mCommentEdit;
    @BindView(R.id.mine_fill_evaluate_photo_gridview)
    ChildGridView mPhotoGrid;
    @BindView(R.id.mine_fill_evaluate_comment_btn)
    TextView mCommentBtn;

    private PhotoAdapter mPhotoAdapter;
    /**
     * 最大上传照片数
     */
    private int mMaxCount = 9;

    /**
     * 订单信息
     */
    private ResMineOrderListData mOrderInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_fill_evaluate_fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        ButterKnife.bind(this);
        IntentUtil intentUtil = new IntentUtil(getIntent());
        String json = intentUtil.getString(ORDERINFO);
        mOrderInfo = ParseUtil.parseObject(json, ResMineOrderListData.class);
        mPhotoAdapter = new PhotoAdapter(getActivity(), mPhotoGrid);
        mPhotoAdapter.setMaxCount(mMaxCount);
        mPhotoAdapter.setIsCrop(false);
    }

    @Override
    protected void initView() {
        super.initView();
        TitleView title = getViews(R.id.common_title);
        title.setOnLeftImageBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPhotoGrid.setAdapter(mPhotoAdapter);
        // 初始化商家信息
        if (mOrderInfo != null) {
            refreshSellerInfo(mOrderInfo.getSeller());
        }
    }

    @Override
    protected void setEvent() {
        super.setEvent();
        // 发表评论按钮，被点击
        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发表评价
                requestFillEvaluate();
            }
        });
    }

    /**
     * 刷新商家信息
     */
    private void refreshSellerInfo(ResMineOrderListData.SellerBean seller) {
        if (seller != null) {
            ViewUtil.bindImage(mQuery, R.id.mine_fill_value_icon, ApplicationData.getInstance().getImageUrl(seller.getStorePictureUrl()));
            mNameTextView.setText(seller.getName());
            mLocationTextView.setText(seller.getAddress());
        }
    }

    /**
     * 获取评分
     */
    private String getRating() {
        int value = (int) mRatingBar.getRating();
        return value+"";
    }

    /**
     * 获取评论
     */
    private String getComment() {
        return mCommentEdit.getText().toString();
    }

    /**
     * 获取评论图片
     */
    private List<String> getEvaluateImgs() {
        return mPhotoAdapter.getImages();
    }

    /**
     * 获取订单id
     */
    private String getOrderId() {
        return mOrderInfo == null ? "" : mOrderInfo.getId() + "";
    }

    /**
     * 获取用户id
     */
    private String getUserId() {
        return LoginModel.getInstance().getLoginUserId();
    }

    /**
     * 请求发表评论
     */
    private void requestFillEvaluate() {
        String userId = getUserId();
        String orderId = getOrderId();
        String score = getRating();
        final String content = getComment();
        List<String> images = getEvaluateImgs();
        if (TextUtils.isEmpty(content)) {
            showToast("评论内容未输入！");
            return;
        }
        mCommentBtn.setEnabled(false);
        Request.getFillEvaluate(userId, orderId, score, content, images,
                ListenerAdapter.createObjectListener(Object.class, new UsualDataBackListener<Object>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, Object data) {
                        if (isSuccess) {
                            // 请求成功
                            showToast("操作成功！");
                            // 刷新评价数据
                            ResMineOrderListData.EvaluateBean evaluate = new ResMineOrderListData.EvaluateBean();
                            evaluate.setContent(content);
                            mOrderInfo.setEvaluate(evaluate);
                            FragmentJumpUtil.toEvaluateDetail(getActivity(), mOrderInfo);
                            // 发送评论成功通知
                            EventBus.getDefault().post(new DataEvent.OnEvaluatedEvent());
                            finish();
                        }else {
                            // 请求失败
                            mCommentBtn.setEnabled(true);
                        }
                    }
                }));
    }
}
