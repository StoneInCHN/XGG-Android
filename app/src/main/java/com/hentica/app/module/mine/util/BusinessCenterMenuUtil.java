package com.hentica.app.module.mine.util;

import android.app.ActionBar;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import com.fiveixlg.app.customer.R;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.module.entity.login.ResLoginData;
import com.hentica.app.module.entity.type.AgencyLevel;
import com.hentica.app.module.index.adapter.FilterAdapter;
import com.hentica.app.module.index.impl.DataGetter;
import com.hentica.app.util.region.Region;
import com.hentica.app.widget.view.CustomCheckBox;
import com.hentica.app.widget.view.MaxListView;
import com.hentica.appbase.famework.util.ViewUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by YangChen on 2017/7/4 20:44.
 * E-mail:656762935@qq.com
 */

public class BusinessCenterMenuUtil {
    private Context mContext;
    /**
     * 省
     */
    private CustomCheckBox mProCheck;
    /**
     * 市
     */
    private CustomCheckBox mCityCheck;
    /**
     * 区
     */
    private CustomCheckBox mCountyCheck;
    /**
     * 省id
     */
    private String mProId;
    /**
     * 市id
     */
    private String mCityId;
    /**
     * 区id
     */
    private String mCountyId;
    /**
     * 代理商信息
     */
    private ResLoginData.AgentBean mAgent;

    private OnFilterListener mOnFilterListener;

    public BusinessCenterMenuUtil(Context context, ResLoginData.AgentBean agent) {
        mContext = context;
        mAgent = agent;
    }

    public interface OnFilterListener {
        void onFilter(String areaId);
    }

    /**
     * 绑定视图
     */
    public void bindViews(CustomCheckBox proCheck, CustomCheckBox cityCheck, CustomCheckBox countyCheck) {
        mProCheck = proCheck;
        mCityCheck = cityCheck;
        mCountyCheck = countyCheck;
        bindPopupWindow(mProCheck, AgencyLevel.PROVINCE);
        bindPopupWindow(mCityCheck, AgencyLevel.CITY);
        bindPopupWindow(mCountyCheck, AgencyLevel.COUNTY);
        if (AgencyLevel.PROVINCE.equals(getAgentLevel())) {
            // 省代理
            mProCheck.setBoxEnable(false);
            mProCheck.setText(getAgentAreaName());
            mProId = getAgentAreaId();
        } else if (AgencyLevel.CITY.equals(getAgentLevel())) {
            // 市代理
            mProCheck.setBoxEnable(false);
            mCityCheck.setBoxEnable(false);
            mCityCheck.setText(getAgentAreaName());
            mCityId = getAgentAreaId();
        } else if (AgencyLevel.COUNTY.equals(getAgentLevel())) {
            // 区代理
            mProCheck.setBoxEnable(false);
            mCityCheck.setBoxEnable(false);
            mCountyCheck.setBoxEnable(false);
            mCountyCheck.setText(getAgentAreaName());
            mCountyId = getAgentAreaId();
        }
    }

