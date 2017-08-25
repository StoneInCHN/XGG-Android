package com.hentica.app.module.mine.presenter.shop;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.lib.util.ListUtil;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineCollect;
import com.hentica.app.module.entity.mine.shop.ResShpCartListData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.presenter.statistics.AbsPtrPresenter;
import com.hentica.app.util.request.Request;

import java.util.List;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public class ShopCartListPresenter extends AbsPtrPresenter<ResShpCartListData> {

    public ShopCartListPresenter(PtrView<ResShpCartListData> ptrView) {
        super(ptrView);
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        Request.getSellerOrderCartList(LoginModel.getInstance().getLoginUserId(),
                String.valueOf(pageNumber), String.valueOf(pageSize),
                ListenerAdapter.createArrayListener(ResShpCartListData.class,
                        new UsualDataBackListener<List<ResShpCartListData>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResShpCartListData> data) {
                                if(isSuccess && getPtrView() != null){
                                    getPtrView().setListDatas(data);
                                    if(!ListUtil.isEmpty(data)){
                                        pageNumber++;
                                    }
                                }
                            }
                        }));
    }

    @Override
    public void onLoadMore() {
        Request.getSellerOrderCartList(LoginModel.getInstance().getLoginUserId(),
                String.valueOf(pageNumber), String.valueOf(pageSize),
                ListenerAdapter.createArrayListener(ResShpCartListData.class,
                        new UsualDataBackListener<List<ResShpCartListData>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResShpCartListData> data) {
                                if(isSuccess && getPtrView() != null){
                                    getPtrView().addListDatas(data);
                                    if(!ListUtil.isEmpty(data)){
                                        pageNumber++;
                                    }
                                }
                            }
                        }));
    }

}
