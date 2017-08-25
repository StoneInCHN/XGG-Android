package com.hentica.app.widget.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.fiveixlg.app.customer.R;

/**
 * Created by YangChen on 2016/10/8 11:26.
 * E-mail:656762935@qq.com
 */

public class NavigateDialog extends DialogFragment {


    /** 百度地图接口 */
    public interface OnBaiduMapListener{
        void toBaiduMap();
    }

    /** 高德地图接口 */
    public interface OnGaodeMapListener{
        void toGaodeMap();
    }

    private OnBaiduMapListener mBaiduListener;

    private OnGaodeMapListener mGaodeListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_navigate_layout,container);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.full_screen_dialog);
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        //设置事件
        setEvent();
    }

    private void initView(){
        // TODO 从底部弹出动画
    }

    private void setEvent(){

        AQuery query = new AQuery(getView());

        //百度地图
        query.id(R.id.dialog_navigate_baidu_btn).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBaiduListener != null){
                    mBaiduListener.toBaiduMap();
                    //关闭dialog
                    dismiss();
                }
            }
        });

        //高德地图
        query.id(R.id.dialog_navigate_gaode_btn).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGaodeListener != null){
                    mGaodeListener.toGaodeMap();
                    //关闭dialog
                    dismiss();
                }
            }
        });

        //取消
        query.id(R.id.dialog_navigate_cancel_btn).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setBaiduListener(OnBaiduMapListener baiduListener) {
        this.mBaiduListener = baiduListener;
    }

    public void setGaodeListener(OnGaodeMapListener gaodeListener) {
        this.mGaodeListener = gaodeListener;
    }
}
