package com.hentica.app.module.mine.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.fragment.CommonPtrFragment;
import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.entity.mine.ResMineCollect;
import com.hentica.app.module.mine.presenter.CollectPtrPresenter;
import com.hentica.app.module.mine.ui.adapter.CollectAdapter;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.baidumap.LocationUtils;
import com.hentica.appbase.famework.adapter.QuickAdapter;

import java.util.List;

/**
 * 我的收藏界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineCollectFragment extends CommonPtrFragment<ResMineCollect> implements PtrView<ResMineCollect> {

    private PtrPresenter mPtrPresenter;

    @Override
    protected String getFragmentTitle() {
        return "我的收藏";
    }

    @Override
    public QuickAdapter createAdapter() {
        return new CollectAdapter();
    }

    @Override
    protected void initData() {
        super.initData();
        mPtrPresenter = new CollectPtrPresenter(this);
        mPtrPresenter.onRefresh();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ResMineCollect item = getItem(position - 1);
        if(item == null){
            return;
        }
        BDLocation location = LocationUtils.getInstance().getLocation();
        String latitude = null;
        String longitude = null;
        if(location != null){
            latitude = location.getLatitude()+"";
            longitude = location.getLongitude()+"";
        }
        FragmentJumpUtil.toBusinessDetail(getUsualFragment(), String.valueOf(item.getId()));
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
    public void setListDatas(List<ResMineCollect> datas) {
        super.setDatas(datas);
    }

    @Override
    public void addListDatas(List<ResMineCollect> datas) {
        super.addDatas(datas);
    }
}
