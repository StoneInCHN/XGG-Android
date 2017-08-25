package com.hentica.app.module.mine.presenter.shop;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.entity.mine.shop.Photo;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.module.mine.ui.shop.DownPhotoView;
import com.hentica.app.util.request.RebatePost;
import com.hentica.appbase.famework.util.ListUtils;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.request.FileRequest;
import com.litesuits.http.response.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.17:41
 */

public class DownPhotoPresenterImpl implements DownPhotoPresenter {

    public static String TAG = DownPhotoPresenterImpl.class.getSimpleName();

    private DownPhotoView photoView;

    private Context context;

    public DownPhotoPresenterImpl(Context context, DownPhotoView photoView) {
        this.photoView = photoView;
        this.context = context;
    }

    @Override
    public void downImages(final List<String> imgUrls) {
        final List<Photo> result = new ArrayList<>();
        if (ListUtils.isEmpty(imgUrls)) {
            photoView.photoDownloadComplete(result);
            return;
        }
        new AsyncTask<Void, Void, List<Photo>>() {
            @Override
            protected List<Photo> doInBackground(Void... params) {
                for (final String url : imgUrls) {
                    downloadImage(url, new Callback<Photo>() {
                        int i = 0;

                        @Override
                        public void callback(boolean isSuccess, Photo data) {
                            //下载成功
                            result.add(data);
                        }

                        @Override
                        public void onFailed(NetData data) {
                            i++;
                            if (i <= 3) {
                                //失败3次内，重试下载
                                downloadImage(url, this);
                            } else {
                                result.add(new Photo(url));
                            }
                        }
                    });
                }
                while (result.size() < imgUrls.size()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<Photo> photo) {
                super.onPostExecute(photo);
                Log.d(TAG, "onPostExecute: ");
                photoView.photoDownloadComplete(photo);
            }
        }.execute();
    }

    /**
     * 下载图片
     *
     * @param url 网络地址
     * @param l   回调
     */
    private void downloadImage(final String url, final Callback<Photo> l) {
        LiteHttp liteHttp = RebatePost.getLiteHttp();
        if(liteHttp == null){
            return;
        }
        //结果
        final Photo photo = new Photo(url);
        //下载文件名
        String fileName = getFileName(url);
        final String filePath = ApplicationData.getInstance().getTempDir() + "photo/" + fileName;
        //如果文件不存在，下载文件
        File file = new File(filePath);
        if(file.exists()){
            photo.setFilePath(filePath);
            l.callback(true, photo);
            return;
        }
        //创建父目录
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        //下载文件
        final FileRequest request = new FileRequest(url, filePath);
        request.setHttpListener(new HttpListener<File>() {
            @Override
            public void onEnd(Response<File> response) {
                super.onEnd(response);
//                Log.d(TAG, "onEnd: response：\n" + response.getResult().getAbsolutePath());
                if(l != null){
                    photo.setFilePath(filePath);
                    l.callback(true, photo);
                }
            }

            @Override
            public void onStart(AbstractRequest<File> request) {
                super.onStart(request);
                Log.d(TAG, "onStart: ");
            }
        });
        liteHttp.executeAsync(request);
    }

    /**
     * 从url中获取文件名
     * @param url
     * @return
     */
    private String getFileName(String url){
        int index = url.lastIndexOf("/");
        if(index == -1){
            return url;
        }
        String name = url.substring(index + 1);
        return name;
    }

}
