package com.hentica.app.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.mine.ui.statistics.MineLefenLedouFragment;
import com.hentica.app.util.BitmapUtil;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.StringUtil;
import com.fiveixlg.app.customer.R;

/**
 * Created by YangChen on 2017/4/26 15:37.
 * E-mail:656762935@qq.com
 */

@SuppressLint("ValidFragment")
public class EncourageDialog2 extends DialogFragment {

    /** 关闭按钮 */
    private ImageView mCloseBtn;
    /** 金额 */
    private TextView mAmountTv;
    /** 查看积分 */
    private TextView mSeeScoreTv;

    private AQuery mQuery;

    private double mAmount;
    private UsualFragment mFrom;

    private static final int IMAGE_BTN_CLOSE_MARGIN_TOP = 111;
    private static final int IMAGE_BTN_CLOSE_MARGIN_RIGHT = 71;
    private static final int AMOUNT_LAYOUT_MARGIN_TOP = 400;
    private static final int TIP_LABEL_MARGIN_BOTTOM = 204;
    private static final int TIP_LABEL_SCORE_MARGIN_BOTTOM = 56;

    public EncourageDialog2(UsualFragment from){
        mFrom = from;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.common_dialog_encourage2, container, false);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.setStyle(STYLE_NORMAL, R.style.alert_dialog);
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        return dialog;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化界面
        this.initView();
        this.setEvent();
    }

    private void initView(){
        mQuery = new AQuery(getView());
        mCloseBtn = mQuery.id(R.id.dialog_encourage_title_img_close).getImageView();
        mAmountTv = mQuery.id(R.id.dialog_encourage_price_tv).getTextView();
        mSeeScoreTv = mQuery.id(R.id.dialog_encourage_see_score_tv).getTextView();

        mAmountTv.setText(String.format("%s",PriceUtil.format(mAmount)));
        mSeeScoreTv.setText(StringUtil.getLinkedString("查看累计积分"));

        ViewTreeObserver vbo = getView().getViewTreeObserver();
        vbo.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getView().getViewTreeObserver().removeOnPreDrawListener(this);
                calculateBackgroundSize();
                return true;
            }
        });
    }
    /**
     * 计算背景图片大小
     */
    private void calculateBackgroundSize(){
        float ratio = calculateScreenRatio();
        int[] bgSize = getBackgroundBitmapSize();
        if(ratio == 1 || bgSize[0] == 0 || bgSize[1] == 0){
            return;
        }

        //计算背景图片大小
        View bgView = getView().findViewById(R.id.dlg_encourage_rlayout_normal);
        RelativeLayout.LayoutParams bgViewLp = (RelativeLayout.LayoutParams) bgView.getLayoutParams();
        bgViewLp.width = (int) (bgSize[0] * ratio);
        bgViewLp.height = (int) (bgSize[1] * ratio);

    }

    /**
     * 计算缩放比例
     * @return
     */
    private float calculateScreenRatio(){
        //屏幕大小
        int screenWidth = getScreenWidth();
        //背景图片大小
        int[] bgSize = getBackgroundBitmapSize();
        if(bgSize[0] != 0 && bgSize[1] != 0) {
            //计算背景图片实际大小
            if (screenWidth > bgSize[0]) {
                return 1;
            } else {
                return (float) screenWidth / bgSize[0];
            }
        }
        return 1;
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    private int getScreenWidth(){
        return getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取背景图片大小
     * @return
     */
    private int[] getBackgroundBitmapSize(){
        return BitmapUtil.getBitmapSize(getContext(), R.drawable.rebate_public_remind_frame1);
    }

    private void setEvent(){
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mSeeScoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到乐分界面
                mFrom.startFrameActivity(MineLefenLedouFragment.class);
            }
        });
    }

    public void setEncourageAmount(double amount){
        mAmount = amount;
    }
}
