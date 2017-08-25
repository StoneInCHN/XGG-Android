package com.hentica.app.module.index;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

/**
 * Created by YangChen on 2017/3/24 17:17.
 * E-mail:656762935@qq.com
 */

@SuppressLint("ValidFragment")
public class IndexBusinessDetailIntroduceFragment extends BaseFragment {

    /** 营业时段 */
    private String mBusinessTime;
    /** "ALL":全部,"WIFI" :WIFI,"FREE_PARKING":免费停车,null 无 */
    private String mFeaturedService;
    /** 简介 */
    private String mDescription;

    private TextView mBusinessTimeTextView;
    private TextView mServiceTextView;
    private TextView mDesTextView;

    private AQuery mQuery;

    public IndexBusinessDetailIntroduceFragment(){}

    public IndexBusinessDetailIntroduceFragment(String val1, String val2, String val3){
        mBusinessTime = val1;
        mFeaturedService = val2;
        mDescription = val3;
    }

    @Override
    public int getLayoutId() {
        return R.layout.index_business_detail_introduce_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    protected void initData() {
        mQuery = new AQuery(getView());
    }

    @Override
    protected void initView() {
        mBusinessTimeTextView = mQuery.id(R.id.index_business_detail_introduce_time).getTextView();
        mServiceTextView = mQuery.id(R.id.index_business_detail_introduce_other_service).getTextView();
        mDesTextView = mQuery.id(R.id.index_business_detail_introduce_desc).getTextView();
    }

    @Override
    protected void setEvent() {

    }

    private String getDealString(String value, String def){
        return TextUtils.isEmpty(value) ? def : value;
    }

    /** 获取服务描述 */
    private String getServiceDes(String service){
        String des = "";
        if(service == null){
            des = "无";
        }else if(TextUtils.equals("ALL",service)){
            des = "WIFI、免费停车";
        }else if(TextUtils.equals("WIFI",service)){
            des = "WIFI";
        }else if(TextUtils.equals("FREE_PARKING",service)){
            des = "免费停车";
        }
        return des;
    }

    public void setBusinessTime(String mBusinessTime) {
        this.mBusinessTime = mBusinessTime;
        mBusinessTimeTextView.setText(getDealString(mBusinessTime,"无"));
    }

    public void setFeaturedService(String mFeaturedService) {
        this.mFeaturedService = mFeaturedService;
        mServiceTextView.setText(getServiceDes(mFeaturedService));
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
        mDesTextView.setText(getDealString(mDescription,"无"));
    }
}
