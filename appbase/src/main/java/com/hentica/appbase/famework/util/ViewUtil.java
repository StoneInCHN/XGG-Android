package com.hentica.appbase.famework.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hentica.app.appbase.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 视图工具
 */
public class ViewUtil {

    /**
     * 拖动条进度变化事件
     */
    public interface OnSeekBarProgressChangedListener {

        /**
         * 进度改变了
         *
         * @param progress 新进度
         */
        public void onChanged(int progress);
    }

    /**
     * 绑定指定id的View行视图的点击与指定id的ToggleButton开关按钮关联
     *
     * @param parentView  父视图
     * @param textLineId  文字行id
     * @param switchBtnId 视图行id
     * @param listener    开关事件
     */
    public static void bindLineAndSwitch(View parentView, int textLineId, int switchBtnId,
                                         OnCheckedChangeListener listener) {

        if (parentView != null) {

            View textView = parentView.findViewById(textLineId);
            final ToggleButton switchButton = (ToggleButton) parentView.findViewById(switchBtnId);

            if (textView != null && switchButton != null) {

                textView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        switchButton.toggle();
                    }
                });
                switchButton.setOnCheckedChangeListener(listener);
            }
        }
    }

    /**
     * 绑定一行数据与开关控件
     *
     * @param lineView   行布局
     * @param switchView 开关
     * @param listener   切换事件
     */
    public static void bindLineAndSwitch(View lineView, View switchView,
                                         OnCheckedChangeListener listener) {

        if (lineView != null && switchView != null) {

            final ToggleButton switchButton = (ToggleButton) switchView;

            if (lineView != null && switchButton != null) {

                lineView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        switchButton.toggle();
                    }
                });
                switchButton.setOnCheckedChangeListener(listener);
            }
        }
    }

    /**
     * 绑定拖动条与文字，使文字显示拖动进度
     *
     * @param parentView 共同父节点
     * @param seekbarId  拖动条id
     * @param textId     文字id
     * @param listener   进度改变事件
     */
    public static void bindSeekbarAndText(View parentView, int seekbarId, int textId,
                                          final OnSeekBarProgressChangedListener listener) {

        bindSeekbarAndText(parentView, seekbarId, textId, 0, listener);
    }

    /**
     * 绑定拖动条与文字，使文字显示拖动进度
     *
     * @param parentView 共同父节点
     * @param seekbarId  拖动条id
     * @param textId     文字id
     * @param listener   进度改变事件
     * @param offset     偏移值
     */
    public static void bindSeekbarAndText(View parentView, int seekbarId, int textId,
                                          final int offset, final OnSeekBarProgressChangedListener listener) {

        if (parentView != null) {
            SeekBar seekBar = (SeekBar) parentView.findViewById(seekbarId);
            final TextView textView = (TextView) parentView.findViewById(textId);

            if (seekBar != null && textView != null) {

                seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        int finalValue = seekBar.getProgress() + offset;
                        textView.setText(finalValue + "");

                        if (listener != null) {
                            listener.onChanged(finalValue);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                        int finalValue = seekBar.getProgress() + offset;
                        textView.setText(finalValue + "");

                        if (listener != null) {
                            listener.onChanged(finalValue);
                        }
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        int finalValue = seekBar.getProgress() + offset;
                        textView.setText(finalValue + "");

                        if (listener != null) {
                            listener.onChanged(finalValue);
                        }
                    }
                });
            }
        }
    }

    /**
     * 是否点击到某视图了
     */
    public static boolean isTouchView(MotionEvent ev, View v) {

        if (ev != null && v != null) {
            int[] location = new int[2];
            v.getLocationOnScreen(location);

            float left = location[0];
            float top = location[1];
            float right = left + v.getWidth();
            float bottom = top + v.getHeight();

            float x = ev.getRawX();
            float y = ev.getRawY();

            return left < right && top < bottom && x >= left && x < right && y >= top && y < bottom;
        }
        return false;
    }

    /**
     * 显示光标，并移动到最后位置
     */
    public static void showSelection(EditText editText) {

        if (editText != null) {

            editText.requestFocus();
            editText.setSelection(editText.getText().length());
        }
    }

    /**
     * 为text加上删除线
     */
    public static void setDeleteLine(TextView textview) {

        if (textview != null) {

            textview.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    /**
     * 为text加上下划线
     */
    public static void setUnderLine(TextView textview) {

        if (textview != null) {

            textview.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    /**
     * 绑定图片
     *
     * @param rootView 父视图
     * @param imgId    imageViewId
     * @param url      图片地址
     */
    public static void bindImage(View rootView, int imgId, String url) {

        ImageView imageView = ViewUtil.getHolderView(rootView, imgId);
        Glide.with(rootView.getContext()).load(url).into(imageView);
    }

    /**
     * 绑定图片
     *
     * @param rootView   父视图
     * @param imgId      imageViewId
     * @param url        图片地址
     * @param defaultImg 默认图片
     */
    public static void bindImage(View rootView, int imgId, String url, final int defaultImg) {
        final ImageView imageView = ViewUtil.getHolderView(rootView, imgId);
        if(imageView == null){
            return;
        }
        if(!TextUtils.isEmpty(url)){
            Glide.with(rootView.getContext())
                    .load(url)
                    .override(200,200)  // 限制图片大小
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(imageView);
        }else{
            Glide.with(rootView.getContext())
                    .load(defaultImg)
                    .override(200,200)  // 限制图片大小
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(imageView);
        }
    }

    public static void bindImage(Context context, ImageView imageView, String url, final int defaultImg){
        if(imageView == null){
            return;
        }
        if(!TextUtils.isEmpty(url)){
            Glide.with(context)
                    .load(url)
                    .override(200,200)  // 限制图片大小
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(imageView);
        }else{
            Glide.with(context)
                    .load(defaultImg)
                    .override(200,200)  // 限制图片大小
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(imageView);
        }
    }

    /**
     * 例: ImageView view = ViewUtil.getHolderView(convertView, R.id.imageView);
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T getHolderView(View view, int id) {

        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag(R.id.tagOfViewHolder);

        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(R.id.tagOfViewHolder, viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    /**
     * 取得多级子节点
     */
    public static <T extends View> T getHolderView(View view, int... ids) {

        if (ids != null) {

            ViewGroup parent = (ViewGroup) view;

            for (int i = 0; i < ids.length; i++) {

                int viewId = ids[i];
                if (i < ids.length - 1) {

                    parent = getHolderView(parent, viewId);
                } else {

                    return getHolderView(parent, viewId);
                }
            }
        }
        return null;
    }

    /**
     * 取得文本值，支持 textView editView
     */
    public static String getText(View root, int viewId) {

        View view = getHolderView(root, viewId);

        if (view instanceof TextView) {
            return ((TextView) view).getText().toString();
        }

        return null;
    }

    /**
     * 设置文本值，支持 textView editView
     */
    public static void setText(View root, int viewId, CharSequence text) {

        View view = getHolderView(root, viewId);

        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    /**
     * 显示Toast
     */
    public static void showShortToast(Context context, CharSequence msg) {
        getToast(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast
     */
    public static void showLongToast(Context context, CharSequence msg) {
        getToast(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 生成一个Toast
     */
    public static Toast getToast(Context context, CharSequence msg, int length) {
        return Toast.makeText(context, msg, length);
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context
                .getResources().getDisplayMetrics());
    }

    /**
     * 返回当前屏幕是否为竖屏。
     *
     * @return 当且仅当当前屏幕为竖屏时返回true, 否则返回false。
     */
    public static boolean isScreenOriatationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 设置指定View为全透明
     */
    public static void setBackgroundAlpha(View parent, int viewId) {

        ViewUtil.getHolderView(parent, viewId).getBackground().mutate().setAlpha(0);
    }

    // 用于保存视图id
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * 生成视图id
     *
     * @return 生成的视图id
     */
    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    /** 合并数组 */

    public static <T> List<T> mergeList(List<T> list1, List<T> list2) {

        if (list1 == null) {

            list1 = new ArrayList<T>();
        }

        if (list2 != null) {

            list1.addAll(list2);
        }

        return list1;
    }

    /**
     * 是否保留状态栏
     */
    public static void setKeepStatusBar(View parentView,View statusBar,Context context,boolean keepStatusBar) {
        if (parentView.isInEditMode()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            statusBar.setVisibility(keepStatusBar ? VISIBLE : GONE);
            setStatusBarVisible(parentView,statusBar,context, keepStatusBar);

        } else {

            statusBar.setVisibility(GONE);
        }
    }

    // 设置系统状态栏可见性
    public static void setStatusBarVisible(View parentView,View statusBar,Context context,boolean visible) {

        if (parentView.isInEditMode()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            if (context != null) {

                StatusBarUtil.setTranslucentStatus((Activity) context, visible);
                if (visible) {
                    StatusBarUtil.setTitleHeight(context, statusBar);
                }
            }
        }
    }

    /** 切换字体大小 */
    public static void swichRadioGroupTextSize(Context context, RadioGroup group,@DimenRes int checkedTextSizeId,@DimenRes int normalTextSizeId){
        int checkedId = group.getCheckedRadioButtonId();
        for (int i = 0; i < group.getChildCount(); i++) {
            View view = group.getChildAt(i);
            if(view instanceof RadioButton){
                RadioButton button = (RadioButton) view;
                if (button.getId() == checkedId) {

                    int textSize = context.getResources().getDimensionPixelSize(checkedTextSizeId);
                    button.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                } else {

                    int textSize = context.getResources().getDimensionPixelSize(normalTextSizeId);
                    button.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
            }
        }
    }

}
