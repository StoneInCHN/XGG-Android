package com.hentica.app.framework.fragment.ptrscrollviewcontainer;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hentica.app.module.common.base.BaseFragment;

/**
 * Container的子界面
 * Created by Snow on 2017/2/7.
 */

public abstract class AbsContainerSubFragment<T> extends BaseFragment {

    protected String TAG = getClass().getSimpleName();
    //父容器
    private AbsCommonPtrScrollViewContainer<T> mParentFragment;

    private T mData;

    /** 设置父容器 */
    public void setParent(AbsCommonPtrScrollViewContainer<T> fragment){
        mParentFragment = fragment;
    }

    /** 设置数据 */
    public void setData(T data){
        this.mData = data;
        refreshUI(mData);
    }

    public T getData(){
        return this.mData;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        refreshUI(mData);
    }

    @Override
    protected void setEvent() {

    }

    /**
     * 根据数据显示界面
     * 已做空判断
     */
    protected void refreshUI(T data){
        if(data == null){
            return;
        }
    }

    /**
     * 添加显示子界面
     * @param clazz
     */
    @Nullable
    protected AbsContainerSubFragment addSubFragment(Class<? extends AbsContainerSubFragment<T>> clazz){
        if(mParentFragment != null){
            return mParentFragment.addSubFragment(clazz, mData);
        }
        return null;
    }

    /** 设置父界面标题 */
    protected void setParentFragmentTitle(String title){
        if(!TextUtils.isEmpty(title) && mParentFragment != null) {
            mParentFragment.setTitle(title);
        }
    }

    protected final void reloadData() {
        if (mParentFragment != null) {
            mParentFragment.loadDetailData();
        }
    }

    protected AbsCommonPtrScrollViewContainer getContainerFragment(){
        return mParentFragment;
    }
}
