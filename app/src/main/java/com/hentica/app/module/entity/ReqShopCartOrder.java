package com.hentica.app.module.entity;

import java.util.List;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public class ReqShopCartOrder {

    /**
     * userId : 1
     * entityIds : [1,2,3]
     * entityId  : 1
     * token : aadcf940-2c8a-4a74-aa60-d0946a18bb6d__1495301212850
     */

    private long userId;
    private long entityId;
    private String token;
    private List<Long> entityIds;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Long> getEntityIds() {
        return entityIds;
    }

    public void setEntityIds(List<Long> entityIds) {
        this.entityIds = entityIds;
    }
}
