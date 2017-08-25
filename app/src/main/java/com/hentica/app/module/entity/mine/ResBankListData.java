package com.hentica.app.module.entity.mine;

/**
 * Created by Snow on 2017/5/28 0028.
 */

public class ResBankListData {

    /**
     * cardNum : XXXXXX1
     * isDefault : false
     * cardType : XXX1
     * bankLogo : http://www.test.logo1.png
     * bankName : 平安银行1
     * id : 1
     */

    private String cardNum;
    private boolean isDefault;
    private String cardType;
    private String bankLogo;
    private String bankName;
    private int id;

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
