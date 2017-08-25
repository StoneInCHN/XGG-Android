package com.hentica.app.module.mine.view.shop;

import com.hentica.app.framework.fragment.FragmentListener;

import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/1.17:09
 */

public interface ShopSettleView extends FragmentListener.UsualViewOperator{

    /** 店铺logo */
    String getLogoPath();

    /** 店铺联系人手机号 */
    String getPhone();

    /** 店铺名称 */
    String getShopName();

    /** 店铺类别 */
    String getCategoryId();

    /** 店铺折扣 */
    String getDiscount();

    /** 店铺电话 */
    String getShopPhone();

    /** 店铺所在地id */
    String getAreaId();

    /** 店铺详情地址 */
    String getAddress();

    /** 营业执照 */
    String getLicenseNumber();

    /** 营业执照图片 */
    String getLicensePhotoPath();

    /** 店铺环境照片 */
    List<String> getShopEnvPhotosPath();

    /** 商家承诺书照片 */
    List<String> getCommitmentPhotosPath();

    /** 店铺简介 */
    String getShopDescripte();

    /** 纬度 */
    String getLatitude();

    /** 经度 */
    String getLongitude();

    /**  */
    String getApplyId();

    /**
     * 是否正在解析地址
     * @return
     */
    boolean inParseLocation();

    /** 申请成功 */
    void onSuccess();
}

