package com.hentica.app.module.mine.ui;

import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

/**
 * 回复评价界面
 *
 * @author 
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineReplyFragment extends BaseFragment {

        @Override
        public int getLayoutId() {
            return R.layout.mine_reply_fragment;
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
