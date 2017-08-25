package com.hentica.app.module.mine.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.entity.mine.ResMineRecommendRec;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.StringUtil;
import com.hentica.app.util.TimeUtils;
import com.hentica.app.util.TimerHelper;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

/**
 * 推荐记录Adapter
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/27.14:44
 */

public class RecommendHistoryAdapter extends QuickAdapter<ResMineRecommendRec> {

    @Override
    protected int getLayoutRes(int type) {
        return R.layout.mine_recommend_his_item;
    }

    @Override
    protected void fillView(int position, View convertView, ViewGroup parent, ResMineRecommendRec data) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if(holder == null){
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        //显示数据
        HtmlBuilder builder = HtmlBuilder.newInstance();
        builder.appendNormal("累计收益乐分：").appendRed(PriceUtil.format4Decimal(data.getTotalRecommendLeScore()));
        holder.mTvScore.setText(builder.create());
        String url = "";
        if(TextUtils.isEmpty(data.getEndUser().getSellerName())){
            //会员
            holder.mTvType.setText("会员");
            holder.mTvName.setText(data.getEndUser().getNickName());
            url = ApplicationData.getInstance().getImageUrl(data.getEndUser().getUserPhoto());
        }else{
            //商家
            holder.mTvType.setText("商家");
            holder.mTvName.setText(data.getEndUser().getSellerName());
            url = ApplicationData.getInstance().getImageUrl(data.getEndUser().getUserPhoto());
        }
        //注册日期
        holder.mTvTime.setText("注册时间："+DateHelper.stampToDate(data.getEndUser().getCreateDate()+"", "yyyy-MM-dd HH:mm"));
        //显示头像
        Glide.with(convertView.getContext())
                .load(url)
                .error(R.drawable.rebate_mine_head_bg_acquiescent)
                .override(200, 200)
                .into(holder.mImgIcon);
    }

    private class ViewHolder{

        ImageView mImgIcon;
        TextView mTvName;
        TextView mTvTime;
        TextView mTvScore;
        TextView mTvType;

        public ViewHolder(View view){
            mImgIcon = (ImageView) view.findViewById(R.id.mine_item_img_icon);
            mTvName = (TextView) view.findViewById(R.id.mine_item_tv_name);
            mTvTime = (TextView) view.findViewById(R.id.mine_item_tv_time);
            mTvScore = (TextView) view.findViewById(R.id.mine_item_tv_score);
            mTvType = (TextView) view.findViewById(R.id.mine_item_tv_type);
        }

    }

}
