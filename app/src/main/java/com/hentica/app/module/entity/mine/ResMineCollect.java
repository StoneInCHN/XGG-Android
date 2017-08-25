package com.hentica.app.module.entity.mine;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/31.17:59
 */

public class ResMineCollect {



    /**
     * avgPrice : 1
     * latitude : null
     * name : 小面馆
     * rateScore : null
     * id : 1
     * sellerCategory : {"categoryName":"面馆"}
     * longitude : null
     */

    private int avgPrice;
    private String name;
    private double rateScore;
    private int id;
    private SellerCategoryBean sellerCategory;
    private double longitude;
    /**
     * address : null
     * distance : 0.22
     * unitConsume : 100
     * latitude : 30.55418
     * discount : 9.5
     * rateScore : 4
     * storePictureUrl : http:
     * rebateScore : 50
     * longitude : 104.075239
     */
    private String address;
    private String distance;
    private int unitConsume;
    private double latitude;
    private double discount;
    private String storePictureUrl;
    private int rebateScore;

    public int getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(int avgPrice) {
        this.avgPrice = avgPrice;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRateScore() {
        return rateScore;
    }

    public void setRateScore(double rateScore) {
        this.rateScore = rateScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SellerCategoryBean getSellerCategory() {
        return sellerCategory;
    }

    public void setSellerCategory(SellerCategoryBean sellerCategory) {
        this.sellerCategory = sellerCategory;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public int getUnitConsume() {
        return unitConsume;
    }

    public void setUnitConsume(int unitConsume) {
        this.unitConsume = unitConsume;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getStorePictureUrl() {
        return storePictureUrl;
    }

    public void setStorePictureUrl(String storePictureUrl) {
        this.storePictureUrl = storePictureUrl;
    }

    public int getRebateScore() {
        return rebateScore;
    }

    public void setRebateScore(int rebateScore) {
        this.rebateScore = rebateScore;
    }

    public static class SellerCategoryBean {
        /**
         * categoryName : 面馆
         */

        private String categoryName;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }
}
