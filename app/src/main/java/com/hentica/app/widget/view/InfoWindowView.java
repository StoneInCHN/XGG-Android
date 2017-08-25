package com.hentica.app.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;

/**
 * Created by YangChen on 2017/4/10 20:52.
 * E-mail:656762935@qq.com
 */

public class InfoWindowView extends FrameLayout {

    private TextView mAddressTv;
    private TextView mDistanceTv;
    private TextView mNavBtn;

    private String mAddress;
    private String mDistance;
    private OnLocationClickListener mListener;

    public interface OnLocationClickListener{
        void onLocation();
    }

    public InfoWindowView(Context context) {
        this(context,null);
    }

    public InfoWindowView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InfoWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        setEvent();
    }

    private void initView(Context context){
        View view = View.inflate(context, R.layout.mine_location_info_window,this);
        if(view != null){
            mAddressTv = (TextView) view.findViewById(R.id.info_window_address_text);
            mDistanceTv = (TextView) view.findViewById(R.id.info_window_distance_text);
            mNavBtn = (TextView) view.findViewById(R.id.info_window_navigate_btn);
        }
    }

    private void setEvent(){
        mNavBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onLocation();
                }
            }
        });
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
        mAddressTv.setText(mAddress);
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String mDistance) {
        this.mDistance = mDistance;
        mDistanceTv.setText(mDistance);
    }

    public void setListener(OnLocationClickListener listener){
        mListener = listener;
    }
}
