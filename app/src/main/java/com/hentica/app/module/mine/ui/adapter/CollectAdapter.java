package com.hentica.app.module.mine.ui.adapter;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.entity.mine.ResMineCollect;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/27.16:53
 */

public class CollectAdapter extends QuickAdapter<ResMineCollect> {
    @Override
    protected int getLayoutRes(int type) {
        return R.layout.mine_collect_item;
    }

    @Override
    protected void fillView(int position, View convertView, ViewGroup parent, ResMineCollect data) {
        if(position == 0){
            convertView.findViewById(R.id.mine_item_div_top).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.mine_item_div_normal).setVisibility(View.GONE);
        }else{
            convertView.findViewById(R.id.mine_item_div_top).setVisibility(View.GONE);
            convertView.findViewById(R.id.mine_item_div_normal).setVisibility(View.VISIBLE);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if(holder == null){
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            resizeView(convertView);
        }
        //显示数据
        Glide.with(convertView.getContext())
                .load(ApplicationData.getInstance().getImageUrl(data.getStorePictureUrl()))
                .override(200, 200)
                .into(holder.mImgLogo);// 2017/4/6 图片
        holder.mTvTitle.setText(data.getName());//商家名称
        holder.mTvType.setText(data.getSellerCategory().getCategoryName());//商家类型
        // 2017/4/6 根据经纬度计算距离
        holder.mTvDistance.setText(String.format("%skm", data.getDistance()));
        holder.mTvScore.setText(String.format("%.1f分", data.getRateScore()));//评分
        holder.mTvAverage.setText(String.format("人均%d元", data.getAvgPrice()));//人均消费
        // 2017/4/6 地址
        holder.mTvAddress.setText(data.getAddress());
        // 2017/4/6 消费XXX送XX积分
        holder.mTvDesc.setText(String.format("消费100赠送%d积分", data.getRebateScore()));
    }

    /**
     * 重新计算View大小
     */
    private void resizeView(final View convertView){
        if(convertView == null){
            return;
        }
        convertView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    convertView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                //图片高度
                int imgHeight = convertView.findViewById(R.id.mine_item_img_icon).getHeight();
                //标题布局高度
                int titleHeight = convertView.findViewById(R.id.mine_item_tv_title).getHeight();
                //地点布局高度
                int addressHeight = convertView.findViewById(R.id.mine_item_tv_address).getHeight();
                int height = imgHeight - titleHeight - addressHeight;
                View itemLayout = convertView.findViewById(R.id.layout_info);
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) itemLayout.getLayoutParams();
                lp.height = height;
            }
        });
    }

    private class ViewHolder{

        ImageView mImgLogo;
        TextView mTvTitle;
        TextView mTvType;
        TextView mTvDistance;
        TextView mTvScore;
        TextView mTvAverage;
        TextView mTvAddress;
        TextView mTvDesc;

        public ViewHolder(View view){

            mImgLogo = (ImageView) view.findViewById(R.id.mine_item_img_icon);
            mTvTitle = (TextView) view.findViewById(R.id.mine_item_tv_title);
            mTvType = (TextView) view.findViewById(R.id.mine_item_tv_type);
            mTvDistance = (TextView) view.findViewById(R.id.mine_item_tv_distance);
            mTvScore = (TextView) view.findViewById(R.id.mine_item_tv_score);
            mTvAverage = (TextView) view.findViewById(R.id.mine_item_tv_average);
            mTvAddress = (TextView) view.findViewById(R.id.mine_item_tv_address);
            mTvDesc = (TextView) view.findViewById(R.id.mine_item_tv_description);
        }

    }
}
