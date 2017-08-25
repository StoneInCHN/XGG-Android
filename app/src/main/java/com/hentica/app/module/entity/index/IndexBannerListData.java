package com.hentica.app.module.entity.index;

/**
 * Created by YangChen on 2017/4/8 10:33.
 * E-mail:656762935@qq.com
 */

public class IndexBannerListData {

    /**
     * bannerUrl : dsd
     * linkUrl :
     * bannerName : 2222
     * id : 2
     */

    private String bannerUrl;
    private String linkUrl;
    private String bannerName;
    private long id;

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
