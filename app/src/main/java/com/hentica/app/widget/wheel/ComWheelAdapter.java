package com.hentica.app.widget.wheel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.hentica.app.lib.util.TextGetter;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * Created by Snow on 2017/2/13.
 */

public class ComWheelAdapter<T> extends AbstractWheelTextAdapter {

    private static int maxSize = 14;
    private static int minSize = 12;

    private List<T> mDatas = new ArrayList<>();

    private TextGetter<T> mGetter;

    public void setTextGetter(TextGetter<T> getter){
        this.mGetter = getter;
    }

    protected ComWheelAdapter(Context context, int index) {
        super(context, R.layout.common_dialog_wheel_item_layout, R.id.tempValue, index, maxSize, minSize);
    }

    @Override
    public CharSequence getItemText(int index) {
        if(mGetter != null){
            return mGetter.getText(mDatas.get(index));
        }
        return mDatas.get(index).toString();
    }

    @Override
    public int getItemsCount() {
        return mDatas.size();
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        return super.getItem(index, convertView, parent);
    }

    public void setDatas(List<T> list) {
        mDatas.clear();
        if (list != null && !list.isEmpty()) {
            mDatas.addAll(list);
        }
    }

    /**
     * index <0 || index >= mDatas.size()，返回null
     * @param index
     * @return
     */
    public T getItem(int index){
        if(index <0 || index >= mDatas.size()){
            return null;
        }
        return mDatas.get(index);
    }
}
