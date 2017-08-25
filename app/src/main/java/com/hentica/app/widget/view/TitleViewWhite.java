package com.hentica.app.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;


/**
 * Created by YangChen on 2016/10/18 20:44.
 * E-mail:656762935@qq.com
 */

public class TitleViewWhite extends TitleView {

    public TitleViewWhite(Context context) {
        this(context,null);
    }

    public TitleViewWhite(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleViewWhite(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //重新设置界面样式
        resetUI();
    }

    /** 重新设置界面样式 */
    private void resetUI(){
//         左图片按钮
         setLeftImageBtnImg(R.drawable.rebate_public_back3);
        // 标题颜色
        TextView titleView = getTitleTextView();
        titleView.setTextColor(getResources().getColor(R.color.text_white));
        // 右边文字按钮颜色
        TextView rightTextBtn = getRightTextBtn();
        rightTextBtn.setTextColor(getResources().getColor(R.color.text_white));
        // 底部分割线
        TextView bottomLineView = getBottomLineView();
        bottomLineView.setVisibility(View.GONE);
        this.setBackgroundColor(getResources().getColor(R.color.bg_red));
     }
}
