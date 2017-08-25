package com.hentica.app.module.mine.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.hentica.app.framework.data.ApplicationData;
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

public class StatisticsLefinAdapter extends StatisticsNormalAdapter<ResMineLeFen> {

    @Override
    protected void fillView(ViewHolder holder, ResMineLeFen data, int pos) {
        super.fillView(holder, data, pos);
        //积分
        StringBuilder builder = new StringBuilder();
        //判断是否是提现
        builder.append(LeScoreType.WITHDRAW.equals(data.getLeScoreType()) ? "-" : "+")
                .append(PriceUtil.format4Decimal(data.getAmount()))//返回积分
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
        if(LeScoreType.WITHDRAW.equals(data.getLeScoreType())) {
            //消费直接返商户的收益
            holder.mTvName.setText(data.getEndUser().getNickName());//好友名称
            setImg(holder.mImg, ApplicationData.getInstance().getImageUrl(data.getEndUser().getUserPhoto()));//好友图片
            builder.append(String.format("%s赠送商家乐分", data.getRemark()));// 2017/3/31 支付方式
//        }else if(LeScoreType.BONUS.equals(data.getLeScoreType())){
//            // 乐心（积分）产生的分红
//            holder.mTvName.setText(holder.mTvName.getContext().getResources().getString(R.string.app_name));//app名称
//            setImg(holder.mImg, R.drawable.ic_launcher);//app图标
//            builder.append("乐心分红赠送乐分");
        }else if(LeScoreType.AGENT.equals(data.getLeScoreType())){
            //鼓励金收益
            holder.mTvName.setText(holder.mTvName.getContext().getResources().getString(R.string.app_name));//app名称
            setImg(holder.mImg, R.drawable.ic_launcher);//app图标
            builder.append("消费赠送鼓励金");
        }else if(LeScoreType.RECOMMEND_USER.equals(data.getLeScoreType())){
            // 推荐好友消费返利
            holder.mTvName.setText(data.getRecommender());//好友名称
            setImg(holder.mImg, ApplicationData.getInstance().getImageUrl(data.getRecommenderPhoto()));//好友头像
            builder.append("好友消费赠送乐分");
        }else if(LeScoreType.RECOMMEND_SELLER.equals(data.getLeScoreType())){
            // 推荐店铺收益返利
            holder.mTvName.setText(data.getRecommender());//好友名称
            setImg(holder.mImg, ApplicationData.getInstance().getImageUrl(data.getRecommenderPhoto()));//好友头像
            builder.append("好友收益赠送乐分");
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
                holder.mTvStatus.setText("审核通过");
                holder.mTvStatus.setVisibility(View.VISIBLE);
            }else if(WithdrawStatus.AUDIT_FAILED.equals(data.getWithdrawStatus())){
                holder.mTvStatus.setText("审核退回");
                holder.mTvStatus.setVisibility(View.VISIBLE);
            }
        }
        holder.mTvDesc.setText(builder.toString());
    }
}
