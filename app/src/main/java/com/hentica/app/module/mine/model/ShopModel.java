package com.hentica.app.module.mine.model;

import com.hentica.app.module.entity.mine.shop.ResShopInfo;

/**
 * 用户保存店铺信息
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.15:11
 */

public class ShopModel {

    private static ShopModel mInstance;

    private ResShopInfo mShopInfo;

    private ShopModel(){

    }

    /**
     * 获取ShopMode实例
     * @return
     */
    public static ShopModel getInstance(){
        if(mInstance == null){
            synchronized (ShopModel.class){
                if(mInstance == null){
                    mInstance = new ShopModel();
                }
            }
        }
        return mInstance;
    }

    public void saveShopInfo(ResShopInfo shopInfo){
        this.mShopInfo = shopInfo;
    }

    public ResShopInfo getShopInfo(){
        return this.mShopInfo;
    }

}
