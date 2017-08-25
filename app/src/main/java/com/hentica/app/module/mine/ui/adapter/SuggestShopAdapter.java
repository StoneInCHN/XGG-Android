package com.hentica.app.module.mine.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.entity.mine.ResSaleSuggestShopListData;
import com.hentica.app.module.entity.type.ApplyStatus;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.PriceUtil;
import com.hentica.appbase.famework.adapter.QuickAdapter;

/**
 * Created by Snow on 2017/5/23.
 */

public class SuggestShopAdapter extends QuickAdapter<ResSaleSuggestShopListData> {
    @Override
    protected int getLayoutRes(int type) {
        return R.layout.mine_suggest_shop_fragment;
    }

    @Override
    protected void fillView(int position, View convertView, ViewGroup parent, ResSaleSuggestShopListData data) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if(holder == null){
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        //显示数据
        //审核状态
        HtmlBuilder builder = HtmlBuilder.newInstance();
        String applyStatus = data.getSellerApplication().getApplyStatus();
        holder.mTvType.setVisibility(View.VISIBLE);
        if(ApplyStatus.AUDIT_WAITING.equals(applyStatus)){
            //待审核
            holder.mTvType.setText("待审核");
            holder.mTvType.setTextColor(convertView.getResources().getColor(R.color.text_red));
            builder.appendNormal("联系人手机号：").appendNormal(data.getSellerApplication().getContactCellPhone());
        }else if(ApplyStatus.AUDIT_PASSED.equals(applyStatus)){
            //审核通过
            holder.mTvType.setVisibility(View.GONE);
            builder.appendNormal("累计收益乐分：").appendRed(PriceUtil.format4Decimal(data.getTotalRecommendLeScore()));
        }else if(ApplyStatus.AUDIT_FAILED.equals(applyStatus)){
            //审核失败
            holder.mTvType.setText("审核失败");
            holder.mTvType.setTextColor(convertView.getResources().getColor(R.color.text_gray));
            builder.appendNormal("联系人手机号：").appendNormal(data.getSellerApplication().getContactCellPhone());
        }
        holder.mTvScore.setText(builder.create());
        //显示名称
        holder.mTvName.setText(data.getSellerApplication().getSellerName());
        // 注册日期
        holder.mTvTime.setText("注册时间："+ DateHelper.stampToDate(data.getCreateDate()+"", "yyyy-MM-dd HH:mm"));
        //显示商家头像
        Glide.with(convertView.getContext())
                .load(ApplicationData.getInstance().getImageUrl(data.getSellerApplication().getStorePhoto()))
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
