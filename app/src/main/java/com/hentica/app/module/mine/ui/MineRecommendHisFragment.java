package com.hentica.app.module.mine.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.fragment.CommonPtrFragment;
import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.entity.mine.ResMineRecommendRec;
import com.hentica.app.module.mine.presenter.RecommendHistorPtrPresenter;
import com.hentica.app.module.mine.ui.adapter.RecommendHistoryAdapter;
import com.hentica.appbase.famework.adapter.QuickAdapter;

import java.util.List;

/**
 * 推荐记录界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineRecommendHisFragment extends CommonPtrFragment<ResMineRecommendRec> implements PtrView<ResMineRecommendRec> {

    private PtrPresenter mPtrPresenter;

    @Override
    protected String getFragmentTitle() {
        return "推荐记录";
    }

    @Override
    public QuickAdapter<ResMineRecommendRec> createAdapter() {
        return new RecommendHistoryAdapter();
    }

    @Override
    protected void initData() {
        super.initData();
        mPtrPresenter = new RecommendHistorPtrPresenter(this);
        mPtrPresenter.onRefresh();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        mPtrPresenter.onRefresh();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        mPtrPresenter.onLoadMore();
    }

    @Override
    public void setListDatas(List<ResMineRecommendRec> datas) {
        super.setDatas(datas);
    }

    @Override
    public void addListDatas(List<ResMineRecommendRec> datas) {
        super.addDatas(datas);
    }
}
