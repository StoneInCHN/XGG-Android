package com.hentica.app.module.index;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.RebateDataBackListener;
import com.hentica.app.module.common.listener.RebateListenerAdapter;
import com.hentica.app.module.entity.index.IndexEvaluateListData;
import com.hentica.app.module.entity.index.IndexPageData;
import com.hentica.app.module.index.adapter.CommentAdapter;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import java.util.List;

import butterknife.BindView;

/**
 * 全部评价界面
 *
 * @author 
 * @createTime 2017-03-23 下午15:13:27
 */
public class IndexAllCommentFragment extends BaseFragment {

    public static final String SELLERID = "SellerId";
    public static final String RATING = "Rating";
    public static final String SCORE = "Score";

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.index_all_comment_rating_bar)
    RatingBar mRatingBar;
    @BindView(R.id.index_all_comment_score)
    TextView mScoreTextView;
    @BindView(R.id.index_all_comment_comment_count)
    TextView mCommentCountTextView;
    @BindView(R.id.index_all_comment_comment_list)
    PullToRefreshListView mPtrListView;

    private CommentAdapter mAdapter;
    /** 商家id */
    private String mSellerId;
    /** 评星 */
    private float mRating;
    /** 评分 */
    private String mScore;

    /** 当前页数 */
    private int mPageNum;
    /** 每页大小 */
    private int mPageSize = 10;

    @Override
    public int getLayoutId() {
        return R.layout.index_all_comment_fragment;
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
        mSellerId = intentUtil.getString(SELLERID);
        mRating = intentUtil.getFloat(RATING,0);
        mScore = intentUtil.getString(SCORE);
    }

    @Override
    protected void initData() {
        mAdapter = new CommentAdapter(getContext(),getUsualFragment());
        mAdapter.showReplay(true);
    }

    @Override
    protected void initView() {
        // 显示标题
        mTitleView.setVisibility(View.VISIBLE);
        mPtrListView.setAdapter(mAdapter);
        ListViewUtils.setDefaultEmpty(mPtrListView.getRefreshableView());
        setRate(mRating);
        setScore(mScore);
        requestCommentList(false);
    }

    @Override
    protected void setEvent() {
        mPtrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                requestCommentList(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 加载更多
                requestCommentList(true);
            }
        });
    }

    /******************************** 界面控制 **************************************/
    /** 评星数 */
    private void setRate(float rate){
        mRatingBar.setRating(rate);
    }
    /** 评分 */
    private void setScore(String score){
        mScoreTextView.setText(score);
    }
    /** 评论数 */
    private void setCommentCount(int count){
        mCommentCountTextView.setText(count+"条评论");
    }
    /** 列表数据 */
    private void setListData(List<IndexEvaluateListData> data){
        mAdapter.setData(data);
    }

    /** 请求评论列表 */
    private void requestCommentList(final boolean isLoadMore){
        mPageNum = isLoadMore ? ++mPageNum : 1;
        String pageSize = mPageSize+"";
        Request.getSellerEvaluateList(mSellerId+"",mPageNum+"",pageSize,
                RebateListenerAdapter.createArrayListener(IndexEvaluateListData.class, new RebateDataBackListener<List<IndexEvaluateListData>>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<IndexEvaluateListData> data) {
                        if(isSuccess){
                            // 请求成功
                            mPtrListView.onRefreshComplete();
                            PullToRefreshBase.Mode mode = data.size() < mPageSize ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                            mPtrListView.setMode(mode);
                            if(isLoadMore){
                                // 合并数据
                                data = ViewUtil.mergeList(mAdapter.getData(),data);
                            }
                            setListData(data);
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
