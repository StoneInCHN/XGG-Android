package com.hentica.app.module.mine.ui.shop;

import com.hentica.app.framework.fragment.ptrscrollviewcontainer.AbsContainerSubFragment;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

/**
 * 入驻审核中界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineSettleCheckingFragment extends AbsContainerSubFragment<ResUserProfile> {

    @Override
    public int getLayoutId() {
        return R.layout.mine_settle_checking_fragment;
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
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {

    }

}
