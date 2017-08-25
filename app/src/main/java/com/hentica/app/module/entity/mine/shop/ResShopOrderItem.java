package com.hentica.app.module.entity.mine.shop;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/6.13:59
 */

public class ResShopOrderItem {

    /**
     * seller : {"name":"是的是的从"}
     * endUser : {"nickName":"Andrea","cellPhoneNum":"15902823856","userPhoto":null}
     * amount : double
     * remark : null
     * id : 2
     * sn : 2017654331
     * userScore : 100
     *
     * evaluate : {}
     * createDate : 1493267715000
     * status : PAID
     * 对于每条order记录，如果"status": "PAID"，表示用户已支付，订单待评价；
     * 如果"status": "FINISHED"，"evaluate": {"sellerReply": null},表示用户已评价，商家未回复；如果"status": "FINISHED"，"evaluate": {"sellerReply": "不为null"},表示商已回复用户评价
     * sellerScore
     * rebateAmount : 10
     * isSallerOrder : true
     * isClearing : false
     */
    private SellerBean seller;
    private EndUserBean endUser;
    private double amount;
    private String remark;
    private int id;
    private String sn;
    private double userScore;
    private EvaluateBean evaluate;
    private long createDate;
    private String status;
    private double sellerScore;

    private double rebateAmount;
    private boolean isSallerOrder;
    private boolean isClearing;


    public SellerBean getSeller() {
        return seller;
    }

    public void setSeller(SellerBean seller) {
        this.seller = seller;
    }

    public EndUserBean getEndUser() {
        return endUser;
    }

    public void setEndUser(EndUserBean endUser) {
        this.endUser = endUser;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public double getUserScore() {
        return userScore;
    }

    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    public EvaluateBean getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(EvaluateBean evaluate) {
        this.evaluate = evaluate;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSellerScore() {
        return sellerScore;
    }

    public void setSellerScore(double sellerScore) {
        this.sellerScore = sellerScore;
    }

    public double getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(double rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    public boolean isIsSallerOrder() {
        return isSallerOrder;
    }

    public void setIsSallerOrder(boolean isSallerOrder) {
        this.isSallerOrder = isSallerOrder;
    }

    public boolean isIsClearing() {
        return isClearing;
    }

    public void setIsClearing(boolean isClearing) {
        this.isClearing = isClearing;
    }

    public static class SellerBean {
        /**
         * name : 是的是的从
         * id : 1
         */

        private String name;

        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class EndUserBean {
        /**
         * nickName : Andrea
         * cellPhoneNum : 15902823856
         * userPhoto : null
         */

        private String nickName;
        private String cellPhoneNum;
        private String userPhoto;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getCellPhoneNum() {
            return cellPhoneNum;
        }

        public void setCellPhoneNum(String cellPhoneNum) {
            this.cellPhoneNum = cellPhoneNum;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }
    }

    public static class EvaluateBean {

        private String sellerReply;

        public String getSellerReply() {
            return sellerReply;
        }

        public void setSellerReply(String sellerReply) {
            this.sellerReply = sellerReply;
        }
    }
}
