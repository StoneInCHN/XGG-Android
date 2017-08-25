package com.hentica.app.framework.fragment.ptr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.util.ListViewUtils;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

import java.util.List;

/**
 * PullToRefreshFragment
 * 一般带下拉刷新的fragment界面
 * Created by Snow on 2016/12/21.
 */

public abstract class AbsPtrFragment<T> extends UsualFragment implements PullToRefreshBase.OnRefreshListener2<ListView>, AdapterView.OnItemClickListener {

//    CustomPtrListView mPtrLv;

    protected PullToRefreshListView mPtrLv;

    private PullToRefreshBase.OnRefreshListener2<ListView> mOnRefreshListener;

    private AdapterView.OnItemClickListener mItemClickListener;

    private QuickAdapter<T> mAdapter;

    /** 设置刷新事件 */
    public void setOnRefreshListener(PullToRefreshBase.OnRefreshListener2<ListView> l){
        mOnRefreshListener = l;
    }

    /** 设置列表点击事件 */
    public void setOnItemClickListener(AdapterView.OnItemClickListener l){
        mItemClickListener = l;
    }

    public QuickAdapter<T> getAdpater(){
        return mAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_abs_ptr_fragment, container, false);
        initView(view);
        return view;
    }

    protected void initView(View view){
        mPtrLv = (PullToRefreshListView) view.findViewById(R.id.common_ptr_lv);
        ListViewUtils.setDefaultEmpty(mPtrLv.getRefreshableView());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = createListViewAdapter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initData();
        this.handleView();
        this.setEvent();
    }

    protected void initData(){

    }

    /** 创建列表数据适配器 */
    protected abstract QuickAdapter<T> createListViewAdapter();

    protected void handleView(){
        mPtrLv.setAdapter(mAdapter);
    }

    protected void setEvent(){
        mPtrLv.setOnRefreshListener(this);
        mPtrLv.setOnItemClickListener(this);
    }

    /** 设置数据 */
    public void setDatas(List<T> datas){
        if(mAdapter != null){
            mAdapter.setDatas(datas);
        }
        onRefreshComplete();
    }

    public void onRefreshComplete(){
        mPtrLv.onRefreshComplete();
    }

    /** 添加数据 */
    public void addDatas(List<T> datas){
        if(mAdapter != null){
            mAdapter.addAll(datas);
        }
        onRefreshComplete();
    }

    /** 获取列表中的指定项数据 */
    @Nullable
    public T getItem(int position){
        if(position < 0 || position >= mAdapter.getCount()){
            return null;
        }
        return mAdapter.getItem(position);
    }

    /** 获取现在数据数量 */
    public int getListSize(){
        return mAdapter.getCount();
    }

    @Override
    public void onStart() {
        super.onStart();
//        mPtrLv.autoPullDown();
    }

    public PullToRefreshListView getPtrListView(){
        return mPtrLv;
    }

}
