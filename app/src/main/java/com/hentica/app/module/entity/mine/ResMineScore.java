package com.hentica.app.module.entity.mine;

/**
 * 积分记录
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/31.11:15
 */

public class ResMineScore {


    /**
     * seller : {"name":"中和加油站","storePictureUrl":"http:"}
     * id : 2
     * rebateScore : 32
     * userCurScore : 200
     * createDate : 1490452089000
     * paymentType : 微信
     */

    private SellerBean seller;
    private int id;
    private double rebateScore;
    private double userCurScore;
    private long createDate;
    private String paymentType;

    public SellerBean getSeller() {
        return seller;
    }

    public void setSeller(SellerBean seller) {
        this.seller = seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRebateScore() {
        return rebateScore;
    }

    public void setRebateScore(double rebateScore) {
        this.rebateScore = rebateScore;
    }

    public double getUserCurScore() {
        return userCurScore;
    }

    public void setUserCurScore(double userCurScore) {
        this.userCurScore = userCurScore;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public static class SellerBean {
        /**
         * name : 中和加油站
         * storePictureUrl : http:
         */

        private String name;
        private String storePictureUrl;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStorePictureUrl() {
            return storePictureUrl;
        }

        public void setStorePictureUrl(String storePictureUrl) {
            this.storePictureUrl = storePictureUrl;
        }
    }
}
