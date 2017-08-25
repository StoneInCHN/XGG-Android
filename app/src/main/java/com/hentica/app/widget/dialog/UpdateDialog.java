package com.hentica.app.widget.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.util.ViewUtil;
import com.fiveixlg.app.customer.R;

import java.util.Locale;

/**
 * 更新对话框
 *
 * @author mili
 * @createTime 2016-4-19 下午2:33:26
 */
public class UpdateDialog extends DialogFragment {

    /**
     * 监听对话框取消事件
     */
    public interface OnDismissListener {

        /**
         * 对话框被取消了
         */
        void onDismiss(UpdateDialog dialog);
    }

    // 取消事件
    private OnDismissListener mDismissListener;

    private boolean isFource = false;//是否强制更新

    /**
     * 取消事件
     */
    public void setDismissListener(OnDismissListener dismissListener) {
        mDismissListener = dismissListener;
    }

    public void setFource(boolean isFource){
        this.isFource = isFource;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.common_dialog_update, container, false);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.alert_dialog);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // 初始化界面
        this.initView();
        this.setEvent();
    }

    /**
     * 设置更新进度，参数为百分比，1表示完成
     */
    public void setProgress(float progress) {

        try {
            int progressInt = (int) (progress * 100);

            ProgressBar progressBar = ViewUtil.getHolderView(getView(), R.id.common_dialog_update_progress);
            TextView progressTextView = ViewUtil.getHolderView(getView(), R.id.common_dialog_update_progress_text);

            progressBar.setProgress(progressInt);
            progressTextView.setText(String.format(Locale.getDefault(), "%d%%", progressInt));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (mDismissListener != null) {

            mDismissListener.onDismiss(this);
        }
    }

    /**
     * 初始化界面
     */
    private void initView() {
        this.setProgress(0);
    }

    /**
     * 设置事件
     */
    private void setEvent() {
        AQuery query = new AQuery(getView());

        // 关闭
        query.id(R.id.common_dialog_update_close).clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        query.id(R.id.common_dialog_update_close).visibility(isFource ? View.GONE : View.VISIBLE);
        getDialog().setCanceledOnTouchOutside(!false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (isFource) {
                        return true;
                    }
                }
                return false;
            }
        });
    }


}
