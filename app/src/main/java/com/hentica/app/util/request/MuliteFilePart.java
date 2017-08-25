package com.hentica.app.util.request;

import com.litesuits.http.request.content.multi.FilePart;
import com.litesuits.http.utils.StringCodingUtils;

import java.io.File;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/5.10:45
 */

public class MuliteFilePart extends FilePart {
    public MuliteFilePart(String key, File file) {
        super(key, file);
    }

    public MuliteFilePart(String key, File file, String mimeType) {
        super(key, file, mimeType);
    }

    @Override
    protected byte[] createContentDisposition() {
        String dis = "Content-Disposition: form-data; name=\"" + key;
        return StringCodingUtils.getBytes(dis + "\"; filename=\"" + file.getName() + "\"\r\n", infoCharset);
    }
}
