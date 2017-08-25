package com.hentica.app.module.mine.presenter.statistics;

import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.lib.util.ListUtil;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineLeXin;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.util.request.Request;

import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/24.12:04
 */

public class LexinPtrPresenter extends AbsPtrPresenter<ResMineLeXin> {

    public LexinPtrPresenter(PtrView ptrView) {
        super(ptrView);
    }

    @Override
    public void onRefresh() {
        //刷新，获取第1页数据
        pageNumber = 1;
        Request.getEndUserLeMindRec(LoginModel.getInstance().getLoginUserId(), String.valueOf(pageNumber),
                String.valueOf(pageSize), ListenerAdapter.createArrayListener(ResMineLeXin.class,
                        new UsualDataBackListener<List<ResMineLeXin>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResMineLeXin> data) {
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
        Request.getEndUserLeMindRec(LoginModel.getInstance().getLoginUserId(), String.valueOf(pageNumber),
                String.valueOf(pageSize), ListenerAdapter.createArrayListener(ResMineLeXin.class,
                        new UsualDataBackListener<List<ResMineLeXin>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResMineLeXin> data) {
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
