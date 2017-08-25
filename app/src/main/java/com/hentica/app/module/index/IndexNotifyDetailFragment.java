package com.hentica.app.module.index;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.index.IndexNotifyListData;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import butterknife.BindView;

/**
 * Created by YangChen on 2017/4/8 17:38.
 * E-mail:656762935@qq.com
 */

public class IndexNotifyDetailFragment extends BaseFragment {

    public static final String NOTIFYDATA = "NotifyData";

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.index_detail_introduce_title)
    TextView mTitleTv;
    @BindView(R.id.index_detail_introduce_date)
    TextView mDateTv;
    @BindView(R.id.index_detail_introduce_content)
    TextView mContentTv;
    @BindView(R.id.index_detail_introduce_img)
    ImageView mImageView;

    private IndexNotifyListData mNotifyData;

    @Override
    public int getLayoutId() {
        return R.layout.index_detail_introduce_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    protected void handleIntentData(Intent intent) {
        IntentUtil intentUtil = new IntentUtil(intent);
        String json = intentUtil.getString(NOTIFYDATA);
        mNotifyData = ParseUtil.parseObject(json,IndexNotifyListData.class);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTitleView.setTitleText("消息详情");
        mImageView.setVisibility(View.GONE);
        if(mNotifyData != null){
            mTitleTv.setText(mNotifyData.getMessageTitle());
            mDateTv.setText(DateHelper.stampToDate(mNotifyData.getCreateDate()+""));
            mContentTv.setText(mNotifyData.getMessageContent());
        }
    }

    @Override
    protected void setEvent() {

    }
}
