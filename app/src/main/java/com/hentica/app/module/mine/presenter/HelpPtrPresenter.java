package com.hentica.app.module.mine.presenter;

import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineHelpItem;
import com.hentica.app.module.mine.presenter.statistics.AbsPtrPresenter;
import com.hentica.app.util.request.Request;

import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/27.17:37
 */

public class HelpPtrPresenter extends AbsPtrPresenter<ResMineHelpItem> {

    public HelpPtrPresenter(PtrView ptrView) {
        super(ptrView);
    }

    @Override
    public void onRefresh() {
        Request.getSettingConfigUserHelpList(ListenerAdapter.createArrayListener(ResMineHelpItem.class,
                new UsualDataBackListener<List<ResMineHelpItem>>(getPtrView()) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<ResMineHelpItem> data) {
                        if(isSuccess){
                            getPtrView().setListDatas(data);
                        }
                    }
                }));
    }
}
