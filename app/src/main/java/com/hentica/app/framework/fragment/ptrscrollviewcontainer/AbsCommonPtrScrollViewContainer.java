package com.hentica.app.framework.fragment.ptrscrollviewcontainer;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.listener.CallbackAdapter;
import com.hentica.app.widget.view.TitleView;
import com.hentica.appbase.famework.util.ListUtils;
import com.fiveixlg.app.customer.R;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 常用带下拉刷新ScrollView，且不同状态显示不同子界面
 * Created by Snow on 2017/2/7.
 */

public abstract class AbsCommonPtrScrollViewContainer<T> extends BaseFragment {

    @BindView(R.id.common_base_ptr_scv)
    PullToRefreshScrollView mPtrScv;//可以下拉ScrollView;

    //所有加入子界面
    private Map<Class<? extends AbsContainerSubFragment>, AbsContainerSubFragment<T>> mSubFragments = new HashMap<>();

    private DataLoadPresenter<T> mLoader;

    @Override
    final public int getLayoutId() {
        return R.layout.common_base_ptr_scrollview_container;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    final protected TitleView initTitleView() {
        return (TitleView) getView().findViewById(R.id.common_title);
    }

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setTitleText(getFragmentTitle());
    }

    /** 获取界面标题 */
    protected abstract String getFragmentTitle();

    @Override
    protected void initData() {
        mLoader = createDataLoadPresenter();
        loadDetailData();
    }

    /** 创建加载数据控制器 */
    protected abstract DataLoadPresenter<T> createDataLoadPresenter();

    /** 获得数据加载控制器 */
    protected final DataLoadPresenter<T> getDataLoadPresenter(){
        return this.mLoader;
    }

    /**
     * 获取详细数据
     * 需要子类实现
     */
    public final void loadDetailData(){
        if(getDataLoadPresenter() != null){
            //加载数据
            //2017/2/7 请求参数
            getDataLoadPresenter().loadData(new CallbackAdapter<T>() {
                @Override
                public void callback(boolean isSuccess, T data) {
                    if(isSuccess && data != null){
                        setDetailData(data);
                    }
                }
            }, loadDetailDataParams());
        }
    }

    /** 获取数据的参数 */
    protected abstract String[] loadDetailDataParams();

    @Override
    protected void initView(){

    }

    public final void setTitle(String title){
        if(getTitleView() != null){
            getTitleView().setTitleText(title);
        }
    }

    @Override
    protected void setEvent() {
        mPtrScv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                loadDetailData();
            }
        });
    }

    /**
     *  设置详细数据数据
     * @param data
     */
    protected void setDetailData(T data){
        mPtrScv.onRefreshComplete();
        addSubFragmentByData(data);
    }

    /**
     * 根据数据加载子界面
     * 需要子类实现
     * @param data
     */
    protected final void addSubFragmentByData(T data){
        Class clazz = getSubFragmentClass(data);
        if(clazz == null){
            return;
        }
        AbsContainerSubFragment fragment = addSubFragment(clazz, data);
        configSubFragemnt(fragment);
    }

    /** 获取子界面 */
    @Nullable
    protected abstract Class getSubFragmentClass(T data);

    /** 设置子界面 */
    protected void configSubFragemnt(AbsContainerSubFragment fragment ){

    }

    public final AbsContainerSubFragment addSubFragment(Class<? extends AbsContainerSubFragment<T>> clazz, T data){
        /** 获取子界面 */
        List<AbsContainerSubFragment<T>> subFragments = getSubFragments();
        AbsContainerSubFragment result  = null;
        if(ListUtils.isEmpty(subFragments)) {
            //创建新的子界面
            result = createSubFragment(clazz);
            addSubFragment(result);
            result.setData(data);
            return result;
        }
        //不为空——找到目标子界面
        for(AbsContainerSubFragment<T> subFragment : subFragments){
            if(subFragment.getClass() == clazz){
                addSubFragment(subFragment);
                subFragment.setData(data);
                return subFragment;
            }
        }
        result = createSubFragment(clazz);
        addSubFragment(result);
        result.setData(data);
        return result;
    }

    /** 创建新的子界面 */
    @Nullable
    private AbsContainerSubFragment<T> createSubFragment(Class<? extends AbsContainerSubFragment<T>> clazz){
        AbsContainerSubFragment<T> fragment = null;
        try {
            //获取构造方法，创建新实例
            fragment = clazz.getConstructor()
                    .newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    /** 添加一个子界面 */
    public final void addSubFragment(AbsContainerSubFragment<T> fragment){
        if(fragment == null){
            return;
        }
        fragment.setParent(this);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        //隐藏子界面
        hideSubFragment(ft);
        //子界面未添加，添加并显示
        if(!fragment.isAdded()){
            addFragment(ft, fragment);
        }
        //显示子界面
        showFragment(ft, fragment);
        //提交事务
        ft.commitAllowingStateLoss();
    }

    /** 添加一个子界面 */
    private FragmentTransaction addFragment(FragmentTransaction ft, AbsContainerSubFragment<T> fragment){
        if(ft == null) {
            ft = getChildFragmentManager().beginTransaction();
        }
        ft.add(R.id.common_base_container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .hide(fragment);
        mSubFragments.put(fragment.getClass(), fragment);
        return ft;
    }

    /** 显示某个子界面 */
    private FragmentTransaction showFragment(FragmentTransaction ft, AbsContainerSubFragment<T> fragment){
        if(ft == null) {
            ft = getChildFragmentManager().beginTransaction();
        }
        ft.show(fragment);
        return ft;
    }

    /** 隐藏所有子界面 */
    private FragmentTransaction hideSubFragment(FragmentTransaction ft){
        if(ft == null){
            ft = getChildFragmentManager().beginTransaction();
        }
        Iterator<Class<? extends AbsContainerSubFragment>> it = mSubFragments.keySet().iterator();
        while(it.hasNext()){
            Class<? extends AbsContainerSubFragment> key = it.next();
            AbsContainerSubFragment<T> fragment = mSubFragments.get(key);
            if(fragment.isAdded()){
                ft.hide(fragment);
            }
        }
        return ft;
    }

    /** 获取添加的子界面 */
    protected List<AbsContainerSubFragment<T>> getSubFragments(){
        List<AbsContainerSubFragment<T>> result = new ArrayList<>();
        Iterator<Class<? extends AbsContainerSubFragment>> it = mSubFragments.keySet().iterator();
        AbsContainerSubFragment<T> sub = null;
        while(it.hasNext()){
            Class<? extends AbsContainerSubFragment> key= it.next();
            sub = mSubFragments.get(key);
            if(sub != null){
                result.add(sub);
            }
        }
        return result;
    }

    /** 隐藏fragment */
    public final void removeFragment(){
        if(getChildFragmentManager().getBackStackEntryCount() > 0){
            getChildFragmentManager().popBackStack();
        }else{
            finish();
        }
    }

    /**
     * 设置底部操作栏是否显示
     * @param visiable true：显示， false：隐藏
     */
    protected final void setBottomBarVisiable(boolean visiable){
        mQuery.id(R.id.common_base_bottom).visibility(visiable ? View.VISIBLE : View.GONE);
    }

    /**
     * 添加询问操作栏
     * @param fragment
     */
    protected final void setBottomBarFragment(Fragment fragment){
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.common_base_bottom, fragment).commit();
    }
}
