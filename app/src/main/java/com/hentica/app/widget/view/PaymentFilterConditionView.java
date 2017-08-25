package com.hentica.app.widget.view;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.hentica.app.util.CalenderHelper;
import com.hentica.appbase.famework.adapter.QuickChooseAdapter;
import com.hentica.appbase.famework.adapter.QuickSingleChooseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Snow on 2017/7/14.
 */

public class PaymentFilterConditionView extends LinearLayout {

    @BindView(R.id.filter_conditions)
    GridView mFilterConditions;
    @BindView(R.id.filter_btn_reset)
    TextView mFilterBtnReset;
    @BindView(R.id.filter_btn_complete)
    TextView mFilterBtnComplete;

    private ConditionsAdapter mAdpater;

    private FilterSelect mFilterSelect;

    public void setFilterSelect(FilterSelect filterSelect) {
        mFilterSelect = filterSelect;
    }

    public PaymentFilterConditionView(Context context) {
        this(context, null);
    }

    public PaymentFilterConditionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaymentFilterConditionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.mine_payment_list_filter_dialog, this);
        ButterKnife.bind(view);
        initData();
    }

    private void initData(){
        mAdpater = new ConditionsAdapter();
        mAdpater.isAutoSelected(true);
        mAdpater.setDatas(getCondiitions());
        mFilterConditions.setAdapter(mAdpater);
    }

    /**
     * 获取筛选条件数据
     * @return
     */
    private List<PaymentConditioin> getCondiitions(){
        List<PaymentConditioin> result = new ArrayList<>();
        result.add(new PaymentConditioin("全部"));
        result.add(getCondition("当天", 1));
        result.add(getCondition("近3天", 3));
        result.add(getCondition("近7天", 7));
        return result;
    }

    /**
     *
     * @param text 文字
     * @param dayCount 天数，包括当天
     * @return
     */
    private PaymentConditioin getCondition(String text, int dayCount){
        PaymentConditioin result = new PaymentConditioin(text);
        Calendar calendar = Calendar.getInstance();
        //当天结束
        CalenderHelper.setCalendar(calendar, 0, 23, 59, 59);
        result.setEndTime(calendar.getTimeInMillis());
        if (dayCount < 1) {
            dayCount = 0;
        } else {
            dayCount = 1 - dayCount;
        }
        //开始时间
        CalenderHelper.resetCalendar(calendar);
        CalenderHelper.setCalendar(calendar, dayCount, 0, 0, 0);
        result.setStartTime(calendar.getTimeInMillis());
        return result;
    }

    /**
     * 重置选择
     * @param v
     */
    @OnClick(R.id.filter_btn_reset)
    public void resetSelected(View v){
        mAdpater.resetSelected();
    }

    /**
     * 完成按钮
     * @param v
     */
    @OnClick(R.id.filter_btn_complete)
    public void complete(View v) {
        PaymentConditioin data = mAdpater.getSelected();
        if (data != null && mFilterSelect != null) {
            mFilterSelect.selected(data.getStartTime(), data.getEndTime());
        }
    }

    public interface FilterSelect{
        /**
         *
         * @param startTime 开始时间
         * @param endTime 结束时间
         */
        void selected(long startTime, long endTime);
    }

    private class ConditionsAdapter extends QuickSingleChooseAdapter<PaymentConditioin> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.mine_payment_list_filter_item;
        }

        @Override
        protected int getItemCheckBoxId() {
            return R.id.filter_ckb;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, PaymentConditioin data) {
            super.fillView(position, convertView, parent, data);
            CheckBox ckb = (CheckBox) convertView.findViewById(getItemCheckBoxId());
            ckb.setText(data.getText());
        }
    }

    private class PaymentConditioin {
        private long startTime;
        private long endTime;
        private String text;

        public PaymentConditioin() {
        }

        public PaymentConditioin(String text) {
            this.text = text;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

}
