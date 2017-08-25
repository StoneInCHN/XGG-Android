package com.hentica.app.module.entity.mine;

/**
 * 乐心
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/31.14:25
 */

public class ResMineLeFen {


    /**
     * amount : 200
     * recommenderPhoto : 222
     * id : 2
     * userCurLeScore : 1222
     * recommender : 111
     * withdrawStatus : AUDIT_PASSED
     * createDate : 1490452089000
     * leScoreType : BONUS
     */
    private double amount;
    private String recommenderPhoto;
    private int id;
    private double userCurLeScore;
    private String recommender;
    private String withdrawStatus;
    private String status;
    private long createDate;
    private String leScoreType;
    /**
     * endUser : {"nickName":"Andrea","userPhoto":""}
     * amount : 200
     * remark :
     * userCurLeScore : 1222
     */



    private EndUserBean endUser;
    private String remark;
    /**
     * seller : {"storePictureUrl":"/upload/store/sign/src_d077bf55-0653-4c90-bea6-b4b6db7a5379.jpg","name":"川西坝子1（西南店）"}
     */

    private SellerBean seller;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRecommenderPhoto() {
        return recommenderPhoto;
    }

    public void setRecommenderPhoto(String recommenderPhoto) {
        this.recommenderPhoto = recommenderPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getUserCurLeScore() {
        return userCurLeScore;
    }

    public void setUserCurLeScore(double userCurLeScore) {
        this.userCurLeScore = userCurLeScore;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public String getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getLeScoreType() {
        return leScoreType;
    }

    public void setLeScoreType(String leScoreType) {
        this.leScoreType = leScoreType;
    }

    public EndUserBean getEndUser() {
        return endUser;
    }

    public void setEndUser(EndUserBean endUser) {
        this.endUser = endUser;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SellerBean getSeller() {
        return seller;
    }

    public void setSeller(SellerBean seller) {
        this.seller = seller;
    }

    public static class EndUserBean {
        /**
         * nickName : Andrea
         * userPhoto :
         */

        private String nickName;
        private String userPhoto;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }
    }

    public static class SellerBean {
        /**
         * storePictureUrl : /upload/store/sign/src_d077bf55-0653-4c90-bea6-b4b6db7a5379.jpg
         * name : 川西坝子1（西南店）
         */

        private String storePictureUrl;
        private String name;

        public String getStorePictureUrl() {
            return storePictureUrl;
        }

        public void setStorePictureUrl(String storePictureUrl) {
            this.storePictureUrl = storePictureUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
