package com.hentica.app.module.mine.ui.textcontent;

import com.hentica.app.module.entity.ConfigKey;
import com.hentica.app.module.mine.presenter.TextContentPresenter;
import com.hentica.app.module.mine.presenter.TextContentPresenterImpl;
import com.hentica.app.module.mine.ui.MineTextContentFragment;
import com.hentica.app.widget.view.TitleView;

/**
 * Created by YangChen on 2017/6/15 20:57.
 * E-mail:656762935@qq.com
 */

public class ExplainFragment extends MineTextContentFragment {
    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setTitleText("说明");
    }

    @Override
    protected TextContentPresenter getPresenter() {
        return new TextContentPresenterImpl(ConfigKey.SELLER_PAYMENT_DESC, this);
    }
}
