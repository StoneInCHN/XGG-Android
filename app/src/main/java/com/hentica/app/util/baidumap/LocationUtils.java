package com.hentica.app.util.baidumap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hentica.app.framework.fragment.RequestPermissionResultListener;
import com.hentica.app.util.LogUtils;
import com.hentica.app.util.PermissionHelper;

/**
 * 百度地图定位工具
 * Created by kezhong.
 * E-Mail:396926020@qq.com
 * on 2016/11/11 11:03
 */

public class LocationUtils implements BDLocationListener , RequestPermissionResultListener {

    private static LocationUtils mInstance;
    private LocationClient locationClient;

    private LocationCallBack mCallBack;

    private BDLocation mLocation;

    public BDLocation getLocation(){
        return mLocation;
    }

    private Fragment mFragment;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionHelper.requestPermissionsResult(mFragment, requestCode, permissions, grantResults, permissionGrant);
    }

    public interface LocationCallBack{

        /**
         * 定位成功后回调
         * @param location
         *      返回的定位数据，不会为null
         * @return
         *      true：结束定位，false：继续定位
         */
        boolean callBack(BDLocation location);

    }

    private PermissionHelper.PermissionGrant permissionGrant = new PermissionHelper.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode){
                case PermissionHelper.CODE_ACCESS_COARSE_LOCATION:
                    start();
                    break;
                case PermissionHelper.CODE_ACCESS_FINE_LOCATION:
                    start();
                    break;
            }
        }
    };

    private void setCallBack(LocationCallBack callBack) {
        this.mCallBack = callBack;
    }

    private LocationUtils(Context context){
        locationClient = new LocationClient(context);
        locationClient.registerLocationListener(this);
        locationClient.setLocOption(getDefaultOption());
    }

    /**
     * 创建新实例
     * @param context
     * @param l
     *          定位成功后的回调监听
     * @return
     */
    public static LocationUtils newInstance(Context context, LocationCallBack l){
        if(mInstance != null){
            mInstance.destory();
        }
        mInstance = new LocationUtils(context);
        mInstance.setCallBack(l);
        return mInstance;
    }

    /**
     * 获取实例。
     * 可以通过getLocation()方法获取定位数据
     * @return
     */
    public static LocationUtils getInstance(){
        return mInstance;
    }

    private LocationClientOption getDefaultOption(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=5000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        return option;
    }

    public void startPermission(Fragment fragment){
        mFragment = fragment;
        PermissionHelper.requestPermission(fragment, PermissionHelper.CODE_ACCESS_COARSE_LOCATION, permissionGrant);
    }

    public void start(){
        locationClient.start();
    }

    public void stop(){
        locationClient.stop();
    }

    public void destory(){
        if(locationClient != null){
            locationClient.stop();
            locationClient = null;
        }
        if(mInstance != null){
            mInstance = null;
        }
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        LogUtils.v("Location", "onReceiveLocation");
        if(location == null){
            return;
        }
        mLocation = location;
        if(mCallBack != null){
            boolean stop = mCallBack.callBack(location);
            if(stop){
                stop();
            }
        }
    }
}

//Receive Location
//        StringBuffer sb = new StringBuffer(256);
//        sb.append("time : ");
//        sb.append(location.getTime());
//        sb.append("\nerror code : ");
//        sb.append(location.getLocType());
//        sb.append("\nlatitude : ");
//        sb.append(location.getLatitude());
//        sb.append("\nlontitude : ");
//        sb.append(location.getLongitude());
//        sb.append("\nradius : ");
//        sb.append(location.getRadius());
//        if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
//            sb.append("\nspeed : ");
//            sb.append(location.getSpeed());// 单位：公里每小时
//            sb.append("\nsatellite : ");
//            sb.append(location.getSatelliteNumber());
//            sb.append("\nheight : ");
//            sb.append(location.getAltitude());// 单位：米
//            sb.append("\ndirection : ");
//            sb.append(location.getDirection());// 单位度
//            sb.append("\naddr : ");
//            sb.append(location.getAddrStr());
//            sb.append("\ndescribe : ");
//            sb.append("gps定位成功");
//        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
//            sb.append("\naddr : ");
//            sb.append(location.getAddrStr());
//            //运营商信息
//            sb.append("\noperationers : ");
//            sb.append(location.getOperators());
//            sb.append("\ndescribe : ");
//            sb.append("网络定位成功");
//        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//            sb.append("\ndescribe : ");
//            sb.append("离线定位成功，离线定位结果也是有效的");
//        } else if (location.getLocType() == BDLocation.TypeServerError) {
//            sb.append("\ndescribe : ");
//            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//            sb.append("\ndescribe : ");
//            sb.append("网络不同导致定位失败，请检查网络是否通畅");
//        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//            sb.append("\ndescribe : ");
//            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//        }
//        sb.append("\nlocationdescribe : ");
//        sb.append(location.getLocationDescribe());// 位置语义化信息
//        List<Poi> list = location.getPoiList();// POI数据
//        if (list != null) {
//            sb.append("\npoilist size = : ");
//            sb.append(list.size());
//            for (Poi p : list) {
//                sb.append("\npoi= : ");
//                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
//            }
//        }
//        Log.i("BaiduLocationApiDem", sb.toString());