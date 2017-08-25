package com.hentica.app.util.baidumap;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;

/**
 * 百度地图Geo解析工具
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/8.14:15
 */

public class GeoCodeUtils {

    private GeoCoder mGeoSearch;

    private GeoCodeUtils(){
        initGeoCoder();
    }

    public static GeoCodeUtils newInstance(OnGetGeoCoderResultListener l){
        GeoCodeUtils result = new GeoCodeUtils();
        result.setGtoCoderResultListener(l);
        return result;
    }

    /** 初始化GeoCode解析 */
    private void initGeoCoder(){
        mGeoSearch = GeoCoder.newInstance();
    }

    private void setGtoCoderResultListener(OnGetGeoCoderResultListener l){
        mGeoSearch.setOnGetGeoCodeResultListener(l);
    }

    /**
     * 解析经纬度坐标，获取点地址等信息
     * @param latitude
     * @param longitude
     */
    public void reverseGeoCode(double latitude, double longitude){
        LatLng latLng = new LatLng(latitude, longitude);
        mGeoSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }

    /**
     * 解析经纬度坐标，获取点地址等信息
     * @param options 配置数据
     */
    public void reverseGeoCode(ReverseGeoCodeOption options){
        mGeoSearch.reverseGeoCode(options);
    }

    /**
     * 解析地址信息，获取该地址的经纬度
     * @param city
     * @param address
     */
    public void getGeoCode(String city, String address){
        mGeoSearch.geocode(new GeoCodeOption().city(city).address(address));
    }

    /**
     * 解析地址信息，获取该地址的经纬度
     * @param option 配置数据
     */
    public void getGeoCode(GeoCodeOption option){
        mGeoSearch.geocode(option);
    }

    public void destory(){
        if(mGeoSearch != null) {
            mGeoSearch.destroy();
        }
    }
}
