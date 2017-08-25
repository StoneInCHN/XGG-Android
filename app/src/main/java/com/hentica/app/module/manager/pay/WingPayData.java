package com.hentica.app.module.manager.pay;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by YangChen on 2017/5/16 17:16.
 * E-mail:656762935@qq.com
 */

public class WingPayData extends AbsPayData {

    Hashtable<String,String> mParamsHashtable = new Hashtable<>();

    public Hashtable<String, String> getParamsHashtable() {
        return mParamsHashtable;
    }

    public void setParamsHashtable(Hashtable<String, String> mParamsHashtable) {
        this.mParamsHashtable = mParamsHashtable;
    }
}