    /**
     * 为每个控件绑定一个下拉菜单
     */
    private void bindPopupWindow(CustomCheckBox checkBox, final String aLevel) {
        View view = createFilterView();
        MaxListView listView = ViewUtil.getHolderView(view, R.id.shop_business_center_filter_list);
        listView.setListViewHeight(800);
        PopupWindow popupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        FilterAdapter<Region> adapter = new FilterAdapter<Region>(new DataGetter<Region>() {
            @Override
            public String getText(Region data) {
                return data.getName();
            }

            @Override
            public String getValue(Region data) {
                return data.getId();
            }
        });
        listView.setAdapter(adapter);
        final MenuInfo menuInfo = new MenuInfo(listView, adapter, popupWindow);
        checkBox.setTag(menuInfo);
        registClickListener(checkBox, aLevel);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 筛选列表被点击
                Region region = (Region) view.getTag(R.id.tagOfDataId);
                if (region != null) {
                    if (AgencyLevel.PROVINCE.equals(aLevel)) {
                        mProId = region.getId();
                        mCityId = "";
                        mCountyId = "";
                        mProCheck.setText(region.getName());
                        mCityCheck.setText("全市");
                        mCountyCheck.setText("全区");
                    } else if (AgencyLevel.CITY.equals(aLevel)) {
                        mCityId = region.getId();
                        mCountyId = "";
                        mCityCheck.setText(region.getName());
                        mCountyCheck.setText("全区");
                    } else if (AgencyLevel.COUNTY.equals(aLevel)) {
                        mCountyId = region.getId();
                        mCountyCheck.setText(region.getName());
                        String areaId = TextUtils.isEmpty(mCityId) ? mProId : mCityId;
                    }
                    if(mOnFilterListener != null){
                        mOnFilterListener.onFilter(getAreaId());
                    }
                    menuInfo.mPopupWindow.dismiss();

                }
            }
        });
        // 筛选视图背景被点击
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuInfo.mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 为触发控件设置点击事件
     */
    private void registClickListener(final CustomCheckBox checkBox, final String aLevel) {
        if (checkBox != null) {
            checkBox.setOnBoxClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuInfo menuInfo = (MenuInfo) checkBox.getTag();
                    swichCheckStatus(checkBox);
                    if (AgencyLevel.PROVINCE.equals(aLevel)) {
                        // 省代理
                    } else if (AgencyLevel.CITY.equals(aLevel)) {
                        // 查找市列表
                        List<Region> cityList = ConfigModel.getInstace().findChildRegions(mProId + "");
                        if(cityList != null){
                            // 全市
                            Region region = new Region();
                            region.setId("");
                            region.setName("全市");
                            cityList.add(0, region);
                        }
                        menuInfo.mAdapter.setDatas(cityList);
                    } else if (AgencyLevel.COUNTY.equals(aLevel)) {
                        // 查找区列表
                        List<Region> countyList = ConfigModel.getInstace().findChildRegions(mCityId + "");
                        if(countyList != null){
                            // 全区
                            Region region = new Region();
                            region.setId("");
                            region.setName("全区");
                            countyList.add(0, region);
                        }
                        menuInfo.mAdapter.setDatas(countyList);
                    }
                    menuInfo.mPopupWindow.showAsDropDown(checkBox);
                }
            });
        }
    }

    /**
     * 创建一个下拉界面
     */
    private View createFilterView() {
        View view = View.inflate(mContext, R.layout.shop_business_center_filter_view2, null);
        return view;
    }

    /**
     * 获取代理商等级
     */
    private String getAgentLevel() {
        return mAgent == null ? "" : mAgent.getAgencyLevel();
    }

    /**
     * 获取代理商区域名
     */
    private String getAgentAreaName() {
        return mAgent == null ? "" : mAgent.getAreaName();
    }

    /**
     * 获取代理商区域id
     */
    private String getAgentAreaId() {
        return mAgent == null ? "" : mAgent.getAreaId() + "";
    }

    /**
     * 刷新选中状态
     */
    public void swichCheckStatus(CustomCheckBox checkedBox) {
        if (mProCheck.getId() != checkedBox.getId()) {
            mProCheck.setChecked(false);
        }
        if (mCityCheck.getId() != checkedBox.getId()) {
            mCityCheck.setChecked(false);
        }
        if (mCountyCheck.getId() != checkedBox.getId()) {
            mCountyCheck.setChecked(false);
        }
    }

    /**
     * 一个下拉菜单的信息
     */
    private class MenuInfo {

        MaxListView mListView;
        FilterAdapter mAdapter;
        PopupWindow mPopupWindow;

        public MenuInfo(MaxListView listView, FilterAdapter adapter, PopupWindow popupWindow) {
            mListView = listView;
            mAdapter = adapter;
            mPopupWindow = popupWindow;
        }
    }

    public void setOnFilterListener(OnFilterListener mOnFilterListener) {
        this.mOnFilterListener = mOnFilterListener;
    }

    private String getAreaId(){
        String areaId;
        if(!TextUtils.isEmpty(mCountyId)){
            areaId = mCountyId;
        }else if(!TextUtils.isEmpty(mCityId)){
            areaId = mCityId;
        }else {
            areaId = mProId;
        }
        return areaId;
    }
}
