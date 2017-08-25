package com.hentica.app.module.db;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.lib.util.FileHelper;
import com.hentica.app.lib.util.MD5Util;
import com.hentica.app.lib.util.PhoneInfo;
import com.hentica.app.util.ArrayListUtil;
import com.hentica.app.util.LogUtils;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.util.region.Region;
import com.hentica.appbase.famework.util.ListUtils;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.impl.SingleSQLiteImpl;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置数据，业务层
 *
 * @author mili
 * @createTime 2016/10/21
 */
public class ConfigModel {

    private static final String DB_PATH = "db/area.db";

    private Context context;

    // 单实例
    private static ConfigModel mConfigModel = new ConfigModel();

    // 数据库实例
    private LiteOrm mLiteOrm;

    /**
     * 获取单例
     */
    public static ConfigModel getInstace() {
        return mConfigModel;
    }

    /**
     * 销毁单例
     */
    public void destory() {
        if (mConfigModel != null) {

            if (mConfigModel.mLiteOrm != null) {

                mConfigModel.mLiteOrm.close();
                mConfigModel.mLiteOrm = null;
            }
        }
    }

    // 构造函数
    private ConfigModel() {
        //注册EventBus
//        EventBus.getDefault().register(this);
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        LogUtils.d("ConfigModel", "init");
        this.context = context;
        String assetsPath = DB_PATH;
        String dbPath = ApplicationData.getInstance().getSystemNotTempDir() + DB_PATH;
        String dbPathTmp = ApplicationData.getInstance().getSystemTempDir() + DB_PATH;
        int oldVersion = StorageUtil.getApkVersion();
        int newVersion = PhoneInfo.getAndroidVersioncode();
        if(newVersion > oldVersion && oldVersion > 0) {
            //新版本——更新本地数据库
            //创建临时文件
            //暂时过滤
            File dbFile = new File(dbPath);
            if(dbFile.exists()) {
                File file = new File(dbPathTmp);
                if (file.exists()) {
                    FileHelper.deleteFile(dbPathTmp);
                }
                //复制数据库到临时文件中
                copyAssets(context, assetsPath, dbPathTmp);

                String dbMD5 = MD5Util.getMd5ByFile(dbPath);
                String tmpMD5 = MD5Util.getMd5ByFile(dbPathTmp);
                //判断文件MD5是否相同
                if (dbMD5 != null && tmpMD5 != null && !dbMD5.equals(tmpMD5)) {
                    //不同，复制数据库
                    FileHelper.deleteFile(dbPath);
                }
                FileHelper.deleteFile(dbPathTmp);
                StorageUtil.saveApkVersion(newVersion);
            }
        }
        LogUtils.d("ConfigModel", "start copy dbFile");
        copyAssets(context, assetsPath, dbPath);
        LogUtils.d("ConfigModel", "copy dbFile finish");
        try {
            LogUtils.d("ConfigModel", "open liteorm");
            mLiteOrm = SingleSQLiteImpl.newSingleInstance(context, dbPath);
            LogUtils.d("ConfigModel", "open liteorm finish");
        }catch (Exception e){
            LogUtils.d("ConfigModel", "open liteorm error");
            e.printStackTrace();
            LogUtils.d("ConfigModel", e.getMessage());
        }
    }

    /**
     * 重新加载数据库
     */
    public void reload(){
        String dbPath = ApplicationData.getInstance().getNotTempDir() + DB_PATH;
        if(mLiteOrm != null){
            mLiteOrm = null;
        }
        mLiteOrm = SingleSQLiteImpl.newSingleInstance(context, dbPath);
    }


    /**
     * 获取省份列表
     *
     * @return
     */
    public  List<Region> getProvinceList() {
        return mLiteOrm.query(QueryBuilder.create(Region.class).where("parent IS NULL"));
//        return this.findChildRegions(Constants.CONFIG_DB_COUNTRY_ID_CHINA);
    }

