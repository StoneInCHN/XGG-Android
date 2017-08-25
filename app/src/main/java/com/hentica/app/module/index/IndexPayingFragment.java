package com.hentica.app.module.index;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.event.DataEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * 正在支付界面
 * Created by Snow on 2017/7/10.
 * JUME_TAG跳转标记，1正常买单跳转，2录单跳转
 */

public class IndexPayingFragment extends BaseFragment {

    @BindView(R.id.img_time)
    ImageView mImageView;
    private AnimationDrawable mTimeAnim;
    @BindView(R.id.tv_count)
    TextView mTvCount;

    public static final String JUMP_TAG = "jumpTAg";
    public static final String ORDER_ID = "orderId";
    public static final String ENCOURAGE_AMOUNT = "encourageAmount";
    private int mJumpTag = 1;
    private String mOrderId = "";
    private double encourageAmount = 0;

    @Override
    public int getLayoutId() {
        return R.layout.index_paying_fragment;
    }

    @Override
    protected void handleIntentData(Intent intent) {
        super.handleIntentData(intent);
        mJumpTag = intent.getIntExtra(JUMP_TAG, mJumpTag);
        mOrderId = intent.getStringExtra(ORDER_ID);
        encourageAmount = intent.getDoubleExtra(ENCOURAGE_AMOUNT, encourageAmount);
    }

    @Override
    protected void initData() {
        Drawable drawable = getResources().getDrawable(R.drawable.time_anim);
        mImageView.setBackgroundDrawable(drawable);
        if (drawable instanceof AnimationDrawable) {
            mTimeAnim = (AnimationDrawable) drawable;
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onStart() {
        super.onStart();
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTimeAnim.start();
                startCountDownTimer(5);
            }
        }, 20);
    }

    private void startCountDownTimer(int duration){
        CountDownTimer timer = new CountDownTimer(duration * 1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished > 0) {
                    long seconds = (long) Math.ceil(millisUntilFinished / 1000f);
                    mTvCount.setText(String.valueOf(seconds));
                }
            }

            @Override
            public void onFinish() {
                mTvCount.setText(String.valueOf(0));
                if (mJumpTag == 1) {
                    toOrderDetail();
                } if (mJumpTag == 2) {
                    toOrderManager();
                }
                finish();
            }
        };
        timer.start();
    }

    /**
     *  跳转订单详情
     */
    private void toOrderDetail(){
        EventBus.getDefault().post(new DataEvent.OnPaySuccess());
        FragmentJumpUtil.toOrderDetail(getUsualFragment(), mOrderId, encourageAmount);
        finish();
    }

    /**
     * 跳转订单管理
     */
    private void toOrderManager(){
        FragmentJumpUtil.toShopRecordOrders(getUsualFragment(), null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
