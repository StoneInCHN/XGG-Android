package com.hentica.app.module.mine.presenter.base;

import com.hentica.app.module.mine.view.shop.IView;

/**
 * Created by Snow on 2017/5/24 0024.
 */

public class AbsPresenter<V extends IView> implements IPresenter<V> {

    private V mView;

    protected V getView(){
        return mView;
    }

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
