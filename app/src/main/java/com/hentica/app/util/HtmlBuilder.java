package com.hentica.app.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;

import com.hentica.app.framework.AppApplication;
import com.fiveixlg.app.customer.R;

/**
 * html工具，用于拼接带颜色的文字
 *
 * @author mili
 * @createTime 2016/10/18
 */
public class HtmlBuilder {

    /**
     * 创建一个新的实例
     */
    public static HtmlBuilder newInstance() {
        return new HtmlBuilder();
    }

    // 用于拼接字符串
    private StringBuilder mBuilder = new StringBuilder();

    public HtmlBuilder apendSpace(){
        mBuilder.append("&nbsp;");
        return this;
    }

    /**
     * 添加普通字符串
     */
    public HtmlBuilder append(String str) {

        mBuilder.append(str);
        return this;
    }

    /**
     * 添加蓝色文字
     */
//    public HtmlBuilder appendBlue(String str) {
//
//        return appendColor(str, R.color.text_blue);
//    }

    /**
     * 添加正常颜色文字
     * @param str
     * @return
     */
    public HtmlBuilder appendNormal(String str){
        return appendColor(str, R.color.text_black);
    }

    /** 添加灰色文件 */
    public HtmlBuilder appendGray(String str){
        return appendColor(str, R.color.text_gray);
    }

    /**
     * 添加图片
     *
     * @param res 图片资源
     */
    public HtmlBuilder appendImage(@DrawableRes int res) {

        // <img src="res" />
        mBuilder.append("<img src=\"").append(res).append("\" />");
        return this;
    }

    /**
     * 添加红色文字
     */
    public HtmlBuilder appendRed(String str) {

        return appendColor(str, R.color.text_red);
    }

    /**
     * 添加橙色文字
     * @param str
     * @return
     */
    public HtmlBuilder appendOrange(String str){
        return appendColor(str, R.color.text_orange);
    }

    /**
     * 添加回车
     * @return
     */
    public HtmlBuilder appendNextLine(){
        return append("<br />");
    }

    // 创建最终字符串，直接是html格式
    public CharSequence create() {

        return Html.fromHtml(mBuilder.toString(), mImageGetter, null);
    }

    // 获取图片
    private Html.ImageGetter mImageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            int res = NumberUtil.getInt(source, 0);
            Drawable result = ResourcesCompat.getDrawable(AppApplication.getInstance().getResources(), res, null);
            if (result != null){
                result.setBounds(0, 0, result.getIntrinsicWidth(), result.getIntrinsicHeight());
            }
            return result;
        }
    };


    // 添加带颜色的文字
    public HtmlBuilder appendColor(String str, int colorRes) {

        int color = ResourcesCompat.getColor(AppApplication.getInstance().getResources(), colorRes, null);
        mBuilder.append(String.format("<font color=\"#%06X\">%s</font>", color & 0xFFFFFF, str));
        return this;
    }
}
