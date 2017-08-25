package com.hentica.app.util;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.fiveixlg.app.customer.R;


/**
 * Created by kezhong.
 * E-Mail:396926020@qq.com
 * on 2016/10/22 15:44
 */

public class ListViewUtils {

    /**
     * 给ListView设置一个默认的空界面
     * @param listView
     * @return
     *          空界面
     */
    public static View setDefaultEmpty(AbsListView listView){
        return setEmptyView(listView, R.layout.common_empty_view);
    }

    /**
     * 给ListView设置ListView内容为空时显示的界面
     * @param listView
     *          要设置的ListView
     * @param emptyViewId
     *          空内容的界面的id
     * @return
     *          空界面
     */
    public static View setEmptyView(AbsListView listView, @LayoutRes int emptyViewId){
        if(listView == null || emptyViewId == 0){
            return null;
        }
        View emptyView = View.inflate(listView.getContext(), emptyViewId, null);
        return setEmptyView(listView, emptyView);
    }

    /**
     * 给ListView设置ListView内容为空时显示的界面
     * @param listView
     *          要设置的ListView
     * @param emptyView
     *          空内容的View
     * @return
     *          空界面
     */
    public static View setEmptyView(AbsListView listView, View emptyView){
        if(listView == null || emptyView == null){
            return null;
        }
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ((ViewGroup)listView.getParent()).addView(emptyView, lp);
        listView.setEmptyView(emptyView);
        return emptyView;
    }
}
