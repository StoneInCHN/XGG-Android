package com.hentica.app.module.entity.mine;

import java.util.List;

/**
 * Created by YangChen on 2017/4/8 15:02.
 * E-mail:656762935@qq.com
 */

public class ResMineEvaluateDetailData {

    /**
     * score :
     * endUser : {"nickName":"Andrea","userPhoto":""}
     * id : 1
     * evaluateImages : []
     * content : wewes
     * order : {"amount":""}
     * sellerReply :
     */

    private float score;
    /**
     * nickName : Andrea
     * userPhoto :
     */

    private EndUserBean endUser;
    private long id;
    private String content;
    /**
     * amount :
     */

    private OrderBean order;
    private String sellerReply;
    private List<String> evaluateImages;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public EndUserBean getEndUser() {
        return endUser;
    }

    public void setEndUser(EndUserBean endUser) {
        this.endUser = endUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public String getSellerReply() {
        return sellerReply;
    }

    public void setSellerReply(String sellerReply) {
        this.sellerReply = sellerReply;
    }

    public List<String> getEvaluateImages() {
        return evaluateImages;
    }

    public void setEvaluateImages(List<String> evaluateImages) {
        this.evaluateImages = evaluateImages;
    }

    public static class EndUserBean {
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

    public static class OrderBean {
        private String amount;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
