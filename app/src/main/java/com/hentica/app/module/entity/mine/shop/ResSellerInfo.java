package com.hentica.app.module.entity.mine.shop;

/**
 * Created by Snow on 2017/5/24 0024.
 */

public class ResSellerInfo {


    /**
     * realName : 李洛克
     * address : 四川省成都市武侯区吉泰路
     * name : 川西坝子1（西南店）
     * discount : 9.0
     * cartCount : 0
     * id : 1
     */

    private String realName;
    private String address;
    private String name;
    private double discount;
    private int cartCount;
    private int id;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getCartCount() {
        return cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
