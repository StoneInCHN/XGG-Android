package com.hentica.app.module.mine.ui.shop;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/13.18:01
 */

public class OrderDetailHelper {

    private static OrderDetailHelper mInstance;

    private boolean isExist = false;

    private OrderDetailHelper(){

    }

    public static OrderDetailHelper getInstance(){
        if(mInstance == null){
            synchronized (OrderDetailHelper.class){
                if(mInstance == null){
                    mInstance = new OrderDetailHelper();
                }
            }
        }
        return mInstance;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public void destory(){
        mInstance = null;
    }
}
