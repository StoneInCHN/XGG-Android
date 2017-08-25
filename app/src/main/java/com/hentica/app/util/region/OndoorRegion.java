package com.hentica.app.util.region;

import com.litesuits.orm.db.annotation.Table;

/**
 * 上门服务地区
 * Created by YangChen on 2016/12/6 14:45.
 * E-mail:656762935@qq.com
 */

@Table("car_pec_config_dooor_to_door_area")
public class OndoorRegion {
    /** id */
    private String id;
    /** 省id */
    private String pro_id;
    /** 市id */
    private String city_id;
    /** 区id */
    private String count_id;
    /** 备注 */
    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String mId) {
        id = mId;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String mPro_id) {
        pro_id = mPro_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String mCity_id) {
        city_id = mCity_id;
    }

    public String getCount_id() {
        return count_id;
    }

    public void setCount_id(String mCount_id) {
        count_id = mCount_id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String mRemarks) {
        remarks = mRemarks;
    }
}
