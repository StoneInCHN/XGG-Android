package com.hentica.app.framework.data;

import android.os.Environment;
import android.text.TextUtils;

import com.hentica.app.framework.AppApplication;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.util.region.Region;
import com.hentica.app.util.rsa.RsaUtils;

import java.util.List;

/**
 * 保存一些全局变量，设置值时，只改变内存中的值，不会存储到本地，也不会请求网络<br />
 * 若要存储到本地，请使用Storage类
 */
public class ApplicationData {

    // 是否是测试模式
    public static final boolean IS_DEBUG = false;
    public static final boolean PRINT_LOG = true;

    // 单实例
    private static ApplicationData sApplicationData;

    // 服务器地址 内网
    private String mServerUrl = "http://118.190.83.191:10001";//测试服务器
//   private String mServerUrl = "http://xgg.wa12580.cn";//正式环境（生产环境？？？）

    public final String mImageBaseUrl = mServerUrl +"/rebate-interface/";
    public final String mApkDownloadUrl = mServerUrl +"/rebate-interface";

    // 主界面是否存在
    private boolean mIsMainLayoutExists;

    private String mWxAppId;
    // 城市数据
    private List<Region> mCitys;

    /** 配置数据 */
    //private MResConfigData mConfigData;

    /** 是否正在登录中（防止登录中其他请求引起掉线） */
    private boolean mIsOnLogin;

    private String mRsaPublicKey;//加密公钥

    private String mServicePhone;//客服电话

    private String mServiceTime;//服务时间

    private boolean mIsCheckSwitchCity = false;//是否确认了切换城市

    // 构造函数
    private ApplicationData() {

    }

    /**
     * 取得全局变量实例
     */
    public static ApplicationData getInstance() {

        if (sApplicationData == null) {

            sApplicationData = new ApplicationData();
        }
        return sApplicationData;
    }

    /**
     * 销毁单实例
     */
    public static void destoryInstance() {

        sApplicationData = null;
    }

    /**
     * 服务器地址
     */
    public String getServerUrl() {
        return mServerUrl;
    }

    /**
     * 取得可写目录，清除缓存时不会清除
     */
    public String getNotTempDir() {

        return Environment.getExternalStorageDirectory().getPath() + "/" + AppApplication.getInstance().getPackageName() + "/real/";
    }

    public String getSystemNotTempDir(){
        return AppApplication.getInstance().getCacheDir().getParentFile().getPath() + "/real/";
    }

    /**
     * 取得可写目录，本应用所有文件操作都在此目录下
     */
    public String getTempDir() {

        return Environment.getExternalStorageDirectory().getPath() + "/" + AppApplication.getInstance().getPackageName() + "/temp/";
    }

    public String getSystemTempDir(){
        return AppApplication.getInstance().getCacheDir() + "/temp/";
    }

    public String getTempPhotoDir(){
        return getTempDir() + "photo/";
    }

    /**
     * 用户是否登录了
     */
    public boolean isLogin() {

        return LoginModel.getInstance().isLogin();
    }

    /**
     * 取得登录用户id
     */
    public String getLoginUserId() {

        return LoginModel.getInstance().getLoginUserId();
    }

    /**
     * 取得登录签名key，用于数据传输加密
     */
    public String getLoginSignKey() {

        return LoginModel.getInstance().getLoginSignKey();
    }

    /**
     * 取得登录session
     */
    public String getLoginSession() {

        return LoginModel.getInstance().getLoginSession();
    }

    /**
     * 登录失效
     *
     * @param isByUser 是否是用户主动退出的
     */
    public void loginInvaild(boolean isByUser) {

        LoginModel.getInstance().logout(isByUser);
    }

    /**
     * 主界面是否存在
     */
    public void setIsMainLayoutExists(boolean isMainLayoutExists) {
        this.mIsMainLayoutExists = isMainLayoutExists;
    }

    public boolean isOnLogin() {
        return mIsOnLogin;
    }

    public void setOnLogin(boolean mOnLogin) {
        mIsOnLogin = mOnLogin;
    }

    /**
     * 主界面是否存在
     */
    public boolean isMainLayoutExists() {
        return mIsMainLayoutExists;
    }

    public String getWxAppId() {
        return mWxAppId;
    }

    public void setWxAppId(String mWxAppId) {
        this.mWxAppId = mWxAppId;
    }

    public void setToken(String token){
        LoginModel.getInstance().setToken(token);
    }

    public String getToken(){
        return LoginModel.getInstance().getToken();
    }

    public String getImageUrl(String url){
        return TextUtils.isEmpty(url) ? "" : mImageBaseUrl + url;
    }

    /** 设置加密密钥 */
    public void setRsaPublicKey(String key){
        this.mRsaPublicKey = key;
        if(!TextUtils.isEmpty(mRsaPublicKey)) {
            RsaUtils.getPublicKey(this.mRsaPublicKey);
        }
    }

    /** 获取加密密钥 */
    public String getRsaPublicKey(){
        return mRsaPublicKey;
    }

    /** 保存城市数据 */
    public void setCitys(List<Region> citys){
        mCitys = citys;
    }

    /** 获取城市数据 */
    public List<Region> getCitys(){
        return mCitys;
    }

    public String getmServicePhone() {
        return mServicePhone;
    }

    public void setmServicePhone(String mServicePhone) {
        this.mServicePhone = mServicePhone;
    }

    public String getmServiceTime() {
        return mServiceTime;
    }

    public void setmServiceTime(String mServiceTime) {
        this.mServiceTime = mServiceTime;
    }

    /** 设置配置数据 */
//    public void setConfigData(MResConfigData  configData){
//        mConfigData = configData;
//    }

    /** 取得配置数据 */
//    public MResConfigData getConfigData(){
//        return mConfigData;
//    }

    public boolean isCheckSwitchCity() {
        return mIsCheckSwitchCity;
    }

    public void setCheckSwitchCity(boolean mCheckSwitchCity) {
        mIsCheckSwitchCity = mCheckSwitchCity;
    }
}
