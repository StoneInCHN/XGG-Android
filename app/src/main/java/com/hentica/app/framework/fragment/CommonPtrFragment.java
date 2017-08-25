package com.hentica.app.framework.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hentica.app.framework.fragment.ptr.AbsPtrFragment;
import com.hentica.app.framework.fragment.ptr.CommonPtr;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.widget.view.TitleView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

import java.util.List;

/**
 * Created by Snow on 2016/12/21.
 */
public abstract class CommonPtrFragment<T> extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ListView>, AdapterView.OnItemClickListener {
    private AbsPtrFragment<T> mPtrFragment;

    @Override
    public int getLayoutId() {
        return R.layout.common_ptr_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return (TitleView) getView().findViewById(R.id.common_title);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setTitleText(getFragmentTitle());
    }

    /** 获取界面标题 */
    protected abstract String getFragmentTitle();

    @Override
    protected void initView() {
        mPtrFragment = createListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.mine_appointment_adviser_content, mPtrFragment, mPtrFragment.getClass().getName())
                .commit();
    }

    /** 创建一个带下拉刷新的界面 */
    protected AbsPtrFragment<T> createListFragment(){
        CommonPtr<T> fragment = new CommonPtr<>();
        fragment.setCommonPtrFragment(this);
        return fragment;
    }

    /** 创建Adpater */
    public abstract QuickAdapter<T> createAdapter();

    @Override
    protected void setEvent() {
    }

    public AbsPtrFragment getPtrFragment(){
        return mPtrFragment;
    }

    public PullToRefreshListView getPtrListView(){
        return mPtrFragment.getPtrListView();
    }

    /** 设置数据 */
    public void setDatas(List<T> datas){
        mPtrFragment.setDatas(datas);
    }

    /** 添加数据 */
    public void addDatas(List<T> datas){
        mPtrFragment.addDatas(datas);
    }

    /** 获取列表现在数据大小 */
    public int getListSize(){
        return mPtrFragment.getListSize();
    }

    public QuickAdapter<T> getAdapter(){
        return mPtrFragment.getAdpater();
    }

    /** 获取数据 */
    @Nullable
    public T getItem(int position){
        T t = null;
        if(mPtrFragment != null){
            t = mPtrFragment.getItem(position);
        }
        return t;
    }

    @Override
    public void dismissLoadingDialog() {
        super.dismissLoadingDialog();
        if(mPtrFragment != null) {
            mPtrFragment.onRefreshComplete();
        }
    }
}
