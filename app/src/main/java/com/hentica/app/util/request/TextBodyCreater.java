package com.hentica.app.util.request;

import com.hentica.app.lib.net.Post;
import com.hentica.app.util.LogUtils;
import com.litesuits.http.request.content.HttpBody;
import com.litesuits.http.request.content.multi.FilePart;
import com.litesuits.http.request.content.multi.MultipartBody;
import com.litesuits.http.request.content.multi.StringPart;

import java.io.File;
import java.util.List;

/**
 * 请求参数为Text/html格式
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/29.10:12
 */

public class TextBodyCreater implements HttpBodyCreater {
    @Override
    public HttpBody createHttpBody(List<Post.ParamNameValuePair> stringParams,
                                   List<Post.ParamNameValuePair> fileParams) {
        // 组装参数
        MultipartBody httpBody = new MultipartBody();
        // 文本参数
        for (Post.ParamNameValuePair param : stringParams) {

            httpBody.addPart(new StringPart(param.getName(), param.getValue()));
        }

        // 文件参数
        for (Post.ParamNameValuePair filePair : fileParams) {

            httpBody.addPart(new FilePart(filePair.getName(), new File(filePair.getValue())));
        }

        return httpBody;
    }
}
