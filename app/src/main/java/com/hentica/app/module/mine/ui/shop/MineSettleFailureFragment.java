package com.hentica.app.module.mine.ui.shop;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.hentica.app.framework.fragment.ptrscrollviewcontainer.AbsContainerSubFragment;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/5.20:38
 */

public class MineSettleFailureFragment extends AbsContainerSubFragment<ResUserProfile> {

    public static final String DATA_APPLY_ID = "applyId";
    public static final String DATA_REASON = "reason";
    public static final String DATA_MOBILE = "mobile";

    private String mApplyId = "";
    private String mReason = "";
    private String mMobile = "";
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
    protected void handleIntentData(Intent intent) {
        super.handleIntentData(intent);
        mApplyId = intent.getStringExtra(DATA_APPLY_ID);
        mReason = intent.getStringExtra(DATA_REASON);
        mMobile = intent.getStringExtra(DATA_MOBILE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setViewText("失败原因："+mReason, R.id.tv_reason);
        setViewVisiable(!TextUtils.isEmpty(mReason), R.id.tv_reason);
    }



    @Override
    protected void setEvent() {
        //重新入驻
        getViews(R.id.mine_failure_btn_resettle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MineSettledBusinessActivity.class);
                intent.putExtra(MineSettledBusinessActivity.DATE_APPLY_ID, mApplyId);
                intent.putExtra(MineSettledBusinessActivity.DATE_MOBILE, mMobile);
                startActivity(intent);
                finish();
            }
        });
    }

}
