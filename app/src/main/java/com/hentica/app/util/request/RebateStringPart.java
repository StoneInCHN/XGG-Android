package com.hentica.app.util.request;

import com.litesuits.http.data.Consts;
import com.litesuits.http.request.content.multi.StringPart;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/28.18:25
 */

public class RebateStringPart extends StringPart {
    public RebateStringPart(String key, String string) {
        super(key, string, Consts.DEFAULT_CHARSET, Consts.MIME_TYPE_JSON);
    }
}
