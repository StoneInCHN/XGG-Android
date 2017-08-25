package com.hentica.app.module.update;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.net.Post;
import com.hentica.app.lib.util.PhoneInfo;
import com.hentica.app.module.entity.ResAppUpdateData;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.request.Request;

/**
 * 查检更新
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/11.20:24
 */

public class CheckUpdatePresenterImpl implements CheckUpdatePresenter<ResAppUpdateData> {

    @Override
    public void checkUpdate(final Callback<ResAppUpdateData> l) {
        //获取版本信息
        Request.getCommonGetAppVersion(String.valueOf(PhoneInfo.getAndroidVersioncode()),
                new Post.FullListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onProgress(long curr, long max) {

                    }

                    @Override
                    public void onResult(NetData result) {
                        if(l != null){
                            ResAppUpdateData updateData = null;
                            if(result.isSuccess()){
                                //成功
                                updateData = ParseUtil.parseObject(result.getData(), ResAppUpdateData.class);
                            }
                            l.callback(result.isSuccess(), updateData);
                        }
                    }
                });
    }
}
