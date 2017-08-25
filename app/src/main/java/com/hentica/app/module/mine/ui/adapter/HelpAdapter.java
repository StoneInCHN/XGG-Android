package com.hentica.app.module.mine.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hentica.app.module.entity.mine.ResMineHelpItem;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/27.17:32
 */

public class HelpAdapter extends QuickAdapter<ResMineHelpItem> {
    @Override
    protected int getLayoutRes(int type) {
        return R.layout.mine_help_item;
    }

    @Override
    protected void fillView(int position, View convertView, ViewGroup parent, ResMineHelpItem data) {
        TextView tvName = (TextView) convertView.findViewById(R.id.help_item_tv_title);
        tvName.setText(data.getTitle());
    }
}
