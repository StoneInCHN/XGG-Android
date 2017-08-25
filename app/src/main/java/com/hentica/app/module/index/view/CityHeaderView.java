package com.hentica.app.module.index.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.lib.view.ChildGridView;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.module.entity.index.IndexCityData;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.region.Region;
import com.hentica.appbase.famework.adapter.QuickAdapter;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by YangChen on 2017/5/8 14:57.
 * E-mail:656762935@qq.com
 */

public class CityHeaderView extends FrameLayout {

    private TextView mLoacationTv;
    private TextView mNotipTv;
    private String mCityName;
    private boolean mHideNotipTv;
    private ChildGridView mHotCityGrid;
    private String mSelectedCityId;
    private LinearLayout mLocateLayout;
    private HotCityAdapter mAdapter;
    private UsualFragment mParent;

    public CityHeaderView(Context context, UsualFragment parent) {
        this(context, null, parent);
    }

    public CityHeaderView(Context context, AttributeSet attrs, UsualFragment parent) {
        this(context, attrs, 0, parent);
    }

    public CityHeaderView(Context context, AttributeSet attrs, int defStyleAttr, UsualFragment parent) {
        super(context, attrs, defStyleAttr);
        mParent = parent;
        initView(context);
    }

    private void initView(Context context){
        View view = View.inflate(context, R.layout.index_select_city_header_view, this);
        if(view != null){
            mLoacationTv = (TextView) view.findViewById(R.id.index_select_city_name);
            mNotipTv = (TextView) view.findViewById(R.id.index_select_city_no_tip);
            mHotCityGrid = (ChildGridView) view.findViewById(R.id.index_select_city_hot_city_grid);
            mLocateLayout = (LinearLayout) view.findViewById(R.id.index_select_city_locate_layout);
            mAdapter = new HotCityAdapter();
            mHotCityGrid.setAdapter(mAdapter);
            if(!TextUtils.isEmpty(mCityName)){
                mLoacationTv.setText(mCityName);
            }
            mNotipTv.setVisibility(mHideNotipTv ? GONE : VISIBLE);
        }
    }

    /** 热门城市列表适配器 */
    private class HotCityAdapter extends QuickAdapter<IndexCityData> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.index_select_hot_city_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, final IndexCityData data) {
            AQuery query = new AQuery(convertView);
            final CheckBox hotCityCheck = query.id(R.id.index_select_city_hot_city_check).getCheckBox();
            boolean isSelected = TextUtils.equals(data.getCityId(),mSelectedCityId);
            hotCityCheck.setChecked(isSelected);
            hotCityCheck.setText(data.getCityName());
            hotCityCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(hotCityCheck.isChecked()){
                        // 查询选中城市
                        Region city = ConfigModel.getInstace().getCityRegionByName(data.getCityName());
                        // 保存城市信息到本地
                        StorageUtil.saveSelectedRegion(city);
                        // 发送选择城市通知
                        EventBus.getDefault().post(new DataEvent.OnSelectedCityEvent());
                        if(mParent != null){
                            mParent.finish();
                        }
                    }else {
                        hotCityCheck.setChecked(!hotCityCheck.isChecked());
                    }
                }
            });
        }
    }

    public void setSelectedCityId(String cityId){
        mSelectedCityId = cityId;
    }

    public void setLoaction(String cityName){
        mCityName = cityName;
        if(mLoacationTv != null){
            mLoacationTv.setText(mCityName);
        }
    }

    public void HideNotipTv(boolean hideNotipTv){
        mHideNotipTv = hideNotipTv;
        if(mNotipTv!= null){
            mNotipTv.setVisibility(mHideNotipTv ? GONE : VISIBLE);
        }
    }

    public void setHotCitys(List<IndexCityData> hotCitys){
        mAdapter.setDatas(hotCitys);
    }

    public void setLocateClicked(OnClickListener listener){
        if(listener != null){
            mLocateLayout.setOnClickListener(listener);
        }
    }
}
