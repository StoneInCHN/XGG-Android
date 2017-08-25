package com.hentica.app.module.mine.presenter.shop;

import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.shop.ResShopCategory;
import com.hentica.app.module.mine.view.shop.ShopCategoryView;
import com.hentica.app.util.request.Request;

import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/5.16:32
 */

public class ShopCategoryPresenterImpl implements ShopCategoryPresenter {
    private ShopCategoryView mCategoryView;

    public ShopCategoryPresenterImpl(ShopCategoryView categoryView){
        this.mCategoryView = categoryView;
    }

    @Override
    public void getGategory() {
        Request.getSellerGetSellerCategory(ListenerAdapter.createArrayListener(
                ResShopCategory.class, new UsualDataBackListener<List<ResShopCategory>>(mCategoryView) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<ResShopCategory> data) {
                        if(isSuccess){
                            mCategoryView.setCategory(data);
                        }
                    }
                }
        ));
    }
}
