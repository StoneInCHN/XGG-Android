package com.hentica.app.util;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kezhong.
 * E-Mail:396926020@qq.com
 * on 2016/10/24 10:09
 */

public class TimerHelper {


    private Timer mTimer;
    private TimerTask mTimerTask;

    private Handler mHandler;

    private Task mTask;

    private long delay = 1000;

    /**
     * 是否重复。
     */
    private boolean isRepeat = false;

    /**
     * 时间间隔
     */
    private long periond = 1000;


    public TimerHelper(Task task){
        this.mTask = task;
        mHandler = new Handler();
        create();
    }

    public TimerHelper(Task task, long delay){
        this(task);
        setDelay(delay);
    }

    public TimerHelper(Task task, long delay, long periond){
        this(task, delay);
        setPeriond(periond);
        setRepeat(true);
    }

    /**
     * 创建Timer、TimerTask
     */
    private void create(){
        if(mTimer == null){
            mTimer = new Timer();
        }
        if(mTimerTask == null){
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(mTask != null){
                                mTask.doTask();
                            }
                        }
                    });
                }
            };
        }
    }

    /**
     * 设置去得间隔时间
     * @param millisecond
     *          运行间隔时间，默认时间为1000毫秒
     */
    public void setPeriond(long millisecond){
        this.periond = millisecond;
    }


    /**
     * 设置Timer运行延迟时间
     * @param millisecond
     */
    public void setDelay(long millisecond){
        this.delay = millisecond;
    }

    /**
     * 设置Timer是否重复运行
     * @param mRepeat
     *          true：重复运行
     */
    public void setRepeat(boolean mRepeat) {
        isRepeat = mRepeat;
    }

    /**
     * Timer是否重复运行
     * @return
     */
    public boolean isRepeat() {
        return isRepeat;
    }

    /**
     * 启动计时器
     */
    public void start(){
        stop();
        if(mTimer == null || mTimerTask == null) create();
        if(isRepeat()){
            mTimer.schedule(mTimerTask, delay, periond);
        }else{
            mTimer.schedule(mTimerTask, delay);
        }
    }

    public void stop(){
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
        if(mTimerTask != null){
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    public interface Task{
        void doTask();
    }

}
