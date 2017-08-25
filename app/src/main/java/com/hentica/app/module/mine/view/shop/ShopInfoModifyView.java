package com.hentica.app.module.mine.view.shop;

import com.hentica.app.framework.fragment.FragmentListener;

import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.13:46
 */

public interface ShopInfoModifyView extends FragmentListener.UsualViewOperator{

    /**
     * 修改成功
     */
    void modifySuccess();

    /**
     * 修改失败
     */
    void modifyFailure();

    /** 商家id */
    String getSellerId();

    /**
     * 店铺Logo是否修改
     * @return true：是,false：否
     */
    boolean isLogoModify();

    /**
     * 获取店铺logo
     * @return
     */
    String getLogoPath();

    /**
     * 获取平均消费
     * @return
     */
    String getAnverage();

    /**
     * 获取营业时间
     * @return
     */
    String getBusinessTime();

    /**
     * 获取店铺电话
     * @return
     */
    String getPhone();

    /**
     * 获取店铺名称
     * @return
     */
    String getName();

    /**
     * 获取详细地址
     * @return
     */
    String getAddress();

    /**
     * 获取其他服务
     * @return
     */
    String getService();

    /**
     * 环境照片是否修改
     * @return
     */
    boolean isEnvironmentPhotoModify();

    /**
     * 环境照片
     * @return
     */
    List<String> getEnvironment();

    /**
     * 获取店铺简介
     * @return
     */
    String getDescription();

    /** 纬度 */
    String getLatitude();

    /** 经度 */
    String getLongitude();

    String getAreaId();

    /**
     * 是否正在解析地址
     * @return
     */
    boolean inParseLocation();
}
