package com.hentica.app.module.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.hentica.app.module.mine.ui.statistics.MineStatisticsListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YangChen on 2017/3/27 14:48.
 * E-mail:656762935@qq.com
 */

public class CommonSlideAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private List<T> mFragments = new ArrayList<>();

    public CommonSlideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public T getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }


    /** 设置显示界面 */
    public void setFragments(List<T> fragments){
        // 清空之前的fragment
        mFragments.clear();
        if(fragments != null && !fragments.isEmpty()){
            mFragments.addAll(fragments);
        }
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 覆盖销毁函数，不执行销毁操作
    }
}
