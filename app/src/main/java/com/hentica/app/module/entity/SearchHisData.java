package com.hentica.app.module.entity;

/**
 * Created by YangChen on 2017/3/31 17:07.
 * E-mail:656762935@qq.com
 */

public class SearchHisData {
    /** 搜索关键字 */
    private String mkeyWords;
    /** 搜索日期 */
    private String mDate;

    public String getKeyWords() {
        return mkeyWords;
    }

    public void setKeyWords(String mKeyWords) {
        this.mkeyWords = mKeyWords;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }
}
