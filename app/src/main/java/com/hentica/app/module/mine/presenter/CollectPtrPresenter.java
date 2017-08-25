package com.hentica.app.module.mine.presenter;

import com.baidu.location.BDLocation;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.lib.util.ListUtil;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineCollect;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.presenter.statistics.AbsPtrPresenter;
import com.hentica.app.util.baidumap.LocationUtils;
import com.hentica.app.util.request.Request;

import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/27.17:16
 */

public class CollectPtrPresenter extends AbsPtrPresenter<ResMineCollect>{

    public CollectPtrPresenter(PtrView<ResMineCollect> ptrView) {
        super(ptrView);
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        BDLocation bdLocation = LocationUtils.getInstance().getLocation();
        double latitude = bdLocation != null ? bdLocation.getLatitude() : 0;
        double longitude = bdLocation != null ? bdLocation.getLongitude() : 0;
        Request.getEndUserFavoriteSellerList(LoginModel.getInstance().getLoginUserId(),
                String.valueOf(latitude), String.valueOf(longitude) , String.valueOf(pageNumber),
                String.valueOf(pageSize), ListenerAdapter.createArrayListener(ResMineCollect.class,
                        new UsualDataBackListener<List<ResMineCollect>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResMineCollect> data) {
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
        BDLocation bdLocation = LocationUtils.getInstance().getLocation();
        double latitude = bdLocation != null ? bdLocation.getLatitude() : 0;
        double longitude = bdLocation != null ? bdLocation.getLongitude() : 0;
        Request.getEndUserFavoriteSellerList(LoginModel.getInstance().getLoginUserId(),
                String.valueOf(latitude), String.valueOf(longitude) , String.valueOf(pageNumber),
                String.valueOf(pageSize),
                ListenerAdapter.createArrayListener(ResMineCollect.class,
                        new UsualDataBackListener<List<ResMineCollect>>(getPtrView()) {

                            @Override
                            protected void onComplete(boolean isSuccess, List<ResMineCollect> data) {
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
