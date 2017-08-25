package com.hentica.app.module.entity;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public class ResBankCardInfo {


    /**
     * bank : 工商银行
     * type : E时代卡
     * nature : 借记卡
     * kefu : 95588
     * logo : http://apiserver.qiniudn.com/gongshang.png
     * info : 四川省-成都
     */

    private String bank;
    private String type;
    private String nature;
    private String kefu;
    private String logo;
    private String info;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getKefu() {
        return kefu;
    }

    public void setKefu(String kefu) {
        this.kefu = kefu;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
