package com.hentica.app.module.index.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.lib.util.TextGetter;
import com.hentica.app.widget.CharacterHeadHelper2;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

/**
 * Created by YangChen on 2017/4/5 11:27.
 * E-mail:656762935@qq.com
 */

public class SlideAdapter<T> extends QuickAdapter<T> {

    private CharacterHeadHelper2 mHeaderHelper;

    public SlideAdapter(CharacterHeadHelper2 headerHelper){
        mHeaderHelper = headerHelper;
    }

    @Override
    protected int getLayoutRes(int type) {
        return R.layout.index_select_city_item;
    }

    @Override
    protected void fillView(int position, View convertView, ViewGroup parent, T data) {
        AQuery query = new AQuery(convertView);
        LinearLayout headerLayout = (LinearLayout) query.id(R.id.index_select_city_item_layout_header).getView();
        TextView headerTv = query.id(R.id.index_select_city_item_tv_header).getTextView();
        TextView nameTv = query.id(R.id.index_select_city_item_tv_name).getTextView();
        if(mHeaderHelper != null){
            TextGetter textGetter = mHeaderHelper.getTextGetter();
            CharacterHeadHelper2.LetterGetter letterGetter = mHeaderHelper.getLetterGetter();
            if(textGetter != null){
                nameTv.setText(textGetter.getText(data));
            }
            if(letterGetter != null){
                headerTv.setText(letterGetter.getLetter(data)+"");
            }
            mHeaderHelper.refreshLetterView(position,headerTv,headerLayout);
        }
        convertView.setTag(data);
    }
}
