package com.hentica.app.module.entity.index;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public class IndexPayAllinpayData {

    /**
     * inputCharset : 1
     * pickupUrl : http://www.baidu.com
     * receiveUrl : http://118.190.83.191:10001/rebate-interface/payNotify/notify_allinpay_H5.jhtml
     * version : v1.0
     * language : 1
     * signType : 0
     * merchantId : 008510154113610
     * orderNo : 201705259898
     * orderAmount : 300
     * orderCurrency : 0
     * orderDatetime : 20170525164958
     * productName : 川西坝子1（西南店）
     * ext1 : <USER>170525863749065</USER>
     * payType : 33
     * signMsg : 313925b4f532dae42a5a5c2fddf333ba
     * payH5orderUrl : https://cashier.allinpay.com/mobilepayment/mobile/SaveMchtOrderServlet.action
     * encourageAmount : 0.003
     * orderId : 35
     */

    private String inputCharset;
    private String pickupUrl;
    private String receiveUrl;
    private String version;
    private String language;
    private String signType;
    private String merchantId;
    private String orderNo;
    private String orderAmount;
    private String orderCurrency;
    private String orderDatetime;
    private String productName;
    private String ext1;
    private String payType;
    private String signMsg;
    private String payH5orderUrl;
    private double encourageAmount;
    private int orderId;

    public String getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
    }

    public String getPickupUrl() {
        return pickupUrl;
    }

    public void setPickupUrl(String pickupUrl) {
        this.pickupUrl = pickupUrl;
    }

    public String getReceiveUrl() {
        return receiveUrl;
    }

    public void setReceiveUrl(String receiveUrl) {
        this.receiveUrl = receiveUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    public String getPayH5orderUrl() {
        return payH5orderUrl;
    }

    public void setPayH5orderUrl(String payH5orderUrl) {
        this.payH5orderUrl = payH5orderUrl;
    }

    public double getEncourageAmount() {
        return encourageAmount;
    }

    public void setEncourageAmount(double encourageAmount) {
        this.encourageAmount = encourageAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
