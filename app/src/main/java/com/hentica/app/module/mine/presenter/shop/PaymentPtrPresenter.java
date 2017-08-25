package com.hentica.app.module.mine.presenter.shop;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.ConfigKey;
import com.hentica.app.module.entity.index.IndexPayTypeListData;
import com.hentica.app.module.mine.presenter.statistics.AbsPtrPresenter;
import com.hentica.app.util.ArrayListUtil;
import com.hentica.app.util.request.Request;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public class PaymentPtrPresenter extends AbsPtrPresenter<IndexPayTypeListData> {

    public PaymentPtrPresenter(PtrView<IndexPayTypeListData> ptrView) {
        super(ptrView);
    }

    @Override
    public void onRefresh() {
        Request.getSystemConfigGetConfigByKey(ApplicationData.getInstance().getLoginUserId(), ConfigKey.PAYMENTTYPE,
                ListenerAdapter.createArrayListener(IndexPayTypeListData.class, new UsualDataBackListener<List<IndexPayTypeListData>>(getPtrView()) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<IndexPayTypeListData> data) {
                        if (isSuccess) {
                            // TODO 本地屏蔽乐豆支付
                            hideLeBeanLeMind(data);
                            // 请求成功
                            getPtrView().setListDatas(data);
                        }
                    }
                }));
    }

    /** 屏蔽乐豆支付 */
    private void hideLeBeanLeMind(List<IndexPayTypeListData> data){
//        int index = -1;
        Iterator<IndexPayTypeListData> it = data.iterator();
        while (it.hasNext()) {
            IndexPayTypeListData child = it.next();
            if (child.getId() == 4 || child.getId() == 5) {
                it.remove();
            }
        }
//        if(!ArrayListUtil.isEmpty(data)){
//            for(int i = 0; i < data.size(); i++){
//                IndexPayTypeListData pay = data.get(i);
//                if(pay.getId() == 4){
//                    index = i;
//                }
//            }
//        }
//        if(index >= 0){
//            data.remove(index);
//        }
    }

    @Override
    public void onLoadMore() {

    }
}
