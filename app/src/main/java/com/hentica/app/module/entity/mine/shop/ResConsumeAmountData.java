package com.hentica.app.module.entity.mine.shop;

import java.util.List;

/**
 * Created by YangChen on 2017/7/7 11:52.
 * E-mail:656762935@qq.com
 */

public class ResConsumeAmountData {

    /**
     * areaId : 2279
     * date : 2017-06-22
     * totalAmount : 68
     * discountAmounts : [{"discount":8,"amount":68}]
     */

    private int areaId;
    private String date;
    private double totalAmount;
    /**
     * discount : 8
     * amount : 68
     */

    private List<DiscountAmountsBean> discountAmounts;

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<DiscountAmountsBean> getDiscountAmounts() {
        return discountAmounts;
    }

    public void setDiscountAmounts(List<DiscountAmountsBean> discountAmounts) {
        this.discountAmounts = discountAmounts;
    }

    public static class DiscountAmountsBean {
        private double discount;
        private double amount;

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }
}
