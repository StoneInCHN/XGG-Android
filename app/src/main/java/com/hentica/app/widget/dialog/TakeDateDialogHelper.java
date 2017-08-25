package com.hentica.app.widget.dialog;

import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hentica.app.util.DateHelper;

import java.util.Date;

/**
 * Created by Snow on 2016/11/17.
 */

public class TakeDateDialogHelper {
    private TakeDateDialogHelper mInstance;

    private FragmentManager fragmentManager;

    private TakeDateDialog dialog;

    private View eventView;

    private TextView showView;

    private TakeDateDialog.OnTakeDateListener listener;

    public TakeDateDialogHelper setListener(TakeDateDialog.OnTakeDateListener listener){
        this.listener = listener;
        return this;
    }

    public TakeDateDialogHelper(FragmentManager fm){
        fragmentManager = fm;

        dialog = new TakeDateDialog();
    }

    public TakeDateDialogHelper bind(View eventView, final TextView showView){
        this.eventView = eventView;
        this.showView = showView;
        return this;
    }

    public TakeDateDialogHelper build(){
        showDialog();
        return this;
    }

    public TakeDateDialogHelper showStartNow(){
        dialog.setTimeLimite(DateHelper.getDateStr(new Date()), null);
        showDialog();
        return this;
    }

    public TakeDateDialogHelper showEndNow(){
        dialog.setTimeLimite(null, DateHelper.getDateStr(new Date()));
        showDialog();
        return this;
    }


    private void showDialog(){
        if(eventView == null || showView == null){
            return;
        }
        eventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = showView.getText().toString();
                if(TextUtils.isEmpty(value)){
                    dialog.setDefaultTime(true);
                }else{
                    dialog.setDefaultTime(value);
                }
                dialog.setListener(new TakeDateDialog.OnTakeDateListener() {
                    @Override
                    public void takeTime(int year, int month, int day) {
                        showView.setText(String.format("%04d-%02d-%02d", year, month, day));
                        if(listener != null){
                            listener.takeTime(year, month, day);
                        }
                    }
                });
                dialog.show(fragmentManager, "dialog");
            }
        });
    }
}
