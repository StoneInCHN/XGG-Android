package com.hentica.app.module.entity.mine.shop;

/**
 * 环境照片数据结构
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.17:38
 */

public class Photo {

    private String url;
    private String filePath;

    public Photo(String url) {
        this.url = url;
    }

    /**
     * 网络路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 网络路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 本地保存地址
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 本地保存地址
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
