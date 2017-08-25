package com.hentica.app.module.mine.ui.shop;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.view.ChildGridView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.ResMineEvaluateDetailData;
import com.hentica.app.module.mine.model.ShopModel;
import com.hentica.app.module.mine.presenter.EvaluateDetailPresenter;
import com.hentica.app.module.mine.presenter.EvaluateDetailPresenterImpl;
import com.hentica.app.module.mine.presenter.EvaluateReplyPresetenr;
import com.hentica.app.module.mine.presenter.EvaluateReplyPresetenrImpl;
import com.hentica.app.module.mine.ui.adapter.ImageAdapter;
import com.hentica.app.module.mine.view.shop.EvaluateDetailView;
import com.hentica.app.module.mine.view.shop.EvaluateReplyView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.GlideUtil;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;
import com.hentica.appbase.famework.util.ListUtils;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商家回复评价
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/11.16:06
 */

public class MineReplyEvaluateFragment extends BaseFragment implements EvaluateDetailView,
        EvaluateReplyView {

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.mine_fill_value_icon)
    ImageView mIconView;
    @BindView(R.id.mine_fill_evaluate_name)
    TextView mNameTv;
    @BindView(R.id.mine_fill_evaluate_location)
    TextView mLocationTv;
    @BindView(R.id.mine_fill_evaluate_rating_bar)
    RatingBar mRatingBar;
    @BindView(R.id.mine_fill_evaluate_edit)
    TextView mContentTv;
    @BindView(R.id.mine_fill_evaluate_photo_gridview)
    ChildGridView mImageGv;
    @BindView(R.id.mine_fill_evaluate_reply)
    TextView mReplyTv;
    /**
     * 图片列表适配器
     */
    private ImageAdapter mImageAdapter;

    public static final String DATA_ORDER_ID = "orderId";

    private long mOrderId = 0;

    private EvaluateDetailPresenter mEvaluateDetailPresenter;

    private EvaluateReplyPresetenr mReplyPresenter;

    private ResMineEvaluateDetailData mEvaluateData;

    @Override
    public int getLayoutId() {
        return R.layout.mine_fill_evaluate_fragment;
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
        title.setTitleText("回复评价");
    }

    @Override
    protected void handleIntentData(Intent intent) {
        mOrderId = intent.getLongExtra(DATA_ORDER_ID, mOrderId);
    }

    @Override
    protected void initData() {
        mEvaluateDetailPresenter = new EvaluateDetailPresenterImpl(this);
        mEvaluateDetailPresenter.getDetailData(String.valueOf(mOrderId));
        mReplyPresenter = new EvaluateReplyPresetenrImpl(this);
        mImageAdapter = new ImageAdapter();
    }

    @Override
    protected void initView() {
        //显示回复评价
        setViewVisiable(true, R.id.mine_fill_evaluate_layout_reply);
        setViewText("回复评价", R.id.mine_fill_evaluate_comment_btn);
        setViewEnable(false, R.id.mine_fill_evaluate_edit);
        mLocationTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        mImageGv.setAdapter(mImageAdapter);
        mRatingBar.setIsIndicator(true);
    }

    @Override
    protected void setEvent() {
        //回复评价
        setViewClickEvent(R.id.mine_fill_evaluate_comment_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEvaluateData != null) {
                    mReplyPresenter.toReply(String.valueOf(mEvaluateData.getId()), getReplyContent());
                }
            }
        });
    }

    @Override
    public void setDetailData(ResMineEvaluateDetailData data) {
        //显示评价数据
        mEvaluateData = data;
        refreshUI(data);
    }

    private String getReplyContent() {
        return mQuery.id(R.id.mine_fill_evaluate_reply).getText().toString();
    }

    /**
     * 刷新界面
     */
    private void refreshUI(ResMineEvaluateDetailData data) {
        if (data != null) {
            mRatingBar.setRating(data.getScore());
            mContentTv.setText(data.getContent());
            mImageAdapter.setDatas(wrapImagesUrl(data.getEvaluateImages()));
            GlideUtil.loadHeadIcon(getActivity(),
                    ApplicationData.getInstance().getImageUrl(data.getEndUser().getUserPhoto()),
                    mIconView);//显示用户头像
            mNameTv.setText(data.getEndUser().getNickName());//显示用户昵称
            //显示支付金额
            HtmlBuilder builder = HtmlBuilder.newInstance();
            builder.appendNormal("支付金额：").appendRed(data.getOrder().getAmount());
            setViewText(builder.create(), R.id.mine_fill_evaluate_location);
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

    @Override
    public void replySuccess() {
        //回复评价成功
//        跳转评价详情
        FragmentJumpUtil.toEvaluateDetail(getUsualFragment(), mOrderId, ShopModel.getInstance().getShopInfo());
        EventBus.getDefault().post(new DataEvent.OnShopReplySuccessEvent());
        finish();
    }
}
