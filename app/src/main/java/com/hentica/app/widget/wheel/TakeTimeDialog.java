package com.hentica.app.widget.wheel;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hentica.appbase.famework.util.ListUtils;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 请调用{@linkplain OnSelectedComplete#setTimeType(TimeType)}设置时间类型
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.10:21
 */

public class TakeTimeDialog<T> extends DialogFragment {

    private View mRootView;

    private Wheel<T> wheel1;
    private Wheel<T> wheel2;

    private String mDefault1;
    private String mDefault2;

    private List<T> mWheel1Datas = new ArrayList<>();
    private List<T> mWheel2Datas = new ArrayList<>();

    private OnSelectedComplete onSelectedComplete;

    private TimeType<T> mTimeType;

    public interface OnSelectedComplete<T> {
        void selectedDatas(T value1, T value2);
    }

    public void setTimeType(TimeType type){
        this.mTimeType = type;
    }

    /**
     * 设置默认值
     *
     * @param default1 第1个的默认值
     * @param default2 第2个的默认值
     */
    public void setDefaultDatas(String default1, String default2) {
        this.mDefault1 = default1;
        this.mDefault2 = default2;
    }

    public void setOnSelectedCompleteListener(OnSelectedComplete<T> l) {
        this.onSelectedComplete = l;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.common_dialog_take_addr, container, false);
        return mRootView;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.full_screen_dialog);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initData();
        this.initView();
        this.setEvent();
    }

    private void initData() {
        if(mTimeType == null){
            return;
        }
        if(ListUtils.isEmpty(mTimeType.getWheel1Datas())){
            mWheel1Datas.addAll(mTimeType.getWheel1Datas());
        }
        if(ListUtils.isEmpty(mTimeType.getWheel2Datas())){
            mWheel2Datas.addAll(mTimeType.getWheel2Datas());
        }
    }

    private void initView() {
        if(mTimeType == null){
            return;
        }
        //设置主轮
        wheel1 = (Wheel<T>) mRootView.findViewById(R.id.wheel_1);
        wheel1.setWheelDatas(mTimeType.getWheel1Datas());//设置主轮数据源
        wheel1.setDefaultData(mDefault1);//设置主轮默认数据
        wheel1.setTextGetter(mTimeType.getWheel1TextGetter());
        wheel1.invalidate();
        //设置第1级联动
        wheel2 = (Wheel<T>) mRootView.findViewById(R.id.wheel_2);
        wheel2.setWheelDatas(mTimeType.getWheel2Datas());//设置主轮数据源
        wheel2.setDefaultData(mDefault2);//设置第1从轮默认数据
        wheel2.setTextGetter(mTimeType.getWheel2TextGetter());
        wheel2.invalidate();
        mRootView.findViewById(R.id.wheel_3).setVisibility(View.GONE);
    }

    private void setEvent() {
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //关闭按钮
        mRootView.findViewById(R.id.common_dialog_title_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //完成按钮
        mRootView.findViewById(R.id.common_dialog_title_complete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectedComplete != null) {
                    onSelectedComplete.selectedDatas(wheel1.getSelectedDatas(), wheel2.getSelectedDatas());
                }
                dismiss();
            }
        });
    }

}
