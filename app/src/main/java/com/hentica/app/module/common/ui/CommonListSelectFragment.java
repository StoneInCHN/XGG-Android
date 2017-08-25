package com.hentica.app.module.common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.util.ListViewUtils;
import com.hentica.appbase.famework.adapter.QuickAdapter;

import java.util.List;

/**
 * Created by Snow on 2016/12/8.
 */

public abstract class CommonListSelectFragment<T> extends BaseFragment {

    AbsListView mLvCorp;

    QuickAdapter<T> mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter.setDatas(getAdapterDatas());
    }



    @Override
    protected void initData() {
        mAdapter = createListAdapter();
        mLvCorp = getListView();
        mLvCorp.setAdapter(mAdapter);
        ListViewUtils.setDefaultEmpty(mLvCorp);
    }

    protected abstract AbsListView getListView();

    /** 创建Adpater */
    protected abstract QuickAdapter<T> createListAdapter();

    /** 获取adatper数据 */
    protected abstract List<T> getAdapterDatas();

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {
        mLvCorp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mAdapter != null) {
                    onListItemClick(mAdapter.getItem(position), position);
                }
            }
        });
    }

    protected abstract void onListItemClick(T data, int position);

    /**
     * 设置Adpater数据
     * @param datas
     */
    protected void setAdapterDatas(List<T> datas){
        mAdapter.setDatas(datas);
    }


}
