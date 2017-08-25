package com.hentica.app.widget.wheel;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import static com.hentica.app.util.TimeUtils.getTimes;
import static com.hentica.app.util.TimeUtils.splitStringHour;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.11:01
 */

public class TakeHHTimeHelper {

    private FragmentManager mFragmentManager;

    private View mEventView;

    private TextView mTextView;

    private int defaultLeft = 0;//左侧默认值
    private int defaultRight = 0;//右侧默认值

    private TakeTimeDialog.OnSelectedComplete<Integer> l;

    /**
     * 从固定格式字符串中获取小时
     *
     * @param time 格式"XX:00-XX:00"
     * @return
     */
    public static int[] getHours(String time) {
        int[] result = new int[2];
        if (time != null) {
            String[] arrays = getTimes(time);
            result[0] = splitStringHour(arrays[0]);
            if (arrays.length > 1) {
                //获取第2个值
                result[1] = splitStringHour(arrays[1]);
            }
        }
        return result;
    }

    /**
     * @param fm
     */
    public TakeHHTimeHelper(FragmentManager fm, String defaultTime) {
        this.mFragmentManager = fm;
        splitDefaultTime(defaultTime);
    }

    /**
     * 拆分默认时间
     *
     * @param defaultTime 格式"XX:00-XX:00"
     * @return
     */
    private void splitDefaultTime(String defaultTime) {
        int[] hours = getHours(defaultTime);
        defaultLeft = hours[0];
        defaultRight = hours[1];
    }

    /**
     * 设置选中监听
     * @param l
     * @return
     */
    public TakeHHTimeHelper setOnSelectedCompleteListener(TakeTimeDialog.OnSelectedComplete l){
        this.l = l;
        return this;
    }

    /**
     * 绑定控件
     * @param eventView 响应点击事件的View
     * @param textView  显示结果内容的TextView
     * @return
     */
    public TakeHHTimeHelper bindView(View eventView, final TextView textView){
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
    public TakeHHTimeHelper bindView(View eventView){
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
                            String value = String.format("%02d:00-%02d:00", value1, value2);
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
        dialog.setTimeType(new TimeTypeHH());
        dialog.setDefaultDatas(String.format("%02d", defaultLeft), String.format("%02d",defaultRight));
        dialog.setOnSelectedCompleteListener(l);
        dialog.show(mFragmentManager, dialog.getClass().getSimpleName());
    }

}
