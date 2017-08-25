package com.hentica.app.module.mine.presenter;

import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.lib.util.ListUtil;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.shop.ResShopOrderItem;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.presenter.statistics.AbsPtrPresenter;
import com.hentica.app.util.request.Request;

import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/29.11:28
 */

public class OrderManagerPtrPresenter extends AbsPtrPresenter<ResShopOrderItem> {

    private String mEntityId;
    private boolean isSallerOrder;
    private String isClearing;
    private String orderStatus;
    public void setEntityId(String entityId) {
        this.mEntityId = entityId;
    }

    public OrderManagerPtrPresenter(PtrView<ResShopOrderItem> ptrView, String entityId, boolean isSallerOrder, String orderStatus, String isClearing) {
        super(ptrView);
        this.mEntityId = entityId;
        this.isSallerOrder = isSallerOrder;
        this.orderStatus = orderStatus;
        this.isClearing = isClearing;
    }

    @Override
    public void onRefresh() {
        //刷新，获取第1页数据
        pageNumber = 1;
        Request.getOrderGetOrderUnderSeller(LoginModel.getInstance().getLoginUserId(), mEntityId, String.valueOf(pageNumber),
                String.valueOf(pageSize), isSallerOrder, orderStatus, isClearing, ListenerAdapter.createArrayListener(ResShopOrderItem.class,
                        new UsualDataBackListener<List<ResShopOrderItem>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResShopOrderItem> data) {
                                if (isSuccess) {
                                    getPtrView().setListDatas(data);
                                    if (!ListUtil.isEmpty(data)) {
                                        pageNumber++;
                                    }
                                }
                            }
                        }));
    }

    @Override
    public void onLoadMore() {
        //加载更多
        Request.getOrderGetOrderUnderSeller(LoginModel.getInstance().getLoginUserId(), mEntityId, String.valueOf(pageNumber),
                String.valueOf(pageSize), isSallerOrder, orderStatus, isClearing, ListenerAdapter.createArrayListener(ResShopOrderItem.class,
                        new UsualDataBackListener<List<ResShopOrderItem>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResShopOrderItem> data) {
                                if (isSuccess) {
                                    getPtrView().addListDatas(data);
                                    if (!ListUtil.isEmpty(data)) {
                                        pageNumber++;
                                    }
                                }
                            }
                        }));
    }

}
