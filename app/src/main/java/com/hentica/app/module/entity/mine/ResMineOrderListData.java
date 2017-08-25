package com.hentica.app.module.entity.mine;

/**
 * Created by YangChen on 2017/4/6 17:54.
 * E-mail:656762935@qq.com
 */

public class ResMineOrderListData {

    /**
     * name : 店铺名字
     * storePictureUrl : 店铺图标
     */

    private SellerBean seller;
    /**
     * seller : {"name":"店铺名字","storePictureUrl":"店铺图标"}
     * amount : 100
     * remark : 备注
     * id : 1
     * sn : 1111
     * userScore : 12
     * evaluate : {"content":"评论内容","sellerReply":"商家回复"}
     * createDate : 1491235741000
     * status : FINISHED
     */

    private double amount;
    private String remark;
    private int id;
    private String sn;
    private double userScore;
    /**
     * content : 评论内容
     * sellerReply : 商家回复
     * isSallerOrder :
     * rebateAmount
     */

    private EvaluateBean evaluate;
    private long createDate;
    private String status;
    private boolean isSallerOrder;
    private double rebateAmount;

    public double getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(double rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    public boolean isSallerOrder() {
        return isSallerOrder;
    }

    public void setSallerOrder(boolean sallerOrder) {
        isSallerOrder = sallerOrder;
    }

    public SellerBean getSeller() {
        return seller;
    }

    public void setSeller(SellerBean seller) {
        this.seller = seller;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public double getUserScore() {
        return userScore;
    }

    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    public EvaluateBean getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(EvaluateBean evaluate) {
        this.evaluate = evaluate;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class SellerBean {


        private String name;
        private String storePictureUrl;
        /**
         * id : 1
         * address :
         */
        private int id;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStorePictureUrl() {
            return storePictureUrl;
        }

        public void setStorePictureUrl(String storePictureUrl) {
            this.storePictureUrl = storePictureUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class EvaluateBean {
        private String content;
        private String sellerReply;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSellerReply() {
            return sellerReply;
        }

        public void setSellerReply(String sellerReply) {
            this.sellerReply = sellerReply;
        }
    }
}
