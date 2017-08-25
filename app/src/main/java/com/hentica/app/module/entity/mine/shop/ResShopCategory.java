package com.hentica.app.module.entity.mine.shop;

/**
 * 店铺行业类型
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/5.16:31
 */

public class ResShopCategory {

    /**
     * id : 1
     * categoryName : KTV
     */

    private int id;
    private String categoryName;
    private String categoryPicUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPicUrl() {
        return categoryPicUrl;
    }

    public void setCategoryPicUrl(String categoryPicUrl) {
        this.categoryPicUrl = categoryPicUrl;
    }
}
