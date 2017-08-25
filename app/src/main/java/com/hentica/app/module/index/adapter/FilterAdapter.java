package com.hentica.app.module.index.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.hentica.app.module.index.impl.DataGetter;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

/**
 * Created by YangChen on 2017/4/5 21:22.
 * E-mail:656762935@qq.com
 */

/** 列表适配器 */
public class FilterAdapter<T> extends QuickAdapter<T>{

    private DataGetter<T> mDataGetter;
    private String mDefaultVal;

    public FilterAdapter(DataGetter dataGetter){
        mDataGetter = dataGetter;
    }

    @Override
    protected int getLayoutRes(int type) {
        return R.layout.list_filter_dialog_item;
    }

    @Override
    protected void fillView(int position, View convertView, ViewGroup parent, T data) {
        AQuery query = new AQuery(convertView);
        if(mDataGetter != null){
            boolean isChecked = TextUtils.equals(mDefaultVal,mDataGetter.getValue(data));
            query.id(R.id.list_filter_dialog_item_check).text(mDataGetter.getText(data)).checked(isChecked);
            query.id(R .id.list_filter_dialog_item_check_tip).visibility(isChecked ? View.VISIBLE : View.GONE);
            convertView.setTag(R.id.tagOfNameId,mDataGetter.getText(data));
            convertView.setTag(R.id.tagOfValueId,mDataGetter.getValue(data));
            convertView.setTag(R.id.tagOfDataId,data);
        }
    }

    public void setDataGetter(DataGetter<T> mDataGetter) {
        this.mDataGetter = mDataGetter;
    }

    public void setDefaultVal(String mDefaultVal) {
        this.mDefaultVal = mDefaultVal;
    }

    public String getDefaultVal() {
        return mDefaultVal;
    }
}