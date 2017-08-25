package com.hentica.app.module.mine.ui.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hentica.app.util.ViewUtil;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/11.16:29
 */

public class ImageAdapter extends QuickAdapter<String> {

    @Override
    protected int getLayoutRes(int type) {
        return R.layout.mine_fill_evaluate_photo_item;
    }

    @Override
    protected void fillView(int position, View convertView, ViewGroup parent, String data) {
        final AQuery query = new AQuery(convertView);
        query.id(R.id.e07_item_del_photo_btn).gone();
        ViewUtil.bindImage(convertView,R.id.e07_item_photo_img,data);
        //显示加载动画
        query.id(R.id.e07_item_progress).visibility(View.VISIBLE);
        Glide.with(convertView.getContext())
                .load(data)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        query.id(R.id.e07_item_progress).visibility(View.GONE);
                        query.id(R.id.e07_item_photo_img).getImageView().setImageBitmap(resource);
                    }
                });
    }
}