package com.hentica.app.module.down;

import com.hentica.app.lib.util.NetHelper;
import com.hentica.app.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Snow on 2017/5/8.
 */

public class Okhttp3DownManager implements DownloadManager {
    @Override
    public void download(String url, final String savePath, final NetHelper.DownLoadListener l) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if(l != null){
                    l.onOver(false);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                long total = 0;
                long current = 0;
                try {
                    total = response.body().contentLength();
                    File file = new File(savePath);
                    File parent = file.getParentFile();
                    if(!parent.exists()){
                        parent.mkdirs();
                    }
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    inputStream = response.body().byteStream();
                    fileOutputStream = new FileOutputStream(new File(savePath));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                        current += len;
                        LogUtils.i("Apk下载", "progress：" + ((int)(current * 1000 / (float)total)) / 10f + " \tcur：" + current + " \ttotal：" + total);
                        if(l != null){
                            l.update((int)current, (int)total);
                        }
                    }
                    fileOutputStream.flush();
                    if(l != null){
                        l.onOver(true);
                    }
                } catch (IOException e) {
                    LogUtils.i("Apk下载", "IOException");
                    e.printStackTrace();
                    if(l != null){
                        l.onOver(false);
                    }
                } finally{
                    if(inputStream != null){
                        inputStream.close();
                    }
                    if(fileOutputStream != null){
                        fileOutputStream.close();
                    }
                }
                LogUtils.d("Apk下载", "文件下载成功");
            }
        });
    }
}
