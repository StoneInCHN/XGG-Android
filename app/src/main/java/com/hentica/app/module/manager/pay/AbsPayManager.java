package com.hentica.app.module.manager.pay;

import android.app.Activity;

/**
 * Created by Snow on 2017/1/17.
 */

public abstract class AbsPayManager<T extends AbsPayData> implements IPayManager<T> {

    public static String TAG = AbsPayManager.class.getSimpleName();

    protected Activity mActivity;
    protected IPayListener mListener;

    public AbsPayManager(Activity activity){
        this.mActivity = activity;
    }

    @Override
    public void setListener(IPayListener l) {
        mListener = l;
    }
}
