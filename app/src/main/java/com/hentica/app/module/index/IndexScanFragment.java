package com.hentica.app.module.index;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hentica.app.framework.fragment.RequestPermissionResultListener;
import com.hentica.app.lib.util.MD5Util;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.index.IndexScanData;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.PermissionHelper;
import com.hentica.app.widget.dialog.SelfAlertDialog;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.capture.CaptureFragment;
import com.hentica.app.widget.view.capture.QRCodeHelper;
import com.fiveixlg.app.customer.R;

import butterknife.BindView;

/**
 * 扫一扫界面
 *
 * @author 
 * @createTime 2017-03-28 下午14:23:27
 */
public class IndexScanFragment extends BaseFragment {

    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.index_scan_camera_layout)
    FrameLayout mCameraLayout;
    @BindView(R.id.index_scan_scan_img_layout)
    FrameLayout mScanImgLayout;
    @BindView(R.id.index_scan_scan_frame_img)
    ImageView mScanFrameImg;
    @BindView(R.id.index_scan_scan_move_img)
    ImageView mScanLineView;

    /** 扫描界面 */
    private CaptureFragment mScanFragment;

    private double mLatitude;
    private double mLongitude;

    @Override
    public int getLayoutId() {
        return R.layout.index_scan_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    protected void handleIntentData(Intent intent) {
        IntentUtil intentUtil = new IntentUtil(intent);
        mLatitude = intentUtil.getDouble(LATITUDE,-1);
        mLongitude = intentUtil.getDouble(LONGITUDE,-1);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        // 扫描线上下移动
        mScanImgLayout.post(new Runnable() {

            @Override
            public void run() {
                int layoutHeight = mScanImgLayout.getMeasuredHeight();
                int imageHeight = mScanLineView.getMeasuredHeight();

                // 动画，由上至下
                TranslateAnimation animation = new TranslateAnimation(Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -imageHeight / 2,
                        Animation.RELATIVE_TO_PARENT, 1 - imageHeight * 0.5f / layoutHeight);
                animation.setDuration(4000);
                animation.setStartTime(0);
                // 循环播放
                animation.setRepeatCount(Integer.MAX_VALUE);
                animation.setRepeatMode(Animation.REVERSE);
                mScanLineView.startAnimation(animation);
            }
        });

        // 二维码扫描视图
        mScanFragment = new CaptureFragment(new QRCodeHelper.CaptureQRCodeListener() {

            @Override
            public void onResult(String result) {

                handleResult(result);
            }
        });
        mScanFragment.setPreViewFrame(mScanFrameImg);
        getChildFragmentManager().beginTransaction()
                .add(R.id.index_scan_camera_layout, mScanFragment, "扫描框").commit();
    }

    @Override
    protected void setEvent() {

    }

    private void handleResult(String result){
        // 处理扫描结果
        IndexScanData data = ParseUtil.parseObject(result,IndexScanData.class);
        if(data != null){
            // 对比加密信息
            String md5 = MD5Util.MD5("享个购");
            md5 = md5.toLowerCase();
            if(TextUtils.equals(md5,data.getFlag()) && !TextUtils.isEmpty(data.getSellerId())){
                // 跳转到订单界面
                FragmentJumpUtil.toPayFragment(getUsualFragment(),mLatitude,mLongitude,data.getSellerId());
                finish();
            }else {
              showDialog();
            }
        }else {
            showDialog();
        }
    }

    /** 弹出提示框 */
    private void showDialog(){
        final SelfAlertDialog dialog = new SelfAlertDialog();
        dialog.setText("无效的商家列表，是否重新扫描？");
        dialog.setSureClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show(getChildFragmentManager(),"scan");
    }
}
