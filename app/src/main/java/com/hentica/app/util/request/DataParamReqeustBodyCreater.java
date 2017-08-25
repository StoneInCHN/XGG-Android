package com.hentica.app.util.request;

import com.google.gson.Gson;
import com.hentica.app.lib.net.Post;
import com.hentica.app.util.LogUtils;
import com.litesuits.http.data.Consts;
import com.litesuits.http.request.content.HttpBody;
import com.litesuits.http.request.content.StringBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public class DataParamReqeustBodyCreater implements HttpBodyCreater {
    @Override
    public HttpBody createHttpBody(List<Post.ParamNameValuePair> stringParams, List<Post.ParamNameValuePair> fileParams) {
        StringBody body = new StringBody("");
        for(Post.ParamNameValuePair params : stringParams){
            if(params.getName().equals("Request")){
                body = new StringBody(params.getValue());
            }
        }
        body.setContentType(Consts.MIME_TYPE_JSON);
        //打印请求参数
        LogUtils.i("Request", body.getString());
        return body;
    }
}
