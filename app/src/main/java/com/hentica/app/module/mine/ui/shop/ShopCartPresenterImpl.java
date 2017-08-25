package com.hentica.app.module.mine.ui.shop;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.ReqShopCartDelete;
import com.hentica.app.module.entity.ReqShopCartOrder;
import com.hentica.app.module.entity.mine.shop.ResGenerateOrder;
import com.hentica.app.module.entity.mine.shop.ResShpCartListData;
import com.hentica.app.module.mine.presenter.base.AbsPresenter;
import com.hentica.app.module.mine.view.shop.ShopCartView;
import com.hentica.app.util.request.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public class ShopCartPresenterImpl extends AbsPresenter<ShopCartView> implements ShopCartPresenter {
    @Override
    public void deleteOrders(final ResShpCartListData requestData) {
        if(requestData == null){
            return;
        }
        int id = 0;
        try{
            id = Integer.parseInt(ApplicationData.getInstance().getLoginUserId());
        }catch (NumberFormatException e){ }
        List<Long> ids = new ArrayList<>();
        ids.add(new Long(requestData.getId()));
        ReqShopCartDelete request = new ReqShopCartDelete();
        request.setEntityIds(ids);
        request.setUserId(id);
        request.setToken(ApplicationData.getInstance().getToken());
        Request.getSellerOrderCartDelete(request,
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(getView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if (isSuccess && getView() != null) {
                                    getView().deleteSuccess(requestData);
                                }
                            }
                        }
                )
        );
    }

    @Override
    public void toPayOrders(List<ResShpCartListData> datas, long sellerId) {
        if(datas == null || datas.isEmpty()){
            return;
        }
        int id = 0;
        try{
            id = Integer.parseInt(ApplicationData.getInstance().getLoginUserId());
        }catch (NumberFormatException e){ }
        List<Long> ids = new ArrayList<>();
        for(ResShpCartListData data : datas) {
            ids.add(new Long(data.getId()));
        }
        ReqShopCartOrder request = new ReqShopCartOrder();
        request.setEntityId(sellerId);
        request.setUserId(id);
        request.setEntityIds(ids);
        request.setToken(ApplicationData.getInstance().getToken());
        Request.getSellerOrderCartConfirmOrder(request,
                ListenerAdapter.createObjectListener(ResGenerateOrder.class,
                        new UsualDataBackListener<ResGenerateOrder>(getView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResGenerateOrder data) {
                                if (isSuccess && getView() != null) {
                                    getView().generateSuccess(data);
                                }
                            }
                        }
                )
        );
    }
}
