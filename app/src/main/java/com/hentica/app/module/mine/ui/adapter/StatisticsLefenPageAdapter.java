package com.hentica.app.module.mine.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.hentica.app.module.mine.ui.statistics.MineStatisticsListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/24.16:59
 */

public class StatisticsLefenPageAdapter extends FragmentStatePagerAdapter {

    private List<MineStatisticsListFragment> mFragments = new ArrayList<>();

    public StatisticsLefenPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public MineStatisticsListFragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void setFragments(List<MineStatisticsListFragment> fragments){
        mFragments.clear();
        if(fragments != null && !fragments.isEmpty()){
            mFragments.addAll(fragments);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
