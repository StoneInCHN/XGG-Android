package com.hentica.app.module.index.adapter;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.entity.index.IndexSellerListData;
import com.hentica.app.util.ViewUtil;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页商家列表适配器
 * Created by YangChen on 2017/3/24 10:14.
 * E-mail:656762935@qq.com
 */

public class BusinessAdapter extends BaseAdapter{

    private Context mContext;
    private List<IndexSellerListData> mDatas = new ArrayList<>();

    public BusinessAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public IndexSellerListData getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.index_main_business_list_item,null);
            AQuery query = new AQuery(convertView);
            holder = new ViewHolder();
            holder.mIconView = query.id(R.id.index_main_business_list_item_img).getImageView();
            holder.mNameTextView = query.id(R.id.index_main_business_list_item_name).getTextView();
            holder.mDistanceTextView = query.id(R.id.index_main_business_list_item_distance).getTextView();
            holder.mLabelTextView = query.id(R.id.index_main_business_list_item_label).getTextView();
            holder.mScoreTextView = query.id(R.id.index_main_business_list_item_score).getTextView();
            holder.mPriceTextView = query.id(R.id.index_main_business_list_item_price).getTextView();
            holder.mLocationTextView = query.id(R.id.index_main_business_list_item_location).getTextView();
            holder.mDiscountTextView = query.id(R.id.index_main_business_list_item_discount).getTextView();
            holder.mLabelBeanPay = query.id(R.id.tv_bean_pay).getView();
            convertView.setTag(holder);
            resizeView(convertView);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 填充数据
        IndexSellerListData data = getItem(position);
        String url = ApplicationData.getInstance().getImageUrl(data.getStorePictureUrl());
        ViewUtil.bindImage(mContext,holder.mIconView,url,R.drawable.rebate_homepage_banner);
        holder.mNameTextView.setText(data.getSellerName());
        holder.mDistanceTextView.setText(getDistance(data.getDistance()));
        holder.mLabelTextView.setText(data.getCategoryName());
        holder.mScoreTextView.setText(data.getRateScore()+"分");
        holder.mPriceTextView.setText(getAvgPrice(data));
        holder.mLocationTextView.setText(data.getAddress());
        holder.mDiscountTextView.setText(String.format("消费%s元赠送%s积分",data.getUnitConsume(),data.getRebateUserScore()));
        holder.mLabelBeanPay.setVisibility(data.isBeanPay() ? View.VISIBLE : View.GONE);
        return convertView;
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
                //信息外面布局高度
                int layoutInfoHeight = convertView.findViewById(R.id.layout_info).getHeight();
                //标题布局高度
                int layoutInfoTitleHeight = convertView.findViewById(R.id.index_main_business_list_item_layout1).getHeight();
                //地点布局高度
                int layoutInfoLocationheight = convertView.findViewById(R.id.index_main_business_list_item_location).getHeight();
                int height = layoutInfoHeight - layoutInfoTitleHeight - layoutInfoLocationheight;
                View itemLayout = convertView.findViewById(R.id.index_main_business_list_item_layout2);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) itemLayout.getLayoutParams();
                lp.height = height;
            }
        });
    }

    /** 界面缓存 */
    private class ViewHolder{
        // 图标
        ImageView mIconView;
        // 标题
        TextView mNameTextView;
        // 距离
        TextView mDistanceTextView;
        // 标签
        TextView mLabelTextView;
        // 评分
        TextView mScoreTextView;
        // 价格
        TextView mPriceTextView;
        // 位置
        TextView mLocationTextView;
        // 优惠
        TextView mDiscountTextView;
        // 乐豆抵扣
        View mLabelBeanPay;
    }

    public List<IndexSellerListData> getDatas() {
        return mDatas;
    }

    public void setDatas(List<IndexSellerListData> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    /** 获取人均消费 */
    private String getAvgPrice(IndexSellerListData data){
        String avgPrice = TextUtils.isEmpty(data.getAvg_price()) ? "1" : data.getAvg_price();
        return String.format("人均%s元",avgPrice);
    }

    /** 获取距离 */
    private String getDistance(String distance){
        return distance == null ? "" : distance+"km";
    }
}
