package com.hentica.app.widget.wheel;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hentica.app.lib.util.TextGetter;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2017/2/14.
 */

public class CommonWheelDialog<T> extends DialogFragment {

    private View mRootView;

    private int mWheelCount = 0;//滚轮数量

    private boolean isLinked = false;//是否联动

    private List<TextGetter<T>> mGetters = new ArrayList<>();

    private List<DatasGetter<T>> mDataGetters = new ArrayList<>();

    public CommonWheelDialog(){
        this(1);
    }

    /**
     *
     * @param wheelCount 滚轮数据，>0有效
     */
    public CommonWheelDialog(int wheelCount){
        this(wheelCount, false);
    }

    /**
     *
     * @param wheelCount
     * @param isLinked
     */
    public CommonWheelDialog(int wheelCount, boolean isLinked){
        super();
        this.mWheelCount = wheelCount;
        this.isLinked = isLinked;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.common_dialog_take_addr, container, false);
        return mRootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.full_screen_dialog);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initData();
        this.initView();
        this.setEvent();
    }

    private void initData(){

    }

    private void initView(){

    }

    private void setEvent(){

    }
}
