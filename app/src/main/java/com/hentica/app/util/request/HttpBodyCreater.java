package com.hentica.app.util.request;

import com.hentica.app.lib.net.Post;
import com.litesuits.http.request.content.HttpBody;

import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/29.10:01
 */

public interface HttpBodyCreater {

    /**
     *
     * @param stringParams 字符串参数
     * @param fileParams 文件参数
     * @return
     */
    HttpBody createHttpBody(List<Post.ParamNameValuePair> stringParams, List<Post.ParamNameValuePair> fileParams);

}
