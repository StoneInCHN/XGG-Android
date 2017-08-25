package com.hentica.app.module.mine.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.entity.mine.Constans;
import com.hentica.app.module.entity.mine.ResMineLeFen;
import com.hentica.app.module.entity.type.LeScoreType;
import com.hentica.app.module.entity.type.WithdrawStatus;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.PriceUtil;
import com.fiveixlg.app.customer.R;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/31.11:44
 */

public class StatisticsLefenAdapter extends StatisticsNormalAdapter<ResMineLeFen> {

    @Override
    protected void fillView(ViewHolder holder, ResMineLeFen data, int pos) {
        super.fillView(holder, data, pos);
        //积分
        StringBuilder builder = new StringBuilder();
        //判断是否是提现
        if (data.getAmount() > 0) {
            builder.append("+");
        }
        builder.append(PriceUtil.format4Decimal(data.getAmount()))//返回积分
                .append("\n")
                .append(PriceUtil.format4Decimal(data.getUserCurLeScore()));
        holder.mTvScore.setText(builder.toString());
        //描述
        builder = new StringBuilder();
        //时间
        builder.append(DateHelper.stampToDate(String.valueOf(data.getCreateDate()), "yyyy-MM-dd HH:mm"))//显示时间
                .append("\n");
        //提醒状态
        holder.mTvStatus.setVisibility(View.GONE);
        //判断类型根据类型
//        if(LeScoreType.ENCOURAGE.equals(data.getLeScoreType())){
//            //鼓励金收益
//            holder.mTvName.setText(holder.mTvName.getContext().getResources().getString(R.string.app_name));//app名称
//            setImg(holder.mImg, R.drawable.ic_launcher);//app图标
//            builder.append("消费赠送鼓励金");
        if(LeScoreType.RECOMMEND_USER.equals(data.getLeScoreType())){
            // 推荐好友消费返利
            holder.mTvName.setText(data.getRecommender());//商家名称
            setImg(holder.mImg, ApplicationData.getInstance().getImageUrl(data.getRecommenderPhoto()));//商家头像
            builder.append("好友消费赠送乐分");
        }else if(LeScoreType.RECOMMEND_SELLER.equals(data.getLeScoreType())){
            // 推荐店铺收益返利
            holder.mTvName.setText(data.getRecommender());//好友名称
            setImg(holder.mImg, ApplicationData.getInstance().getImageUrl(data.getRecommenderPhoto()));//好友头像
            builder.append("业务员发展商家收益赠送乐分");
        }else if(LeScoreType.AGENT.equals(data.getLeScoreType())){
            // 代理商提成
            holder.mTvName.setText(holder.mTvName.getContext().getResources().getString(R.string.app_name));//app名称
            setImg(holder.mImg, R.drawable.ic_launcher);//app图标
            builder.append("代理商提成赠送乐分");
        }else if(LeScoreType.WITHDRAW.equals(data.getLeScoreType())){
            // 提现
            holder.mTvName.setText(holder.mTvName.getContext().getResources().getString(R.string.app_name));//app名称
            setImg(holder.mImg, R.drawable.ic_launcher);//app图标
            builder.append("提现");
            // 2017/3/31 备注
            if(!TextUtils.isEmpty(data.getRemark())){
                builder.append("("+data.getRemark()+")");
            }
            //判断提现状态
            if(WithdrawStatus.AUDIT_WAITING.equals(data.getWithdrawStatus())){
                holder.mTvStatus.setText("待审核");
                holder.mTvStatus.setVisibility(View.VISIBLE);
            }else if(WithdrawStatus.AUDIT_PASSED.equals(data.getWithdrawStatus())){
                if (Constans.Status.PROCESSING.equals(data.getStatus())){
                    holder.mTvStatus.setText("处理中");
                } else if (Constans.Status.SUCCESS.equals(data.getStatus())) {
                    holder.mTvStatus.setText("处理成功");
                } else if (Constans.Status.FAILED.equals(data.getStatus())) {
                    holder.mTvStatus.setText("处理失败");
                } else {
                    holder.mTvStatus.setText("审核通过");
                }
                holder.mTvStatus.setVisibility(View.VISIBLE);
            }else if(WithdrawStatus.AUDIT_FAILED.equals(data.getWithdrawStatus())){
                holder.mTvStatus.setText("审核退回");
                holder.mTvStatus.setVisibility(View.VISIBLE);
            }
        } else if(LeScoreType.CONSUME.equals(data.getLeScoreType())){
            String sellerName = "";
            String sellerPhoto = "";
            if (data.getSeller() != null) {
                sellerName = data.getSeller().getName();
                sellerPhoto = data.getSeller().getStorePictureUrl();
            }
            holder.mTvName.setText(sellerName);//商家名称
            setImg(holder.mImg, ApplicationData.getInstance().getImageUrl(sellerPhoto));//商家头像
            builder.append("消费");
        } else if (LeScoreType.BONUS.equals(data.getLeScoreType())){
            // 乐心（积分）产生的分红
            holder.mTvName.setText(holder.mTvName.getContext().getResources().getString(R.string.app_name));//app名称
            setImg(holder.mImg, R.drawable.ic_launcher);//app图标
            builder.append("乐心分红赠送积分");
        } else if (LeScoreType.TRANSFER.equals(data.getLeScoreType())) {
            //转账
            holder.mTvName.setText(data.getRecommender());
            setImg(holder.mImg, ApplicationData.getInstance().getImageUrl(data.getRecommenderPhoto()));
            builder.append(data.getRemark());
        }
        holder.mTvDesc.setText(builder.toString());
    }
}
