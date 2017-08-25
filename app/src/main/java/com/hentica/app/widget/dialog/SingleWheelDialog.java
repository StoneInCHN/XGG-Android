package com.hentica.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * 通用单个滚轮对话框，需传入数据源。 可以通过构造方法和setDatas传入。
 * OnSelectedListener监听，用于监听用户选择内容与内容对应的索引，可以通过构造方法或setListener方法设置。
 * 可以通过setDefaultSelected()
 */
public class SingleWheelDialog<T> extends DialogFragment implements OnWheelChangedListener,
        OnWheelScrollListener {
    private View mView;
    private List<WheelBaseData<T>> mData = new ArrayList<WheelBaseData<T>>();
    private int NUM_WHEEL_ITEMS = 7;
    private int maxSize = 16;
    private int minSize = 13;
    private WheelView mWheelView;
    private SingleWheelAdapter mAdapter;
    private boolean cycle = false;
    private OnWheelChangedListener mWheelChangedListener = this;
    private OnWheelScrollListener mWheelScrollListener = this;
    private OnSelectedListener<T> mListener;
    private T mDefData;

    /**
     * 单个滚轮的的选择监听
     */
    public interface OnSelectedListener<T> {

        /**
         * 得到选中的内容
         *
         * @param data  : 选中的基本元素对象
         * @param index : 选中项的索引
         */
        void getSelected(WheelBaseData<T> data, int index);

    }

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setListener(OnSelectedListener<T> listener) {
        mListener = listener;
    }

    public SingleWheelDialog() {
        super();
    }

    public void setDatas(List<WheelBaseData<T>> mData) {
        this.mData = mData;
    }

    /**
     * 设置内容为默认选中 要选中的内容。
     */
    public void setDefaultSelected(T data) {
        mDefData = data;
    }

    /**
     * 设置滚轮是否可以重复
     */
    public void setCycle(boolean cycle) {
        this.cycle = cycle;
    }

    /**
     * 设置监听
     */
    public void setOnWheelChangedListener(OnWheelChangedListener listener) {
        mWheelChangedListener = listener;
    }

    /**
     * 设置监听
     */
    public void setOnWheelScrollListener(OnWheelScrollListener listener) {
        mWheelScrollListener = listener;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.common_dialog_single_wheel, container, false);
        return mView;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.full_screen_dialog);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        initData();
        initView();
        setEvent();
        mWheelView.setCurrentItem(getDefaultIndex());
    }

    private void initData() {
        if (mAdapter == null) {
            mAdapter = new SingleWheelAdapter(getContext(), mData);
        } else {
            mAdapter.setList(mData);
        }
    }

    private void initView() {
        initWheel();
    }

    private void initWheel() {
        AQuery query = new AQuery(mView);
        mWheelView = (WheelView) query.id(R.id.common_dialog_single_wheel).getView();
        if (mWheelView != null) {
            mWheelView.setVisibleItems(NUM_WHEEL_ITEMS);
            mWheelView.setCyclic(cycle);
            mWheelView.addChangingListener(mWheelChangedListener);
            mWheelView.addScrollingListener(mWheelScrollListener);
            mWheelView.setViewAdapter(mAdapter);
        }
    }

    private void setEvent() {
        AQuery query = new AQuery(mView);
        query.clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        query.id(R.id.common_dialog_title_close_btn).clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        query.id(R.id.common_dialog_title_complete_btn).clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    WheelBaseData<T> data = getSelected();
                    int index = getSelectedIndex();
                    if (data != null) {
                        mListener.getSelected(data, index);
                    }
                }
                dismiss();
            }
        });
    }

    private WheelBaseData<T> getSelected() {
        WheelBaseData<T> result = null;
        int index = mWheelView.getCurrentItem();
        if (index < mData.size()) {
            result = mData.get(index);
        }
        return result;
    }

    private int getSelectedIndex() {
        int result = 0;
        int index = mWheelView.getCurrentItem();
        if (index < mData.size()) {
            result = index;
        }
        return result;
    }

    @Override
    public void onScrollingStarted(WheelView wheel) {

    }

    @Override
    public void onScrollingFinished(WheelView wheel) {
        int index = wheel.getCurrentItem();
        String currentText = mAdapter.getItemText(index).toString();
        setTextSize(currentText, mAdapter);
    }

    @Override
    public void onChanged(WheelView wheel, int olddata, int newdata) {

    }

    private void setTextSize(String currentText, SingleWheelAdapter adapter) {
        List<View> views = adapter.getTestViews();
        for (int i = 0, count = views.size(); i < count; i++) {
            TextView text = (TextView) views.get(i);
            if (currentText.equals(text.getText().toString())) {
                text.setTextSize(maxSize);
            } else {
                text.setTextSize(minSize);
            }
        }
    }

    public class SingleWheelAdapter extends AbstractWheelTextAdapter {
        private List<WheelBaseData<T>> mList = new ArrayList<WheelBaseData<T>>();

        protected SingleWheelAdapter(Context context, List<WheelBaseData<T>> data) {
            super(context, R.layout.common_dialog_wheel_item_layout, R.id.tempValue,
                    getDefaultIndex(), maxSize, minSize);
            setList(data);
        }

        @Override
        public CharSequence getItemText(int index) {
            return mList.get(index).getShowText();
        }

        @Override
        public int getItemsCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public View getItem(int index, View convertView, ViewGroup parent) {
            return super.getItem(index, convertView, parent);
        }

        public void setList(List<WheelBaseData<T>> list) {
            mList.clear();
            if (list != null) {
                mList.addAll(list);
            }
            checkDatas();
        }

        private void checkDatas() {
            if (mList.isEmpty()) {
                mList.add(new EmptyWheelData<T>("无"));
            }
        }
    }

    /**
     * 为空时的显示数据
     */
    private static class EmptyWheelData<T> extends WheelBaseData<T> {

        public EmptyWheelData(String showText) {
            setShowText(showText);
        }
    }

    /**
     * 得到默认选中项的索引
     */
    private int getDefaultIndex() {

        int result = 0;

        for (int i = 0, count = mData.size(); i < count; i++) {

            T data = mData.get(i).getData();

            if (mDefData == data || (mDefData != null && mDefData.equals(data))) {

                result = i;
                break;
            }
        }
        return result;
    }
}
