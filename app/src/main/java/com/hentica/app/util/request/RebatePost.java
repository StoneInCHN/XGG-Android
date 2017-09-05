package com.hentica.app.util.request;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.json.JNode;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.net.Post;
import com.hentica.app.util.LogUtils;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.request.FileRequest;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.HttpBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理参数为json格式的请求
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/28.17:25
 */

public class RebatePost extends Post {

    private HttpBodyCreater mBodyCreate;

    /**
     * 构造函数，url为将要访问的网址
     *
     * @param url
     * @param create 生成HttpBody，不能为空
     */
    public RebatePost(String url, HttpBodyCreater create) {
        super(url);
        this.mBodyCreate = create;
    }

    @Override
    public void setParams(List<ParamNameValuePair> params) {

        if (mIsClearNullParam) {

            // 去除空参数
            params = clearNullParam(params);
        }
        StringRequest request;
        super.setParams(params);
    }

    /**
     * 解析返回值
     */
    protected NetData parseResult(int httpCode, String content) {
        if (content != null && !TextUtils.isEmpty(content)) {
            LogUtils.i("Response", content);
            NetData netData = new NetData();

            // 解析为json字符串
            JNode resultNode = new JNode(content);
            // 创建请求结果
            netData.setHttpCode(httpCode);
            netData.setResult(content);
            netData.setDataNode(resultNode.getChild("msg"));
            netData.setData(netData.getDataNode().getJsonString());
            netData.setErrCode(resultNode.getChild("code").getInt(-1));
            netData.setErrMsg(resultNode.getChild("desc").getString());
            String token = resultNode.getChild("token").getString();
            if (!TextUtils.isEmpty(token)) {
                ApplicationData.getInstance().setToken(token);
            }
            return netData;
        }
        return null;
    }

    @Override
    protected HttpBody createHttpBody() {
        if (mBodyCreate != null) {
            return mBodyCreate.createHttpBody(mParams, mFileParams);
        }
        return null;
    }

    /**
     * 去除空参数
     */
    protected List<ParamNameValuePair> clearNullParam(List<ParamNameValuePair> oldParams) {

        List<ParamNameValuePair> newParams = new ArrayList<ParamNameValuePair>();

        if (oldParams != null) {

            for (ParamNameValuePair param : oldParams) {

                // 若参数或值为空，则视为无效
                if (!(TextUtils.isEmpty(param.getName()) || TextUtils.isEmpty(param.getValue()))) {

                    newParams.add(param);
                }
            }
        }

        return newParams;
    }

    public static LiteHttp getLiteHttp() {
        return mLiteHttp;
    }

    public static void downloadFile(String fileUrl, String savePath) {
        mLiteHttp.executeAsync(
                new FileRequest(fileUrl, savePath)
                        .setMethod(HttpMethods.Get)
                        .setHttpListener(new HttpListener<File>() {
                            @Override
                            public void onStart(AbstractRequest<File> request) {
                                super.onStart(request);
                                LogUtils.i("onStart", "onStart");
                            }

                            @Override
                            public void onSuccess(File file, Response<File> response) {
                                super.onSuccess(file, response);
                                LogUtils.i("onSuccess", response.toString());
                            }

                            @Override
                            public void onFailure(HttpException e, Response<File> response) {
                                super.onFailure(e, response);
                                LogUtils.i("onFailure", response.toString());
                            }

                            @Override
                            public void onLoading(AbstractRequest<File> request, long total, long len) {
                                super.onLoading(request, total, len);
                                LogUtils.i("onLoading", "Progress：" + (int) ((float) len / total * 1000) / 100f + " \ttotla：" + total + " \tlen：" + len);
                            }
                        }));
    }

}
