package com.hentica.app.util;

import android.app.Activity;
import android.content.Intent;

import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.mine.ui.bank.BankCardAddFragment;
import com.hentica.app.module.mine.ui.bank.RealNameVerificationActivity;

/**
 * Created by Snow on 2017/5/28 0028.
 */

public class VerifyHelper {
    private static VerifyHelper sInstance;

    private Class<? extends UsualFragment> fragment;
    private Intent intent;
    private VerifyHelper(){

    }

    public static VerifyHelper newInstance(){
        if(sInstance != null){
            sInstance = null;
        }
        if(sInstance == null){
            synchronized (VerifyHelper.class){
                if(sInstance == null){
                    sInstance = new VerifyHelper();
                }
            }
        }
        return sInstance;
    }

    public static VerifyHelper getInstance(){
        if(sInstance == null){
            return newInstance();
        }
        return sInstance;
    }

    public static void destory(){
        if(sInstance != null){
            sInstance = null;
        }
    }

    public void initData(Class<? extends UsualFragment> clazz, Intent intent){
        fragment = clazz;
        this.intent = intent;
    }

    public void startFragment(Activity activity, boolean isAuth){
        if(isAuth){
            if(fragment == null){
                return;
            }
            FragmentJumpUtil.toFragment(activity, fragment, intent);
        }else{
            activity.startActivity(new Intent(activity, RealNameVerificationActivity.class));
        }
    }

    public void startFragment(Activity activity, boolean isAuth, boolean ownBankCard){
        if(isAuth){
            if(fragment == null){
                return;
            }
            if(ownBankCard){
                FragmentJumpUtil.toFragment(activity, fragment, intent);
            }else{
                Intent intent = new Intent();
                intent.putExtra(BankCardAddFragment.DATA_IS_DEFAULT, true);
                FragmentJumpUtil.toFragment(activity, BankCardAddFragment.class, intent);
            }
        }else{
            activity.startActivity(new Intent(activity, RealNameVerificationActivity.class));
        }
    }

}
