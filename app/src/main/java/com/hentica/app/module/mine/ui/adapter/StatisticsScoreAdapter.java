package com.hentica.app.module.mine.ui.adapter;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.entity.mine.ResMineScore;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.PriceUtil;
import com.fiveixlg.app.customer.R;

/**
 * 积分记录adpater
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/31.11:44
 */

public class StatisticsScoreAdapter extends StatisticsNormalAdapter<ResMineScore> {

    @Override
    protected void fillView(ViewHolder holder, ResMineScore data, int pos) {
        super.fillView(holder, data, pos);
        boolean isAdd = !(data.getSeller() == null || TextUtils.isEmpty(data.getPaymentType()));
        //名称
        String name = isAdd ? data.getSeller().getName() : holder.mTvName.getContext().getResources().getString(R.string.app_name);
        holder.mTvName.setText(name);
        if(isAdd) {
            setImg(holder.mImg, ApplicationData.getInstance().getImageUrl(data.getSeller().getStorePictureUrl()));
        }else{
            setImg(holder.mImg, R.drawable.ic_launcher);
        }
        StringBuilder builder = new StringBuilder();
        //积分//2017/4/14 显示积分转换乐心记录显示(-)
        builder.append(isAdd ? "+" : "-")
                .append(PriceUtil.format4Decimal(Math.abs(data.getRebateScore())))//返回积分
                .append("\n")
                .append(PriceUtil.format4Decimal(data.getUserCurScore()));
        holder.mTvScore.setText(builder.toString());
        //描述
        builder = new StringBuilder();
        //时间
        builder.append(DateHelper.stampToDate(String.valueOf(data.getCreateDate()), "yyyy-MM-dd HH:mm"))//显示时间
                .append("\n")
                .append(isAdd ? String.format("%s赠送积分", data.getPaymentType()) : "积分转化乐心");//方式
        holder.mTvDesc.setText(builder.toString());
    }
}
