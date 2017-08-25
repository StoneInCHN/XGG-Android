package com.hentica.app.module.entity.index;

/**
 * 单个商家数据
 * Created by YangChen on 2017/3/30 10:12.
 * E-mail:656762935@qq.com
 */

public class IndexSellerListData {

    /**
     * business_time : null
     * rebateUserScore : 100
     * address : null
     * distance : 0.24
     * unitConsume : 100
     * latitude : 30.55319
     * sellerName : 中和加油站
     * description : null
     * rateScore : 5
     * avg_price : 220
     * storePictureUrl : http:
     * featured_service : 0
     * categoryName : KTV
     * sellerId : 2
     * storePhone : 1212
     * favorite_num : 500
     * longitude : 104.076231
     * isBeanPay
     */

    private String business_time;
    private String rebateUserScore;
    private String address;
    private String distance;
    private String unitConsume;
    private boolean userCollected;
    private double latitude;
    private String sellerName;
    private String description;
    private float rateScore;
    private String avg_price;
    private String storePictureUrl;
    private String featured_service;
    private String categoryName;
    private long sellerId;
    private String storePhone;
    private int favorite_num;
    private double longitude;
    private boolean isBeanPay;

    public String getBusiness_time() {
        return business_time;
    }

    public void setBusiness_time(String business_time) {
        this.business_time = business_time;
    }

    public String getRebateUserScore() {
        return rebateUserScore;
    }

    public void setRebateUserScore(String rebateUserScore) {
        this.rebateUserScore = rebateUserScore;
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

    public boolean isUserCollected() {
        return userCollected;
    }

    public void setUserCollected(boolean mUserCollected) {
        userCollected = mUserCollected;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRateScore() {
        return rateScore;
    }

    public void setRateScore(float rateScore) {
        this.rateScore = rateScore;
    }

    public String getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(String avg_price) {
        this.avg_price = avg_price;
    }

    public String getStorePictureUrl() {
        return storePictureUrl;
    }

    public void setStorePictureUrl(String storePictureUrl) {
        this.storePictureUrl = storePictureUrl;
    }

    public String getFeatured_service() {
        return featured_service;
    }

    public void setFeatured_service(String featured_service) {
        this.featured_service = featured_service;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public int getFavorite_num() {
        return favorite_num;
    }

    public void setFavorite_num(int favorite_num) {
        this.favorite_num = favorite_num;
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
}
