package com.hentica.app.module.config;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.net.Post;
import com.hentica.app.module.entity.ConfigData;
import com.hentica.app.module.entity.ConfigKey;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.module.listener.CallbackAdapter;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.request.Request;
import com.hentica.app.util.rsa.RsaUtils;

/**
 * 配置数据工具
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/14.17:11
 */

public class ConfigDataUtl {

    private static ConfigDataUtl mInstance;

    private ConfigDataUtl() {

    }

    /**
     * 获取实例
     *
     * @return
     */
    public static ConfigDataUtl getInstance() {
        if (mInstance == null) {
            synchronized (ConfigDataUtl.class) {
                if (mInstance == null) {
                    mInstance = new ConfigDataUtl();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取rsa加密公钥
     *
     * @param l
     */
    public void getRsaPublicKey(final Callback<String> l) {
        final String publicKey = StorageUtil.getRsaPublickKey();
        if (!TextUtils.isEmpty(publicKey)) {
            if(l != null) {
                RsaUtils.getPublicKey(publicKey);
                l.callback(true, publicKey);
            }
        }
        requestRsaKey(new CallbackAdapter<String>() {
            @Override
            public void callback(boolean isSuccess, String data) {
                if (TextUtils.isEmpty(publicKey) && l != null) {
                    RsaUtils.getPublicKey(data);
                    l.callback(isSuccess, data);
                }
            }
        });
    }

    public void requestRsaKey(final Callback<String> l) {
        Request.getEndUserRsa(new Post.FullListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(long curr, long max) {

            }

            @Override
            public void onResult(NetData result) {
                if (result.isSuccess()) {
                    String key = result.getErrMsg();
                    StorageUtil.saveRsaPublickKey(key);
                    if (l != null) {
                        l.callback(true, key);
                    }
                }
            }
        });
    }

    /**
     * 获取电话
     * @param l
     */
    public void getServicePhone(final Callback<String> l) {
        final String phone = StorageUtil.getServicePhone();
        if (!TextUtils.isEmpty(phone) && l != null) {
                l.callback(true, phone);
        }
        requestServicePhone(new CallbackAdapter<String>() {
            @Override
            public void callback(boolean isSuccess, String data) {
                if(TextUtils.isEmpty(phone) && l != null){
                    l.callback(isSuccess, data);
                }
            }
        });
    }

    private void requestServicePhone(Callback<String> l) {
        //获取客服电话
        Request.getSettingConfigGetConfigByKey(ConfigKey.CUSTOMER_PHONE,
                new Post.FullListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onProgress(long curr, long max) {

                    }

                    @Override
                    public void onResult(NetData result) {
                        if (result.isSuccess()) {
                            ConfigData data = ParseUtil.parseObject(result.getData(), ConfigData.class);
                            ApplicationData.getInstance().setmServicePhone(data.getConfigValue());
                            StorageUtil.saveServicePhone(data.getConfigValue());
                            ApplicationData.getInstance().setmServiceTime(result.getErrMsg());
                            StorageUtil.saveBusinessTime(result.getErrMsg());
                        }
                    }
                });
    }

    /**
     * 获取服务时间
     * @param l
     */
    public void getBusinessTime(final Callback<String> l){
        final String time = StorageUtil.getBusinessTime();
        if(!TextUtils.isEmpty(time) && l != null){
            l.callback(true, time);
        }
        requestServicePhone(new CallbackAdapter<String>() {
            @Override
            public void callback(boolean isSuccess, String data) {
                if(TextUtils.isEmpty(time) && (l != null)){
                    l.callback(isSuccess, data);
                }
            }
        });
    }

}
