package com.hentica.app.module.entity;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/11.20:27
 */

public class ResAppUpdateData {


    /**
     * apkPath : qwqwqw
     * id : 2
     * versionName : 3333
     * updateContent : dssdsd
     * versionCode : 3
     * isForced : true
     */

    private String apkPath;
    private int id;
    private String versionName;
    private String updateContent;
    private int versionCode;
    private boolean isForced;

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public boolean isIsForced() {
        return isForced;
    }

    public void setIsForced(boolean isForced) {
        this.isForced = isForced;
    }
}
