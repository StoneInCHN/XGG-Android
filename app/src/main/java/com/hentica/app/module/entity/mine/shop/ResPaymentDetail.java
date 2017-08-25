package com.hentica.app.module.entity.mine.shop;

import java.util.List;

/**
 * Created by YangChen on 2017/5/28 14:38.
 * E-mail:656762935@qq.com
 */

public class ResPaymentDetail {


    /**
     * amount : 107.8
     * totalOrderAmount : 110
     * bankCard : {"cardNum":"6222024402020399394","cardType":"借记卡","bankName":"工商银行","bankLogo":"http://apiserver.qiniudn.com/gongshang.png"}
     * orders : [{"order":{"amount":100,"sn":"2017071110921","rebateAmount":2,"sellerIncome":98,"sellerDiscount":9.8,"createDate":1499762234000}},{"order":{"amount":10,"sn":"2017071211025","rebateAmount":0.2,"sellerIncome":9.8,"sellerDiscount":9.8,"createDate":1499853046000}}]
     * id : 6
     * clearingSn : 201707142324
     * isClearing : true
     * createDate : 1500005021000
     */

    private double amount;
    private double totalOrderAmount;
    private BankCardBean bankCard;
    private int id;
    private String clearingSn;
    private boolean isClearing;
    private long createDate;
    private List<OrdersBean> orders;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(double totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public BankCardBean getBankCard() {
        return bankCard;
    }

    public void setBankCard(BankCardBean bankCard) {
        this.bankCard = bankCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public static class BankCardBean {
        /**
         * cardNum : 6222024402020399394
         * cardType : 借记卡
         * bankName : 工商银行
         * bankLogo : http://apiserver.qiniudn.com/gongshang.png
         */

        private String cardNum;
        private String cardType;
        private String bankName;
        private String bankLogo;

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
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

        public String getBankLogo() {
            return bankLogo;
        }

        public void setBankLogo(String bankLogo) {
            this.bankLogo = bankLogo;
        }
    }

    public static class OrdersBean {
        /**
         * order : {"amount":100,"sn":"2017071110921","rebateAmount":2,"sellerIncome":98,"sellerDiscount":9.8,"createDate":1499762234000}
         */

        private OrderBean order;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public static class OrderBean {
            /**
             * amount : 100
             * sn : 2017071110921
             * rebateAmount : 2
             * sellerIncome : 98
             * sellerDiscount : 9.8
             * createDate : 1499762234000
             */

            private double amount;
            private String sn;
            private double rebateAmount;
            private double sellerIncome;
            private double sellerDiscount;
            private long createDate;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public double getRebateAmount() {
                return rebateAmount;
            }

            public void setRebateAmount(double rebateAmount) {
                this.rebateAmount = rebateAmount;
            }

            public double getSellerIncome() {
                return sellerIncome;
            }

            public void setSellerIncome(double sellerIncome) {
                this.sellerIncome = sellerIncome;
            }

            public double getSellerDiscount() {
                return sellerDiscount;
            }

            public void setSellerDiscount(double sellerDiscount) {
                this.sellerDiscount = sellerDiscount;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }
        }
    }
}
