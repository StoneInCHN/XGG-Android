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
 * 请求参数为Json格式
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/29.10:08
 */

public class JsonBodyCreater implements HttpBodyCreater {

    @Override
    public HttpBody createHttpBody(List<Post.ParamNameValuePair> stringParams,
                                   List<Post.ParamNameValuePair> fileParams) {
        Map<String, String> paramMap = new HashMap<>();
        // 文本参数
        for (Post.ParamNameValuePair param : stringParams) {
            paramMap.put(param.getName(), param.getValue());
        }
        StringBody body = new StringBody(new Gson().toJson(paramMap));
        body.setContentType(Consts.MIME_TYPE_JSON);
        //打印请求参数
        LogUtils.e("Request", body.getString());
        return body;
    }

}
