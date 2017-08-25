package com.hentica.app.module.common.ui;

import android.util.Log;
import android.widget.RadioGroup;

import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;


/**
 * 测试界面仅供测试用
 * Created by Snow on 2016/12/14.
 */

public class TestFragment extends BaseFragment {

    RadioGroup mRg;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    protected TitleView initTitleView() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mRg = getViews(R.id.rg);
    }

    @Override
    protected void setEvent() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "onCheckedChanged: " + checkedId);
            }
        });
    }



}
