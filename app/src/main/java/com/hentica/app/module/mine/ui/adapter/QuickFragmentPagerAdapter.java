package com.hentica.app.module.mine.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/24.16:59
 */

public class QuickFragmentPagerAdapter <T extends Fragment> extends FragmentStatePagerAdapter {

    private List<T> mFragments = new ArrayList<>();

    public QuickFragmentPagerAdapter(FragmentManager fm) {
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

    public void setFragments(List<T> fragments){
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
