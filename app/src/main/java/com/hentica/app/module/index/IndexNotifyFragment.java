package com.hentica.app.module.index;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.index.IndexNotifyListData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrListView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

import java.util.List;

import butterknife.BindView;

/**
 * 通知界面
 *
 * @author
 * @createTime 2017-03-28 下午14:23:27
 */
public class IndexNotifyFragment extends BaseFragment {

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.index_notify_list)
    CustomPtrListView mNotifyPtrLv;

    private NotifyAdapter mAdapter;

    private int mPageNum;
    private int mPageSize = 10;
    // 是否需要刷新数据
    private boolean mNeedReload = true;

    @Override
    public void onResume() {
        super.onResume();
        // 若需要刷新
        if(mNeedReload){
            requestNotifyList(false);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.index_notify_fragment;
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
        mAdapter = new NotifyAdapter();
    }

    @Override
    protected void initView() {
        mNotifyPtrLv.setAdapter(mAdapter);
        ListViewUtils.setDefaultEmpty(mNotifyPtrLv.getRefreshableView());
    }

    @Override
    protected void setEvent() {
        // 列表被点击
        mNotifyPtrLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IndexNotifyListData data = (IndexNotifyListData) view.getTag();
                // 跳转到消息详情
                FragmentJumpUtil.toNotifyDetail(getUsualFragment(), data);
                // 若为未读状态，请求阅读消息
                if(!data.isIsRead()){
                    requestReadNotify(data.getId()+"");
                }
            }
        });
        // 下拉刷新
        mNotifyPtrLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                requestNotifyList(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 加载更多
                requestNotifyList(true);
            }
        });
    }

    /** 请求消息列表 */
    private void requestNotifyList(final boolean isLoadMore){
        String userId = LoginModel.getInstance().getLoginUserId();
        String pageNum = isLoadMore ? ++mPageNum+"" : 1+"";
        String pageSize = mPageSize+"";
        Request.getMessageGetMsgList(userId,pageNum,pageSize,
                ListenerAdapter.createArrayListener(IndexNotifyListData.class, new UsualDataBackListener<List<IndexNotifyListData>>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<IndexNotifyListData> data) {
                        if(isSuccess){
                            mNotifyPtrLv.onRefreshComplete();
                            PullToRefreshBase.Mode mode = data.size() < mPageSize ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH;
                            mNotifyPtrLv.setMode(mode);
                            if(isLoadMore){
                                // 合并数据
                                data = ViewUtil.mergeList(mAdapter.getDatas(),data);
                            }
                            mAdapter.setDatas(data);
                            // 标为不需要请求
                            mNeedReload = false;
                        }
                    }
                }));
    }

    /** 请求阅读消息 */
    private void requestReadNotify(String msgId){
        String userId = LoginModel.getInstance().getLoginUserId();
        Request.getMessageReadMessage(userId,msgId,
                ListenerAdapter.createObjectListener(Object.class, new UsualDataBackListener<Object>(this) {
                    @Override
                    protected void onComplete(boolean isSuccess, Object data) {
                        if(isSuccess){
                            // 操作成功
                            mNeedReload = true;
                        }
                    }
                }));
    }

    /** 通知列表适配器 */
    private class NotifyAdapter extends QuickAdapter<IndexNotifyListData> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.index_notify_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, IndexNotifyListData data) {
            AQuery query = new AQuery(convertView);
            query.id(R.id.index_notify_unread_tip).visibility(data.isIsRead() ? View.GONE : View.VISIBLE);
            query.id(R.id.index_notify_item_title).text(data.getMessageTitle());
            query.id(R.id.index_notify_item_date).text(DateHelper.stampToDate(data.getCreateDate()+""));
            query.id(R.id.index_notify_item_content).text(data.getMessageContent());
            convertView.setTag(data);
        }
    }

}
