package com.hentica.app.module.mine.presenter.statistics;

import android.os.AsyncTask;

import com.hentica.app.framework.fragment.ptr.PtrPresenter;
import com.hentica.app.framework.fragment.ptr.PtrView;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/24.11:58
 */

public class AbsPtrPresenter<T> implements PtrPresenter {

    private PtrView<T> mPtrView;

    protected int pageNumber = 1;

    protected int pageSize = 20;

    public AbsPtrPresenter(PtrView<T> ptrView){
        this.mPtrView = ptrView;
    }

    protected PtrView<T> getPtrView(){
        return mPtrView;
    }

    @Override
    public void onRefresh() {
        // TODO: 2017/3/24  连接服务器获取数据
        new AsyncTask<T, T, T>(){

            @Override
            protected T doInBackground(T... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(T t) {
                super.onPostExecute(t);
                mPtrView.setListDatas(createTestList());
            }
        }.execute();
    }

    @Override
    public void onLoadMore() {
        // TODO: 2017/3/24  连接服务器获取数据
        new AsyncTask<T, T, T>(){

            @Override
            protected T doInBackground(T... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(T t) {
                super.onPostExecute(t);
                mPtrView.addListDatas(createTestList());
            }
        }.execute();
    }

    // TODO: 2017/3/24 创建测试数据，待处理
    private List createTestList(){
        List result = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            result.add("Item：" + i);
        }
        return result;
    }
}
