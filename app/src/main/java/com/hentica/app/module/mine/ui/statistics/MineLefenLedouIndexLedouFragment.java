package com.hentica.app.module.mine.ui.statistics;

import com.hentica.app.widget.view.TabTitle;

/**
 * 乐分乐豆界面，默认显示乐豆
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineLefenLedouIndexLedouFragment extends MineLefenLedouFragment {

    @Override
    protected void configTabTitle(TabTitle tabTitle) {
        super.configTabTitle(tabTitle);
        tabTitle.setDefaultIndex(1);
    }
}
