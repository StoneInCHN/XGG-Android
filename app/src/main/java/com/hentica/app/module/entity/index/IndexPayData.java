package com.hentica.app.module.entity.index;

import com.google.gson.annotations.SerializedName;

/**
 * 微信支付数据
 * Created by YangChen on 2017/4/12 14:42.
 * E-mail:656762935@qq.com
 */

public class IndexPayData {

    /**
     * appId : wx75b0585936937e4a
     * nonce_str : YpcIEhUZjKJ1o9Ky
     * out_trade_no : 201704123651
     * package : Sign=WXPay
     * partnerId : 1249451301
     * prepay_id : wx20170412092227eb1b73ac760041702352
     * sign : 1441D8B0E44052844E28FE370F6145DE
     * timeStamp : 0412092227
     */

    private String appid;
    private String out_trade_no;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timestamp;
    private String orderStr;  // 支付宝支付字段
    private double encourageAmount; // 鼓励金
    private long orderId;  // 订单id
    private String noncestr;
    //------------翼支付相关字段--------------
    private String ORDERAMOUNT;  // 订单金额
    private String CURTYPE;  // 币种
    private String MERCHANTID;  // 商户代码
    private String BACKMERCHANTURL;  // 后台通知地址
    private String ATTACHAMOUNT;  // 附加金额
    private String ORDERSEQ;  // 订单编号
    private String PRODUCTAMOUNT;  // 产品金额
    private String USERIP;  // 用户IP
    private String BUSITYPE;  // 业务类型
    private String MAC;  // MAC加密串
    private String PRODUCTDESC;  // 产品名称
    private String CUSTOMERID;  // 用户id，在商户系统的登录账号
    private String ORDERTIME;  // 订单时间
    private String ACCOUNTID;  // 翼支付账号
    private String ORDERREQTRANSEQ;  // 流水号
    //------------通联快捷支付相关字段--------------
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
    //九派支付相关字段
    private int quickPayChannel;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String mAppid) {
        appid = mAppid;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String mOut_trade_no) {
        out_trade_no = mOut_trade_no;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String mPackageX) {
        packageX = mPackageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String mPartnerid) {
        partnerid = mPartnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String mPrepayid) {
        prepayid = mPrepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String mSign) {
        sign = mSign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String mTimestamp) {
        timestamp = mTimestamp;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String mOrderStr) {
        orderStr = mOrderStr;
    }

    public double getEncourageAmount() {
        return encourageAmount;
    }

    public void setEncourageAmount(double mEncourageAmount) {
        encourageAmount = mEncourageAmount;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long mOrderId) {
        orderId = mOrderId;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String mNoncestr) {
        noncestr = mNoncestr;
    }

    public String getORDERAMOUNT() {
        return ORDERAMOUNT;
    }

    public void setORDERAMOUNT(String mORDERAMOUNT) {
        ORDERAMOUNT = mORDERAMOUNT;
    }

    public String getCURTYPE() {
        return CURTYPE;
    }

    public void setCURTYPE(String mCURTYPE) {
        CURTYPE = mCURTYPE;
    }

    public String getMERCHANTID() {
        return MERCHANTID;
    }

    public void setMERCHANTID(String mMERCHANTID) {
        MERCHANTID = mMERCHANTID;
    }

    public String getBACKMERCHANTURL() {
        return BACKMERCHANTURL;
    }

    public void setBACKMERCHANTURL(String mBACKMERCHANTURL) {
        BACKMERCHANTURL = mBACKMERCHANTURL;
    }

    public String getATTACHAMOUNT() {
        return ATTACHAMOUNT;
    }

    public void setATTACHAMOUNT(String mATTACHAMOUNT) {
        ATTACHAMOUNT = mATTACHAMOUNT;
    }

    public String getORDERSEQ() {
        return ORDERSEQ;
    }

    public void setORDERSEQ(String mORDERSEQ) {
        ORDERSEQ = mORDERSEQ;
    }

    public String getPRODUCTAMOUNT() {
        return PRODUCTAMOUNT;
    }

    public void setPRODUCTAMOUNT(String mPRODUCTAMOUNT) {
        PRODUCTAMOUNT = mPRODUCTAMOUNT;
    }

    public String getUSERIP() {
        return USERIP;
    }

    public void setUSERIP(String mUSERIP) {
        USERIP = mUSERIP;
    }

    public String getBUSITYPE() {
        return BUSITYPE;
    }

    public void setBUSITYPE(String mBUSITYPE) {
        BUSITYPE = mBUSITYPE;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String mMAC) {
        MAC = mMAC;
    }

    public String getPRODUCTDESC() {
        return PRODUCTDESC;
    }

    public void setPRODUCTDESC(String mPRODUCTDESC) {
        PRODUCTDESC = mPRODUCTDESC;
    }

    public String getCUSTOMERID() {
        return CUSTOMERID;
    }

    public void setCUSTOMERID(String mCUSTOMERID) {
        CUSTOMERID = mCUSTOMERID;
    }

    public String getORDERTIME() {
        return ORDERTIME;
    }

    public void setORDERTIME(String mORDERTIME) {
        ORDERTIME = mORDERTIME;
    }

    public String getACCOUNTID() {
        return ACCOUNTID;
    }

    public void setACCOUNTID(String mACCOUNTID) {
        ACCOUNTID = mACCOUNTID;
    }

    public String getORDERREQTRANSEQ() {
        return ORDERREQTRANSEQ;
    }

    public void setORDERREQTRANSEQ(String mORDERREQTRANSEQ) {
        ORDERREQTRANSEQ = mORDERREQTRANSEQ;
    }

    public String getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(String mInputCharset) {
        inputCharset = mInputCharset;
    }

    public String getPickupUrl() {
        return pickupUrl;
    }

    public void setPickupUrl(String mPickupUrl) {
        pickupUrl = mPickupUrl;
    }

    public String getReceiveUrl() {
        return receiveUrl;
    }

    public void setReceiveUrl(String mReceiveUrl) {
        receiveUrl = mReceiveUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String mVersion) {
        version = mVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String mLanguage) {
        language = mLanguage;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String mSignType) {
        signType = mSignType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String mMerchantId) {
        merchantId = mMerchantId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String mOrderNo) {
        orderNo = mOrderNo;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String mOrderAmount) {
        orderAmount = mOrderAmount;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(String mOrderCurrency) {
        orderCurrency = mOrderCurrency;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String mOrderDatetime) {
        orderDatetime = mOrderDatetime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String mProductName) {
        productName = mProductName;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String mExt1) {
        ext1 = mExt1;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String mPayType) {
        payType = mPayType;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String mSignMsg) {
        signMsg = mSignMsg;
    }

    public String getPayH5orderUrl() {
        return payH5orderUrl;
    }

    public void setPayH5orderUrl(String mPayH5orderUrl) {
        payH5orderUrl = mPayH5orderUrl;
    }

    public int getQuickPayChannel() {
        return quickPayChannel;
    }

    public void setQuickPayChannel(int quickPayChannel) {
        this.quickPayChannel = quickPayChannel;
    }
}
