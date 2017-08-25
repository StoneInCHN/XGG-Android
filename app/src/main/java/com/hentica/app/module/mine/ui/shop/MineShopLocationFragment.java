package com.hentica.app.module.mine.ui.shop;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.util.baidumap.GeoCodeUtils;
import com.hentica.app.util.baidumap.LocationUtils;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

/**
 * 店铺地址定位
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/8.13:52
 */

public class MineShopLocationFragment extends BaseFragment implements OnGetGeoCoderResultListener{

    public static final String DATA_LATITUDE = "latitude";
    public static final String DATA_LONGITUDE = "longitude";
    public static final String DATA_NAME = "name";

    public static final int REQUEST_CODE = 0x1;

    public static final String RESULT_DATA_ADDRESS = "address";
    public static final String RESULT_DATA_CITY = "city";
    public static final String RESULT_DATA_DISTRICT = "district";
    public static final String RESULT_DATA_LATITUDE = DATA_LATITUDE;
    public static final String RESULT_DATA_LONGITUDE = DATA_LONGITUDE;

    private double mLatitude = 1000;
    private double mLongitude = 1000;
    private String mName = "";
    private MapView mMap;
    private BaiduMap mBaiduMap;
    private GeoCodeUtils codeUtils;

    private String address;
    private String city;
    private String district;
    @Override
    public int getLayoutId() {
        return R.layout.mine_shop_location_fragment;
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
        super.handleIntentData(intent);
        mLatitude = intent.getDoubleExtra(DATA_LATITUDE, 1000);
        mLongitude = intent.getDoubleExtra(DATA_LONGITUDE, 1000);
        mName = intent.getStringExtra(DATA_NAME);
    }

    @Override
    protected void initData() {
        codeUtils = GeoCodeUtils.newInstance(this);
        if(mLatitude == 1000 || mLongitude == 1000){
            //获取当前定位
            BDLocation location = LocationUtils.getInstance().getLocation();
            if(location != null){
                mLatitude = location.getLatitude();
                mLongitude = location.getLongitude();
            }else{
                LocationUtils locationUtils= LocationUtils.newInstance(getContext(), new LocationUtils.LocationCallBack() {
                    @Override
                    public boolean callBack(BDLocation location) {
                        if(location != null && location.getLatitude() != 4.9e-324 && location.getLongitude() != 4.9e-324) {
                            locatPoint(mLatitude, mLongitude);
                        }
                        return true;
                    }
                });
                this.setRequestPermissionResultListener(locationUtils);
                locationUtils.startPermission(this);
            }
        }
    }

    @Override
    protected void initView() {
        mMap = getViews(R.id.mBaiduMap);
        mBaiduMap = mMap.getMap();
        mBaiduMap.setMapStatus(
                MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));
        locatPoint(mLatitude, mLongitude);
        toParseLocation(mLatitude, mLongitude);
        if(!TextUtils.isEmpty(mName)) {
            setViewText(mName, R.id.location_tv_name);
        }
    }

    @Override
    protected void setEvent() {
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
//                获取地图拖动后中心点坐标
                LatLng target = arg0.target;
                mLatitude = target.latitude;
                mLongitude = target.longitude;
                Log.d(TAG, "onMapStatusChangeFinish: \nlatitude=" + mLatitude + "\nlongitude=" + mLongitude);
                //解析坐标
                toParseLocation(mLatitude, mLongitude);
            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {

            }
        });
        //确定按钮
        setViewClickEvent(R.id.location_btn_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLatitude == 1000 || mLongitude == 1000){
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(RESULT_DATA_ADDRESS, address);
                intent.putExtra(RESULT_DATA_CITY, city);
                intent.putExtra(RESULT_DATA_DISTRICT, district);
                intent.putExtra(RESULT_DATA_LATITUDE, mLatitude);
                intent.putExtra(RESULT_DATA_LONGITUDE, mLongitude);
                getActivity().setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * 定位地图
     */
    private void locatPoint(double latitude, double longtitude) {
        LatLng latLng = new LatLng(latitude, longtitude);
        try {
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(latLng));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析地址
     */
    private void toParseLocation(double latitude, double longitude) {
        //解析地址数据
        codeUtils.reverseGeoCode(latitude, longitude);
    }

    /**
     * 设置指示显示的地址内容
     */
    private void setPointerAddress(String address) {
        mQuery.id(R.id.location_tv_address).text(address);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            showToast("抱歉，未能找到结果！");
            return;
        }
        ReverseGeoCodeResult.AddressComponent addressDetail = result.getAddressDetail();
        city = addressDetail.city;
        district = addressDetail.district;
        address = addressDetail.street + addressDetail.streetNumber;
        LatLng location = result.getLocation();
        if (location != null) {
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(location));
        }
        setPointerAddress(result.getAddress());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(codeUtils != null){
            codeUtils.destory();
            codeUtils = null;
        }
    }
}
