package com.hentica.app.util;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 辅助工具，用于方便地绑定视图
 */
public class ViewBinder {

    /**
     * 绑定视图，返回最终视图
     */
    public static View bindFragmentView(Fragment fragment, int layoutId, LayoutInflater inflater, @Nullable ViewGroup container) {

        View rootView = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(fragment, rootView);
        return rootView;
    }
}
