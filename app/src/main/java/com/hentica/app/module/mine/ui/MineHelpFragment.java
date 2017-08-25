package com.hentica.app.module.mine.ui;

import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.fragment.CommonPtrFragment;
import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.entity.mine.ResMineHelpItem;
import com.hentica.app.module.mine.presenter.HelpPtrPresenter;
import com.hentica.app.module.mine.ui.adapter.HelpAdapter;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.appbase.famework.adapter.QuickAdapter;

import java.util.List;

/**
 * 用户帮助界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineHelpFragment extends CommonPtrFragment<ResMineHelpItem> implements PtrView<ResMineHelpItem>{

    private PtrPresenter mPtrPresenter;

    @Override
    protected String getFragmentTitle() {
        return "用户帮助";
    }

    @Override
    public QuickAdapter<ResMineHelpItem> createAdapter() {
        return new HelpAdapter();
    }

    @Override
    protected void initData() {
        super.initData();
        mPtrPresenter = new HelpPtrPresenter(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ResMineHelpItem item = getItem(position - 1);
        if(item != null) {
            FragmentJumpUtil.toHelpDetailFragment(getUsualFragment(), item.getId(), item.getTitle());
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mPtrPresenter.onRefresh();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        mPtrPresenter.onLoadMore();
    }

    @Override
    public void setListDatas(List<ResMineHelpItem> datas) {
        super.setDatas(datas);
    }

    @Override
    public void addListDatas(List<ResMineHelpItem> datas) {
        super.addDatas(datas);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPtrListView().setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPtrPresenter.onRefresh();
    }
}
