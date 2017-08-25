package com.hentica.app.module.entity.bonus;

/**
 * Created by YangChen on 2017/4/10 21:42.
 * E-mail:656762935@qq.com
 */

public class BonusPeopleData {

    /**
     * bonusLeScore :
     * highBonusLeScore :
     * consumeTotalAmount :
     * reportDate : 2017-04-04
     * id : 12
     */

    private String bonusLeScore;
    private String highBonusLeScore;
    private String consumeTotalAmount;
    private String reportDate;
    private long id;

    public String getBonusLeScore() {
        return bonusLeScore;
    }

    public void setBonusLeScore(String bonusLeScore) {
        this.bonusLeScore = bonusLeScore;
    }

    public String getHighBonusLeScore() {
        return highBonusLeScore;
    }

    public void setHighBonusLeScore(String highBonusLeScore) {
        this.highBonusLeScore = highBonusLeScore;
    }

    public String getConsumeTotalAmount() {
        return consumeTotalAmount;
    }

    public void setConsumeTotalAmount(String consumeTotalAmount) {
        this.consumeTotalAmount = consumeTotalAmount;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
