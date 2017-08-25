package com.hentica.app.module.index.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.lib.view.ChildGridView;
import com.hentica.app.module.entity.index.IndexEvaluateListData;
import com.hentica.app.util.ArrayListUtil;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.ViewUtil;
import com.meg7.widget.CircleImageView;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by YangChen on 2017/3/27 16:13.
 * E-mail:656762935@qq.com
 */

public class CommentAdapter extends BaseAdapter {

    private Context mContext;
    private UsualFragment mFrom;
    private List<IndexEvaluateListData> mData = new ArrayList<>();

    private boolean isShowReplay = false;

    public CommentAdapter(Context context, UsualFragment from){
        mContext = context;
        mFrom = from;
    }

    public void showReplay(boolean showReplay) {
        isShowReplay = showReplay;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public IndexEvaluateListData getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.index_business_detail_comment_item,null);
            AQuery query = new AQuery(convertView);
            holder = new ViewHolder();
            holder.mImageView = (CircleImageView) query.id(R.id.index_business_detail_comment_item_head).getView();
            holder.mNameTextView = query.id(R.id.index_business_detail_comment_item_name).getTextView();
            holder.mRatingBar = (RatingBar) query.id(R.id.index_business_detail_comment_item_rating_bar).getView();
            holder.mDateTextView = query.id(R.id.index_business_detail_comment_item_date).getTextView();
            holder.mContentTextView = query.id(R.id.index_business_detail_comment_item_content).getTextView();
            holder.mImageGridView = (ChildGridView) query.id(R.id.index_business_detail_comment_item_img_grid).getView();
            holder.mReplayTextView = (TextView) query.id(R.id.index_business_detail_comment_item_replay).getView();
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        IndexEvaluateListData data = getItem(position);
        // 填充数据
        String url = getHeadUrl(data.getEndUser());
        ViewUtil.bindImage(mContext,holder.mImageView,url, R.drawable.rebate_homepage_banner);
        holder.mNameTextView.setText(getNickName(data.getEndUser()));
        holder.mRatingBar.setRating(data.getScore());
        holder.mDateTextView.setText(DateHelper.stampToDate(data.getCreateDate()+""));
        holder.mContentTextView.setText(data.getContent());
        final ImageAdapter imageAdapter = new ImageAdapter(mContext);
        holder.mImageGridView.setAdapter(imageAdapter);
        imageAdapter.setDatas(data.getEvaluateImages());
        // 图片被点击
        holder.mImageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> imgList = imageAdapter.getImgUrlList();
                if(position < imgList.size() && !ArrayListUtil.isEmpty(imgList)){
                    // 查看大图
                    FragmentJumpUtil.toImageGallery(mFrom, imgList,position);
                }else {
                    mFrom.showToast("图片数据有误！");
                }
            }
        });
        if (isShowReplay) {
            holder.mReplayTextView.setVisibility(TextUtils.isEmpty(data.getSellerReply()) ? View.GONE : View.VISIBLE);
            holder.mReplayTextView.setText(String.format(Locale.getDefault(), "商家回复：%s", data.getSellerReply()));
        } else {
            holder.mReplayTextView.setVisibility(View.GONE);
        }
        return convertView;
    }

    /** 界面缓存 */
    private class ViewHolder{
        // 图片
        CircleImageView mImageView;
        // 昵称
        TextView mNameTextView;
        // 评分
        RatingBar mRatingBar;
        // 日期
        TextView mDateTextView;
        // 内容
        TextView mContentTextView;
        // 图片列表
        ChildGridView mImageGridView;
        // 商家回复
        TextView mReplayTextView;
    }

    public List<IndexEvaluateListData> getData() {
        return mData;
    }

    public void setData(List<IndexEvaluateListData> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        this.mData = mData;
        notifyDataSetChanged();
    }

    /** 获取用户昵称 */
    private String getNickName(IndexEvaluateListData.EndUserBean user){
        return user == null ? "" : user.getNickName();
    }

    /** 获取用户头像 */
    private String getHeadUrl(IndexEvaluateListData.EndUserBean user){
        return user == null ? "" : ApplicationData.getInstance().getImageUrl(user.getUserPhoto());
    }
}
