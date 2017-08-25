package com.hentica.app.module.entity.mine.shop;

/**
 * Created by YangChen on 2017/5/28 14:38.
 * E-mail:656762935@qq.com
 */

public class ResPaymentList {


    /**
     * amount : 100
     * id : 1
     * clearingSn : 2145121
     * isClearing : false
     * createDate : 1495282878000
     */

    private double amount;
    private long id;
    private String clearingSn;
    private boolean isClearing;
    private long createDate;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClearingSn() {
        return clearingSn;
    }

    public void setClearingSn(String clearingSn) {
        this.clearingSn = clearingSn;
    }

    public boolean isIsClearing() {
        return isClearing;
    }

    public void setIsClearing(boolean isClearing) {
        this.isClearing = isClearing;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
