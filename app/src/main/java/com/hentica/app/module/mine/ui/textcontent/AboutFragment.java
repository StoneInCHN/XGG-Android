package com.hentica.app.module.mine.ui.textcontent;

import com.hentica.app.module.entity.ConfigKey;
import com.hentica.app.module.mine.presenter.TextContentPresenter;
import com.hentica.app.module.mine.presenter.TextContentPresenterImpl;
import com.hentica.app.module.mine.ui.MineTextContentFragment;
import com.hentica.app.widget.view.TitleView;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.11:51
 */

public class AboutFragment extends MineTextContentFragment {

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setTitleText("关于");
    }

    @Override
    protected TextContentPresenter getPresenter() {
        return new TextContentPresenterImpl(ConfigKey.ABOUT, this);
    }
}
