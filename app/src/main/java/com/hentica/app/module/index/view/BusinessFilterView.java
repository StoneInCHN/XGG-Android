package com.hentica.app.module.index.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hentica.app.module.entity.type.ServiceType;
import com.fiveixlg.app.customer.R;

/**
 * Created by YangChen on 2017/4/6 11:55.
 * E-mail:656762935@qq.com
 */

public class BusinessFilterView extends FrameLayout {

    /** wifi */
    private CheckBox mWifiCheck;
    /** 免费停车 */
    private CheckBox mPassCheck;
    /** 重置 */
    private TextView mResetBtn;
    /** 完成 */
    private TextView mCompleteBtn;
    /** 选中的服务 */
    private String mServiceType = "";
    /** 完成回调 */
    private OnCompleteClickListener mListener;

    public BusinessFilterView(Context context) {
        this(context, null);
    }

    public BusinessFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BusinessFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
        this.setEvent();
    }

    private void initView(Context context){
        View view = View.inflate(context, R.layout.index_business_list_filter_view,this);
        if(view != null){
            mWifiCheck = (CheckBox) view.findViewById(R.id.index_business_list_filter_wifi_check);
            mPassCheck = (CheckBox) view.findViewById(R.id.index_business_list_filter_pass_check);
            mResetBtn = (TextView) view.findViewById(R.id.index_business_list_filter_reset_btn);
            mCompleteBtn = (TextView) view.findViewById(R.id.index_business_list_filter_complete_btn);
        }
        // 刷新按钮状态
        refreshServiceCheck();
    }

    private void setEvent(){
        // wifi按钮被点击
        mWifiCheck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 刷新选中服务
                refreshServiceType();
            }
        });

        // 免费停车按钮被点击
        mPassCheck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 刷新选中服务
                refreshServiceType();
            }
        });

        // 重置按钮
        mResetBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mWifiCheck.setChecked(false);
                mPassCheck.setChecked(false);
                mServiceType = "";
                refreshServiceCheck();
            }
        });

        // 确认
        mCompleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onComplete(mServiceType);
                }
            }
        });
    }

    /** 刷新按钮状态 */
    private void refreshServiceCheck(){
        if(TextUtils.equals(ServiceType.ALL,mServiceType)){
            // 全部
            mWifiCheck.setChecked(true);
            mPassCheck.setChecked(true);
        }else if(TextUtils.equals(ServiceType.WIFI,mServiceType)){
            // WIFI
            mWifiCheck.setChecked(true);
            mPassCheck.setChecked(false);
        }else if(TextUtils.equals(ServiceType.FREE_PARKING,mServiceType)){
            // 免费停车
            mWifiCheck.setChecked(false);
            mPassCheck.setChecked(true);
        }else {
            mWifiCheck.setChecked(false);
            mPassCheck.setChecked(false);
        }
    }

    /** 刷新选中服务 */
    private void refreshServiceType(){
        boolean isWifiChecked = mWifiCheck.isChecked();
        boolean isPassCheched = mPassCheck.isChecked();
        if(isWifiChecked && isPassCheched){
            mServiceType = ServiceType.ALL;
        }else if(isWifiChecked && !isPassCheched){
            mServiceType = ServiceType.WIFI;
        }else if(!isWifiChecked && isPassCheched){
            mServiceType = ServiceType.FREE_PARKING;
        }else{
            mServiceType = "";
        }
    }

    public String getServiceType() {
        return mServiceType;
    }

    public void setServiceType(String mServiceType) {
        this.mServiceType = mServiceType;
        // 刷新按钮状态
        refreshServiceCheck();
    }

    public void setListener(OnCompleteClickListener mListener) {
        this.mListener = mListener;
    }

    /** 完成按钮回调 */
    public interface OnCompleteClickListener{
        void onComplete(String serviceType);
    }
}
