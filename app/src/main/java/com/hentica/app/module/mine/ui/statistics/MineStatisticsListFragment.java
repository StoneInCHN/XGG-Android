package com.hentica.app.module.mine.ui.statistics;

import android.support.annotation.IdRes;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.event.DataEvent;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;


/**
 * 乐分、乐心、积分、乐豆统计列表界面
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/24.10:07
 */

public abstract class MineStatisticsListFragment<T> extends BaseFragment implements PtrView<T>{

    private PullToRefreshListView mPtrListView;
    private PtrPresenter mPtrPresenter;
    private QuickAdapter<T> mAdapter;

    /** 布局界面 */
    @Override
    public int getLayoutId() {
        return R.layout.mine_statistics_list_fragment;
    }

    @Override
    protected void initData() {
        mAdapter = getListAdapter();
        mPtrPresenter = getPtrPresenter();
    }

    /** 获取Adapter */
    protected abstract QuickAdapter<T> getListAdapter();

    /** 获取PtrPresenter，用于获取刷新与加载更多数据 */
    protected abstract PtrPresenter getPtrPresenter();

    @Override
    protected void initView() {
        mPtrListView = getViews(getPtrListViewId());
        ListViewUtils.setDefaultEmpty(mPtrListView.getRefreshableView());
        mPtrListView.setAdapter(mAdapter);
    }

    /** 获取下拉列表id */
    protected @IdRes int getPtrListViewId(){
        return R.id.mine_statistics_ptr_list;
    }

    /** 获取左侧统计标签 */
    protected @IdRes int getStatisticsItemLeft(){
        return R.id.mine_statistics_tv_label_1;
    }

    /** 获取左侧统计显示值控件 */
    protected @IdRes int getStatisticsItemLeftValue(){
        return R.id.mine_statistics_tv_value_1;
    }

    /** 获取右侧统计项标签 */
    protected @IdRes int getStatisticsItemRight(){
        return R.id.mine_statistics_tv_label_2;
    }

    /** 获取右侧统计显示值控件 */
    protected @IdRes int getStatisticsItemRightValue(){
        return R.id.mine_statistics_tv_value_2;
    }


    @Override
    protected void setEvent() {
        //下拉刷新事件
        mPtrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(mPtrPresenter != null){
                    mPtrPresenter.onRefresh();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(mPtrListView != null){
                    mPtrPresenter.onLoadMore();
                }
            }
        });
    }

    @Override
    public void dismissLoadingDialog() {
        super.dismissLoadingDialog();
        if(mPtrListView != null){
            mPtrListView.onRefreshComplete();
        }
    }

    @Override
    public int getListSize() {
        return mAdapter.getCount();
    }

    @Override
    public void setListDatas(List<T> datas) {
        mAdapter.setDatas(datas);
        mPtrListView.onRefreshComplete();
    }

    @Override
    public void addListDatas(List<T> datas) {
        mAdapter.addAll(datas);
        mPtrListView.onRefreshComplete();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPtrPresenter.onRefresh();
    }

    @Subscribe
    public void onEvent(DataEvent.OnLoginEvent event){
        //登录成功刷新界面
        mPtrPresenter.onRefresh();
    }
}
