package com.hentica.app.module.mine.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hentica.app.module.entity.mine.ResMineLeXin;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.PriceUtil;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/24.13:44
 */

public class StatisticsLexinAdapter extends QuickAdapter<ResMineLeXin> {
    @Override
    protected int getLayoutRes(int type) {
        return R.layout.mine_statistics_list_item_lexin;
    }

    @Override
    protected void fillView(int position, View convertView, ViewGroup parent, ResMineLeXin data) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if(holder == null){
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        //显示数据
        //乐心
        StringBuilder builder = new StringBuilder();
        StringBuilder descBuilder = new StringBuilder();
        //时间
        descBuilder.append(DateHelper.stampToDate(String.valueOf(data.getCreateDate()), "yyyy-MM-dd HH:mm"))//显示时间
                .append("\n");
        if(data.getScore() == null && data.getAmount() < 0){
            // 分红满限额扣除乐心
            builder.append("-")
                    .append(Math.abs(data.getAmount()));//扣除乐心
//            descBuilder.append("分红满限额扣除乐心");
        }else{
            builder.append("+")
                    .append(PriceUtil.format(data.getAmount()));//返回积分
//            descBuilder.append(String.format("积分：%s", (data.getScore())));//积分
//            descBuilder.append("积分转化乐心");//积分
        }
        descBuilder.append(data.getRemark());
        builder.append("\n").append(data.getUserCurLeMind());
        holder.mTvScore.setText(builder.toString());
        holder.mTvDesc.setText(descBuilder.toString());
    }

    protected class ViewHolder{

        TextView mTvDesc;
        TextView mTvScore;

        public ViewHolder(View view){
            mTvDesc = (TextView) view.findViewById(R.id.mine_tv_time);
            mTvScore = (TextView) view.findViewById(R.id.mine_tv_add_lexin);
        }

    }
}
