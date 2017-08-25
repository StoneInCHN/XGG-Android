package com.hentica.app.widget.wheel;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hentica.app.lib.util.TextGetter;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.util.region.Region;
import com.fiveixlg.app.customer.R;

import java.util.List;

/**
 * Created by Snow on 2017/2/14.
 */

public class TakeAddrWheelDialog extends DialogFragment {

    private View mRootView;

    private Wheel<Region> wheel1;
    private Wheel<Region> wheel2;
    private Wheel<Region> wheel3;

    private String mDefault1;
    private String mDefault2;
    private String mDefault3;

    private OnSelectedComplete onSelectedComplete;

    public interface OnSelectedComplete{

        void selectedDatas(Region value1, Region value2, Region value3);
    }

    /**
     * 设置默认值
     * @param default1 第1个的默认值
     * @param default2 第2个的默认值
     * @param default3 第3个的默认值
     */
    public void setDefaultDatas(String default1, String default2, String default3){
        this.mDefault1 = default1;
        this.mDefault2 = default2;
        this.mDefault3 = default3;
    }

    public void setOnSelectedCompleteListener(OnSelectedComplete l){
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

    private void initData(){

    }

    private void initView(){
        //设置主轮
        wheel1 = (Wheel<Region>) mRootView.findViewById(R.id.wheel_1);
        wheel1.setWheelDatas(ConfigModel.getInstace().getProvinceList());//设置主轮数据源
        wheel1.setDefaultData(mDefault1);//设置主轮默认数据
        wheel1.setTextGetter(new TextGetter<Region>() {//设置数据显示规则
            @Override
            public String getText(Region obj) {
                if(obj != null){
                    return obj.getName();
                }
                return "";
            }
        });
        wheel1.invalidate();
        //设置第1级联动
        wheel2 = (Wheel<Region>) mRootView.findViewById(R.id.wheel_2);
        wheel2.setDefaultData(mDefault2);//设置第1从轮默认数据
        //设置联动
        wheel1.setLinkedWheel(wheel2, new DatasGetter<Region>() {
            @Override
            public List<Region> getDatas(Region selectedDatas) {
                return ConfigModel.getInstace().findChildRegions(selectedDatas.getId());
            }
        });
        wheel3 = (Wheel<Region>) mRootView.findViewById(R.id.wheel_3);
        wheel3.setDefaultData(mDefault3);
        wheel2.setLinkedWheel(wheel3, new DatasGetter<Region>() {
            @Override
            public List<Region> getDatas(Region selectedDatas) {
                return ConfigModel.getInstace().findChildRegions(selectedDatas.getId());
            }
        });
    }

    private void setEvent(){
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
                if(onSelectedComplete != null){
                    onSelectedComplete.selectedDatas(wheel1.getSelectedDatas(), wheel2.getSelectedDatas(), wheel3.getSelectedDatas());
                }
                dismiss();
            }
        });
    }
}
