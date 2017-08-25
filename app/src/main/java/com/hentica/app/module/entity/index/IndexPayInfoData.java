package com.hentica.app.module.entity.index;

import java.util.List;

/**
 * 买单支付信息
 * Created by Snow on 2017/6/27.
 */

public class IndexPayInfoData {

    /**
     * payType : [{"id":5,"configValue":"乐分支付"},{"id":1,"configValue":"微信支付"},{"id":2,"configValue":"支付宝支付"},{"id":3,"configValue":"快捷支付"}]
     * userCurLeBean : 0.1
     * isBeanPay : false
     * userCurLeScore : 96563.07
     */

    private double userCurLeBean;
    private boolean isBeanPay;
    private double userCurLeScore;
    private List<IndexPayTypeListData> payType;

    public double getUserCurLeBean() {
        return userCurLeBean;
    }

    public void setUserCurLeBean(double userCurLeBean) {
        this.userCurLeBean = userCurLeBean;
    }

    public boolean isIsBeanPay() {
        return isBeanPay;
    }

    public void setIsBeanPay(boolean isBeanPay) {
        this.isBeanPay = isBeanPay;
    }

    public double getUserCurLeScore() {
        return userCurLeScore;
    }

    public void setUserCurLeScore(double userCurLeScore) {
        this.userCurLeScore = userCurLeScore;
    }

    public List<IndexPayTypeListData> getPayType() {
        return payType;
    }

    public void setPayType(List<IndexPayTypeListData> payType) {
        this.payType = payType;
    }

}
