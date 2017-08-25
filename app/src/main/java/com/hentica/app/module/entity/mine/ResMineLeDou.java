package com.hentica.app.module.entity.mine;

/**
 * 乐豆
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/31.16:15
 */

public class ResMineLeDou {


    /**
     * amount : 100
     * userCurLeBean : 100
     * id : 1
     * type : BONUS
     * createDate : 1490279289000
     */

    private double amount;
    private double userCurLeBean;
    private int id;
    private String type;
    private long createDate;
    private String remark;
    /**
     * amount : 100
     * recommenderPhoto :
     * userCurLeBean : 100
     * recommender :
     */

    private String recommenderPhoto;
    private String recommender;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getUserCurLeBean() {
        return userCurLeBean;
    }

    public void setUserCurLeBean(double userCurLeBean) {
        this.userCurLeBean = userCurLeBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getRecommenderPhoto() {
        return recommenderPhoto;
    }

    public void setRecommenderPhoto(String recommenderPhoto) {
        this.recommenderPhoto = recommenderPhoto;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
