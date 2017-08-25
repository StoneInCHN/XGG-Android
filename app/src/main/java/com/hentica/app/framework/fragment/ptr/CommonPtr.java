package com.hentica.app.framework.fragment.ptr;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.fragment.CommonPtrFragment;
import com.hentica.appbase.famework.adapter.QuickAdapter;

/**
 * Created by Snow on 2017/5/13.
 */

public class CommonPtr<T> extends AbsPtrFragment<T> {

    private CommonPtrFragment mCommonPtrFragment;

    public void setCommonPtrFragment(CommonPtrFragment fragment){
        this.mCommonPtrFragment = fragment;
    }

    @Override
    protected QuickAdapter<T> createListViewAdapter() {
        if(mCommonPtrFragment != null){
            return mCommonPtrFragment.createAdapter();
        }
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mCommonPtrFragment != null){
            mCommonPtrFragment.onItemClick(parent, view, position, id);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        if(mCommonPtrFragment != null){
            mCommonPtrFragment.onPullDownToRefresh(refreshView);
        }
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if(mCommonPtrFragment != null){
            mCommonPtrFragment.onPullUpToRefresh(refreshView);
        }
    }
}
