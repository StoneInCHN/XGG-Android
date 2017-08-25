package com.hentica.app.module.entity.index;

/**
 * Created by Snow on 2017/6/27.
 */

public class ReqIndexPayOrder {

    private long userId;
    private String token;
    private String payType;
    private String payTypeId;
    private double amount;
    private long sellerId;
    private boolean isBeanPay;
    private double deductLeBean;
    private String remark;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(String payTypeId) {
        this.payTypeId = payTypeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public boolean isBeanPay() {
        return isBeanPay;
    }

    public void setBeanPay(boolean beanPay) {
        isBeanPay = beanPay;
    }

    public double getDeductLeBean() {
        return deductLeBean;
    }

    public void setDeductLeBean(double deductLeBean) {
        this.deductLeBean = deductLeBean;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
