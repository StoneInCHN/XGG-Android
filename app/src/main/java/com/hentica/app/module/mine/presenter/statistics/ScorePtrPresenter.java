package com.hentica.app.module.mine.presenter.statistics;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.RebateDataBackListener;
import com.hentica.app.module.common.listener.RebateListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineScore;
import com.hentica.app.module.mine.view.statistics.ScorePtrView;
import com.hentica.app.util.request.Request;
import com.hentica.appbase.famework.util.ListUtils;

import java.util.List;

/**
 * 获取积分
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/24.12:03
 */

public class ScorePtrPresenter extends AbsPtrPresenter<ResMineScore> {

    private ScorePtrView mScorePtrview;

    public ScorePtrPresenter(ScorePtrView ptrView) {
        super(ptrView);
        mScorePtrview = ptrView;
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        //刷新，获取第1页数据
        Request.getEndUserScoreRec(ApplicationData.getInstance().getLoginUserId(),
                String.valueOf(pageNumber),
                String.valueOf(pageSize),
                RebateListenerAdapter.createArrayListener(ResMineScore.class,
                        new RebateDataBackListener<List<ResMineScore>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResMineScore> data) {
                                if(isSuccess){
                                    getPtrView().setListDatas(data);
                                    if(!ListUtils.isEmpty(data)){
                                        pageNumber++;
                                    }
                                }
                            }

                            @Override
                            public void setResult(NetData data) {
                                super.setResult(data);
                                mScorePtrview.setParseScore(data.getErrMsg());
                            }
                        }));
    }

    @Override
    public void onLoadMore() {
        //加载更多
        Request.getEndUserScoreRec(ApplicationData.getInstance().getLoginUserId(),
                String.valueOf(pageNumber),
                String.valueOf(pageSize),
                ListenerAdapter.createArrayListener(ResMineScore.class,
                        new UsualDataBackListener<List<ResMineScore>>(getPtrView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, List<ResMineScore> data) {
                                if(isSuccess){
                                    getPtrView().addListDatas(data);
                                    if(!ListUtils.isEmpty(data)){
                                        pageNumber++;
                                    }
                                }
                            }
                        }));
    }
}
