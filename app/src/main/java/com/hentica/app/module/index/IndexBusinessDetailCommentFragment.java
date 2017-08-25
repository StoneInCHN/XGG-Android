package com.hentica.app.module.index;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.lib.view.ChildListView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.RebateDataBackListener;
import com.hentica.app.module.common.listener.RebateListenerAdapter;
import com.hentica.app.module.entity.index.IndexEvaluateListData;
import com.hentica.app.module.entity.index.IndexPageData;
import com.hentica.app.module.index.adapter.CommentAdapter;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import java.util.List;

/**
 * Created by YangChen on 2017/3/24 17:12.index_business_detail_more_comment_layout
 */

@SuppressLint("ValidFragment")
public class IndexBusinessDetailCommentFragment extends BaseFragment {

    private LinearLayout mMoreLayout;
    private RatingBar mRatingBar;
    private TextView mScoreTextView;
    private TextView mCommentCountTextView;
    private ChildListView mChildListView;

    private CommentAdapter mAdapter;
    private AQuery mAQuery;
    /** 当前分页数 */
    private int mPageNum = 1;
    /** 每页大小 */
    private int mPageSize = 5;
    /** 商户id */
    private String mSellerId;
    /** 评星 */
    private float mRating;
    /** 评分 */
    private String mScore;

    public IndexBusinessDetailCommentFragment(){}

    public IndexBusinessDetailCommentFragment(String sellerId){
        mSellerId = sellerId;
    }

    @Override
    public int getLayoutId() {
        return R.layout.index_business_detail_comment_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    protected void initData() {
        mAdapter = new CommentAdapter(getContext(),getUsualFragment());
        mAdapter.showReplay(true);
        mQuery = new AQuery(getView());
    }

    @Override
    protected void initView() {
        mMoreLayout = (LinearLayout) mQuery.id(R.id.index_business_detail_more_comment_layout).getView();
        mRatingBar = (RatingBar) mQuery.id(R.id.index_business_detail_rating_bar).getView();
        mScoreTextView = mQuery.id(R.id.index_business_detail_score).getTextView();
        mCommentCountTextView = mQuery.id(R.id.index_business_detail_comment_count).getTextView();
        mChildListView = (ChildListView) mQuery.id(R.id.index_business_detail_comment_list).getView();

        mChildListView.setAdapter(mAdapter);
        ListViewUtils.setDefaultEmpty(mChildListView);

        requestCommentList(false);
    }

    @Override
    protected void setEvent() {
        // 查看更多评论
        mMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到评论列表
                FragmentJumpUtil.toAllEvaluate(getUsualFragment(),mSellerId,mRating,mScore);
            }
        });
    }

    /******************************** 界面控制 **************************************/
    /** 评星数 */
    public void setRate(float rate){
        mRating = rate;
        mRatingBar.setRating(mRating);
    }
    /** 评分 */
    public void setScore(String score){
        mScore = score;
        mScoreTextView.setText(mScore);
    }
    /** 评论数 */
    private void setCommentCount(int count){
        mCommentCountTextView.setText(count+"条评论");
    }


    /******************************** 网络请求 **************************************/
    private void requestCommentList(boolean isLoadMore){
        mPageNum = isLoadMore ? ++mPageSize : 1;
        String pageSize = mPageSize+"";
        Request.getSellerEvaluateList(mSellerId,mPageNum+"",pageSize,
                RebateListenerAdapter.createArrayListener(IndexEvaluateListData.class, new RebateDataBackListener<List<IndexEvaluateListData>>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<IndexEvaluateListData> data) {
                        if(isSuccess){
                            // 请求成功
                            mAdapter.setData(data);
                            //boolean hasDatas = !ArrayListUtil.isEmpty(mAdapter.getData());
                            //mMoreLayout.setVisibility(hasDatas ? View.VISIBLE : View.GONE);
                        }
                    }

                    @Override
                    public void extralData(String extral) {
                        // 解析数据
                        IndexPageData page = ParseUtil.parseObject(extral,IndexPageData.class);
                        if(page != null){
                            setCommentCount(page.getTotal());
                        }
                    }
                }));
    }
}
