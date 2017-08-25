package com.hentica.app.module.entity.mine;

/**
 * Created by Snow on 2017/5/28 0028.
 */

public class ResBankCardData {


    /**
     * cardNum : XXXXXXXXXXXX
     * isDefault : true
     * idCard : 51012416698754878
     * cardType : type1
     * bankName : 平安银行1
     * id : 1
     * bankLogo : http://www.test.logo1.png
     */

    private String cardNum;
    private boolean isDefault;
    private String idCard;
    private String cardType;
    private String bankName;
    private int id;
    private String bankLogo;

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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
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

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }
}
