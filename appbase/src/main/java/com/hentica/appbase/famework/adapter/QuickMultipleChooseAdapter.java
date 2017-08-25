package com.hentica.appbase.famework.adapter;

import android.view.View;

/**
 * 公用多选列表，
 * 须指定布局id，选择CheckBox的id。
 * 在方法showView中处理item中各View显示
 * 可以通过getSelectedDatas()方法返回选择数据
 * Created by Snow on 2016/12/21.
 */

public abstract class QuickMultipleChooseAdapter<T> extends QuickChooseAdapter<T> {

    @Override
    protected void setCheckBoxClickEvent(View convertView, int position, T data) {
        if(isSelected(data)){
            removeSelect(data);
        }else{
            addSelected(data);
        }
    }

    public void clearAllSelected(){
        removeAllSelected();
        notifyDataSetChanged();
    }

    public void selectAll(){
        addSelected(getDatas());
    }


}
