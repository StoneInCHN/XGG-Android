package com.hentica.app.module.entity.mine;

import android.text.TextUtils;

/**
 * Created by Snow on 2017/5/23.
 */

public class ResSaleSuggestShopListData {


    /**
     * seller : {}
     * sellerApplication : {"sellerName":"亦车行","id":3,"storePhoto":"/upload/store/sign/src_d6405bd5-94a9-48b2-81ed-219eb6625b3f.jpg","applyStatus":"AUDIT_PASSED","contactCellPhone":"132212162155"}
     * id : 3
     * totalRecommendLeScore : 22
     * createDate : 1492951392000
     */

    private SellerBean seller;
    private SellerApplicationBean sellerApplication;
    private int id;
    private double totalRecommendLeScore;
    private long createDate;

    public SellerBean getSeller() {
        return seller;
    }

    public void setSeller(SellerBean seller) {
        this.seller = seller;
    }

    public SellerApplicationBean getSellerApplication() {
        return sellerApplication;
    }

    public void setSellerApplication(SellerApplicationBean sellerApplication) {
        this.sellerApplication = sellerApplication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalRecommendLeScore() {
        return totalRecommendLeScore;
    }

    public void setTotalRecommendLeScore(double totalRecommendLeScore) {
        this.totalRecommendLeScore = totalRecommendLeScore;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public static class SellerBean {
    }

    public static class SellerApplicationBean {
        /**
         * sellerName : 亦车行
         * id : 3
         * storePhoto : /upload/store/sign/src_d6405bd5-94a9-48b2-81ed-219eb6625b3f.jpg
         * applyStatus : AUDIT_PASSED
         * contactCellPhone : 132212162155
         */

        private String sellerName;
        private int id;
        private String storePhoto;
        private String applyStatus;
        private String contactCellPhone;
        private String notes;

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStorePhoto() {
            return storePhoto;
        }

        public void setStorePhoto(String storePhoto) {
            this.storePhoto = storePhoto;
        }

        public String getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(String applyStatus) {
            this.applyStatus = applyStatus;
        }

        public String getContactCellPhone() {
            return contactCellPhone;
        }

        public void setContactCellPhone(String contactCellPhone) {
            this.contactCellPhone = contactCellPhone;
        }
    }
}
