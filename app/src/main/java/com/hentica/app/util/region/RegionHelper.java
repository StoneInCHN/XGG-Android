package com.hentica.app.util.region;

import android.content.Context;

import com.hentica.app.framework.data.Constants;
import com.hentica.app.util.RegionUpdateUtil;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.List;

/**
 * 区域辅助工具
 */
public class RegionHelper {

    // 程序运行环境
    private static Context mContext;

    // orm单例
    private static LiteOrm liteOrm;

    // 初始化
    public static void nit(Context context) {

        mContext = context;
    }

    // 初始化orm
    private static void initOrm() {

        if (liteOrm == null) {
            String dbPath = RegionUpdateUtil.getInstance().getDbPath();
            liteOrm = LiteOrm.newSingleInstance(mContext, dbPath);
        }
    }

    /**
     * 获取省份列表
     *
     * @return
     */
    public static List<Region> getProvinceList() {

        return RegionHelper.findChildRegions(Constants.CONFIG_DB_COUNTRY_ID_CHINA);
    }

    /**
     * 取得某节点下的所有子节点
     *
     * @param parent_id 某节点
     * @return 子节点
     */
    public static List<Region> findChildRegions(String parent_id) {

        initOrm();
        return liteOrm.query(QueryBuilder.create(Region.class)
                .whereEquals(Region.FIELD_PARENT_ID, parent_id)
                .appendOrderAscBy(Region.FIELD_SORT)
                .appendOrderAscBy(Region.FIELD_ID)
        );
    }

    /**
     * 取得指定节点信息
     *
     * @param regionId 指定节点id
     * @return 节点信息
     */
//    public static Region findRegion(String regionId) {
//
//        initOrm();
//        Region region = liteOrm.queryById(regionId, Region.class);
//        region.setChildren(findChildRegions(regionId));
//        return region;
//    }

}
