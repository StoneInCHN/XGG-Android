package com.hentica.app.module.manager.timedown;

import android.os.CountDownTimer;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/30.10:40
 */

public class TimeDownManager {

    private long duration;//持续时间
    private long step;//间隔

    private CountDownTimer timer;

    public TimeDownManager(long duration, long step) {
        this.duration = duration;
        this.step = step;
        initTimer();
    }

    protected void initTimer(){
        timer = new CountDownTimer(duration, step) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeDownManager.this.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                TimeDownManager.this.onFinish();
            }
        };
    }

    /**
     * 开始
     */
    public final void start() {
        if(duration > 0){
            timer.start();
            onStart();
        }
    }

    /**
     * 倒计时开始调用此方法
     */
    public void onStart() {
    }

    /**
     * 倒计时进行中调用此方法
     */
    public void onTick(long millisUntilFinished) {
    }

    /**
     * 倒计时结束回调
     */
    public void onFinish() {
    }
}
