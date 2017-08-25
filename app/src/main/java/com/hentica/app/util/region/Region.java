package com.hentica.app.util.region;


import com.litesuits.orm.db.annotation.Table;

import java.util.List;

/**
 * 地区
 */
@Table("rm_area")
public class Region {

    // 主键
    public static final String FIELD_ID = "id";
    // 父节点
    public static final String FIELD_PARENT_ID = "parent";
    // 排序
    public static final String FIELD_SORT = "orders";

    /** 城市 */
    public static final String FIELD_IS_CITY = "is_city";

    // 主键，自增
//    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
//   @PrimaryKey(AssignType.AUTO_INCREMENT)
    private String id;

    // 排序
    private String orders;
    // 地区名称全名称
    private String full_name;
    // 地区名称
    private String name;
    //路径
    private String tree_path;
    //父节点
    private String parent;
    // 是否是城市
    private String is_city;
    // 地区拼音
    private String py_name;

    private List<Region> mChildren;

    public Region(){}

    public Region(String id,String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTree_path() {
        return tree_path;
    }

    public void setTree_path(String tree_path) {
        this.tree_path = tree_path;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getIs_city() {
        return is_city;
    }

    public void setIs_city(String is_city) {
        this.is_city = is_city;
    }

    public String getPy_name() {
        return py_name;
    }

    public void setPy_name(String py_name) {
        this.py_name = py_name;
    }

    public List<Region> getmChildren() {
        return mChildren;
    }

    public void setmChildren(List<Region> mChildren) {
        this.mChildren = mChildren;
    }
}
