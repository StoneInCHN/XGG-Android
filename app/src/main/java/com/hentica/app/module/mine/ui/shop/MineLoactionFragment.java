package com.hentica.app.module.mine.ui.shop;

import android.content.Intent;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.framework.AppApplication;
import com.hentica.app.module.entity.index.IndexBusinessDetailData;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.IntentUtil;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.widget.dialog.NavigateDialog;
import com.hentica.app.widget.view.InfoWindowView;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import butterknife.BindView;

/**
 * 地址定位界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineLoactionFragment extends BaseFragment {

    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    public static final String BUSINESSDATA = "BusinessData";

    @BindView(R.id.common_title)
    TitleView mTitleView;
    @BindView(R.id.mine_location_map_view)
    MapView mMapView;
    /** 地图 */
    private BaiduMap mBaiduMap;
    /** 用户经度 */
    private double mLatitude;
    /** 用户纬度 */
    private double mLongitude;
    /** 商家详情信息 */
    private IndexBusinessDetailData mBusinessData;
    private InfoWindowView mInfoWindowView;
    @Override
    public int getLayoutId() {
        return R.layout.mine_location_fragment;
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
        mLatitude = intentUtil.getDouble(LATITUDE, -1);
        mLongitude = intentUtil.getDouble(LONGITUDE, -1);
        String json = intentUtil.getString(BUSINESSDATA);
        mBusinessData = ParseUtil.parseObject(json,IndexBusinessDetailData.class);
    }

    @Override
    protected void initData() {

    }

    /** 获取商家位置经度 */
    private double getBusinessLatitude(){
        return mBusinessData == null ? -1 : mBusinessData.getLatitude();
    }

    /** 获取商家位置纬度 */
    private double getBusinessLongitude(){
        return mBusinessData == null ? -1 : mBusinessData.getLongitude();
    }

    /** 获取商家地址 */
    private String getAddress(){
        return mBusinessData == null ? "" : mBusinessData.getAddress();
    }

    /** 获取距离 */
    private String getDistance(){
        return mBusinessData == null ? "" : mBusinessData.getDistance()+"km";
    }

    @Override
    protected void initView() {
        mBaiduMap = mMapView.getMap();
        addMakerOverlay(R.drawable.rebate_homepage_map_locating_mine,mLatitude,mLongitude);
        addMakerOverlay(R.drawable.rebate_homepage_map_locating_seller,getBusinessLatitude(),getBusinessLongitude());
        LatLng latLng = new LatLng(getBusinessLatitude(), getBusinessLongitude());
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(latLng));
        mInfoWindowView = new InfoWindowView(AppApplication.getInstance().getApplicationContext());
        mInfoWindowView.setAddress(getAddress());
        mInfoWindowView.setDistance(getDistance());
        mInfoWindowView.setListener(new InfoWindowView.OnLocationClickListener() {
            @Override
            public void onLocation() {
                //弹出选择对话框
                NavigateDialog dialog = new NavigateDialog();
                dialog.setBaiduListener(new NavigateDialog.OnBaiduMapListener() {
                    @Override
                    public void toBaiduMap() {
                        FragmentJumpUtil.startNavigationApp(getUsualFragment(), getBusinessLongitude(), getBusinessLatitude(), 1);
                    }
                });

                dialog.setGaodeListener(new NavigateDialog.OnGaodeMapListener() {
                    @Override
                    public void toGaodeMap() {
                        FragmentJumpUtil.startNavigationApp(getUsualFragment(), getBusinessLongitude(), getBusinessLatitude(), 2);
                    }
                });

                dialog.show(getChildFragmentManager(),"选择地图");
            }
        });
        initInfoWindow(mInfoWindowView,getBusinessLatitude(),getBusinessLongitude());
    }

    /** 添加锚点 */
    private void addMakerOverlay(int res,double latitude, double longitude){
        LatLng la = new LatLng(latitude, longitude);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(res);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(la)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    /** 初始化覆盖物 */
    private void initInfoWindow(View view, double latitude, double longitude){
        LatLng latLng = new LatLng(latitude,longitude);
        InfoWindow infoWindow = new InfoWindow(view,latLng,-170);
        mBaiduMap.showInfoWindow(infoWindow);
    }

    @Override
    protected void setEvent() {

    }

}
