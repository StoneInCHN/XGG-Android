package com.hentica.app.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.androidquery.AQuery;
import com.hentica.app.widget.view.MaxListView;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

import java.util.List;

/**
 * Created by YangChen on 2017/4/5 18:04.
 * E-mail:656762935@qq.com
 */

@SuppressLint("ValidFragment")
public class ListFilterDialog<T> extends DialogFragment {

    /** 筛选内容列表 */
    private MaxListView mFilterLv;

    private FilterAdapter mAdapter;
    private DataGetter mDataGetter;
    private String mDefaultVal;
    private List<T> mDatas;
    private OnItemClickListener mListener;

    private AQuery mQuery;

    public ListFilterDialog(List<T> datas){
        mDatas = datas;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list_filter_dialog_fragment, container, false);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.setStyle(STYLE_NORMAL, R.style.alert_dialog);
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        return dialog;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化界面
        this.initView();
        this.setEvent();
    }

    private void initView(){
        mQuery = new AQuery(getView());
        mAdapter = new FilterAdapter();
        mFilterLv = (MaxListView) mQuery.id(R.id.list_filter_dialog_list).getView();
        mFilterLv.setAdapter(mAdapter);
        mFilterLv.setListViewHeight(800);
        mAdapter.setDatas(mDatas);
    }

    private void setEvent(){
        mFilterLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mListener != null && mDataGetter != null){
                    T data = (T) view.getTag();
                    mDefaultVal = mDataGetter.getValue(data);
                    mAdapter.notifyDataSetChanged();
                    mListener.onClick(mDefaultVal);
                }
            }
        });
    }

    public void setDataGetter(DataGetter dataGetter) {
        mDataGetter = dataGetter;
    }

    public void setDefaultVal(String defaultVal) {
        mDefaultVal = defaultVal;
    }

    /** 列表适配器 */
    public class FilterAdapter extends QuickAdapter<T>{

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
            }
            convertView.setTag(data);
        }
    }

    /** 数据采集接口 */
    public interface DataGetter<T>{
        // 获取显示文字
        String getText(T data);
        // 获取内容值
        String getValue(T data);
    }

    /** 列表点击事件 */
    public interface OnItemClickListener{
        void onClick(String value);
    }
}
