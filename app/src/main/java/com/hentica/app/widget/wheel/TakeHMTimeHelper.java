package com.hentica.app.widget.wheel;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.hentica.app.util.TimeUtils;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.11:01
 */

public class TakeHMTimeHelper {

    private FragmentManager mFragmentManager;

    private View mEventView;

    private TextView mTextView;

    private int defaultLeft = 0;//左侧默认值
    private int defaultRight = 0;//右侧默认值

    private TakeTimeDialog.OnSelectedComplete<Integer> l;

    /**
     * @param fm
     * @param defaultTime 默认时间：格式"09:30"
     */
    public TakeHMTimeHelper(FragmentManager fm, String defaultTime) {
        this.mFragmentManager = fm;
        splitDefaultTime(defaultTime);
    }

    /**
     * 拆分默认时间
     *
     * @param defaultTime 格式"09:30"
     * @return
     */
    private void splitDefaultTime(String defaultTime) {
        defaultLeft = TimeUtils.splitStringHour(defaultTime);;
        defaultRight = TimeUtils.splitStringMinute(defaultTime);;
    }

    /**
     * 设置选中监听
     * @param l
     * @return
     */
    public TakeHMTimeHelper setOnSelectedCompleteListener(TakeTimeDialog.OnSelectedComplete l){
        this.l = l;
        return this;
    }

    /**
     * 绑定控件
     * @param eventView 响应点击事件的View
     * @param textView  显示结果内容的TextView
     * @return
     */
    public TakeHMTimeHelper bindView(View eventView, final TextView textView){
        //设置点击事件
        this.mEventView = eventView;
        this.mTextView = textView;
        return this;
    }

    /**
     * 绑定控件
     * @param eventView 响应点击事件的View
     * @return
     */
    public TakeHMTimeHelper bindView(View eventView){
        //设置点击事件
        //设置点击事件
        this.mEventView = eventView;
        return this;
    }

    public void show(){
        if(mEventView != null){
            mEventView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTakeTimeDialgo(new TakeTimeDialog.OnSelectedComplete<Integer>() {
                        @Override
                        public void selectedDatas(Integer value1, Integer value2){
                            String value = String.format("%02d:%02d", value1, value2);
                            defaultLeft = value1;
                            defaultRight = value2;
                            if(mTextView != null){
                                mTextView.setText(value);
                            }
                            if(l != null){
                                l.selectedDatas(value1, value2);
                            }
                        }
                    });
                }
            });
        }
    }

    /**
     * 显示时间选择对话框
     */
    private void showTakeTimeDialgo(TakeTimeDialog.OnSelectedComplete<Integer> l){
        TakeTimeDialog<Integer> dialog = new TakeTimeDialog<>();
        dialog.setTimeType(new TimeTypeHM());
        dialog.setDefaultDatas(String.format("%02d", defaultLeft), String.format("%02d",defaultRight));
        dialog.setOnSelectedCompleteListener(l);
        dialog.show(mFragmentManager, dialog.getClass().getSimpleName());
    }

}
