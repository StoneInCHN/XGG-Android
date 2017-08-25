package com.hentica.app.module.entity.index;

import java.util.List;

/**
 * Created by YangChen on 2017/3/30 16:30.
 * E-mail:656762935@qq.com
 */

public class IndexEvaluateListData {

    private float score;

    /**
     * nickName : Andrea
     * userPhoto :
     */

    private EndUserBean endUser;
    /**
     * endUser : {"nickName":"Andrea","userPhoto":""}
     * id : 1
     * evaluateImages : [{"title":"","source":"http://127.0.0.1:4001/dfdf/dfdf.jpg","large":"","medium":"","thumbnail":"","order":""}]
     * content : wewes
     * createDate : 1490589315000
     * sellerReply : 商家的回复。。。。。。
     */

    private int id;
    private String content;
    private long createDate;
    private String sellerReply;
    /**
     * title :
     * source : http://127.0.0.1:4001/dfdf/dfdf.jpg
     * large :
     * medium :
     * thumbnail :
     * order :
     */

    private List<EvaluateImagesBean> evaluateImages;

    public EndUserBean getEndUser() {
        return endUser;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float mScore) {
        score = mScore;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getSellerReply() {
        return sellerReply;
    }

    public void setSellerReply(String sellerReply) {
        this.sellerReply = sellerReply;
    }

    public List<EvaluateImagesBean> getEvaluateImages() {
        return evaluateImages;
    }

    public void setEvaluateImages(List<EvaluateImagesBean> evaluateImages) {
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

    public static class EvaluateImagesBean {
        private String title;
        private String source;
        private String large;
        private String medium;
        private String thumbnail;
        private String order;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }
    }
}