    /**
     * 搜索省份
     * @param key
     * @return
     */
    public List<Region> getProvinceLike(String key){
        return mLiteOrm.query(QueryBuilder.create(Region.class)
                .whereEquals(Region.FIELD_PARENT_ID, Constants.CONFIG_DB_COUNTRY_ID_CHINA)
                .whereAppendAnd()
                .whereAppend("name like ?", "%"+key+"%")
                .appendOrderAscBy(Region.FIELD_SORT)
                .appendOrderAscBy(Region.FIELD_ID)
        );
    }

    /**
     * 获取所有城市
     * @return
     */
    public List<Region> getCityList(){
        List<Region> provinces = getProvinceList();
        List<Region> citys = new ArrayList<>();
        if(!ListUtils.isEmpty(provinces)){
            List<Region> tmpCity = null;
            for(Region province : provinces){
                tmpCity = findChildRegions(province.getId());
                if(!ListUtils.isEmpty(tmpCity)){
                    citys.addAll(tmpCity);
                }
            }
        }
        return citys;
    }

    /**
     * 根据城市名获取地区全部信息
     */
    public Region getCityRegionByName(String name){
        // 获取所有城市
        List<Region> citys = getAllCitys();
        if(!ArrayListUtil.isEmpty(citys)){
            // 遍历城市列表
            for(Region region : citys){
                if(TextUtils.equals(region.getName(),name)){
                    return region;
                }
            }
        }
        return null;
    }

    /**
     * 获取所有城市
     * @return
     */
    public List<Region> getAllCitys(){
        return mLiteOrm.query(QueryBuilder.create(Region.class)
                .whereEquals(Region.FIELD_IS_CITY, Constants.CONFIG_DB_IS_CITY)
//                .appendOrderAscBy(Region.FIELD_SORT)
                .appendOrderAscBy(Region.FIELD_ID)
        );
    }

    /**
     * 查找包含指定字符城市
     * @param key
     *      关键字
     * @return
     */
    public List<Region> queryCitysLike(String key){
        return mLiteOrm.query(QueryBuilder.create(Region.class)
                .whereEquals(Region.FIELD_IS_CITY, Constants.CONFIG_DB_IS_CITY)
                .whereAppendAnd()
                .whereAppend("name like ?", "%"+key+"%")
                .appendOrderAscBy(Region.FIELD_ID)
        );
    }

    public Region getRegionById(String id){
        return mLiteOrm.queryById(id,  Region.class);
    }

    /**
     * 取得某节点下的所有子节点
     *
     * @param parent_id 某节点
     * @return 子节点
     */
    public List<Region> findChildRegions(String parent_id) {

        return mLiteOrm.query(QueryBuilder.create(Region.class)
                .whereEquals(Region.FIELD_PARENT_ID, parent_id)
//                .appendOrderAscBy(Region.FIELD_SORT)
                .appendOrderAscBy(Region.FIELD_ID)
        );
    }

    /**
     * 查找城市下面的对应区县
     * @param cityId 城市id
     * @param districeName 区县名称
     * @return null，未找到相应数据
     */
    public Region findDistrictWithCity(String cityId, String districeName){
        List<Region> regions = findChildRegions(cityId);
        if(regions == null || regions.isEmpty()){
            return null;
        }
        for(Region tmpRegion : regions){
            if(tmpRegion.getName().equals(districeName)){
                return tmpRegion;
            }
        }
        return null;
    }

    // 复制assets文件，仅目标文件不存在时执行
    private static void copyAssets(Context context, String from, String to) {

        File file = new File(to);
        if (!file.exists()) {

            createParentDir(to);
            try {
                FileHelper.copyFile(context.getAssets().open(from), new FileOutputStream(to));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 创建文件夹
    private static void createParentDir(String path) {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            //noinspection ResultOfMethodCallIgnored
            parent.mkdirs();
        }
    }

    @Subscribe
    public void onEvent(DataEvent.OnDBUploadSuccess event){
        Log.i("Config", "数据库更新成功.");
        reload();
    }
}
