package com.hentica.app.util;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 选中下划线辅助工具<br />
 * 占用了radioGroup的onCheckedChangeListener，请使用 helper.wrapListener(wrapListener)替代
 *
 * @author mili
 * @createTime 2016-7-5 下午7:01:56
 */
public class RadioGroupLineHelper {

    /**
     * 按钮组
     */
    private RadioGroup mRadioGroup;

    /**
     * 下划线视图
     */
    private View mLineView;

    /**
     * 切换事件
     */
    private OnCheckedChangeListener mOnCheckedChangeListener;


    /**
     * 绑定radioGroup
     *
     * @param parent     父节点
     * @param groupId    group节点id
     * @param lineViewId 下划线视图id
     */
    public void bindRadioGroup(View parent, int groupId, int lineViewId) {

        RadioGroup radioGroup = ViewUtil.getHolderView(parent, groupId);
        View lineView = ViewUtil.getHolderView(parent, lineViewId);
        bindRadioGroup(radioGroup, lineView);
    }

    /**
     * 绑定radioGroup
     *
     * @param radioGroup tab
     * @param lineView   下划线视图
     */
    public void bindRadioGroup(RadioGroup radioGroup, View lineView) {

        mRadioGroup = radioGroup;
        mLineView = lineView;
        mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = group.findViewById(checkedId);

                // 设置下划线宽度
                matchWidth(radioButton, mLineView);

                // 设置下划线位置
                alignLeftOnScreen(radioButton, mLineView);

                // 透传事件
                if (mOnCheckedChangeListener != null) {

                    mOnCheckedChangeListener.onCheckedChanged(group, checkedId);
                }
            }
        });
    }

    /**
     * 透传切换事件
     *
     * @param listener
     */
    public void wrapListener(OnCheckedChangeListener listener) {

        mOnCheckedChangeListener = listener;
    }

    /**
     * 把某视图的宽度设置与基准视图一样
     *
     * @param baseView 基准视图
     * @param v        要设置宽度的视图
     */
    private void matchWidth(final View baseView, final View v) {

        if (baseView != null && v != null) {

            v.post(new Runnable() {

                @Override
                public void run() {

                    int width = baseView.getMeasuredWidth();
                    v.getLayoutParams().width = width;
                    v.requestLayout();
                }
            });
        }
    }

    /**
     * 把某视图与基准视图左对齐
     *
     * @param baseView
     * @param v
     */
    private void alignLeftOnScreen(View baseView, View v) {

        int[] baseLocation = new int[2];
        int[] currLocation = new int[2];
        baseView.getLocationOnScreen(baseLocation);
        v.getLocationOnScreen(currLocation);

        int deltaX = baseLocation[0] - currLocation[0];
        v.setX(v.getX() + deltaX);
    }

}
