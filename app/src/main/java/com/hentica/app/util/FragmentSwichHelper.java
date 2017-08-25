package com.hentica.app.util;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.hentica.app.framework.fragment.UsualFragment;
import com.litesuits.orm.db.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YangChen on 2017/3/9 13:50.
 * E-mail:656762935@qq.com
 */

public class FragmentSwichHelper {
    /** 对应的界面 */
    @NotNull
    private UsualFragment mMainFragment;
    /** 真正的单选布局id */
    @NotNull
    private RadioGroup mRealNavBar;
    /** 标题单选布局 */
    @Nullable
    private RadioGroup mTitleNavBar;
    /** 内容装载布局id */
    private int mContentLayoutId;
    /** 所有的界面信息 */
    private List<SwichFragmentInfo> mAllChildFragmentInfos = new ArrayList<>();
    /** 当前界面信息 */
    private SwichFragmentInfo mShowedFragmentInfo;
    /** 选中项改变事件 */
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener;

    /** 界面信息 */
    public static class SwichFragmentInfo{
        /** 对应的界面 */
        public UsualFragment mFragment;
        /** 对应的界面id */
        public int mRadioId;
        /** 对应标题栏的id */
        public int mTitleRadioId;
        /** 界面标识 */
        public String mTag;

        public SwichFragmentInfo(UsualFragment fragment, int radioId, String tag){
            this(fragment,radioId,-1,tag);
        }

        public SwichFragmentInfo(UsualFragment fragment, int radioId, int titleRadioId, String tag){
            mFragment = fragment;
            mRadioId = radioId;
            mTitleRadioId = titleRadioId;
            mTag = tag;
        }
    }

    public FragmentSwichHelper(UsualFragment mainFragment, RadioGroup realNavBar, int contentLayoutId){
        this(mainFragment,realNavBar,null,contentLayoutId);
    }

    public FragmentSwichHelper(UsualFragment mainFragment, RadioGroup realNavBar, RadioGroup titleNavBar, int contentLayoutId){
        mMainFragment = mainFragment;
        mRealNavBar = realNavBar;
        mTitleNavBar = titleNavBar;
        mContentLayoutId = contentLayoutId;
    }

    /** 添加一个子界面 */
    public void addFragmentInfo(SwichFragmentInfo fragmentInfo){
        mAllChildFragmentInfos.add(fragmentInfo);
    }

    /** 创建所有子界面 */
    public void createFragments(){
        initView();
        setEvent();
    }

    /**
     * 按键事件，由fragment传递进来
     *
     * @param keyCode
     *            按键码
     * @param event
     *            事件
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 传递给当前子fragment
        if (mShowedFragmentInfo != null && mShowedFragmentInfo.mFragment != null
                && mShowedFragmentInfo.mFragment.onKeyDown(keyCode, event)) {

            return true;
        }
        return false;
    }

    private void initView(){
        // 注册事件
        for (SwichFragmentInfo fragmentInfo : mAllChildFragmentInfos) {

            mMainFragment.registerTouchListener(fragmentInfo.mFragment);
        }
        // 默认显示第一个
        mShowedFragmentInfo = mAllChildFragmentInfos.get(0);
        switchToTab(mShowedFragmentInfo.mRadioId,mShowedFragmentInfo.mTitleRadioId);
    }

    private void setEvent(){
        mRealNavBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int index = getIndexByRadioId(checkedId);
                int titleRadioId = mAllChildFragmentInfos.get(index).mTitleRadioId;
                switchToTab(checkedId,titleRadioId);
            }
        });
    }

    /** 设置当前显示第几页 */
    public void setCurrIndex(int index){
        if(index >= 0 && index < mAllChildFragmentInfos.size()){
            // 下标合法
            int radioId = mAllChildFragmentInfos.get(index).mRadioId;
            int titleRadio = mAllChildFragmentInfos.get(index).mTitleRadioId;
            switchToTab(radioId,titleRadio);
        }

    }

    /** 切换视图到对应界面 */
    private void switchToTab(int radioId,int titleRadioId){
        // radioId 对应下标
        int index = getIndexByRadioId(radioId);
        if(index < 0 || index >= mAllChildFragmentInfos.size()){
            // 下标不合法
            return;
        }
        mRealNavBar.check(radioId);
        if(mTitleNavBar != null){
            mTitleNavBar.check(titleRadioId);
        }
        FragmentManager fm = mMainFragment.getChildFragmentManager();
        FragmentTransaction fts = fm.beginTransaction();
        // 清空之前显示的界面
        //clearViews(fts);
        // 添加当前要显示的界面
        mShowedFragmentInfo = mAllChildFragmentInfos.get(index);
        fts.replace(mContentLayoutId,mShowedFragmentInfo.mFragment,mShowedFragmentInfo.mTag);
        fts.commit();

        // 发出事件
        if (mOnCheckedChangeListener != null) {

            mOnCheckedChangeListener.onCheckedChanged(mRealNavBar, radioId);
        }
    }

    /** 清空显示的界面，避免出现重叠 */
    private void clearViews(FragmentTransaction fts){
        for(SwichFragmentInfo fragmentInfo : mAllChildFragmentInfos){
            if(fragmentInfo.mFragment != null){
                fts.remove(fragmentInfo.mFragment);
            }
        }
        fts.commit();
    }

    /** 根据id查找界面对应下标 */
    private int getIndexByRadioId(int radioId){
        int index = -1;
        for(int i = 0; i < mAllChildFragmentInfos.size(); i++){
            SwichFragmentInfo fragmentInfo = mAllChildFragmentInfos.get(i);
            if(fragmentInfo.mRadioId == radioId){
                index = i;
            }
        }
        return index;
    }

    public void setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }
}
