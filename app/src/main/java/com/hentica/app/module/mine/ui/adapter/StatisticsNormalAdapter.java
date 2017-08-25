package com.hentica.app.module.mine.ui.adapter;

import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

/**
 * 统计一般样式item的adapter，适用于积分、乐分、乐豆
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/24.11:10
 */

public class StatisticsNormalAdapter<T> extends QuickAdapter<T> {
    @Override
    protected int getLayoutRes(int type) {
        return R.layout.mine_statistics_list_item_common;
    }

    @Override
    protected final void fillView(int position, View convertView, ViewGroup parent, T data) {
        if(position == 0){
            convertView.findViewById(R.id.mine_statistics_top_div_1).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.mine_statistics_top_div_2).setVisibility(View.GONE);
        }else{
            convertView.findViewById(R.id.mine_statistics_top_div_1).setVisibility(View.GONE);
            convertView.findViewById(R.id.mine_statistics_top_div_2).setVisibility(View.VISIBLE);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if(holder == null){
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        fillView(holder, data, position);
    }

    protected void fillView(ViewHolder holder, T data, int pos){};

    protected void setImg(ImageView img, String url){
        Glide.with(img.getContext()).load(url).override(200, 200).error(R.drawable.rebate_mine_head_bg_acquiescent)
                .into(img);
    }

    protected void setImg(ImageView img, @DrawableRes int id){
        Glide.with(img.getContext()).load(id).override(200, 200).error(R.drawable.ic_launcher)
                .into(img);
    }

    protected class ViewHolder{

        ImageView mImg;
        TextView mTvName;
        TextView mTvStatus;
        TextView mTvDesc;
        TextView mTvScore;

        public ViewHolder(View view){
            mImg = (ImageView) view.findViewById(R.id.item_img_icon);
            mTvName = (TextView) view.findViewById(R.id.item_tv_name);
            mTvStatus = (TextView) view.findViewById(R.id.item_tv_status);
            mTvDesc = (TextView) view.findViewById(R.id.mine_tv_time);
            mTvScore = (TextView) view.findViewById(R.id.mine_tv_add_lexin);
        }

    }
}
