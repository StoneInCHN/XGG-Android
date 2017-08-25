package com.hentica.app.module.entity.mine;

import com.google.gson.annotations.SerializedName;

/**
 * 推荐记录数据结构
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/6.17:17
 */

public class ResMineRecommendRec {


    /**
     * endUser : {"totalLeScore":0,"nickName":"0281","userPhoto":null}
     * id : 2
     */



    private EndUserBean endUser;
    private int id;
    private double totalRecommendLeScore;
    /**
     * endUser : {"sellerName":"中和加油站","userPhoto":"","totalLeScore":0,"nickName":"1fdff份饭","createDate":1490279537000}
     */

    public EndUserBean getEndUser() {
        return endUser;
    }

    public void setEndUser(EndUserBean endUser) {
        this.endUser = endUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalRecommendLeScore() {
        return totalRecommendLeScore;
    }

    public void setTotalRecommendLeScore(double totalRecommendLeScore) {
        this.totalRecommendLeScore = totalRecommendLeScore;
    }

    public static class EndUserBean {
        /**
         * sellerName : 中和加油站
         * userPhoto :
         * totalLeScore : 0
         * nickName : 1fdff份饭
         * createDate : 1490279537000
         */

        private String sellerName;
        private String userPhoto;
        private double totalLeScore;
        private String nickName;
        private long createDate;

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }

        public double getTotalLeScore() {
            return totalLeScore;
        }

        public void setTotalLeScore(double totalLeScore) {
            this.totalLeScore = totalLeScore;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }
    }

}
