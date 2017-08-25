package com.hentica.app.module.entity.mine;

/**
 * 乐心
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/31.14:25
 */

public class ResMineLeXin {


    /**
     * score : 10
     * amount : 200
     * id : 1
     * userCurLeMind : 300
     * createDate : 1490279289000
     * remark
     */

    private String score;
    private int amount;
    private int id;
    private int userCurLeMind;
    private long createDate;
    private String remark;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserCurLeMind() {
        return userCurLeMind;
    }

    public void setUserCurLeMind(int userCurLeMind) {
        this.userCurLeMind = userCurLeMind;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
