package com.hentica.app.module.mine.ui.textcontent;

import com.hentica.app.module.entity.ConfigKey;
import com.hentica.app.module.mine.presenter.TextContentPresenter;
import com.hentica.app.module.mine.presenter.TextContentPresenterImpl;
import com.hentica.app.module.mine.ui.MineTextContentFragment;
import com.hentica.app.widget.view.TitleView;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.11:53
 */

public class BankCardServiceAgreementFragment extends MineTextContentFragment {
    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setTitleText("");
    }

    @Override
    protected TextContentPresenter getPresenter() {
        return new TextContentPresenterImpl(ConfigKey.BANKCARD_SERVICE_AGREEMENT, this);
    }
}
