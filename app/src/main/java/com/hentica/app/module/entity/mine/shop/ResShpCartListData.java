package com.hentica.app.module.entity.mine.shop;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public class ResShpCartListData {

    /**
     * endUser : {"nickName":"翼享153119A1","cellPhoneNum":"15892999216"}
     * amount : 100.0
     * rebateAmount : 10.0
     * id : 4
     * createDate : 1495439810000
     */

    private EndUserBean endUser;
    private double amount;
    private double rebateAmount;
    private int id;
    private long createDate;

    public EndUserBean getEndUser() {
        return endUser;
    }

    public void setEndUser(EndUserBean endUser) {
        this.endUser = endUser;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(double rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public static class EndUserBean {
        /**
         * nickName : 翼享153119A1
         * cellPhoneNum : 15892999216
         */

        private String nickName;
        private String cellPhoneNum;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getCellPhoneNum() {
            return cellPhoneNum;
        }

        public void setCellPhoneNum(String cellPhoneNum) {
            this.cellPhoneNum = cellPhoneNum;
        }
    }
}
