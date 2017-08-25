package com.hentica.app.util.request;

import android.os.Handler;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.entity.ResBankCardInfo;
import com.hentica.app.util.ParseUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.*;
import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public class OkhttpUtils {

    public static void getBankCardInfo(final Handler handler, String keyId, String cardId, final com.hentica.app.module.listener.Callback<ResBankCardInfo> l){

//        ResBankCardInfo data = new ResBankCardInfo();
//        data.setBank("中国工商银行");
//        data.setType("E时代");
//        data.setNature("储蓄卡");
//        data.setKefu("95588");
//        data.setLogo("http://apiserver.qiniudn.com/gongshang.png");
//        data.setInfo("四川省-成都");
//        l.callback(true, data);

        OkHttpClient client = new OkHttpClient();
        String url = String.format("http://detectionBankCard.api.juhe.cn/bankCard?key=%s&cardid=%s", keyId, cardId);
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final NetData netData = new NetData();
                netData.setErrMsg(e.getMessage());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(l != null) {
                            l.onFailed(netData);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               String value = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(value);
                    final NetData netData = new NetData();
                    netData.setErrCode(jsonObject.getInt("error_code"));
                    netData.setErrMsg(jsonObject.getString("reason"));
                    netData.setData(jsonObject.getString("result"));
                    netData.setHttpCode(response.code());
                    String result = jsonObject.getString("result");
                    ResBankCardInfo data = null;
                    if(netData.isSuccess()){
                        data = ParseUtil.parseObject(result, ResBankCardInfo.class);
                    }
                    final ResBankCardInfo finalData = data;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(l != null){
                                if(netData.isSuccess()){
                                    l.callback(netData.isSuccess(), finalData);
                                }else{
                                    l.onFailed(netData);
                                }
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    final NetData netData = new NetData();
                    netData.setErrMsg(e.getMessage());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(l != null){

                                l.onFailed(netData);
                            }
                        }
                    });
                }

            }
        });

    }

}

