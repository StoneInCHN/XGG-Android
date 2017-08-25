package com.hentica.app.module.entity.mine.shop;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/6.11:19
 */

public class ResShopInfo {


    /**
     * address : sdsdsds
     * avgPrice : 12
     * latitude : 12
     * description : dfdfdfdfdfdfdfdfdfdf
     * discount : 9.5
     * businessTime : 8:00-22:00
     * storePictureUrl : http:
     * favoriteNum : 230
     * envImgs : []
     * storePhone : 1212
     * name : 店铺名称
     * featuredService : WIFI
     * totalOrderNum : 12
     * totalOrderAmount : 1000.5
     * unClearingAmount : 500
     * id : 1
     * longitude : 12
     * area : {"id":"2288"}
     */



    private String address;
    private double avgPrice;
    private double latitude;
    private String description;
    private double discount;
    private String businessTime;
    private String storePictureUrl;
    private int favoriteNum;
    private String storePhone;
    private String name;
    private String featuredService;
    private int totalOrderNum;
    private double totalOrderAmount;
    private double unClearingAmount;
    private int id;
    private double longitude;
    private boolean isAuth;
    private boolean isOwnBankCard;
    private List<String> envImgs;

    private AreaBean area;
    /**
     * avgPrice : 0.1
     * latitude : 30.5574
     * discount : 9.0
     * totalOrderAmount : 7209.558
     * unClearingAmount : 6272.73
     * longitude : 104.069053
     * envImgs : []
     * area : {"id":2284}
     * limitAmountByDay : 0.0
     * totalSellerOrderAmount : 0.0
     * curLimitAmountByDay : 200.0
     * totalSellerOrderNum : 0
     * isBeanPay : true
     */

    private double limitAmountByDay;
    private double totalSellerOrderAmount;
    private double curLimitAmountByDay;
    private int totalSellerOrderNum;
    private boolean isBeanPay;

    public boolean isOwnBankCard() {
        return isOwnBankCard;
    }

    public void setOwnBankCard(boolean ownBankCard) {
        isOwnBankCard = ownBankCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeaturedService() {
        return featuredService;
    }

    public void setFeaturedService(String featuredService) {
        this.featuredService = featuredService;
    }

    public int getTotalOrderNum() {
        return totalOrderNum;
    }

    public void setTotalOrderNum(int totalOrderNum) {
        this.totalOrderNum = totalOrderNum;
    }

    public double getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(double totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public double getUnClearingAmount() {
        return unClearingAmount;
    }

    public void setUnClearingAmount(double unClearingAmount) {
        this.unClearingAmount = unClearingAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getEnvImgs() {
        return envImgs;
    }

    public void setEnvImgs(List<String> envImgs) {
        this.envImgs = envImgs;
    }

    public AreaBean getArea() {
        return area;
    }

    public void setArea(AreaBean area) {
        this.area = area;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public double getLimitAmountByDay() {
        return limitAmountByDay;
    }

    public void setLimitAmountByDay(double limitAmountByDay) {
        this.limitAmountByDay = limitAmountByDay;
    }

    public double getTotalSellerOrderAmount() {
        return totalSellerOrderAmount;
    }

    public void setTotalSellerOrderAmount(double totalSellerOrderAmount) {
        this.totalSellerOrderAmount = totalSellerOrderAmount;
    }

    public double getCurLimitAmountByDay() {
        return curLimitAmountByDay;
    }

    public void setCurLimitAmountByDay(double curLimitAmountByDay) {
        this.curLimitAmountByDay = curLimitAmountByDay;
    }

    public int getTotalSellerOrderNum() {
        return totalSellerOrderNum;
    }

    public void setTotalSellerOrderNum(int totalSellerOrderNum) {
        this.totalSellerOrderNum = totalSellerOrderNum;
    }

    public boolean isIsBeanPay() {
        return isBeanPay;
    }

    public void setIsBeanPay(boolean isBeanPay) {
        this.isBeanPay = isBeanPay;
    }

    public static class AreaBean {
        /**
         * id : 2288
         */

        @SerializedName("id")
        private String idX;

        public String getIdX() {
            return idX;
        }

        public void setIdX(String idX) {
            this.idX = idX;
        }
    }
}
