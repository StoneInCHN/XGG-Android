package com.hentica.app.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hentica.app.lib.storage.Storage;
import com.hentica.app.module.entity.SearchHisData;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.util.region.Region;

import java.util.ArrayList;
import java.util.List;


/**
 * 封装了所有需要本地保存的数据接口
 */
public class StorageUtil {
    public static void hasLogin(){
        new Storage("firstCheck").putBoolean("isFirstUse", false);
    }

    /**
     * 获取是是第1次登录
     * @return
     */
    public static boolean isFirstLogin(){
        return new Storage("firstCheck").getBool("isFirstUse", true);
    }

    /**
     * 保存版本信息
     * @param version
     */
    public static void saveApkVersion(int version){
        new Storage("version").putInt("version", version);
    }

    /**
     * 获取版本信息
     * @return 未获取到返回0
     */
    public static int getApkVersion(){
        return new Storage("version").getInt("version", 0);
    }


    /**
     * 保存最后一次登录参数
     */
    public static void saveLastLoginInfo(UserLoginData loginData) {
        new Storage("loginInfo").putString("loginData", ParseUtil.toJsonString(loginData));
    }

    /**
     * 取得最后一次登录参数
     */
    public static UserLoginData getLastLoginInfo() {

        String result = new Storage("loginInfo").getString("loginData", "");
        return ParseUtil.parseObject(result, UserLoginData.class);
    }

    /** 保存登录token */
    public static void saveLoginToken(String token){
        new Storage("token").putString("token", token);
    }


    /** 获取登录token */
    public static String getLoginToken(){
        return new Storage("token").getString("token", "");
    }


    /** 取得搜索历史记录 */
    public static List<SearchHisData> getSearchHistoryDatas() {

        String jsonString = new Storage("searchHistory").getString("history", "");
        return ParseUtil.parseArray(jsonString, SearchHisData.class);
    }


    /** 保存搜索历史记录 */
    public static void saveSearchHouseHistory(List<SearchHisData> datas) {

        if (datas == null) {

            datas = new ArrayList<SearchHisData>();
        }
        Gson gson = new Gson();
        new Storage("searchHistory").putString("history", gson.toJson(datas));
    }

    /**
     * 清空历史记录
     *
     * @param storageKeys
     *            存储类型key数组：如"search"
     */
    public static void removeSearchHistory(List<String> storageKeys) {
        for (String key : storageKeys) {
            new Storage(key).remove("searchHistory");
        }
    }

    /**
     * 保存选择地区信息
     * @param region
     */
    public static void saveSelectedRegion(Region region){
        if(region == null){
            return;
        }
        new Storage("region").putString("useRegion", new Gson().toJson(region));
    }

    /**
     * 获取选择地区信息
     * @return
     */
    public static Region getSelectedRegion(){
        String regionStr = new Storage("region").getString("useRegion", "");
        if(TextUtils.isEmpty(regionStr)){
            return null;
        }
        return new Gson().fromJson(regionStr, Region.class);
    }

    /**
     * 保存rsa加密公钥
     * @param key
     */
    public static void saveRsaPublickKey(String key){
        new Storage("rsakey").putString("rsaKey", key);
    }

    /**
     * 获取rsa加密公钥
     * @return
     */
    public static String getRsaPublickKey(){
        return new Storage("rsakey").getString("rsaKey", "");
    }

    /**
     * 保存客服电话
     */
    public static void saveServicePhone(String phone){
        new Storage("servicePhone").putString("servicePhone", phone);
    }

    /**
     * 获取客服电话
     * @return
     */
    public static String getServicePhone(){
        return new Storage("servicePhone").getString("servicePhone", "");
    }

    /**
     * 保存服务时间
     * @param time
     */
    public static void saveBusinessTime(String time){
        new Storage("businessTime").putString("businessTime", time);
    }

    /**
     * 获取服务时间
     * @return
     */
    public static String getBusinessTime(){
        return new Storage("businessTime").getString("businessTime", "");
    }

    public static void saveVersionCode(int versionCode){
        new Storage("versionCode").putInt("versionCode", versionCode);
    }

    public static int getVersionCode(){
        return new Storage("versionCode").getInt("versionCode", -1);
    }

    /**
     * 保存登录账号手机号
     * @param mobile
     */
    public static void saveUserMobile(String mobile){
        new Storage("Mobile").putString("Mobile", mobile);
    }

    public static String getUserMobile(){
        return new Storage("Mobile").getString("Mobile", "");
    }

}
