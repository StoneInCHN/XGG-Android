package com.hentica.app.module.mine.ui;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;

import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.mine.presenter.TextContentPresenter;
import com.hentica.app.module.mine.presenter.TextContentPresenterImpl;
import com.hentica.app.module.mine.view.TextContentView;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

/**
 * 一般文本内容界面
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.10:54
 */

public class MineTextContentFragment extends BaseFragment implements TextContentView {

    public static final String DATA_TITLE = "title";
    public static final String DATA_CONFIG_KEY = "configKey";

    private TextContentPresenter mPresenter;

    private String mTitle = "";
    private String mConfigKey = "";

    @Override
    public int getLayoutId() {
        return R.layout.mine_text_content_fragment;
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
        mTitle = intent.getStringExtra(DATA_TITLE);
        mConfigKey = intent.getStringExtra(DATA_CONFIG_KEY);
    }

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.setTitleText(mTitle);
    }

    @Override
    protected void initData() {
        mPresenter = getPresenter();
        if(mPresenter != null){
            mPresenter.getContent();
        }
    }

    //获取内容Presenter
    protected TextContentPresenter getPresenter(){
        return new TextContentPresenterImpl(mConfigKey, this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void setContent(String content) {
        setViewText(Html.fromHtml(content), R.id.content_tv_text);
        if(!TextUtils.isEmpty(content)){
            setViewVisiable(true , R.id.content_tv_text);
            setViewVisiable(false , R.id.content_empty_view);
        }
    }
}
