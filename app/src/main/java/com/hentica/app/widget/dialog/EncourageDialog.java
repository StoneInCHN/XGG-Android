package com.hentica.app.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.hentica.app.module.mine.ui.statistics.MineLefenLedouIndexLedouFragment;
import com.hentica.app.util.BitmapUtil;
import com.hentica.app.util.LogUtils;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.StringUtil;
import com.hentica.app.widget.view.ChildDragGridView;
import com.fiveixlg.app.customer.R;

/**
 * Created by YangChen on 2017/4/26 15:37.
 * E-mail:656762935@qq.com
 */

@SuppressLint("ValidFragment")
public class EncourageDialog extends DialogFragment {

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

    public EncourageDialog(UsualFragment from){
        mFrom = from;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.common_dialog_encourage, container, false);
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

        mAmountTv.setText(PriceUtil.format4Decimal(mAmount));
        mSeeScoreTv.setText(StringUtil.getLinkedString("查看累计乐豆"));

        final View background = getView().findViewById(R.id.dlg_encourage_rlayout_normal);
        ViewTreeObserver vbo = background.getViewTreeObserver();
        vbo.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                background.getViewTreeObserver().removeOnPreDrawListener(this);
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
        if(bgSize[0] == 0 || bgSize[1] == 0){
            return;
        }
        //计算背景图片大小
        View bgView = getView().findViewById(R.id.dlg_encourage_rlayout_normal);
        RelativeLayout.LayoutParams bgViewLp = (RelativeLayout.LayoutParams) bgView.getLayoutParams();
        bgViewLp.width = (int) (bgSize[0] * ratio);
        bgViewLp.height = (int) (bgSize[1] * ratio);
        //计算关闭按钮位置
        View closeBtn = getView().findViewById(R.id.dialog_encourage_title_img_close);
        FrameLayout.LayoutParams closeBtnLp = (FrameLayout.LayoutParams) closeBtn.getLayoutParams();
        closeBtnLp.setMargins(0, (int)(ratio * IMAGE_BTN_CLOSE_MARGIN_TOP), (int)(ratio * IMAGE_BTN_CLOSE_MARGIN_RIGHT) , 0);
        //计算金额位置
        View amountLayout = getView().findViewById(R.id.dialog_encourage_amount_layout);
        FrameLayout.LayoutParams amountLayoutLp = (FrameLayout.LayoutParams) amountLayout.getLayoutParams();
        amountLayoutLp.setMargins(0, (int) (ratio * AMOUNT_LAYOUT_MARGIN_TOP), 0, 0);
        //计算提示位置
        View tipLabel1 = getView().findViewById(R.id.dialog_encourage_title_tv);
        FrameLayout.LayoutParams tipLable1Lp = (FrameLayout.LayoutParams) tipLabel1.getLayoutParams();
        tipLable1Lp.setMargins(0, 0, 0, (int) (ratio * TIP_LABEL_MARGIN_BOTTOM));
        //计算“查看积分”提示位置
        View tipLabelScore = getView().findViewById(R.id.dialog_encourage_see_score_tv);
        FrameLayout.LayoutParams tipLabelScoreLp = (FrameLayout.LayoutParams) tipLabelScore.getLayoutParams();
        tipLabelScoreLp.setMargins(0, 0, 0, (int) (ratio * TIP_LABEL_SCORE_MARGIN_BOTTOM));
    }

    /**
     * 计算缩放比例
     * @return
     */
    private float calculateScreenRatio(){
        View background = getView().findViewById(R.id.dlg_encourage_rlayout_normal);
        LogUtils.i("backgroundSize", "Width：" + background.getMeasuredWidth() + " \tHeight：" + background.getMeasuredHeight());
        //屏幕大小
        int screenWidth = getScreenWidth();
        //背景图片大小
        int[] bgSize = getBackgroundBitmapSize();
        if(bgSize[0] != 0 && bgSize[1] != 0) {
            //计算背景图片实际大小
//            if (screenWidth > bgSize[0]) {
//                return 1;
//            } else {
                return Math.min((float) background.getMeasuredWidth() / bgSize[0], (float) background.getMeasuredHeight() / bgSize[1]);
//            }
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
                mFrom.startFrameActivity(MineLefenLedouIndexLedouFragment.class);
                dismiss();
            }
        });
    }

    public void setEncourageAmount(double amount){
        mAmount = amount;
    }

    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }
}
