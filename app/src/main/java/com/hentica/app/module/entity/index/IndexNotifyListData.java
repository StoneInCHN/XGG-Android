package com.hentica.app.module.entity.index;

/**
 * Created by YangChen on 2017/4/8 16:40.
 * E-mail:656762935@qq.com
 */

public class IndexNotifyListData {

    /**
     * isRead : true
     * messageTitle : tst
     * id : 1
     * messageContent : test
     * createDate : 1490943982000
     */

    private boolean isRead;
    private String messageTitle;
    private long id;
    private String messageContent;
    private long createDate;

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

}
