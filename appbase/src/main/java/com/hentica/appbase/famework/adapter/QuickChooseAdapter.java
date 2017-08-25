package com.hentica.appbase.famework.adapter;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * 可以通过setDefaultSelectedDatas()方法设置选择数据
 * Created by Snow on 2016/12/20.
 */

public abstract class QuickChooseAdapter<T> extends QuickAdapter<T> {

    List<T> mSelectedDatas = new ArrayList<>();

    private OnItemClickListener<T> mItemClickListener;

    private boolean isAutoSelected = false;

    public interface OnItemClickListener<T>{

        void onItemClick(View view, long position, T data);

    }

    private OnSelectedDatasChangeListener<T> mSelectedDatasChangeListener;

    public interface OnSelectedDatasChangeListener<D>{
        void onSelectedDatasChanged(List<D> selectedDatas);
    }

    public void setSelectedDatasChangeListener(OnSelectedDatasChangeListener<T> selectedDatasChangeListener) {
        mSelectedDatasChangeListener = selectedDatasChangeListener;
    }

    /** 设置默认选择数据 */
    public void setDefaultSelectedDatas(List<T> defaultSelected){
        mSelectedDatas.clear();
        if(defaultSelected != null && !defaultSelected.isEmpty()){
            mSelectedDatas.addAll(defaultSelected);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T> l){
        mItemClickListener = l;
    }

    /**
     * 设置是否自动选择一个默认荐
     * @param isAutoSelected true：是，false：否
     */
    public void isAutoSelected(boolean isAutoSelected){
        this.isAutoSelected = isAutoSelected;
    }

    protected boolean isAutoSelected(){
        return this.isAutoSelected;
    }

    @Override
    protected void fillView(int position, View convertView, ViewGroup parent, T data) {
        setEvent(convertView, position, data);
        handleCheckBox(convertView, data);
        showView(convertView, position, data);
    }

    /** 设置事件 */
    protected void setEvent(final View convertView, final int position, final T data){
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCheckBox(convertView).performClick();
            }
        });
        getCheckBox(convertView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckBoxClickEvent(convertView, position, data);
                if(mItemClickListener != null){
                    mItemClickListener.onItemClick(convertView, position, data);
                }
            }
        });
    }

    protected abstract void setCheckBoxClickEvent(View convertView, int position, final T data);

    /** 处理选框 */
    protected void handleCheckBox(View convertView, T data){

    }

    /** 处理View中相关控件数据显示 */
    protected void showView(View convertView, int position, T data){
        CheckBox ckb = getCheckBox(convertView);
        if(isSelected(data)){
            ckb.setChecked(true);
        }else{
            ckb.setChecked(false);
        }
    }

    /** 获取checkBox */
    protected CheckBox getCheckBox(View view){
        return (CheckBox) view.findViewById(getItemCheckBoxId());
    }

    /** 获取item中checkbox */
    protected abstract  @IdRes int getItemCheckBoxId();

    /** 添加选中 */
    protected void addSelected(T selectedData){
        if(!mSelectedDatas.contains(selectedData)){
            mSelectedDatas.add(selectedData);
            notifyDataSetChanged();
            if(mSelectedDatasChangeListener != null){
                mSelectedDatasChangeListener.onSelectedDatasChanged(mSelectedDatas);
            }
        }
    }

    /** 添加选中数据 */
    protected void addSelected(List<T> selectedDatas){
        if(selectedDatas != null && !selectedDatas.isEmpty()){
            for(T selectedData : selectedDatas){
                addSelected(selectedData);
            }
            notifyDataSetChanged();
        }
    }

    /** 当前数据是否已选择 */
    protected boolean isSelected(T data){
        return mSelectedDatas.contains(data);
    }

    /** 移除选中数据 */
    protected void removeSelect(T select){
        if(mSelectedDatas.contains(select)){
            mSelectedDatas.remove(select);
            notifyDataSetChanged();
            if(mSelectedDatasChangeListener != null){
                mSelectedDatasChangeListener.onSelectedDatasChanged(mSelectedDatas);
            }
        }
    }

    /** 移除选中数据 */
    protected void removeSelect(List<T> selectDatas){
        if(selectDatas!=null && !selectDatas.isEmpty()){
            for(T select : selectDatas){
                removeSelect(select);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 判断是否已全部选中
     * @return true：已全部选中，false：未全部选中
     */
    public boolean isAllSelected(){
        boolean result = true;
        for(T data : getDatas()){
            if(!isSelected(data)){
                result = false;
                break;
            }
        }
        return result;
    }

    /** 移除所有选择数据 */
    protected void removeAllSelected(){
        mSelectedDatas.clear();
        if(mSelectedDatasChangeListener != null){
            mSelectedDatasChangeListener.onSelectedDatasChanged(mSelectedDatas);
        }
    }

    /** 得到选中数据 */
    public List<T> getSelectedDatas(){
        return mSelectedDatas;
    }

    @Override
    public void remove(T data) {
        super.remove(data);
        if(mSelectedDatas.contains(data)){
            mSelectedDatas.remove(data);
        }
        if(mSelectedDatasChangeListener != null){
            mSelectedDatasChangeListener.onSelectedDatasChanged(mSelectedDatas);
        }
    }

    @Override
    public void remove(int position) {
        if(position >= 0 && position < getCount()){
            T data = getItem(position);
            remove(data);
        }
    }

    @Override
    public void remove(Collection<? extends T> datas) {
        if(datas != null && !datas.isEmpty()){
            for(T data : datas){
                remove(data);
            }
        }
    }
}
