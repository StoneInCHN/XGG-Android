package com.hentica.app.module.entity.index;

import java.util.List;

/**
 * Created by YangChen on 2017/3/30 21:16.
 * E-mail:656762935@qq.com
 */

public class IndexBusinessDetailData {

    /**
     * envImgs : ["http://localhost:10001/upload/1.jpg","http://localhost:10001/upload/1.jpg"]
     * name : 花园加油站
     * id : 1
     */

    private String name;
    private long id;
    private List<String> envImgs;
    private boolean userCollected;
    /**
     * address :
     * distance : 0.22
     * unitConsume :
     * avgPrice :
     * latitude : 30.55418
     * description :
     * discount :
     * businessTime :
     * storePictureUrl : http:
     * sellerCategory : {"categoryName":"KTV"}
     * rebateScore :
     * favoriteNum : 230
     * storePhone : 1212
     * featuredService : WIFI
     * longitude : 104.075239
     * isBeanPay
     */

    private String address;
    private String distance;
    private String unitConsume;
    private String avgPrice;
    private double latitude;
    private String description;
    private String discount;
    private String businessTime;
    private String storePictureUrl;
    private float rateScore;
    /**
     * categoryName : KTV
     */

    private SellerCategoryBean sellerCategory;
    private String rebateScore;
    private int favoriteNum;
    private String storePhone;
    private String featuredService;
    private double longitude;
    private boolean isBeanPay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getEnvImgs() {
        return envImgs;
    }

    public void setEnvImgs(List<String> envImgs) {
        this.envImgs = envImgs;
    }

    public boolean isUserCollected() {
        return userCollected;
    }

    public void setUserCollected(boolean mUserCollected) {
        userCollected = mUserCollected;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getUnitConsume() {
        return unitConsume;
    }

    public void setUnitConsume(String unitConsume) {
        this.unitConsume = unitConsume;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public String getStorePictureUrl() {
        return storePictureUrl;
    }

    public void setStorePictureUrl(String storePictureUrl) {
        this.storePictureUrl = storePictureUrl;
    }

    public float getRateScore() {
        return rateScore;
    }

    public void setRateScore(float mRateScore) {
        rateScore = mRateScore;
    }

    public SellerCategoryBean getSellerCategory() {
        return sellerCategory;
    }

    public void setSellerCategory(SellerCategoryBean sellerCategory) {
        this.sellerCategory = sellerCategory;
    }

    public String getRebateScore() {
        return rebateScore;
    }

    public void setRebateScore(String rebateScore) {
        this.rebateScore = rebateScore;
    }

    public int getFavoriteNum() {
        return favoriteNum;
    }

    public void setFavoriteNum(int favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getFeaturedService() {
        return featuredService;
    }

    public void setFeaturedService(String featuredService) {
        this.featuredService = featuredService;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isBeanPay() {
        return isBeanPay;
    }

    public void setBeanPay(boolean beanPay) {
        isBeanPay = beanPay;
    }

    public static class SellerCategoryBean {
        private String categoryName;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }
}
