package com.hentica.app.module.mine.ui.adapter;

import com.hentica.app.module.entity.mine.ResMineLeDou;
import com.hentica.app.module.entity.type.LeBeanChangeType;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.PriceUtil;
import com.fiveixlg.app.customer.R;
import com.hentica.app.util.UrlUtils;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/31.11:44
 */

public class StatisticsLedouAdapter extends StatisticsNormalAdapter<ResMineLeDou> {

    @Override
    protected void fillView(ViewHolder holder, ResMineLeDou data, int pos) {
        super.fillView(holder, data, pos);
        //积分
        StringBuilder builder = new StringBuilder();
        if(data.getAmount() > 0){
            builder.append("+");
        }
        builder.append(PriceUtil.format4Decimal(data.getAmount()))//返回积分
                .append("\n")
                .append(PriceUtil.format4Decimal(data.getUserCurLeBean()));
        holder.mTvScore.setText(builder.toString());
        //描述
        builder = new StringBuilder();
        //时间
        builder.append(DateHelper.stampToDate(String.valueOf(data.getCreateDate()), "yyyy-MM-dd HH:mm"))//显示时间
                .append("\n");

        setImg(holder.mImg, R.drawable.ic_launcher);//app图标
        holder.mTvName.setText(holder.mTvName.getContext().getResources().getString(R.string.app_name));//app名称

        if (LeBeanChangeType.BONUS.equals(data.getType())) {
            // 乐心分红赠送乐豆
            builder.append("乐心分红赠送乐豆");
        } else if(LeBeanChangeType.ENCOURAGE.equals(data.getType())){
            // 消费赠送鼓励金
            builder.append("消费赠送鼓励金");
        } else if (LeBeanChangeType.CONSUME.equals(data.getType())) {
            // 消费
            builder.append("消费");
        } else if (LeBeanChangeType.TRANSFER.equals(data.getType())) {
            // 乐豆转账
            builder.append(data.getRemark());
            holder.mTvName.setText(data.getRecommender());//好友昵称
            setImg(holder.mImg, UrlUtils.getUrl(data.getRecommenderPhoto()));//好友头像
        }
        holder.mTvDesc.setText(builder.toString());
    }
}
