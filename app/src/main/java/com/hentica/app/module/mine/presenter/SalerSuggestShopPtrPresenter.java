package com.hentica.app.module.mine.presenter;

import com.hentica.app.framework.fragment.ptr.AbsPtrFragment;
import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.lib.util.ListUtil;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineRecommendRec;
import com.hentica.app.module.entity.mine.ResSaleSuggestShopListData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.presenter.statistics.AbsPtrPresenter;
import com.hentica.app.util.request.Request;

import java.util.List;

/**
 * Created by Snow on 2017/5/23.
 */

public class SalerSuggestShopPtrPresenter extends AbsPtrPresenter {

    public SalerSuggestShopPtrPresenter(PtrView ptrView) {
        super(ptrView);
    }

    @Override
    public void onRefresh() {
        //刷新，获取第1页数据
        pageNumber = 1;
        Request.getEndUserGetRecommendSellerRec(LoginModel.getInstance().getLoginUserId(), String.valueOf(pageNumber),
                String.valueOf(pageSize), ListenerAdapter.createArrayListener(ResSaleSuggestShopListData.class,
                        new UsualDataBackListener<List<ResSaleSuggestShopListData>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResSaleSuggestShopListData> data) {
                                if(isSuccess){
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
        //加载更多
        Request.getEndUserGetRecommendSellerRec(LoginModel.getInstance().getLoginUserId(), String.valueOf(pageNumber),
                String.valueOf(pageSize), ListenerAdapter.createArrayListener(ResSaleSuggestShopListData.class,
                        new UsualDataBackListener<List<ResSaleSuggestShopListData>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResSaleSuggestShopListData> data) {
                                if(isSuccess){
                                    getPtrView().addListDatas(data);
                                    if(!ListUtil.isEmpty(data)){
                                        pageNumber++;
                                    }
                                }
                            }
                        }));
    }
}
