package com.hentica.appbase.famework.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.hentica.appbase.famework.util.ListUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 公用单选列表，
 * 须指定布局id，选择CheckBox的id。
 * 在方法showView中处理item中各View显示
 * setDatas()方法，在设置数据源中，默认设置第1项为选中。
 * 可以通过getSelected()方法返回选择数据
 * Created by Snow on 2016/12/20.
 */

public abstract class QuickSingleChooseAdapter<T> extends QuickChooseAdapter<T> {


    /** 添加数据，设置默认选择为第1个 */
    @Override
    public void setDatas(Collection<? extends T> datas) {
        super.setDatas(datas);
        if(!isAutoSelected()){
            return;
        }
        if(!ListUtils.isEmpty(datas)){
            ArrayList<T> selected = new ArrayList<>();
            selected.add(getDatas().get(0));
            setDefaultSelectedDatas(selected);
        }
    }

    @Override
    protected void setCheckBoxClickEvent(View convertView, int position, T data) {
        setSelected(data);
        notifyDataSetChanged();
    }

    private void setSelected(T data){
        removeAllSelected();
        addSelected(data);
    }

    /** 获取选择数据 */
    @Nullable
    public T getSelected(){
        if(!ListUtils.isEmpty(getSelectedDatas())){
            return getSelectedDatas().get(0);
        }
        return null;
    }

    @Override
    public void removeAllSelected() {
        super.removeAllSelected();
        notifyDataSetChanged();
    }

    /**
     * 重置选择
     */
    public void resetSelected(){
        if (getCount() > 0) {
            setSelected(getItem(0));
        }
    }
}
