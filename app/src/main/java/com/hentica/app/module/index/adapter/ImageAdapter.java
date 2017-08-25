package com.hentica.app.module.index.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.entity.index.IndexEvaluateListData;
import com.hentica.app.util.ArrayListUtil;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YangChen on 2017/3/30 17:29.
 * E-mail:656762935@qq.com
 */

public class ImageAdapter extends QuickAdapter<IndexEvaluateListData.EvaluateImagesBean> {

    private Context mContext;

    public ImageAdapter(Context context){
        mContext = context;
    }

    @Override
    protected int getLayoutRes(int type) {
        return R.layout.common_image_grid_item;
    }

    @Override
    protected void fillView(int position, View convertView, ViewGroup parent, IndexEvaluateListData.EvaluateImagesBean data) {
        final AQuery query = new AQuery(convertView);
        final ImageView imageView = query.id(R.id.common_image_grid_img).getImageView();
        final ProgressBar progressBar = query.id(R.id.e07_item_progress).getProgressBar();
        progressBar.setVisibility(View.VISIBLE);
        String url = ApplicationData.getInstance().getImageUrl(data.getSource());
//        ViewUtil.bindImage(mContext,imageView,url,R.drawable.rebate_homepage_banner);
        Glide.with(convertView.getContext())
                .load(url)
                .asBitmap()
                .override(800, 800)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        progressBar.setVisibility(View.GONE);
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    /** 获取图片地址集合 */
    public List<String> getImgUrlList(){
        List<String> imgList = new ArrayList<>();
        if(!ArrayListUtil.isEmpty(getDatas())){
            for(IndexEvaluateListData.EvaluateImagesBean data : getDatas()){
                imgList.add(ApplicationData.getInstance().getImageUrl(data.getSource()));
            }
        }
        return imgList;
    }
}
