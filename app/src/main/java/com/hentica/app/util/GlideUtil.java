package com.hentica.app.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/13.17:26
 */

public class GlideUtil {

    private static final int overrideSize= 400;

    public static void loadHeadIconDefault(Context context, String iconUrl, ImageView imgView ,@DrawableRes int defaultImg){
        Glide.with(context)
                .load(iconUrl)
                .error(defaultImg)
                .override(overrideSize, overrideSize)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .into(imgView);
    }

    public static void loadHeadIcon(Context context, String iconUrl, ImageView imgView){
        Glide.with(context)
                .load(iconUrl)
                .override(overrideSize, overrideSize)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .into(imgView);
    }

}
