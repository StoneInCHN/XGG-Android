package com.hentica.app.module.entity.bonus;

/**
 * 全国分红数据
 * Created by YangChen on 2017/4/10 21:18.
 * E-mail:656762935@qq.com
 */

public class BonusNationData {

    /**
     * consumePeopleNum :
     * publicTotalAmount :
     * sellerNum :
     * consumeTotalAmount :
     * consumeByDay :
     * reportDate : 2017-04-08
     * publicAmountByDay :
     * leMindByDay :
     * bonusLeScoreByDay :
     * id : 1
     */

    private String publicTotalAmount;
    private String publicAmountByDay;

    private String consumePeopleNum;
    private String sellerNum;
    private String consumeTotalAmount;
    private String consumeByDay;
    private String reportDate;

    private String leMindByDay;
    private String bonusLeScoreByDay;
    private String totalBonus;
    private String calValue;
    private long id;

    public String getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(String totalBonus) {
        this.totalBonus = totalBonus;
    }

    public String getConsumePeopleNum() {
        return consumePeopleNum;
    }

    public void setConsumePeopleNum(String consumePeopleNum) {
        this.consumePeopleNum = consumePeopleNum;
    }

    public String getPublicTotalAmount() {
        return publicTotalAmount;
    }

    public void setPublicTotalAmount(String publicTotalAmount) {
        this.publicTotalAmount = publicTotalAmount;
    }

    public String getSellerNum() {
        return sellerNum;
    }

    public void setSellerNum(String sellerNum) {
        this.sellerNum = sellerNum;
    }

    public String getConsumeTotalAmount() {
        return consumeTotalAmount;
    }

    public void setConsumeTotalAmount(String consumeTotalAmount) {
        this.consumeTotalAmount = consumeTotalAmount;
    }

    public String getConsumeByDay() {
        return consumeByDay;
    }

    public void setConsumeByDay(String consumeByDay) {
        this.consumeByDay = consumeByDay;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getPublicAmountByDay() {
        return publicAmountByDay;
    }

    public void setPublicAmountByDay(String publicAmountByDay) {
        this.publicAmountByDay = publicAmountByDay;
    }

    public String getLeMindByDay() {
        return leMindByDay;
    }

    public void setLeMindByDay(String leMindByDay) {
        this.leMindByDay = leMindByDay;
    }

    public String getBonusLeScoreByDay() {
        return bonusLeScoreByDay;
    }

    public void setBonusLeScoreByDay(String bonusLeScoreByDay) {
        this.bonusLeScoreByDay = bonusLeScoreByDay;
    }


    public String getCalValue() {
        return calValue;
    }

    public void setCalValue(String calValue) {
        this.calValue = calValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
