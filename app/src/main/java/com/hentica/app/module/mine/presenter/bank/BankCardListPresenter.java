package com.hentica.app.module.mine.presenter.bank;

import com.baidu.location.BDLocation;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.lib.util.ListUtil;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResBankListData;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.presenter.statistics.AbsPtrPresenter;
import com.hentica.app.util.baidumap.LocationUtils;
import com.hentica.app.util.request.Request;

import java.util.List;

/**
 * Created by Snow on 2017/5/28 0028.
 */

public class BankCardListPresenter extends AbsPtrPresenter<ResBankListData> {
    public BankCardListPresenter(PtrView<ResBankListData> ptrView) {
        super(ptrView);
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        Request.getBankCardMyCardList(LoginModel.getInstance().getLoginUserId(),
                String.valueOf(pageNumber), String.valueOf(pageSize)
                , ListenerAdapter.createArrayListener(ResBankListData.class,
                        new UsualDataBackListener<List<ResBankListData>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResBankListData> data) {
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
        Request.getBankCardMyCardList(LoginModel.getInstance().getLoginUserId(),
                String.valueOf(pageNumber), String.valueOf(pageSize),
                ListenerAdapter.createArrayListener(ResBankListData.class,
                        new UsualDataBackListener<List<ResBankListData>>(getPtrView()) {

                            @Override
                            protected void onComplete(boolean isSuccess, List<ResBankListData> data) {
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
