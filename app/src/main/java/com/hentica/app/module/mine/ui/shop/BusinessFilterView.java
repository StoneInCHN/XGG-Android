package com.hentica.app.module.mine.ui.shop;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.util.DateHelper;
import com.hentica.app.widget.dialog.TakeDateDialog;
import com.hentica.app.widget.dialog.TakeDateDialogHelper;

/**
 * Created by YangChen on 2017/7/3 12:03.
 * E-mail:656762935@qq.com
 */

public class BusinessFilterView extends FrameLayout {

    private TextView mStartEdit;
    private TextView mEndEdit;
    private TextView mResetBtn;
    private TextView mCompleteBtn;

    private TakeDateDialogHelper mStartDateDialog;
    private TakeDateDialogHelper mEndDateDialog;
    private UsualFragment mFragment;
    private OnCompleteListener mListener;

    private Context mContext;

    public interface OnCompleteListener{
        void onComplete(String startDate, String endDate);
    }

    public BusinessFilterView(Context context, UsualFragment fragment) {
        this(context, null, fragment);
    }

    public BusinessFilterView(Context context, AttributeSet attrs, UsualFragment fragment) {
        this(context, attrs, 0, fragment);
    }

    public BusinessFilterView(Context context, AttributeSet attrs, int defStyleAttr, UsualFragment fragment) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mFragment = fragment;
        this.initData();
        this.initView();
        this.setEvent();
    }

    private void initData(){
        if(mFragment != null){
            mStartDateDialog = new TakeDateDialogHelper(mFragment.getChildFragmentManager());
            mEndDateDialog = new TakeDateDialogHelper(mFragment.getChildFragmentManager());
        }

    }

    private void initView(){
        View view = View.inflate(mContext, R.layout.shop_business_center_filter_view, this);
        if(view != null){
            mStartEdit = (TextView) view.findViewById(R.id.shop_business_center_filter_start_edit);
            mEndEdit = (TextView) view.findViewById(R.id.shop_business_center_filter_end_edit);
            mResetBtn = (TextView) view.findViewById(R.id.shop_business_center_filter_reset);
            mCompleteBtn = (TextView) view.findViewById(R.id.shop_business_center_filter_complete);

            mStartDateDialog.bind(mStartEdit, mStartEdit).build();
            mEndDateDialog.bind(mEndEdit, mEndEdit).build();
        }
    }

    private void setEvent(){

        if(mResetBtn != null){
            mResetBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStartEdit.setText("");
                    mEndEdit.setText("");
                }
            });
        }

        // 完成按钮被点击
        if(mCompleteBtn != null){
            mCompleteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkDate() && mListener != null){
                        mListener.onComplete(getStartDate(), getEndDate());
                    }
                }
            });
        }
    }

    /** 验证选择的日期 */
    private boolean checkDate(){
        boolean isChecked = true;
        String showText = "";

        if(TextUtils.isEmpty(getStartDate()) && TextUtils.isEmpty(getEndDate())){
            return true;
        }

        if(!TextUtils.isEmpty(getStartDate()) && !TextUtils.isEmpty(getEndDate())){
            // 判断日期是否合理
            if(DateHelper.getDayCount(getStartDate()) > DateHelper.getDayCount(getEndDate())){
                isChecked = false;
                showText = "开始日期不能超过结束日期！";
            }
        }else {
            isChecked = false;
            showText = "日期未选择完整！";
        }
        if(!isChecked){
            mFragment.showToast(showText);
        }
        return isChecked;
    }

    /** 获取开始日期 */
    private String getStartDate(){
        return mStartEdit.getText() == null ? "" : mStartEdit.getText().toString();
    }

    /** 获取结束日期 */
    private String getEndDate(){
        return mEndEdit.getText() == null ? "" : mEndEdit.getText().toString();
    }

    public void setFragment(UsualFragment fragment) {
        mFragment = fragment;
    }

    public void setListener(OnCompleteListener mListener) {
        this.mListener = mListener;
    }
}
