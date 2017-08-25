package com.hentica.app.module.mine.ui.shop;

import android.content.Intent;
import android.view.View;

import com.hentica.app.framework.fragment.ptrscrollviewcontainer.AbsContainerSubFragment;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/5.20:38
 */

public class MineSettleFailureFargment extends AbsContainerSubFragment<ResUserProfile> {

    public static final String DATA_APPLY_ID = "applyId";

    private String mApplyId = "";

    @Override
    public int getLayoutId() {
        return R.layout.mine_settle_failure_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setVisibility(View.GONE);
    }

    @Override
    protected void handleIntentData(Intent intent) {
        super.handleIntentData(intent);
        mApplyId = intent.getStringExtra(DATA_APPLY_ID);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }



    @Override
    protected void setEvent() {
        //重新入驻
        getViews(R.id.mine_failure_btn_resettle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MineSettledBusinessActivity.class);
                intent.putExtra(MineSettledBusinessActivity.DATE_APPLY_ID, mApplyId);
                startActivity(intent);
                getContainerFragment().finish();
            }
        });
    }

}
