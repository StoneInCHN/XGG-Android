package com.hentica.app.module.mine.ui.textcontent;

import android.content.Intent;

import com.hentica.app.module.mine.presenter.HelpTextContentPresenterImpl;
import com.hentica.app.module.mine.presenter.TextContentPresenter;
import com.hentica.app.module.mine.ui.MineTextContentFragment;
import com.hentica.app.widget.view.TitleView;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.11:53
 */

public class HelpFragment extends MineTextContentFragment {

    public static final String DATA_HELP_ID = "id";
    public static final String DATA_HELP_TITLE = "title";

    private int mId;
    private String mTitle;

    @Override
    protected void handleIntentData(Intent intent) {
        super.handleIntentData(intent);
        mId  = intent.getIntExtra(DATA_HELP_ID, mId);
        mTitle  = intent.getStringExtra(DATA_HELP_TITLE);
    }

    @Override
    protected TextContentPresenter getPresenter() {
        return new HelpTextContentPresenterImpl(String.valueOf(mId), this);
    }

    @Override
    protected void initView() {
        TitleView title = getTitleView();
        if(title != null){
            title.setTitleText(mTitle);
        }
    }
}
